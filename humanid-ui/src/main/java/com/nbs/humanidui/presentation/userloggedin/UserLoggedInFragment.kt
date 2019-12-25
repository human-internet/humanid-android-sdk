package com.nbs.humanidui.presentation.userloggedin


import android.app.Dialog
import android.graphics.drawable.Drawable
import android.graphics.drawable.GradientDrawable
import android.graphics.drawable.LayerDrawable
import android.os.Build
import android.os.Bundle
import android.util.DisplayMetrics
import androidx.annotation.RequiresApi
import com.nbs.humanidui.R
import com.nbs.humanidui.base.BaseBottomSheetDialogFragment
import com.nbs.humanidui.presentation.HumanIDOptions
import com.nbs.nucleo.utils.extensions.visible
import io.reactivex.annotations.NonNull
import kotlinx.android.synthetic.main.fragment_user_loggedin.*

class UserLoggedInFragment : BaseBottomSheetDialogFragment() {

    companion object{

        fun newInstance(onButtonSwitchDeviceClickListener: OnButtonSwitchDeviceClickListener): UserLoggedInFragment {
            val fragment = UserLoggedInFragment()
            fragment.onButtonSwitchDeviceClickListener = onButtonSwitchDeviceClickListener
            val bundle = Bundle()
            fragment.arguments = bundle
            return fragment
        }
    }

    private lateinit var onButtonSwitchDeviceClickListener: OnButtonSwitchDeviceClickListener

    override val layoutResource: Int = R.layout.fragment_user_loggedin

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setStyle(R.style.AppBottomSheetDialogTheme, R.style.AppTheme)
    }

    override fun initLib() {

    }

    override fun initIntent() {

    }

    override fun initUI() {
        context?.let {
            val humanIDOptions = HumanIDOptions.fromResource(it)
            if (humanIDOptions?.applicationIcon != -1){
                humanIDOptions?.applicationIcon?.let { it1 ->
                    imgAppIcon.visible()
                    imgAppIcon.setImageResource(it1)
                }
            }

            if (!humanIDOptions?.applicationName.isNullOrEmpty()){
                tvTnC.visible()
                tvTnC.text = "I agree to ${humanIDOptions?.applicationName} Terms of Service"
            }
        }
    }

    override fun initAction() {
        dialog?.setCancelable(true)
        dialog?.setCanceledOnTouchOutside(true)

        btnSwitchDevice.setOnClickListener {
            dismiss()
            onButtonSwitchDeviceClickListener.onButtonSwitchDeviceClicked()
        }
    }

    override fun initProcess() {

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

    interface OnButtonSwitchDeviceClickListener{
        fun onButtonSwitchDeviceClicked()
    }
}
