package com.humanid.humanidui.presentation.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import com.humanid.humanidui.R
import com.humanid.humanidui.domain.CodeNumber
import kotlinx.android.synthetic.main.item_spinner.view.*

class FlagSpinnerAdapter(
        ctx: Context,
        skins: List<CodeNumber>
) : ArrayAdapter<CodeNumber>(ctx, 0, skins) {

    override fun getView(position: Int, recycledView: View?, parent: ViewGroup): View {
        return this.createView(position, recycledView, parent)
    }

    override fun getDropDownView(position: Int, recycledView: View?, parent: ViewGroup): View {
        return this.createView(position, recycledView, parent)
    }

    private fun createView(position: Int, recycledView: View?, parent: ViewGroup): View {
        val codeNumber = getItem(position) ?: CodeNumber()
        val view = recycledView ?: LayoutInflater.from(context).inflate(
                R.layout.item_spinner,
                parent,
                false
        )

        view.imgFlag.setImageResource(codeNumber.flag)
        view.tvCodeNumber.text = codeNumber.number

        return view
    }
}