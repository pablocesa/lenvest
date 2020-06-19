package com.example.lendvest.fragments.loan


import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.RadioButton
import androidx.fragment.app.DialogFragment
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import com.example.lendvest.R
import com.example.lendvest.models.ui.PendingLoanOption
import com.example.lendvest.models.ui.SharedViewModel

/**
 * A simple [Fragment] subclass.
 */
class LoanFilterDialogFragment : DialogFragment() {


    private val model: SharedViewModel by activityViewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_loan_filter_dialog, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val radioBtnAll = view.findViewById<RadioButton>(R.id.radio_button_loan_all_pending)
        val radioBtnPersonal = view.findViewById<RadioButton>(R.id.radio_button_loan_my_pending)

        radioBtnAll.setOnClickListener {
            model.setPendingLoanOption(PendingLoanOption.ALL)
            dismiss()
        }
        radioBtnPersonal.setOnClickListener {
            model.setPendingLoanOption(PendingLoanOption.PERSONAL)
            dismiss()
        }
    }


}
