package com.example.lendvest.fragments


import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.fragment.findNavController

import com.example.lendvest.R
import com.example.lendvest.activities.OnLaunchScreenComplete
import com.example.lendvest.models.ui.LoginRegistrationType
import com.example.lendvest.models.ui.WelcomeViewModel
import com.example.lendvest.utils.getViewModel
import com.example.lendvest.utils.setViewTitle
import kotlinx.android.synthetic.main.fragment_login.*
import kotlinx.android.synthetic.main.fragment_welcome.*

/**
 * A simple [Fragment] subclass.
 */
class WelcomeFragment : Fragment() {

    private var onLaunchScreenComplete: OnLaunchScreenComplete? =null


    val welcomeViewModel by lazy {
        activity?.getViewModel { WelcomeViewModel() }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_welcome, container, false)
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setViewTitle(activity = (activity as AppCompatActivity), title = "Dashboard")

//        requireActivity().getViewModel{ WelcomeViewModel() }

        val welcomeType = welcomeViewModel?.welcomeState

        material_button_welcome_login.setOnClickListener(){
            findNavController().navigate(R.id.loginFragment)
        }

        material_button_welcome_signup.setOnClickListener(){
            findNavController().navigate(R.id.registrationFragment)
        }

        if (welcomeType == LoginRegistrationType.LOGIN) {
            findNavController().navigate(R.id.loginFragment)
        }

        if (welcomeType == LoginRegistrationType.REGISTRATION) {
            findNavController().navigate(R.id.registrationFragment)
        }

        if (welcomeType == LoginRegistrationType.DASHBOARD) {

            findNavController().navigate(R.id.dashboardFragment)
        }
    }

    private fun setOnLaunchScreenComplete(onLaunchScreenComplete: OnLaunchScreenComplete){
        this.onLaunchScreenComplete = onLaunchScreenComplete
    }


//    private fun getActivity(){
//
//    }



}



