package com.humanid.humanidui.presentation.welcome

import android.app.Dialog
import android.content.DialogInterface
import android.content.Intent
import android.graphics.drawable.Drawable
import android.graphics.drawable.GradientDrawable
import android.graphics.drawable.LayerDrawable
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.util.DisplayMetrics
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.annotation.NonNull
import androidx.annotation.RequiresApi
import androidx.core.content.ContextCompat
import androidx.fragment.app.DialogFragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentTransaction
import com.facebook.*
import com.facebook.FacebookSdk.getApplicationContext
import com.facebook.login.LoginManager
import com.facebook.login.LoginResult
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.humanid.humanidui.R
import com.humanid.humanidui.presentation.HumanIDOptions
import com.humanid.humanidui.util.extensions.onClick
import com.humanid.humanidui.util.extensions.visible
import kotlinx.android.synthetic.main.fragment_bottom_sheet_dialog_welcome.*

/**
 * Created by johnson on 6/13/20.
 */
class WelcomeDialogFragment : BottomSheetDialogFragment() {
    var callbackManager = CallbackManager.Factory.create()
    lateinit var  mProfileTracker : ProfileTracker
    companion object {
        var listener: OnWelcomeDialogListener? = null

        fun newInstance(): WelcomeDialogFragment {
            val fragment = WelcomeDialogFragment()
            val bundle = Bundle()
            fragment.arguments = bundle
            return fragment
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setStyle(DialogFragment.STYLE_NO_FRAME, R.style.AppBottomSheetDialogTheme)
    }

    override fun onCreateView(
            inflater: LayoutInflater, container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View? {

        return inflater.inflate(R.layout.fragment_bottom_sheet_dialog_welcome, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        context?.let {
            val humanIDOptions = HumanIDOptions.fromResource(it)
            if (humanIDOptions?.applicationIcon != -1) {
                humanIDOptions?.applicationIcon?.let { it1 ->
                    imgAppIcon.visible()
                    imgAppIcon.setImageDrawable(ContextCompat.getDrawable(it, it1))
                }
            }

            if (!humanIDOptions?.applicationName.isNullOrEmpty()) {
                tvAppName.visible()
                tvTnC.visible()
                tvAppName.text = humanIDOptions?.applicationName
                tvTnC.text = "I hereby agree to ${humanIDOptions?.applicationName} terms of service"
            }
        }
        FacebookSdk.sdkInitialize(getApplicationContext())
        //login_button_FB.setReadPermissions(listOf("public_profile", "email"))


        login_button_FB.setOnClickListener {

            var isLoggedIn = AccessToken.getCurrentAccessToken() != null && !AccessToken.getCurrentAccessToken().isExpired
            if(isLoggedIn){  Toast.makeText(context, "Logged in", Toast.LENGTH_SHORT).show()
                isLoggedIn= false
                AccessToken.setCurrentAccessToken(null);
                if (LoginManager.getInstance() != null) {
                    LoginManager.getInstance().logOut();

                }}
            else{
            com.facebook.login.LoginManager.getInstance().logInWithReadPermissions(this, listOf("public_profile", "email"))
            //login_button_FB.setFragment(this)
            //var callbackManager = CallbackManager.Factory.create()
            com.facebook.login.LoginManager.getInstance().registerCallback(callbackManager, object : FacebookCallback<LoginResult?> {
                override fun onSuccess(loginResult: LoginResult?) {
                    //Toast.makeText(context, "Success", Toast.LENGTH_LONG).show()
                    if (Profile.getCurrentProfile() != null) {
                        var profile = Profile.getCurrentProfile()
                        //Log.v("facebook - profile", profile.firstName)
                        Toast.makeText(context, profile.getFirstName(), Toast.LENGTH_SHORT).show()
                        Toast.makeText(context, profile.getLastName(), Toast.LENGTH_SHORT).show()
                    } else {
                        //Toast.makeText(context, "Profile is null", Toast.LENGTH_SHORT).show()
                        mProfileTracker = object : ProfileTracker() {
                            override fun onCurrentProfileChanged(oldProfile: Profile?, currentProfile: Profile) {
                                mProfileTracker.stopTracking()
                                Toast.makeText(context, Profile.getCurrentProfile().getFirstName(), Toast.LENGTH_SHORT).show()
                                Toast.makeText(context, Profile.getCurrentProfile().getLastName(), Toast.LENGTH_SHORT).show()
                            }
                        }
                                mProfileTracker.startTracking()




                    }
                }

                override fun onCancel() {
                    Toast.makeText(context, "Login Cancelled", Toast.LENGTH_LONG).show()
                }

                override fun onError(exception: FacebookException) {
                    Toast.makeText(context, exception.message, Toast.LENGTH_LONG).show()
                }
            })
        }
        }




        btnContinue.onClick {
            listener?.onButtonContinueClicked()
            dismiss()
        }

        tvTnC.setOnClickListener {
            startActivity(Intent(Intent.ACTION_VIEW, Uri.parse("https://www.human-id.org/privacypolicy")))
        }
    }

    interface OnWelcomeDialogListener {
        fun onButtonContinueClicked()

    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        //callbackManager.onActivityResult(requestCode, resultCode, data)
        super.onActivityResult(requestCode, resultCode, data)
        if (callbackManager.onActivityResult(requestCode, resultCode, data)) {
            return;
        }
    }

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        val dialog = super.onCreateDialog(savedInstanceState)

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O_MR1) {
            setWhiteNavigationBar(dialog)
        }

        return dialog
    }

    @RequiresApi(api = Build.VERSION_CODES.M)
    private fun setWhiteNavigationBar(@NonNull dialog: Dialog) {
        val window = dialog.window
        if (window != null) {
            val metrics = DisplayMetrics()
            window.windowManager.defaultDisplay.getMetrics(metrics)

            val dimDrawable = GradientDrawable()
            // ...customize your dim effect here

            val navigationBarDrawable = GradientDrawable()
            navigationBarDrawable.shape = GradientDrawable.RECTANGLE
            navigationBarDrawable.setColor(resources.getColor(R.color.colorTwilightBlue))

            val layers = arrayOf<Drawable>(dimDrawable, navigationBarDrawable)

            val windowBackground = LayerDrawable(layers)
            windowBackground.setLayerInsetTop(1, metrics.heightPixels)

            window.setBackgroundDrawable(windowBackground)
        }
    }

    override fun show(manager: FragmentManager, tag: String?) {
        try {
            val ft: FragmentTransaction = manager.beginTransaction()
            ft.add(this, tag)
            ft.commitAllowingStateLoss()
        } catch (e: IllegalStateException) {
            Log.d("TransactionOnShow", "Exception", e)
        }
    }

    override fun onCancel(dialog: DialogInterface) {
        super.onCancel(dialog)
        activity?.finish()
    }
}