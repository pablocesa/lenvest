package com.example.lendvest.fragments


import android.animation.AnimatorSet
import android.animation.ObjectAnimator
import android.animation.ValueAnimator
import android.content.Context
import android.graphics.Color
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.lendvest.BR.itemViewModel
import com.example.lendvest.R
import com.example.lendvest.activities.MainActivity
import com.example.lendvest.adapters.RecyclerBindableAdapter
import com.example.lendvest.models.contracts.NotificationType
import com.example.lendvest.models.contracts.ui.OnAuthenticatedComplete
import com.example.lendvest.models.data.Expense
import com.example.lendvest.models.data.Notification
import com.example.lendvest.models.data.Role
import com.example.lendvest.models.ui.OnItemViewClickListener
import com.example.lendvest.services.session.SessionManager
import com.example.lendvest.utils.*
import com.github.mikephil.charting.charts.LineChart
import com.github.mikephil.charting.charts.PieChart
import kotlinx.android.synthetic.main.fragment_dashboard_partner.*
import lecho.lib.hellocharts.model.*
import lecho.lib.hellocharts.view.LineChartView
import lecho.lib.hellocharts.view.PieChartView
import java.math.BigDecimal
import java.time.LocalDateTime


/**
 * A simple [Fragment] subclass.
 */
class DashboardPartnerFragment : Fragment() {

    lateinit var lineChart: LineChart
    lateinit var pieChartInvest: PieChart
    lateinit var overdueLoanNotifications: ArrayList<Notification>
    lateinit var latestLoanOffers:         ArrayList<Expense>
    lateinit var latestInvestmentOffers:   ArrayList<Expense>
    lateinit var recyclerViewOverdueLoans: RecyclerView
    lateinit var recyclerViewInvestments:  RecyclerView
    lateinit var recyclerViewLoanOffers:   RecyclerView
    /*************** USERS *******************************/

    /*****************************************************/
    private lateinit var onAuthenticatedComplete: OnAuthenticatedComplete

    private var sessionManager: SessionManager? = null

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

        sessionManager = context?.let { SessionManager(it) }

        var view: View?=null
        // Inflate the layout for this fragment

        if(isRolePartner()) {
            view = inflater.inflate(R.layout.fragment_dashboard_partner, container, false)
            recyclerViewInvestments = view.findViewById(R.id.recycler_view_dashboard_latest_investment_offers)
            recyclerViewLoanOffers = view.findViewById(R.id.recycler_view_dashboard_latest_loans_offers)
            recyclerViewOverdueLoans = view.findViewById(R.id.recycler_view_dashboard_overdue_loans)
            setupOnCreateViewPartner(view)
            setup(view)
        } else {
            view = inflater.inflate(R.layout.fragment_dashboard_user, container, false)
            recyclerViewLoanOffers = view.findViewById(R.id.recycler_view_dashboard_user_latest_loans_offers)
            recyclerViewOverdueLoans = view.findViewById(R.id.recycler_view_dashboard_user_overdue_loans)
            setup(view)
        }

