package com.example.lendvest.fragments


import android.graphics.Color
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import com.example.lendvest.R
import com.example.lendvest.utils.getColor
import com.example.lendvest.utils.setViewTitle
import com.github.mikephil.charting.charts.PieChart
import com.github.mikephil.charting.data.Entry
import com.github.mikephil.charting.data.PieData
import com.github.mikephil.charting.data.PieDataSet
import com.github.mikephil.charting.data.PieEntry
import com.github.mikephil.charting.formatter.PercentFormatter
import com.github.mikephil.charting.utils.ColorTemplate
import kotlin.collections.MutableMap.MutableEntry


/**
 * A simple [Fragment] subclass.
 */
class LoanApplicationFragment : Fragment() {

    lateinit var pieChartInvest: PieChart

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_loan_application, container, false)

        val pieChart = view.findViewById(R.id.pie_chart_app_lending) as PieChart
        pieChart.setUsePercentValues(true)

        // IMPORTANT: In a PieChart, no values (Entry) should have the same
        // xIndex (even if from different DataSets), since no values can be
        // drawn above each other.
        // IMPORTANT: In a PieChart, no values (Entry) should have the same
// xIndex (even if from different DataSets), since no values can be
// drawn above each other.
        val yvalues =
            ArrayList<PieEntry>()
        yvalues.add(PieEntry(8f, 0))
        yvalues.add(PieEntry(15f, 1))
        yvalues.add(PieEntry(12f, 2))
        yvalues.add(PieEntry(25f, 3))
        yvalues.add(PieEntry(23f, 4))
        yvalues.add(PieEntry(17f, 5))

        val dataSet = PieDataSet(yvalues, "Election Results")

        val xVals = ArrayList<String>()

        xVals.add("January")
        xVals.add("February")
        xVals.add("March")
        xVals.add("April")
        xVals.add("May")
        xVals.add("June")

        val data = PieData(dataSet)
        // In Percentage term
        // In Percentage term
        data.setValueFormatter(PercentFormatter())
        // Default value
        //data.setValueFormatter(new DefaultValueFormatter(0));
        // Default value
//data.setValueFormatter(new DefaultValueFormatter(0));
        pieChart.data = data
//        pieChart.setDescription("This is Pie Chart")

        pieChart.isDrawHoleEnabled = true
        pieChart.transparentCircleRadius = 25f
        pieChart.holeRadius = 25f

        dataSet.setColors(*ColorTemplate.VORDIPLOM_COLORS)
        data.setValueTextSize(13f)
        data.setValueTextColor(Color.DKGRAY)
//        pieChart.setOnChartValueSelectedListener(this)

        pieChart.animateXY(1400, 1400)
        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setViewTitle(activity=(activity as AppCompatActivity), title="Loan Application")
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
