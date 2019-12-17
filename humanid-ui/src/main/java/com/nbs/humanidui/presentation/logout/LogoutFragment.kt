package com.nbs.humanidui.presentation.logout


import android.app.Dialog
import android.graphics.drawable.Drawable
import android.graphics.drawable.GradientDrawable
import android.graphics.drawable.LayerDrawable
import android.os.Build
import android.os.Bundle
import android.util.DisplayMetrics
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.RequiresApi
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.humanid.auth.HumanIDAuth
import com.nbs.humanidui.R
import com.nbs.nucleo.utils.showToast
import io.reactivex.annotations.NonNull
import kotlinx.android.synthetic.main.fragment_logout.*

class LogoutFragment : BottomSheetDialogFragment() {

    companion object{

        fun newInstance(): LogoutFragment {
            val fragment = LogoutFragment()
            val bundle = Bundle()
            fragment.arguments = bundle
            return fragment
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setStyle(R.style.AppBottomSheetDialogTheme, R.style.AppTheme)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {

        return inflater.inflate(R.layout.fragment_logout, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        dialog?.setCancelable(false)
        dialog?.setCanceledOnTouchOutside(false)

        btnLogout.setOnClickListener {
            logout()
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


    private fun logout() {
        btnLogout.isEnabled = false
        btnLogout.text = "Please wait..."

        HumanIDAuth.getInstance().logout()
                .addOnCompleteListener {
                    if (it.isSuccessful){
                        btnLogout.isEnabled = true
                        btnLogout.text = "Logout"
                        dismiss()
                        showToast("You have successfully logout")
                    }else{
                        showToast("Logout failed")
                    }
                }.addOnFailureListener {
                    showToast(it.message.toString())
                }
    }
}