        return view
    }

    private fun setup(view: View?){

        if (view != null) {

        }
        if (view != null) {

        }
        overdueLoanNotifications = ArrayList()
        latestLoanOffers         = ArrayList()

        context?.let { prepareRecyclerViewData(it) }


        /* Overdue loans */
//        val recyclerView = view.findViewById<RecyclerView>(R.id.recycler_view_dashboard_overdue_loans)

        var recyclerViewAdapterLoans: RecyclerBindableAdapter<Expense>

        if (isRolePartner()){
            val recyclerViewAdapter = RecyclerBindableAdapter<Notification>(
                R.layout.layout_view_recycler_item_dashboard_notification,
                itemViewModel
            )
            recyclerViewOverdueLoans?.adapter = recyclerViewAdapter
            recyclerViewAdapter.addItems(overdueLoanNotifications)

            recyclerViewOverdueLoans.addItemDecoration(
                DividerItemDecoration(
                    context,
                    LinearLayoutManager.VERTICAL
                )
            )

            recyclerViewAdapter.setOnListItemViewClickListener(object :
                OnItemViewClickListener {
                override fun onClick(view: View, position: Int) {
                    findNavController().navigate(R.id.action_dashboardFragment_to_notificationDetailFragment)
                }
            })

            /* Latest loan offers */
            recyclerViewAdapterLoans = RecyclerBindableAdapter<Expense>(
                R.layout.layout_view_recycler_item_dashboard_offers,
                itemViewModel
            )
        }else {
            /* Latest loan offers */
            recyclerViewAdapterLoans = RecyclerBindableAdapter<Expense>(
                R.layout.layout_view_recycler_item_expenses,
                itemViewModel
            )
        }



        recyclerViewLoanOffers?.adapter = recyclerViewAdapterLoans
        recyclerViewAdapterLoans.addItems(latestLoanOffers)

        recyclerViewAdapterLoans.setOnListItemViewClickListener(object :
            OnItemViewClickListener {
            override fun onClick(view: View, position: Int) {
                findNavController().navigate(R.id.action_dashboardFragment_to_loanDetailFragment)
            }
        })


    }

    private fun setupOnCreateViewPartner(view: View){
        latestInvestmentOffers   = ArrayList()

        val context = context

        createLineGrapgh(view)
//        context?.let { createPieChart(view, it) }

        context?.let { prepareInvestmentRecyclerViewData(it) }

        /* Latest investment offers */
        val recyclerViewAdapterInvestments = RecyclerBindableAdapter<Expense>(
            R.layout.layout_view_recycler_item_dashboard_invest,
            itemViewModel
        )
        recyclerViewInvestments?.adapter = recyclerViewAdapterInvestments
        recyclerViewAdapterInvestments.addItems(latestInvestmentOffers)

        recyclerViewAdapterInvestments.setOnListItemViewClickListener(object :
            OnItemViewClickListener {
            override fun onClick(view: View, position: Int) {
                findNavController().navigate(R.id.action_dashboardFragment_to_investmentDetailFragment)
            }
        })
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setViewTitle(activity = (activity as AppCompatActivity), title = "")
        (activity as MainActivity?)?.showBottomNav()

        showHideRecyclerViews()

        if(isRolePartner()){
            onAuthenticatedComplete.setup(Role.PARTNER)
            onViewCreatedPartner()
        }else {
            onAuthenticatedComplete.setup(Role.USER)
        }

    }

    private fun onViewCreatedPartner(){

        val scaleDownX: ObjectAnimator =
            ObjectAnimator.ofFloat(image_view_dashboard_tap, "scaleX", 0.5f)
        val scaleDownY: ObjectAnimator =
            ObjectAnimator.ofFloat(image_view_dashboard_tap, "scaleY", 0.5f)
        scaleDownX.setDuration(1000)
        scaleDownY.setDuration(1000)
        scaleDownX.setRepeatCount(ValueAnimator.INFINITE)
        scaleDownX.setRepeatMode(ValueAnimator.REVERSE)
        scaleDownY.setRepeatCount(ValueAnimator.INFINITE)
        scaleDownY.setRepeatMode(ValueAnimator.REVERSE)

        val scaleDown = AnimatorSet()
        scaleDown.play(scaleDownX).with(scaleDownY)

        scaleDown.start()

        line_chart_dashboard.setOnClickListener {
            findNavController().navigate(R.id.action_dashboardFragment_to_analyticsFragment)
        }
    }

    private fun createLineGrapgh(view: View) {
        val lineChartView: LineChartView = view.findViewById(R.id.line_chart_dashboard)
        val axisData = monthsAbbrviations
        val yAxisData = intArrayOf(50, 20, 15, 30, 20, 60, 15, 40, 45, 10, 90, 18)

        val yAxisValues = ArrayList<PointValue>()
        val axisValues = ArrayList<AxisValue>()


        val line: Line = Line(yAxisValues).setColor(Color.parseColor("#F5F5F5"))

        for (i in 0 until axisData.count()) {
            axisValues.add(i, AxisValue(i.toFloat()).setLabel(axisData[i]))
        }

        for (i in 0 until yAxisData.count()) {
            yAxisValues.add(PointValue(i.toFloat(), yAxisData[i].toFloat()))
        }


        val lines = ArrayList<Line>()
        lines.add(line)

        val data =
            LineChartData()
        data.lines = lines

        val axis = Axis()
        axis.values = axisValues
        axis.textSize = 10
        axis.textColor = Color.parseColor("#03A9F4")
        data.axisXBottom = axis

        val yAxis = Axis()
//        yAxis.setName("Sales in millions")
        yAxis.textColor = Color.parseColor("#03A9F4")
        yAxis.textSize = 10
        data.axisYLeft = yAxis

        lineChartView.lineChartData = data
        val viewport =
            Viewport(lineChartView.maximumViewport)
        viewport.top = 110f
        lineChartView.maximumViewport = viewport
        lineChartView.currentViewport = viewport
    }

