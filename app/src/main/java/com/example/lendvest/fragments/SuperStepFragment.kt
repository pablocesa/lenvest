package com.example.lendvest.fragments

import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import com.stepstone.stepper.StepperLayout

open class SuperStepFragment: Fragment() {


    fun getFragmentActivity(): AppCompatActivity {
        return activity as AppCompatActivity
    }

    fun getStepperFragmentActivity(): StepperLayout.StepperListener {
        return activity as StepperLayout.StepperListener
    }
}