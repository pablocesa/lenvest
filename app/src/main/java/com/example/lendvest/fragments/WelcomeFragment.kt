package com.example.lendvest.fragments


import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.NavOptions
import androidx.navigation.fragment.findNavController
import com.example.lendvest.R
import com.example.lendvest.activities.MainActivity
import com.example.lendvest.activities.OnLaunchScreenComplete
import com.example.lendvest.models.data.Role
import com.example.lendvest.models.ui.LoginRegistrationType
import com.example.lendvest.models.ui.WelcomeViewModel
import com.example.lendvest.services.session.SessionManager
import com.example.lendvest.utils.getViewModel
import kotlinx.android.synthetic.main.fragment_welcome.*


/**
 * A simple [Fragment] subclass.
 */
class WelcomeFragment : Fragment() {

    private var onLaunchScreenComplete: OnLaunchScreenComplete? =null
    private var sessionManager: SessionManager? = null

    val welcomeViewModel by lazy {
        activity?.getViewModel { WelcomeViewModel() }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view  = inflater.inflate(R.layout.fragment_welcome, container, false)

//        sessionManager = context.getSharedPreferences(Constants.PREF_NAME, Constants.PRIVATE_MODE)

        sessionManager = context?.let { SessionManager(it) }

        return view
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        (activity as MainActivity?)?.hideBottomNav()

//        setViewTitle(activity = (activity as AppCompatActivity), title = "Dashboard")

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

//        if (welcomeType == LoginRegistrationType.DASHBOARD) {
//
//            if (sessionManager!!.getRole() == Role.PARTNER)
//                findNavController().navigate(R.id.dashboardPartnerFragment)
//            else
//                findNavController().navigate(R.id.dashboardUserFragment)
//        }
    }

    private fun setOnLaunchScreenComplete(onLaunchScreenComplete: OnLaunchScreenComplete){
        this.onLaunchScreenComplete = onLaunchScreenComplete
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

//         Check for user authentication
        if(sessionManager!!.isLoggedIn()) {


            findNavController().navigate(R.id.dashboardPartnerFragment)
////            (activity as MainActivity).makeHomeStart() //<---- THIS is the key
//                findNavController().graph.startDestination = R.id.dashboardUserFragment
//                val navOptions = NavOptions.Builder()
//                    .setPopUpTo(R.id.dashboardUserFragment, true)
//                    .build()
//
//                findNavController().navigate(R.id.dashboardUserFragment,null,navOptions)
//
//            } else {
//                findNavController().navigate(R.id.dashboardUserFragment)
//            }
        }
    }

}



