package com.humanid.humanidui.presentation.welcome

import android.app.Dialog
import android.content.Context
import android.content.DialogInterface
import android.content.Intent
import android.content.pm.PackageInfo
import android.content.pm.PackageManager
import android.graphics.Color
import android.graphics.Color.GREEN
import android.graphics.drawable.Drawable
import android.graphics.drawable.GradientDrawable
import android.graphics.drawable.LayerDrawable
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.os.Handler
import android.text.Html
import android.util.DisplayMetrics
import android.util.Log
import android.view.Gravity
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
import com.facebook.FacebookSdk.getApplicationContext
import com.facebook.*
import com.facebook.login.LoginBehavior
import com.facebook.login.LoginManager
import com.facebook.login.LoginResult
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.humanid.humanidui.R
import com.humanid.humanidui.presentation.HumanIDOptions
import com.humanid.humanidui.util.extensions.onClick
import com.humanid.humanidui.util.extensions.visible
import kotlinx.android.synthetic.main.fragment_bottom_sheet_dialog_welcome.*
import java.util.*


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
        //Toast.makeText(, Toast.LENGTH_SHORT).show()
        //logout_now()
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
        //var isLoggedIns = AccessToken.getCurrentAccessToken() != null && !AccessToken.getCurrentAccessToken().isExpired
        var isLoggedIns = false




        fakefb_btn.setOnClickListener {
             Toast.makeText(context, "fakeFb was clicked", Toast.LENGTH_SHORT)
                log_in_click()
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

    fun isPackageInstalled(c: Context, targetPackage: String): Boolean {
        var pm: PackageManager = c.getPackageManager();
        try {
            var info: PackageInfo = pm.getPackageInfo(targetPackage, PackageManager.GET_META_DATA);
        } catch (e: PackageManager.NameNotFoundException) {
            return false;
        }
        return true;
    }

    fun log_in_click(){
        //isLoggedIns = true

        val hasPackageKat = isPackageInstalled(getApplicationContext(), "com.facebook.katana")
        val hasPackageOrc = isPackageInstalled(getApplicationContext(), "com.facebook.orca")

        if(hasPackageKat){
            LoginManager.getInstance().loginBehavior = LoginBehavior.NATIVE_ONLY

        }
        else{LoginManager.getInstance().loginBehavior = LoginBehavior.WEB_ONLY}
        //
        //

        com.facebook.login.LoginManager.getInstance().logInWithReadPermissions(this, listOf("public_profile", "email"))
        //login_button_FB.setFragment(this)
        com.facebook.login.LoginManager.getInstance().registerCallback(callbackManager, object : FacebookCallback<LoginResult?> {
            override fun onSuccess(loginResult: LoginResult?) {

                if (Profile.getCurrentProfile() != null) {
                    //var profile = Profile.getCurrentProfile()


                    var c: Context? = context

                    var name: String = Profile.getCurrentProfile().getFirstName() + " " + Profile.getCurrentProfile().getLastName()
                    //Toast.makeText(context, Profile.getCurrentProfile().getFirstName(), Toast.LENGTH_SHORT).show()

                    val toast_login = Toast.makeText(c, name + " , Login Successful", Toast.LENGTH_LONG)
                    val view: View = toast_login.getView()
                    view.setPadding(20, 20, 20, 20)
                    view.setBackgroundResource(R.color.colorCoolBlue)
                    //view.setTextColor(Color.RED)
                    toast_login.setGravity(Gravity.CENTER, 0, 0)
                    toast_login.show()



                    val handler = Handler()
                    handler.postDelayed(Runnable {
                        var toast = Toast.makeText(c, "Click Upper Right Corner to Logout", Toast.LENGTH_LONG)
                        toast.setGravity(Gravity.TOP, 0, 0)
                        toast.show()
                    }, 7500)

                } else {

                    mProfileTracker = object : ProfileTracker() {
                        override fun onCurrentProfileChanged(oldProfile: Profile?, currentProfile: Profile) {
                            mProfileTracker.stopTracking()
                            var c: Context? = context

                            var name: String = Profile.getCurrentProfile().getFirstName() + " " + Profile.getCurrentProfile().getLastName()
                            //Toast.makeText(context, Profile.getCurrentProfile().getFirstName(), Toast.LENGTH_SHORT).show()

                            val toast_login = Toast.makeText(c, name + " , Login Successful", Toast.LENGTH_LONG)
                            val view: View = toast_login.getView()
                            view.setPadding(20, 20, 20, 20)
                            view.setBackgroundResource(R.color.colorCoolBlue)
                            //view.setTextColor(Color.RED)
                            toast_login.setGravity(Gravity.CENTER, 0, 0)
                            toast_login.show()


                            val handler = Handler()
                            handler.postDelayed(Runnable {
                                var toast = Toast.makeText(c, "Click Upper Right Corner to Logout", Toast.LENGTH_LONG)
                                toast.setGravity(Gravity.TOP, 0, 0)
                                toast.show()
                            }, 7500)

                        }
                    }
                    mProfileTracker.startTracking()


                }

                //btnContinue.visibility = View.GONE
                //login_button_FB.visibility = View.GONE
                //logout_button_FB.visibility = View.VISIBLE
                //logout_button_FB.transformationMethod = null
                activity?.finish()

            }

            override fun onCancel() {
                Toast.makeText(context, "Login Cancelled", Toast.LENGTH_LONG).show()
            }

            override fun onError(exception: FacebookException) {
                //if(exception.message == "Login Attempt Failed."){

                Toast.makeText(context, exception.message, Toast.LENGTH_LONG).show()
            }
        })
    }
    fun logout_now(){

            AccessToken.setCurrentAccessToken(null);
            if (LoginManager.getInstance() != null) {
                LoginManager.getInstance().logOut()
                //LoginManager.getInstance().setLoginBehavior(LoginBehavior.WEB_ONLY)
                //val intent = Intent(activity, HumanIDActivity::class.java)
                //intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
                //startActivity(intent)

            } }
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        //callbackManager.onActivityResult(requestCode, resultCode, data)
        super.onActivityResult(requestCode, resultCode, data)
        if (callbackManager.onActivityResult(requestCode, resultCode, data)) {
            return;
        }

    }

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        val dialog = super.onCreateDialog(savedInstanceState)
        dialog.setCanceledOnTouchOutside(false)
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

    override fun onDestroy() {
        logout_now()
        super.onDestroy()
    }

}