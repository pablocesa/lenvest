package com.example.lendvest.fragments.loan


import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.example.lendvest.R
import com.example.lendvest.fragments.SuperFragment
import com.stepstone.stepper.Step
import com.stepstone.stepper.VerificationError


/**
 * A simple [Fragment] subclass.
 */
class LoanStepFragment : SuperFragment(), Step {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_loan_step, container, false)
    }

    override fun verifyStep(): VerificationError? { //return null if the user can go to the next step, create a new VerificationError instance otherwise
        return null
    }

    override fun onSelected() { //update UI when selected
    }

    override fun onError(error: VerificationError) { //handle error inside of the fragment, e.g. show error on EditText
    }


}
