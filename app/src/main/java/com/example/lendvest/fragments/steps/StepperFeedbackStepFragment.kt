package com.example.lendvest.fragments.steps

import android.os.Bundle
import android.os.Handler
import android.view.View
import android.widget.TextView
import androidx.annotation.LayoutRes
import androidx.annotation.UiThread
import butterknife.BindView
import com.example.lendvest.R
import com.stepstone.stepper.BlockingStep
import com.stepstone.stepper.StepperLayout
import com.stepstone.stepper.VerificationError

internal class StepperFeedbackStepFragment(override val layoutResId: Int) : ButterKnifeFragment(), BlockingStep {

//    private var fragmentIds: ArrayList<Int> = ArrayList()
    private var resourceId: Int = 0

    companion object {

        private const val STEP_POSITION = "step_position"

        fun newInstance(stepPosition: Int): StepperFeedbackStepFragment {

            val resourceIds = intArrayOf(R.layout.fragment_lending_request, R.layout.fragment_lending_supporting_documents, R.layout.fragment_lending_summary, R.layout.fragment_lending_confirmation)

            val fragment = StepperFeedbackStepFragment(resourceIds[stepPosition])
            val arguments = Bundle()
            arguments.putInt(STEP_POSITION, stepPosition)
            fragment.arguments = arguments
            return fragment
        }
    }

    @BindView(R.id.stepContent)
    lateinit var stepContent: TextView

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        stepContent.text = "Step content #${arguments!!.getInt(STEP_POSITION)} \n ${getString(R.string.lorem_ipsum)}"

    }

    override fun verifyStep(): VerificationError? {
        return null
    }

    override fun onSelected() {}

    override fun onError(error: VerificationError) {}

    @UiThread
    override fun onNextClicked(callback: StepperLayout.OnNextClickedCallback) {
        callback.stepperLayout.showProgress("Operation in progress, please wait...")
        Handler().postDelayed({
            callback.goToNextStep()
            callback.stepperLayout.hideProgress()
        }, 1)
    }

    override fun onCompleteClicked(callback: StepperLayout.OnCompleteClickedCallback) {
        callback.complete()
    }

    @UiThread
    override fun onBackClicked(callback: StepperLayout.OnBackClickedCallback) {
        callback.goToPrevStep()
    }

//    override val layoutResId: Int
//        get() = id

}