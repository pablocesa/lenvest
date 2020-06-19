package com.example.lendvest.fragments


import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.findNavController
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.lendvest.BR
import com.example.lendvest.R
import com.example.lendvest.adapters.RecyclerBindableAdapter
import com.example.lendvest.models.contracts.NotificationType
import com.example.lendvest.models.data.Expense
import com.example.lendvest.models.ui.OnItemViewButtonClickListener
import com.example.lendvest.utils.MathUtil
import com.example.lendvest.utils.getResIdByName
import com.example.lendvest.utils.setViewTitle
import java.math.BigDecimal
import java.time.LocalDateTime


/**
 * A simple [Fragment] subclass.
 */
class ExpensesFragment : SuperFragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_expenses, container, false)



        createRecyclerView(view)

        return view
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setViewTitle(activity=(getFragmentActivity()), title="Expenses")

    }

    fun createRecyclerView(parentView: View){
        val expenses = ArrayList<Expense>()
        val exTypes = arrayOf("Loan", "Contribution", "Investment")
        val iconTypes = arrayOf(
            context!!.getResIdByName("ic_loan", "drawable"),
            context!!.getResIdByName("ic_contributor", "drawable"),
            context!!.getResIdByName("ic_asset", "drawable")
        )

        for (i in 0 until 5){
            val num = MathUtil.rand(0, 2)
            val id = MathUtil.rand(0, 1000)
            val ownerId = MathUtil.rand(0, 1000)

            val amount: BigDecimal? = MathUtil.rand(
                BigDecimal(20.21).setScale(2, BigDecimal.ROUND_HALF_UP),
                BigDecimal(100.28).setScale(2, BigDecimal.ROUND_HALF_UP)
            )
            expenses.add(
                Expense(
                id.toLong(),
                ownerId.toLong(),
                NotificationType.values()[num],
                "",
                amount,
                LocalDateTime.now(),
                iconTypes[num])
            )
        }


        val recyclerView = parentView.findViewById<RecyclerView>(R.id.recycler_view_analytics_expenses)

        val recyclerViewAdapter = RecyclerBindableAdapter<Expense>(
            R.layout.layout_view_recycler_item_expenses,
            BR.itemViewModel
        )
        recyclerView?.adapter = recyclerViewAdapter
        recyclerViewAdapter.addItems(expenses)
//        recyclerView.addItemDecoration(DividerItemDecoration(context, LinearLayoutManager.VERTICAL))

        recyclerViewAdapter.setOnItemViewClickListener(object :
            OnItemViewButtonClickListener {
            override fun onClick(view: View, position: Int) {
                parentView.findNavController().navigate(R.id.action_expensesFragment_to_expenseDetailsFragment)
            }
        })
    }




}
