package com.example.lendvest.fragments


import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup

import com.example.lendvest.R
import com.example.lendvest.models.data.Role
import com.example.lendvest.services.session.SessionManager
import kotlinx.android.synthetic.main.fragment_role.*

/**
 * A simple [Fragment] subclass.
 */
class RoleFragment : Fragment() {

    private var sessionManager: SessionManager? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_role, container, false)

        sessionManager = context?.let { SessionManager(it) }

        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        material_button_role_partner.setOnClickListener{
            sessionManager!!.setLoggedIn("1", Role.PARTNER.toString())
        }

        material_button_role_user.setOnClickListener{
            sessionManager!!.setLoggedIn("2", Role.USER.toString())
        }
    }


}
