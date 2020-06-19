package com.example.lendvest.fragments.steps

import android.content.Context
import android.os.Bundle
import android.os.Handler
import android.view.View
import android.widget.SeekBar
import androidx.annotation.LayoutRes
import androidx.annotation.UiThread
import com.example.lendvest.adapters.OnNavigationBarListener
import com.example.lendvest.models.data.Loan
import com.stepstone.stepper.BlockingStep
import com.stepstone.stepper.StepperLayout
import com.stepstone.stepper.VerificationError
import kotlinx.android.synthetic.main.fragment_lending_request.*
import java.math.BigDecimal
import java.text.DecimalFormat


internal class LoanRequestStepFragment(layoutResId: Int) : ButterKnifeFragment(), BlockingStep {

    companion object {

        private const val CLICKS_KEY = "clicks"

        private const val TAP_THRESHOLD = 0

        private const val LAYOUT_RESOURCE_ID_ARG_KEY = "messageResourceId"

        fun newInstance(@LayoutRes layoutResId: Int): LoanRequestStepFragment {
            val args = Bundle()
            args.putInt(LAYOUT_RESOURCE_ID_ARG_KEY, layoutResId)
            val fragment =
                LoanRequestStepFragment(layoutResId)
            fragment.arguments = args
            return fragment
        }
    }

//    @BindView(R.id.button)
//    lateinit var button: Button

    private var i = 0



    private var loan: Loan? = null

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

        // SeekBar Range
        var MIN_LOAN = 100.0
        var MAX_LOAN = 10000.0
        var STEP_LOAN = 10.0


        // SeekBar Range
        var MIN_INTEREST = 0.0
        var MAX_INTEREST = 160
        var STEP_INTEREST = 0.1

        // SeekBar Range
        var MIN_DURATION = 1
        var MAX_DURATION = 10
        var STEP_DURATION = 1

        seek_bar_lending_request_down_payment.max = ((MAX_LOAN - MIN_LOAN) / STEP_LOAN).toInt()

        loan = Loan(1, 1, text_input_lending_request_reason.text.toString(), BigDecimal(100.0), BigDecimal(5.0), numberOfYears = 1)
        seek_bar_lending_request_down_payment.setOnSeekBarChangeListener(object :
            SeekBar.OnSeekBarChangeListener {
            override fun onStopTrackingTouch(seekBar: SeekBar?) {}
            override fun onStartTrackingTouch(seekBar: SeekBar?) {}
            override fun onProgressChanged(
                seekBar: SeekBar?, progress: Int,
                fromUser: Boolean
            ) {
                loan?.amount = BigDecimal(MIN_LOAN + ( progress * STEP_LOAN ) )
                updateFields()

            }
        })

        seek_bar_lending_request_interest.setOnSeekBarChangeListener(object :
            SeekBar.OnSeekBarChangeListener {
            override fun onStopTrackingTouch(seekBar: SeekBar?) {}
            override fun onStartTrackingTouch(seekBar: SeekBar?) {}
            override fun onProgressChanged(
                seekBar: SeekBar?, progress: Int,
                fromUser: Boolean
            ) {
                loan?.interestRate = BigDecimal(MIN_INTEREST + ( progress * STEP_INTEREST ) )
                updateFields()

            }
        })


        seek_bar_lending_request_loan_period.setOnSeekBarChangeListener(object :
            SeekBar.OnSeekBarChangeListener {
            override fun onStopTrackingTouch(seekBar: SeekBar?) {}
            override fun onStartTrackingTouch(seekBar: SeekBar?) {}
            override fun onProgressChanged(
                seekBar: SeekBar?, progress: Int,
                fromUser: Boolean
            ) {
                loan?.numberOfYears = (MIN_DURATION + ( progress * STEP_DURATION ) )
                updateFields()
            }
        })

//        updateNavigationBar()

//        button.text = Html.fromHtml("Taps: <b>$i</b>")
//        button.setOnClickListener {
//            button.text = Html.fromHtml("Taps: <b>${++i}</b>")
//            updateNavigationBar()
//        }
    }

    fun updateFields(){
        val decimalFormat = DecimalFormat("#,###.00")
        val decimalFormatInterest = DecimalFormat(" #,##0.00 '%'")

        text_view_loan_request_loan_amount.text    = "€ ${decimalFormat.format(loan?.amount)}"
        text_view_loan_request_monthly_payment.text = "€ ${decimalFormat.format(loan?.getMonthlyPayment())}"
        text_view_loan_request_payable_amount.text    = "€ ${decimalFormat.format(loan?.getTotalPayment())}"
//        text_view_loan_request_seekbar_amount.text = "€ ${decimalFormat.format(loan?.amount)}"


        text_view_loan_request_seekbar_amount.text    = "€ ${decimalFormat.format(loan?.amount)}"
        text_view_loan_request_seekbar_interest.text = "${decimalFormatInterest.format(loan?.interestRate)}"
        text_view_loan_request_seekbar_duration.text = "${loan?.numberOfYears} Months"


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