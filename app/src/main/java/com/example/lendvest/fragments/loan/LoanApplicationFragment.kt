package com.example.lendvest.fragments.loan


import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import com.example.lendvest.R
import com.example.lendvest.fragments.steps.AbstractStepperFragment
import com.example.lendvest.utils.setViewTitle


/**
 * A simple [Fragment] subclass.
 */
class LoanApplicationFragment : AbstractStepperFragment() {

    override val layoutResId: Int
        get() = R.layout.activity_custom_stepper_layout_theme

//    override fun onCreateView(
//        inflater: LayoutInflater, container: ViewGroup?,
//        savedInstanceState: Bundle?
//    ): View? {
//        // Inflate the layout for this fragment
//        val view = inflater.inflate(R.layout.fragment_loan_application, container, false)
//        val context = context
//
//
////        mStepperLayout = view.findViewById<View>(R.id.stepperLayout) as StepperLayout
////        val context = context
////        mStepperLayout!!.setListener(getFragmentActivity())
//////        mStepperLayout!!.setAdapter(MyStepAdapter(supportFragmentManager, this))
////        mStepperLayout!!.adapter = SampleFragmentStepAdapter(getFragmentActivity().supportFragmentManager, context)
//
//        return view
//    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setViewTitle(activity=(activity as AppCompatActivity), title="Loan Application")
    }




}
