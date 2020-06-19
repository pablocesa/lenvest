package com.example.lendvest.fragments


import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toolbar
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.NavOptions
import androidx.navigation.fragment.findNavController
import com.example.lendvest.R
import com.example.lendvest.activities.MainActivity
import com.example.lendvest.models.data.Role
import com.example.lendvest.models.ui.LoginRegistrationType
import com.example.lendvest.models.ui.WelcomeViewModel
import com.example.lendvest.services.session.SessionManager
import com.example.lendvest.utils.getViewModel
import com.google.android.material.button.MaterialButton
import kotlinx.android.synthetic.main.fragment_login.*
import kotlinx.android.synthetic.main.fragment_registration.*

/**
 * A simple [Fragment] subclass.
 */
class LoginFragment : SuperFragment() {

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
        sessionManager = context?.let { SessionManager(it) }

        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        (activity as MainActivity?)?.hideBottomNav()

        val session = SessionManager(getFragmentActivity())

        if(session.isLoggedIn()){
            session.destroy()
        }

        loginButton.setOnClickListener(){
            validate()

            if (radio_btn_login_partner.isChecked)
                session.setLoggedIn("1", Role.PARTNER.toString())
            else
                session.setLoggedIn("2", Role.USER.toString())

            authenticate()
        }


        texr_view_login_link_signup.setOnClickListener(){
            welcomeViewModel?.welcomeState = LoginRegistrationType.REGISTRATION
            findNavController().navigate(R.id.welcomeFragment)
        }


    }


    fun authenticate() {
        if(sessionManager!!.isLoggedIn()) {
            welcomeViewModel?.welcomeState = LoginRegistrationType.DASHBOARD
            findNavController().navigate(R.id.welcomeFragment)
        }
    }


    private fun validate(){
        welcomeViewModel?.welcomeState = LoginRegistrationType.DASHBOARD
    }


}
