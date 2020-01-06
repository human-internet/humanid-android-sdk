package com.nbs.humanidui.presentation.registeremail

import android.os.Bundle
import android.text.InputType
import android.view.View
import android.widget.Toast
import com.nbs.humanidui.R
import com.nbs.humanidui.R.drawable
import com.nbs.humanidui.R.string
import com.nbs.humanidui.util.BundleKeys
import com.nbs.humanidui.util.ReactiveFormFragment
import com.nbs.humanidui.util.emptyString
import com.nbs.humanidui.util.enum.LoginType
import com.nbs.humanidui.util.extensions.gone
import com.nbs.humanidui.util.extensions.onClick
import com.nbs.humanidui.util.extensions.visible
import com.nbs.humanidui.util.makeLinks
import com.nbs.humanidui.util.showUncancelableDialog
import com.nbs.humanidui.util.validation.Validation
import com.nbs.humanidui.util.validation.util.notEmptyRule
import kotlinx.android.synthetic.main.fragment_register_email.btnSave
import kotlinx.android.synthetic.main.fragment_register_email.btnSkipEmail
import kotlinx.android.synthetic.main.fragment_register_email.edtEmail
import kotlinx.android.synthetic.main.fragment_register_email.tvAbout
import kotlinx.android.synthetic.main.fragment_register_email.tvMessage
import kotlinx.android.synthetic.main.fragment_register_email.tvSubMessage
import kotlinx.android.synthetic.main.fragment_register_email.tvSuccess
import kotlinx.android.synthetic.main.fragment_register_email.tvTitle
import kotlinx.android.synthetic.main.fragment_register_email.tvWarning

class RegisterEmailFragment : ReactiveFormFragment() {

    companion object {
        var listener: OnRegisterEmailListener? = null

        fun newInstance(type: String = LoginType.NORMAL.type): RegisterEmailFragment {
            val fragment = RegisterEmailFragment()
            val bundle = Bundle()
            bundle.putString(BundleKeys.KEY_REGISTER_EMAIL_TYPE, type)
            fragment.arguments = bundle
            return fragment
        }
    }

    private var registerEmailType = LoginType.NORMAL.type

    override val layoutResource: Int = R.layout.fragment_register_email

    override fun initLib() {
        super.initLib()
        btnSave.isEnabled = false
    }

    override fun initIntent() {
        arguments?.let {
            registerEmailType = it.getString(BundleKeys.KEY_REGISTER_EMAIL_TYPE) ?: emptyString()
        }
    }

    override fun initUI() {
        setSpannableString()

        when (registerEmailType) {
            LoginType.NORMAL.type -> {
            }
            LoginType.OTP_EXPIRED.type -> {
                tvTitle.text = getString(R.string.title_otp_expired)

                tvMessage.text = getString(R.string.message_otp_expired)

                btnSkipEmail.text = getString(R.string.action_wrong_account)

                tvSubMessage.gone()
            }
            LoginType.INPUT_OTP.type -> {

                tvTitle.text = getString(R.string.title_switch_number)

                tvMessage.text = getString(R.string.message_email_confirmation_code)

                edtEmail.hint = getString(R.string.hint_enter_your_code)
                edtEmail.inputType = InputType.TYPE_CLASS_NUMBER

                btnSave.text = getString(R.string.action_enter)

                tvSuccess.gone()

                tvAbout.visible()

                tvSubMessage.gone()

                btnSkipEmail.gone()

                tvWarning.visible()
            }
        }
    }

    override fun initAction() {
        when (registerEmailType) {
            LoginType.NORMAL.type -> {
                btnSave.onClick {
                    listener?.onButtonSaveClicked()

                    showEmailConfirmationDialog()
                }

                btnSkipEmail.onClick {
                    listener?.onButtonSkipEmailClicked()
                }
            }
            LoginType.OTP_EXPIRED.type -> {
                btnSave.onClick {
                    listener?.onButtonSaveClicked()
                }

                btnSkipEmail.onClick {
                    listener?.onButtonWrongAccountClicked()

                    val subMessage = getString(R.string.message_register_number_dialog)
                    val phoneNumber = String.format(getString(R.string.format_phone_number_dialog, getString(R.string.sample_phone_number)))

                    showRegisterNumberDialog(subMessage, phoneNumber)
                }
            }
            LoginType.INPUT_OTP.type -> {
                btnSave.onClick {
                    listener?.onButtonSaveClicked()

                    showChangeNumberDialog()
                }
            }
        }
    }


    override fun initProcess() {

    }

    override fun setupFormValidation() {
//        when(registerEmailType){
//            LoginType.NORMAL.type, LoginType.OTP_EXPIRED.type -> {
//                addValidation(
//                        Validation(
//                                edtEmail,
//                                listOf(notEmptyRule(getString(R.string.error_field_required)),
//                                        emailRule(getString(R.string.error_email_format)))
//                        )
//                )
//            }
//            LoginType.INPUT_OTP.type -> {
                addValidation(
                        Validation(
                                edtEmail,
                                listOf(notEmptyRule(getString(string.error_field_required)))
                        )
                )
//            }
//        }

    }

    override fun onValidationFailed() {
        btnSave.isEnabled = false
    }

    override fun onValidationSuccess() {
        btnSave.isEnabled = true
    }

    private fun showEmailConfirmationDialog() {
        context?.let {
            showUncancelableDialog(
                    context = it,
                    message = getString(string.message_email_dialog),
                    email = getString(string.sample_email),
                    subMessage = getString(string.sub_message_email_dialog),
                    first = getString(string.action_close_and_return),
                    firstListener = {

                    }
            )
        }
    }

    private fun showRegisterNumberDialog(subMessage: String, phoneNumber: String) {
        context?.let {
            showUncancelableDialog(
                    context = it,
                    message = ("$subMessage$phoneNumber."),
                    subMessage = getString(string.sub_message_register_dialog),
                    first = getString(string.action_create_new_account),
                    firstListener = {

                    },
                    second = getString(string.action_recover_account),
                    secondListener = {

                    }
            )
        }
    }

    private fun showChangeNumberDialog() {
        context?.let {
            showUncancelableDialog(
                    it,
                    icon = drawable.ic_success_change_number,
                    title = getString(string.title_change_number),
                    message = getString(string.message_change_number),
                    first = getString(string.action_return),
                    firstListener = {

                    }
            )
        }
    }

    private fun setSpannableString() {
        tvAbout.text = getString(R.string.message_humanid_description)
        tvAbout.makeLinks(
                Pair(getString(R.string.label_learn_about_out_mission), View.OnClickListener {
                    Toast.makeText(context, getString(R.string.label_learn_about_out_mission), Toast.LENGTH_SHORT).show()
                }))
    }

    interface OnRegisterEmailListener {
        fun onButtonSkipEmailClicked()
        fun onButtonWrongAccountClicked()
        fun onButtonSaveClicked()
    }
}