//    private fun createPieChart(view: View, context: Context) {
//        val pieChartView: PieChartView = view.findViewById(R.id.pie_chart_dashboard_lending)
//
//        val pieData = ArrayList<SliceValue>()
//        pieData.add(SliceValue(40f, Color.BLUE))
//        pieData.add(SliceValue(22f, getResourceColor(context, R.color.green)))
//        pieData.add(SliceValue(1f, Color.RED))
//
//        val pieChartData = PieChartData(pieData)
//        pieChartData.setHasCenterCircle(true)
//        pieChartView.pieChartData = pieChartData
//    }

    fun prepareRecyclerViewData(context: Context){
        val iconTypeLoan = context.getResIdByName("ic_loan", "drawable")
        val iconTypeInvest = context.getResIdByName("ic_investment", "drawable")
        overdueLoanNotifications.clear()

        for (i in 0 until 3){
            val num = MathUtil.rand(0, 2)
            val type = MathUtil.rand(0, 3)
            val id = MathUtil.rand(1, 1000)
            val userId = MathUtil.rand(1, 1000)

            var expenseType: NotificationType = NotificationType.LOAN

            if(isRolePartner())
                expenseType = NotificationType.values()[num]
            else{
                if(NotificationType.values()[num] != NotificationType.INVESTMENT){
                    expenseType = NotificationType.values()[num]
                }
            }

            val names = arrayOf("Personal", "Car", "Business", "School")

            val amount: BigDecimal? = MathUtil.rand(
                BigDecimal(20.21).setScale(2, BigDecimal.ROUND_HALF_UP),
                BigDecimal(100.28).setScale(2, BigDecimal.ROUND_HALF_UP)
            )
            latestLoanOffers.add(Expense(id.toLong(), userId.toLong(), expenseType, names[type], amount, LocalDateTime.now(), iconTypeLoan))
        }

    }

    fun prepareInvestmentRecyclerViewData(context: Context){
        val iconTypeLoan = context.getResIdByName("ic_loan", "drawable")
        val iconTypeInvest = context.getResIdByName("ic_investment", "drawable")

        for (i in 0 until 3){
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
            latestInvestmentOffers.add(Expense(id.toLong(), userId.toLong(), expenseType, names[type], amount, LocalDateTime.now(), iconTypeInvest))
        }

    }

    fun showHideRecyclerViews(){
        if (overdueLoanNotifications.isEmpty()){
            recyclerViewOverdueLoans.visibility = View.GONE
            text_view_dashboard_overdue_loans.visibility = View.GONE
        }else {
            recyclerViewOverdueLoans.visibility = View.VISIBLE
            text_view_dashboard_overdue_loans.visibility = View.VISIBLE
        }

        if(isRolePartner()){
            if (latestInvestmentOffers.isEmpty()){
                recyclerViewInvestments.visibility = View.GONE
                text_view_dashboard_latest_investments.visibility = View.GONE
            }else {
                recyclerViewInvestments.visibility = View.VISIBLE
                text_view_dashboard_latest_investments.visibility = View.VISIBLE
            }
        }

        if (latestLoanOffers.isEmpty()){
            recyclerViewLoanOffers.visibility = View.GONE
            text_view_dashboard_latest_loans.visibility = View.GONE
        }else {
            recyclerViewLoanOffers.visibility = View.VISIBLE
            text_view_dashboard_latest_loans.visibility = View.VISIBLE
        }

    }

    private fun isRolePartner(): Boolean {
        return (sessionManager!!.getRole() == Role.PARTNER)
    }



}
