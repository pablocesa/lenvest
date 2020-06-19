package com.example.tripfi.adapters


import GenericRecyclerAdapter
import android.content.Context
import android.view.LayoutInflater
import android.view.View
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import android.view.ViewGroup
import androidx.navigation.findNavController
import androidx.viewpager.widget.ViewPager.OnPageChangeListener
import com.example.lendvest.R
import com.example.lendvest.adapters.GenericPagerAdapter
import com.example.lendvest.models.contracts.LoanDisplay
import com.example.lendvest.models.contracts.LoanDisplayType
import com.example.lendvest.models.data.Loan
import com.example.lendvest.models.data.Role
import com.example.lendvest.models.data.User
import com.example.lendvest.models.ui.ItemViewModel
import com.example.lendvest.models.ui.OnItemViewClickListener
import com.example.lendvest.services.session.SessionManager
import com.example.lendvest.utils.MathUtil
import com.example.lendvest.utils.getResIdByName
import java.math.BigDecimal
import java.time.LocalDateTime


@Suppress("DEPRECATION")
class LoanAdapter(context: Context, sessionManager: SessionManager, internal var displayTypes: ArrayList<LoanDisplay>, val container: ViewGroup?) :
    GenericPagerAdapter(context) {

    private var loans : ArrayList<ItemViewModel> = ArrayList()
    private var users : ArrayList<User> = ArrayList()
    private var currentPosition: Int = 0
    private var sessionManager: SessionManager = sessionManager
//    private var viewModel: ExpenseViewModel?=null

    init {

        for (expenseType in displayTypes) {
//            setLoanData(expenseType)
            addView(getLayoutView())
        }

    }

//    fun setViewModel(viewModel: ItineraryViewModel){
//        this.viewModel = viewModel
//    }

    fun updatePosition(position: Int){
        currentPosition = position
    }

    override fun getNextPageTitle(position: Int): CharSequence {
        currentPosition = position
//        setLoanData(expenseTypes[position])
        return displayTypes[position].name
    }

    fun getLayoutView(): View{
        return LayoutInflater.from(context).inflate(R.layout.layout_view_recycler_view_loans, container, false)
    }

    override fun onCurrentView(position: Int, view: View) : View{
        val recyclerView = getLayoutView().findViewById<RecyclerView>(R.id.recycler_view_loan_loans)
        val recyclerViewAdapter: GenericRecyclerAdapter<ItemViewModel>

        when (displayTypes[position].displayType) {
            LoanDisplayType.OFFERS -> {
                recyclerViewAdapter = GenericRecyclerAdapter(R.layout.layout_view_recycler_item_loan_item)
                recyclerView?.layoutManager = LinearLayoutManager(context)
                recyclerView?.adapter = recyclerViewAdapter
                setLoanData(position)
                recyclerViewAdapter.addItems(loans)

                recyclerViewAdapter.setOnListItemViewClickListener(object :
                    OnItemViewClickListener {
                    override fun onClick(view: View, position: Int) {
                        view.findNavController().navigate(R.id.action_loanFragment_to_loanOfferDetailFragment)
                    }
                })
            }


            LoanDisplayType.LOAN_REQUEST -> {

                if (sessionManager.getRole() === Role.USER ) {
                    recyclerViewAdapter = GenericRecyclerAdapter(R.layout.layout_view_recycler_item_loan_progress)
                    recyclerView?.layoutManager = LinearLayoutManager(context)
                    recyclerView?.adapter = recyclerViewAdapter
                    setLoanData(position)
                    recyclerViewAdapter.addItems(loans)

                    recyclerViewAdapter.setOnListItemViewClickListener(object :
                        OnItemViewClickListener {
                        override fun onClick(view: View, position: Int) {
                            view.findNavController().navigate(R.id.action_loanFragment_to_loanPendingFragment)
                        }
                    })
                } else {
                    recyclerViewAdapter = GenericRecyclerAdapter(R.layout.layout_view_recycler_item_loan_progress)
                    recyclerView?.layoutManager = LinearLayoutManager(context)
                    recyclerView?.adapter = recyclerViewAdapter
                    setLoanData(position)
                    recyclerViewAdapter.addItems(loans)

                    recyclerViewAdapter.setOnListItemViewClickListener(object :
                        OnItemViewClickListener {
                        override fun onClick(view: View, position: Int) {
                            view.findNavController().navigate(R.id.action_loanFragment_to_loanPendingFragment)
                        }
                    })
                }
            }

            LoanDisplayType.MY_DEBTORS -> {
                if (sessionManager.getRole() === Role.PARTNER ){
                    recyclerViewAdapter = GenericRecyclerAdapter(R.layout.layout_view_recycler_item_loan_debtors)
                    recyclerView?.layoutManager = LinearLayoutManager(context)
                    recyclerView?.adapter = recyclerViewAdapter
                    setDebtorsData()
                    recyclerViewAdapter.addItems(users)

                    recyclerViewAdapter.setOnListItemViewClickListener(object :
                        OnItemViewClickListener {
                        override fun onClick(view: View, position: Int) {
                            view.findNavController().navigate(R.id.action_loanFragment_to_loanDebtorFragment)
                        }
                    })
                }
            }

            LoanDisplayType.DEBTS -> {
                recyclerViewAdapter = GenericRecyclerAdapter(R.layout.layout_view_recycler_item_loan_item)
                recyclerView?.layoutManager = LinearLayoutManager(context)
                recyclerView?.adapter = recyclerViewAdapter
                setLoanData(position)
                recyclerViewAdapter.addItems(loans)

                recyclerViewAdapter.setOnListItemViewClickListener(object :
                    OnItemViewClickListener {
                    override fun onClick(view: View, position: Int) {
                        view.findNavController().navigate(R.id.action_loanFragment_to_loanDetailFragment)
                    }
                })
            }
        }

//        Log.d("DEB", "Debtors")
//        recyclerViewAdapter = GenericRecyclerAdapter(R.layout.layout_view_recycler_item_loan_debtors)
//        recyclerView?.layoutManager = LinearLayoutManager(context)
//        recyclerView?.adapter = recyclerViewAdapter
//        setDebtorsData()
//        recyclerViewAdapter.addItems(users)


//        recyclerView?.layoutManager = LinearLayoutManager(context)
//        recyclerView?.adapter = recyclerViewAdapter

//        Log.d("RV: ", "Recycler View: $position")

//        recyclerViewAdapter.addItems(loans)


        return recyclerView
    }



    private fun setLoanData(position: Int) {
        val expenseNames = arrayOf("Personal Loan", "Business Loan", "Car Loan")
        loans.clear()

        for (i in 0 until 7){

            val id = MathUtil.rand(1, 1000)
            val num = MathUtil.rand(0, 2)
            val userId = MathUtil.rand(1, 1000)
            var name = expenseNames[num]

            if (LoanDisplayType.values()[position] == LoanDisplayType.OFFERS) {
                name = "Loan"
            }
//                Log.d("Data: ", "Expense Type: $name")

            val amount: BigDecimal? = MathUtil.rand(
                BigDecimal(20.21).setScale(2, BigDecimal.ROUND_HALF_UP),
                BigDecimal(100.28).setScale(2, BigDecimal.ROUND_HALF_UP)
            )
            val interest: BigDecimal? = MathUtil.rand(
                BigDecimal(1.02).setScale(2, BigDecimal.ROUND_HALF_UP),
                BigDecimal(7.99).setScale(2, BigDecimal.ROUND_HALF_UP)
            )
            loans.add(Loan(id.toLong(), userId.toLong(), name, amount, interest, LocalDateTime.now(), context!!.getResIdByName("ic_loan", "drawable")))

        }
    }

    private fun setDebtorsData() {
        users.clear()

        for (i in 0 until 7){

            val id = MathUtil.rand(1, 1000)

            users.add(
                    User(
                        id = id.toLong(),
                        firstName = "John",
                        lastName = "Doe",
                        email = "john.doe@abc.com",
                        avatar = context!!.getResIdByName("ic_person_black_24dp", "drawable")
                    )
                )

        }
    }





    public val PageChangeListener = object : OnPageChangeListener {
        override fun onPageScrollStateChanged(arg0: Int) {
            //do nothing
        }

        override fun onPageScrolled(arg0: Int, arg1: Float, arg2: Int) {
            //do nothing
        }

        override fun onPageSelected(position: Int) {
            currentPosition = position
           onCurrentView(position, views[position])
        }
    }
}