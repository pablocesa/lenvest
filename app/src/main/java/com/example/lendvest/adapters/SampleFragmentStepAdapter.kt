package com.example.lendvest.adapters

import android.content.Context
import androidx.annotation.IntRange
//import androidx.support.annotation.IntRange
import androidx.fragment.app.FragmentManager
import com.example.lendvest.R
import com.example.lendvest.fragments.steps.LoanRequestStepFragment
import com.example.lendvest.fragments.steps.StepFragmentSample
import com.example.lendvest.fragments.steps.StepperFeedbackStepFragment

import com.stepstone.stepper.Step
import com.stepstone.stepper.adapter.AbstractFragmentStepAdapter
import com.stepstone.stepper.viewmodel.StepViewModel

class SampleFragmentStepAdapter(fm: FragmentManager, context: Context?) : AbstractFragmentStepAdapter(fm,
    context!!
) {

    private var position: Int = 0

    override fun getViewModel(@IntRange(from = 0) position: Int): StepViewModel {
        val titles = arrayOf("Lending request", "Supporting Documents", "Summary", "Confirmation")
        val builder = StepViewModel.Builder(context)
            .setTitle(titles[position])
//        if (position == 1) {
//            builder.setSubtitle("Optional")
//        }
        return builder
            .create()
    }

    override fun createStep(position: Int): Step {
        when (position) {
            0 -> return LoanRequestStepFragment.newInstance(R.layout.fragment_lending_request)
            1 -> return StepFragmentSample.newInstance(R.layout.fragment_lending_supporting_documents)
            2 -> return StepFragmentSample.newInstance(R.layout.fragment_lending_summary)
            3 -> return StepFragmentSample.newInstance(R.layout.fragment_lending_confirmation)
            else -> throw IllegalArgumentException("Unsupported position: " + position)
        }
        this.position = position
    }
//override fun createStep(position: Int): Step {
//    return StepperFeedbackStepFragment.newInstance(position)
//}
    override fun getCount(): Int {
        return 4
    }
}