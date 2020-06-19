package com.example.lendvest.fragments.steps

import android.content.Context
import android.os.Bundle
import android.os.Handler
import android.view.View
import androidx.annotation.LayoutRes
import androidx.annotation.UiThread
import com.example.lendvest.adapters.OnNavigationBarListener
import com.stepstone.stepper.BlockingStep
import com.stepstone.stepper.Step
import com.stepstone.stepper.StepperLayout
import com.stepstone.stepper.VerificationError

internal class StepFragmentSample : ButterKnifeFragment(), BlockingStep {

    companion object {

        private const val CLICKS_KEY = "clicks"

        private const val TAP_THRESHOLD = 0

        private const val LAYOUT_RESOURCE_ID_ARG_KEY = "messageResourceId"

        fun newInstance(@LayoutRes layoutResId: Int): StepFragmentSample {
            val args = Bundle()
            args.putInt(LAYOUT_RESOURCE_ID_ARG_KEY, layoutResId)
            val fragment = StepFragmentSample()
            fragment.arguments = args
            return fragment
        }
    }

//    @BindView(R.id.button)
//    lateinit var button: Button

    private var i = 0

    private var onNavigationBarListener: OnNavigationBarListener? = null

    override fun onAttach(context: Context) {
        super.onAttach(context)
        if (context is OnNavigationBarListener) {
            onNavigationBarListener = context
        }
    }

    @Suppress("DEPRECATION")
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        if (savedInstanceState != null) {
            i = savedInstanceState.getInt(CLICKS_KEY)
        }

//        updateNavigationBar()

//        button.text = Html.fromHtml("Taps: <b>$i</b>")
//        button.setOnClickListener {
//            button.text = Html.fromHtml("Taps: <b>${++i}</b>")
//            updateNavigationBar()
//        }
    }

    override val layoutResId: Int
        get() = arguments!!.getInt(LAYOUT_RESOURCE_ID_ARG_KEY)

    override fun verifyStep(): VerificationError? {
        return if (isAboveThreshold) null else VerificationError("Click ${TAP_THRESHOLD - i} more times!")
    }
//
    private val isAboveThreshold: Boolean
        get() = i >= TAP_THRESHOLD
//
    override fun onSelected() {
        updateNavigationBar()
    }
//
    override fun onError(error: VerificationError) {
//        button.startAnimation(AnimationUtils.loadAnimation(activity, R.anim.shake_error))
    }

    private fun updateNavigationBar() {
        onNavigationBarListener?.onChangeEndButtonsEnabled(isAboveThreshold)
    }
//
//    override fun onSaveInstanceState(outState: Bundle) {
//        outState!!.putInt(CLICKS_KEY, i)
//        super.onSaveInstanceState(outState)
//    }

/***********************************************************************************/

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

//    override fun onSelected() {
//        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
//    }


}