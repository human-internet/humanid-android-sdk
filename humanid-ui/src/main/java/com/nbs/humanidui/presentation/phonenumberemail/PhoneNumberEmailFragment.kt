package com.nbs.humanidui.presentation.phonenumberemail

import android.os.Bundle
import android.view.View
import android.widget.Toast
import com.human.android.util.ReactiveFormFragment
import com.human.android.util.extensions.isEnabled
import com.nbs.humanidui.util.makeLinks
import com.nbs.humanidui.R
import com.nbs.humanidui.domain.CodeNumber
import com.nbs.humanidui.presentation.adapter.SpinnerAdapter
import com.nbs.validacion.Validation
import com.nbs.validacion.util.emailRule
import com.nbs.validacion.util.notEmptyRule
import com.nbs.validacion.util.numberOnlyRule
import com.nbs.validacion.util.onClick
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

            spinnerCodeNumber.adapter = SpinnerAdapter(
                    it,
                    getCodeNumberData()
            )
        }

    }

    override fun initAction() {
        btnEnterEmail.onClick {
            listener?.onButtonEnterClicked()
        }
    }

    override fun initProcess() {

    }

    override fun setupFormValidation() {
        addValidation(
                Validation(
                        edtPhoneNumber,
                        listOf(notEmptyRule(getString(R.string.error_field_required)),
                                numberOnlyRule(getString(R.string.error_number_format)))
                )
        )

        addValidation(
                Validation(
                        edtEmail,
                        listOf(notEmptyRule(getString(R.string.error_field_required)),
                                emailRule(getString(R.string.error_email_format)))
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