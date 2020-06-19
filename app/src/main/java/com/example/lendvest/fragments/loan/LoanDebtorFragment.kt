package com.example.lendvest.fragments.loan

import android.content.Context
import android.net.Uri
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import com.example.lendvest.R
import com.example.lendvest.utils.setViewTitle

/**
 * A simple [Fragment] subclass.
 * Activities that contain this fragment must implement the
 * [LoanDebtorFragment.OnFragmentInteractionListener] interface
 * to handle interaction events.
 * Use the [LoanDebtorFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class LoanDebtorFragment : Fragment() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)


    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view =  inflater.inflate(R.layout.fragment_loan_debtor, container, false)

        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setViewTitle(activity=(activity as AppCompatActivity), title="Debtor")
    }

}
