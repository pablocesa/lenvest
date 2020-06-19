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
import kotlinx.android.synthetic.main.fragment_registration.*

/**
 * A simple [Fragment] subclass.
 */
class RegistrationFragment : Fragment() {

    val welcomeViewModel by lazy {
        activity?.getViewModel { WelcomeViewModel() }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_registration, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

       material_button_signup.setOnClickListener{
           validate()
           findNavController().navigate(R.id.welcomeFragment)
       }

        text_view_registration_link_login.setOnClickListener{
            welcomeViewModel?.welcomeState = LoginRegistrationType.LOGIN
            findNavController().navigate(R.id.welcomeFragment)
        }
    }

    private fun validate(){
        welcomeViewModel?.welcomeState = LoginRegistrationType.LOGIN
    }


}
