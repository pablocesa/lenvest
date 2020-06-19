package com.example.lendvest.fragments.steps

import android.app.Dialog
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.LayoutRes
import androidx.fragment.app.Fragment

import butterknife.ButterKnife
import com.example.lendvest.R
import com.example.lendvest.fragments.SuperFragment
import com.example.lendvest.fragments.dialogs.DialogSignaturePadFragment
import kotlinx.android.synthetic.main.fragment_lending_summary.*


internal abstract class ButterKnifeFragment : SuperFragment() {

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater!!.inflate(layoutResId, container, false)

        ButterKnife.bind(this, view)
        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        if(layoutResId == R.layout.fragment_lending_summary){
            cardview_lending_summary_signature.setOnClickListener{
                val dialog = DialogSignaturePadFragment()
                dialog.show(getFragmentActivity().supportFragmentManager, "")
            }
        }
    }

    @get:LayoutRes
    protected abstract val layoutResId: Int

}