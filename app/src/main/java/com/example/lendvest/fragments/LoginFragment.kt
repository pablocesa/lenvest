package com.example.lendvest.fragments


import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import com.example.lendvest.R
import com.example.lendvest.models.ui.LoginRegistrationType
import com.example.lendvest.models.ui.WelcomeViewModel
import com.example.lendvest.utils.getViewModel
import com.google.android.material.button.MaterialButton
import kotlinx.android.synthetic.main.fragment_login.*
import kotlinx.android.synthetic.main.fragment_registration.*

/**
 * A simple [Fragment] subclass.
 */
class LoginFragment : Fragment() {

    lateinit var loginButton: MaterialButton


    val welcomeViewModel by lazy {
        activity?.getViewModel { WelcomeViewModel() }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view =  inflater.inflate(R.layout.fragment_login, container, false)

        loginButton = view.findViewById(R.id.material_button_login)

        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)


        loginButton.setOnClickListener(){
            validate()
            findNavController().navigate(R.id.welcomeFragment)
        }


        texr_view_login_link_signup.setOnClickListener(){
            welcomeViewModel?.welcomeState = LoginRegistrationType.REGISTRATION
            findNavController().navigate(R.id.welcomeFragment)
        }


    }


    private fun validate(){
        welcomeViewModel?.welcomeState = LoginRegistrationType.DASHBOARD
    }


}
