package com.nbs.humanidui.presentation.phonenumberemail

import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.core.content.res.ResourcesCompat
import com.nbs.humanidui.util.ReactiveFormFragment
import com.nbs.humanidui.util.extensions.isEnabled
import com.nbs.humanidui.util.makeLinks
import com.nbs.humanidui.R
import com.nbs.humanidui.R.string
import com.nbs.humanidui.domain.CodeNumber
import com.nbs.humanidui.util.validation.Validation
import com.nbs.humanidui.util.validation.util.onClick
import com.nbs.humanidui.util.validation.util.onTextChange
import kotlinx.android.synthetic.main.fragment_phone_number_email.*

class PhoneNumberEmailFragment : ReactiveFormFragment() {

    companion object{
        var listener: OnPhoneEmailListener? = null

        fun newInstance(): PhoneNumberEmailFragment {
            val fragment = PhoneNumberEmailFragment()
            val bundle = Bundle()
            fragment.arguments = bundle
            return fragment
        }
    }

    override val layoutResource: Int = R.layout.fragment_phone_number_email

    override fun initLib() {
        super.initLib()
        btnEnterEmail.isEnabled(false)
    }

    override fun initIntent() {

    }

    override fun initUI() {
        context?.let {
            setSpannableString()
        }

    }

    override fun initAction() {
        btnEnterEmail.onClick {
            listener?.onButtonEnterClicked()
        }

        val typface = context?.let { ResourcesCompat.getFont(it, R.font.roboto_bold) }
        ccpPhoneNumber.setTypeFace(typface)

        edtPhoneNumber.onTextChange {text->
            text.let {
                if (it.startsWith("0")) {
                    if (it.isNotEmpty()) {
                        edtPhoneNumber.setText(it.substring(1))
                    } else {
                        edtPhoneNumber.setText("")
                    }
                }
            }
        }
    }

    override fun initProcess() {

    }

    override fun setupFormValidation() {
        addValidation(
                Validation(
                        edtPhoneNumber,
                        listOf(
                            com.nbs.humanidui.util.validation.util.notEmptyRule(getString(string.error_field_required)),
                            com.nbs.humanidui.util.validation.util.numberOnlyRule(getString(string.error_number_format))
                        )
                )
        )

        addValidation(
                Validation(
                        edtEmail,
                        listOf(
                            com.nbs.humanidui.util.validation.util.notEmptyRule(getString(string.error_field_required)),
                            com.nbs.humanidui.util.validation.util.emailRule(getString(string.error_email_format))
                        )
                )
        )
    }

    override fun onValidationFailed() {
        btnEnterEmail.isEnabled(false)
    }

    override fun onValidationSuccess() {
        btnEnterEmail.isEnabled(true)
    }

    private fun getCodeNumberData(): List<CodeNumber> {
        val skinData = mutableListOf<CodeNumber>()

        val codeNumbers = resources.getStringArray(R.array.list_of_code_number)
        val flags = resources.obtainTypedArray(R.array.list_of_flag)

        codeNumbers.forEachIndexed { i, _ ->
            skinData.add(
                    CodeNumber(
                            codeNumbers[i],
                            flags.getResourceId(i, 0)
                    )
            )
        }
        flags.recycle()
        return skinData
    }

    private fun setSpannableString() {
        btnLearnOurMission.text = getString(R.string.message_humanid_description)

        btnLearnOurMission.makeLinks(
                Pair(getString(R.string.label_learn_about_out_mission), View.OnClickListener {
                    Toast.makeText(context, getString(R.string.label_learn_about_out_mission), Toast.LENGTH_SHORT).show()
                }))
    }

    interface OnPhoneEmailListener {
        fun onButtonEnterClicked()
    }

}