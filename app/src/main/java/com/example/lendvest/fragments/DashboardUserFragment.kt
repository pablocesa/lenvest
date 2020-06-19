package com.example.lendvest.fragments


import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.RecyclerView
import com.example.lendvest.BR
import com.example.lendvest.R
import com.example.lendvest.activities.MainActivity
import com.example.lendvest.adapters.RecyclerBindableAdapter
import com.example.lendvest.models.contracts.NotificationType
import com.example.lendvest.models.contracts.ui.OnAuthenticatedComplete
import com.example.lendvest.models.data.Expense
import com.example.lendvest.models.data.Notification
import com.example.lendvest.models.data.Role
import com.example.lendvest.utils.MathUtil
import com.example.lendvest.utils.getResIdByName
import com.example.lendvest.utils.setViewTitle
import java.math.BigDecimal
import java.time.LocalDateTime


/**
 * A simple [Fragment] subclass.
 */
class DashboardUserFragment : SuperFragment() {
    lateinit var latestLoanOffers:         ArrayList<Expense>
    lateinit var overdueLoanNotifications: ArrayList<Notification>

    lateinit var recyclerViewOverdueLoans: RecyclerView
    lateinit var recyclerViewLoanOffers: RecyclerView
    private lateinit var onAuthenticatedComplete: OnAuthenticatedComplete


    override fun onAttach(context: Context) {
        super.onAttach(context)
        try {
            onAuthenticatedComplete = context as OnAuthenticatedComplete
        } catch (castException: ClassCastException) {
            /** The activity does not implement the listener.  */
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_dashboard_user, container, false)

//        val navOptions = NavOptions.Builder()
//            .setPopUpTo(R.id.dashboardUserFragment, true)
//            .build()

        recyclerViewOverdueLoans = view.findViewById(R.id.recycler_view_dashboard_user_overdue_loans)
        recyclerViewLoanOffers   = view.findViewById(R.id.recycler_view_dashboard_user_latest_loans_offers)

        latestLoanOffers         = ArrayList()
        overdueLoanNotifications = ArrayList()

        prepareRecyclerViewData()

        val recyclerViewAdapter = RecyclerBindableAdapter<Notification>(
            R.layout.layout_view_recycler_item_dashboard_notification,
            BR.itemViewModel
        )
        recyclerViewOverdueLoans?.adapter = recyclerViewAdapter
        recyclerViewAdapter.addItems(overdueLoanNotifications)

        val recyclerViewAdapterLoanOffers = RecyclerBindableAdapter<Expense>(
            R.layout.layout_view_recycler_item_expenses,
            BR.itemViewModel
        )
        recyclerViewLoanOffers?.adapter = recyclerViewAdapterLoanOffers
        recyclerViewAdapterLoanOffers.addItems(latestLoanOffers)


        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        onAuthenticatedComplete.setup(Role.USER)
        (activity as AppCompatActivity).supportActionBar!!.setDisplayHomeAsUpEnabled(true)
//        (activity as AppCompatActivity).supportActionBar!!.setHomeAsUpIndicator(R.drawable.ha);
        val context = context
//        val drawer = findViewById(R.id.drawer_layout) as DrawerLayout
//        val toggle = ActionBarDrawerToggle(
//            getFragmentActivity(),
//            drawer,
//            toolbar,
//            R.string.navigation_drawer_open,
//            R.string.navigation_drawer_close
//        )
//        drawer.addDrawerListener(toggle)
//        toggle.setDrawerIndicatorEnabled(true)
//        toggle.syncState()

        setViewTitle(activity = (activity as AppCompatActivity), title = "")
        (activity as MainActivity?)?.showBottomNav()

        (activity as MainActivity).showBottomNavigationView(true)
    }


    fun prepareRecyclerViewData(){
        val iconTypeLoan = context!!.getResIdByName("ic_loan", "drawable")
        val iconTypeInvest = context!!.getResIdByName("ic_investment", "drawable")
        overdueLoanNotifications.clear()

        for (i in 0 until 1){
            val num = MathUtil.rand(0, 2)
            val id = MathUtil.rand(1, 1000)
            val userId = MathUtil.rand(1, 1000)
            val expenseType = NotificationType.values()[num]
            val names = arrayOf("Land", "House", "Bus", "School")
            val type = MathUtil.rand(0, 3)

            val amount: BigDecimal? = MathUtil.rand(
                BigDecimal(20.21).setScale(2, BigDecimal.ROUND_HALF_UP),
                BigDecimal(100.28).setScale(2, BigDecimal.ROUND_HALF_UP)
            )
            overdueLoanNotifications.add(Expense(id.toLong(), userId.toLong(), expenseType, names[type], amount, LocalDateTime.now(), iconTypeLoan))
        }

        for (i in 0 until 3){
            val num = MathUtil.rand(0, 2)
            val type = MathUtil.rand(0, 3)
            val id = MathUtil.rand(1, 1000)
            val userId = MathUtil.rand(1, 1000)
            val expenseType = NotificationType.values()[num]
            val names = arrayOf("Personal", "Car", "Business", "School")

            val amount: BigDecimal? = MathUtil.rand(
                BigDecimal(20.21).setScale(2, BigDecimal.ROUND_HALF_UP),
                BigDecimal(100.28).setScale(2, BigDecimal.ROUND_HALF_UP)
            )
            latestLoanOffers.add(Expense(id.toLong(), userId.toLong(), expenseType, names[type], amount, LocalDateTime.now(), iconTypeLoan))
        }

    }


}
