package com.example.lendvest.fragments.loan


import android.graphics.drawable.BitmapDrawable
import android.graphics.drawable.Drawable
import android.opengl.Visibility
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.core.content.res.ResourcesCompat
import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.RecyclerView
import com.example.lendvest.BR
import com.example.lendvest.R
import com.example.lendvest.adapters.RecyclerBindableAdapter
import com.example.lendvest.fragments.FileDataFragment
import com.example.lendvest.fragments.SuperFragment
import com.example.lendvest.fragments.dialogs.DialogSignaturePadFragment
import com.example.lendvest.models.data.FileData
import com.example.lendvest.models.data.Role
import com.example.lendvest.models.ui.OnItemViewClickListener
import com.example.lendvest.models.ui.SharedViewModel
import com.example.lendvest.services.session.SessionManager
import com.example.lendvest.utils.getResIdByName
import com.example.lendvest.utils.setViewTitle
import kotlinx.android.synthetic.main.fragment_loan_pending.*

/**
 * A simple [Fragment] subclass.
 */
class LoanPendingFragment : SuperFragment() {

    lateinit var fileDataList:   ArrayList<FileData>
    lateinit var recyclerView: RecyclerView
    private val model: SharedViewModel by activityViewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view =  inflater.inflate(R.layout.fragment_loan_pending, container, false)

        sessionManager = context?.let { SessionManager(it) }

        recyclerView = view.findViewById(R.id.recycler_view_loan_pending_documents)

        val recyclerViewAdapter = RecyclerBindableAdapter<FileData>(
            R.layout.layout_view_recycler_item_docs,
            BR.itemViewModel
        )

        fileDataList = ArrayList()

        fileDataList.add(FileData(1, "Passport photo", R.drawable.kreevs, getDrawable(R.drawable.kreevs)))
        fileDataList.add(FileData(1, "ID / Passport", R.drawable.employee_id, getDrawable(R.drawable.employee_id)))
        fileDataList.add(FileData(2, "Bank statement", R.drawable.bank_statement_sample1, getDrawable(R.drawable.bank_statement_sample1)))
        fileDataList.add(FileData(3, "Pay slips", R.drawable.account_statement_lg, getDrawable(R.drawable.account_statement_lg)))
        fileDataList.add(FileData(4, "Employment letter", R.drawable.account_statement_lg, getDrawable(R.drawable.account_statement_lg)))

        recyclerView?.adapter = recyclerViewAdapter

        recyclerViewAdapter.addItems(fileDataList)


        recyclerViewAdapter.setOnListItemViewClickListener(object :
            OnItemViewClickListener {
            override fun onClick(view: View, position: Int) {
                val bundle = bundleOf("IMAGE_RESOURCE_ID" to fileDataList[position].resourceId)
                findNavController().navigate(R.id.action_loanPendingFragment_to_fileDataFragment, bundle)
            }
        })

        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setViewTitle(activity=(activity as AppCompatActivity), title="Loan Request")

        if (sessionManager!!.getRole() === Role.USER) {
            card_view_loan_pending_decision.visibility = View.GONE
        }else {
            image_view_loan_pending_signature.setOnClickListener {
                val dialog = DialogSignaturePadFragment()
                dialog.show(getFragmentActivity().supportFragmentManager, "")
            }

            button_pending_loan_reject.setOnClickListener {
                getFragmentActivity().supportFragmentManager.popBackStack()
                super.onBackPressed()
            }


            button_pending_loan_propose.setOnClickListener {
                getFragmentActivity().supportFragmentManager.popBackStack()
                super.onBackPressed()
            }


            button_pending_loan_approve.setOnClickListener {
                getFragmentActivity().supportFragmentManager.popBackStack()
                super.onBackPressed()
            }
        }
    }


}
