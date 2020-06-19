package com.example.lendvest.fragments.steps

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.annotation.LayoutRes
import butterknife.BindView

import com.stepstone.stepper.StepperLayout
import com.stepstone.stepper.VerificationError

import butterknife.ButterKnife
import com.example.lendvest.R
import com.example.lendvest.adapters.OnNavigationBarListener
import com.example.lendvest.adapters.SampleFragmentStepAdapter
import com.example.lendvest.fragments.SuperFragment

abstract class AbstractStepperFragment : SuperFragment(), StepperLayout.StepperListener,
    OnNavigationBarListener {

    companion object {
        private const val CURRENT_STEP_POSITION_KEY = "position"
    }

    @BindView(R.id.stepperLayout)
    lateinit var stepperLayout: StepperLayout

    @get:LayoutRes
    protected abstract val layoutResId: Int

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(layoutResId, container, false)


        ButterKnife.bind(getFragmentActivity())
        val startingStepPosition = savedInstanceState?.getInt(CURRENT_STEP_POSITION_KEY) ?: 0
        stepperLayout = view.findViewById<View>(R.id.stepperLayout) as StepperLayout
        stepperLayout.setAdapter(SampleFragmentStepAdapter(getFragmentActivity().supportFragmentManager, context), startingStepPosition)

        stepperLayout.setListener(getFragmentActivity() as StepperLayout.StepperListener)

        return view
    }


    override fun onSaveInstanceState(outState: Bundle) {
        outState.putInt(CURRENT_STEP_POSITION_KEY, stepperLayout.currentStepPosition)
        super.onSaveInstanceState(outState)
    }

    override fun onBackPressed() {
        val currentStepPosition = stepperLayout.currentStepPosition
        if (currentStepPosition > 0) {
            stepperLayout.onBackClicked()
        } else {
            getFragmentActivity().finish()
        }
    }

    override fun onCompleted(completeButton: View) {
        Toast.makeText(context, "onCompleted!", Toast.LENGTH_SHORT).show()
    }

    override fun onError(verificationError: VerificationError) {
        Toast.makeText(context, "onError! -> " + verificationError.errorMessage, Toast.LENGTH_SHORT).show()
    }

    override fun onStepSelected(newStepPosition: Int) {
        Toast.makeText(context, "onStepSelected! -> " + newStepPosition, Toast.LENGTH_SHORT).show()
    }

    override fun onReturn() {
        getFragmentActivity().finish()
    }

    override fun onChangeEndButtonsEnabled(enabled: Boolean) {
        stepperLayout.setNextButtonVerificationFailed(!enabled)
        stepperLayout.setCompleteButtonVerificationFailed(!enabled)
    }



}