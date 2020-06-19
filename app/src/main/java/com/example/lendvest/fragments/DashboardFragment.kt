package com.example.lendvest.fragments


import android.graphics.Color
import android.graphics.Typeface
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.navigation.findNavController
import androidx.recyclerview.widget.RecyclerView
import com.example.lendvest.BR.itemViewModel
import com.example.lendvest.R
import com.example.lendvest.adapters.RecyclerBindableAdapter
import com.example.lendvest.models.data.Notification
import com.example.lendvest.models.ui.OnItemViewButtonClickListener
import com.example.lendvest.utils.getColor
import com.example.lendvest.utils.setViewTitle
import com.example.lendvest.views.CustomMarker
import com.github.mikephil.charting.animation.Easing
import com.github.mikephil.charting.charts.LineChart
import com.github.mikephil.charting.charts.PieChart
import com.github.mikephil.charting.components.Legend
import com.github.mikephil.charting.data.*
import com.github.mikephil.charting.formatter.PercentFormatter


/**
 * A simple [Fragment] subclass.
 */
class DashboardFragment : Fragment() {

    lateinit var lineChart: LineChart
    lateinit var pieChartInvest: PieChart

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view =  inflater.inflate(R.layout.fragment_dashboard, container, false)

        lineChart = view.findViewById(R.id.line_chart_dashboard)
        pieChartInvest = view.findViewById(R.id.pie_chart_dashboard_lending)

        fillInLineChartDashboardData()
//        fillInPieChartDashboardData()
        setupPieChartView()

        val notifications = ArrayList<Notification>()
        notifications.add(Notification("Next payment in 4 days..."))
        notifications.add(Notification("Next meeting in 2 days..."))
        notifications.add(Notification("New loan request available..."))


        val recyclerView = view.findViewById<RecyclerView>(R.id.recycler_view_dashboard_notification)
        val recyclerViewAdapter = RecyclerBindableAdapter<Notification>(R.layout.layout_view_recycler_item_dashboard_notification, itemViewModel)
        recyclerView?.adapter   = recyclerViewAdapter
        recyclerViewAdapter.addItems(notifications)

        recyclerViewAdapter.setOnItemViewClickListener(object :
            OnItemViewButtonClickListener {
            override fun onClick(view: View, position: Int) {
                view.findNavController().navigate(R.id.action_dashboardFragment_to_notificationDetailFragment)
            }
        })
        
        


        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setViewTitle(activity=(activity as AppCompatActivity), title="Dashboard")
    }
    
    private fun fillInLineChartDashboardData(){
        //Part1
        val entries = ArrayList<Entry>()

//Part2
        entries.add(Entry(1f, 20f))
        entries.add(Entry(1.02f, 2f))
        entries.add(Entry(1.04f, 15f))
        entries.add(Entry(1.08f, 20f))
        entries.add(Entry(1.10f, 2f))

//Part3
        val vl = LineDataSet(entries, "My Type")

//Part4
        vl.setDrawValues(false)
        vl.setDrawFilled(true)
        vl.lineWidth = 3f
        vl.fillColor = R.color.gray
        vl.fillAlpha = R.color.red

        val j = 1f

//Part5
        lineChart.xAxis.labelRotationAngle = 0f

//Part6
        lineChart.data = LineData(vl)

//Part7
        lineChart.axisRight.isEnabled = false
        lineChart.xAxis.axisMaximum = j+0.1f

//Part8
        lineChart.setTouchEnabled(true)
        lineChart.setPinchZoom(true)

//Part9
        lineChart.description.text = "Days"
        lineChart.setNoDataText("No forex yet!")

//Part10
        lineChart.animateX(1800, Easing.EaseInExpo)

//Part11
        val markerView = context?.let { CustomMarker(it, R.layout.layout_view_marker) }
        lineChart.marker = markerView
    }

    private fun fillInPieChartDashboardData(){
        pieChartInvest.setUsePercentValues(true)
        val xvalues = ArrayList<PieEntry>()
        xvalues.add(PieEntry(34.0f, "London"))
        xvalues.add(PieEntry(28.2f, "Coventry"))
        xvalues.add(PieEntry(37.9f, "Manchester"))
        val dataSet = PieDataSet(xvalues, "")
        val data = PieData(dataSet)
//        pieChartColor(data,dataSet)
        // In Percentage
        data.setValueFormatter(PercentFormatter())

        pieChartInvest.data = data
        pieChartInvest.description.text = ""
        pieChartInvest.isDrawHoleEnabled = false
        data.setValueTextSize(13f)


//        pieChartInvest.setOnChartValueSelectedListener(context)

        chartDetails(pieChartInvest, Typeface.SANS_SERIF)
    }

    fun chartDetails(pieChartInvest: PieChart, tf: Typeface) {
        pieChartInvest.description.isEnabled = true
        pieChartInvest.centerText = ""
        pieChartInvest.setCenterTextSize(10F)
        pieChartInvest.setCenterTextTypeface(tf)
        val l = pieChartInvest.legend
        pieChartInvest.legend.isWordWrapEnabled = true
        pieChartInvest.legend.isEnabled = false
        l.verticalAlignment = Legend.LegendVerticalAlignment.BOTTOM
        l.horizontalAlignment = Legend.LegendHorizontalAlignment.CENTER
        l.formSize = 20F
        l.formToTextSpace = 5f
        l.form = Legend.LegendForm.SQUARE
        l.textSize = 12f
        l.orientation = Legend.LegendOrientation.HORIZONTAL
        l.isWordWrapEnabled = true
        l.setDrawInside(false)
        pieChartInvest.setTouchEnabled(false)
        pieChartInvest.setDrawEntryLabels(false)
        pieChartInvest.legend.isWordWrapEnabled = true
        pieChartInvest.setExtraOffsets(20f, 0f, 20f, 0f)
        pieChartInvest.setUsePercentValues(true)
        // pieChartInvest.rotationAngle = 0f
        pieChartInvest.setUsePercentValues(true)
        pieChartInvest.setDrawCenterText(false)
        pieChartInvest.description.isEnabled = true
        pieChartInvest.isRotationEnabled = false
    }


    fun setupPieChartView() {

        val currentActivity = activity as AppCompatActivity

        pieChartInvest?.setUsePercentValues(true)

        pieChartInvest.setUsePercentValues(true)
        pieChartInvest.setExtraOffsets(5f, 10f, 5f, 5f)

        val entries = ArrayList<PieEntry>()

        entries.add(PieEntry(40f, "Active"))
        entries.add(PieEntry(22f, "Completed"))
        entries.add(PieEntry(0f, "Lost"))

        // add colors
        val colors = ArrayList<Int>()
        colors.add(getColor(currentActivity, R.color.colorPrimary))
        colors.add(getColor(currentActivity, R.color.green))
        colors.add(getColor(currentActivity, R.color.red))

        val dataSet = PieDataSet(entries, "Lending Status")
        dataSet.sliceSpace = 3f
        dataSet.selectionShift = 5f

        dataSet.colors = colors

        val pieData = PieData(dataSet)
        pieChartInvest.data = pieData
        pieChartInvest.getDescription().setEnabled(false)
        pieData.setValueTextSize(13f)

        pieChartInvest.invalidate()
    }





}
