package com.example.lendvest.fragments


import GenericRecyclerAdapter
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.lendvest.R
import com.example.lendvest.models.contracts.LoanDisplayType
import com.example.lendvest.models.data.Loan
import com.example.lendvest.models.ui.ItemViewModel
import com.example.lendvest.models.ui.OnItemViewClickListener
import com.example.lendvest.utils.MathUtil
import com.example.lendvest.utils.getResIdByName
import com.example.lendvest.utils.setViewTitle
import java.math.BigDecimal
import java.time.LocalDateTime

/**
 * A simple [Fragment] subclass.
 */
class InvestmentFragment : Fragment() {

    private var investments : ArrayList<ItemViewModel> = ArrayList()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view =  inflater.inflate(R.layout.fragment_investment, container, false)

        val recyclerView = view.findViewById<RecyclerView>(R.id.recycler_view_investment)

        val recyclerViewAdapter: GenericRecyclerAdapter<ItemViewModel> =
            GenericRecyclerAdapter(R.layout.layout_view_recycler_item_investment)

        recyclerView?.layoutManager = LinearLayoutManager(context)
        recyclerView?.adapter = recyclerViewAdapter
        setLoanData()
        recyclerViewAdapter.addItems(investments)

        recyclerViewAdapter.setOnListItemViewClickListener(object :
            OnItemViewClickListener {
            override fun onClick(view: View, position: Int) {
                view.findNavController().navigate(R.id.action_investmentFragment_to_investmentDetailFragment)
            }
        })

       return view
    }

    private fun setLoanData() {
        val expenseNames = arrayOf("Small business", "Land", "Bus")
        investments.clear()

        for (i in 0 until 7){

            val id = MathUtil.rand(1, 1000)
            val num = MathUtil.rand(0, 2)
            val userId = MathUtil.rand(1, 1000)
            var name = expenseNames[num]

            val amount: BigDecimal? = MathUtil.rand(
                BigDecimal(20.21).setScale(2, BigDecimal.ROUND_HALF_UP),
                BigDecimal(100.28).setScale(2, BigDecimal.ROUND_HALF_UP)
            )
            val interest: BigDecimal? = MathUtil.rand(
                BigDecimal(1.02).setScale(2, BigDecimal.ROUND_HALF_UP),
                BigDecimal(7.99).setScale(2, BigDecimal.ROUND_HALF_UP)
            )
            investments.add(Loan(id.toLong(), userId.toLong(), name, amount, interest, LocalDateTime.now(), context!!.getResIdByName("ic_loan", "drawable")))

        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setViewTitle(activity=(activity as AppCompatActivity), title="Investment")
    }


}
