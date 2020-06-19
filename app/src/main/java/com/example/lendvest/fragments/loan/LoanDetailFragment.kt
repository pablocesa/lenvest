package com.example.lendvest.fragments.loan


import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup

import com.example.lendvest.R
import com.example.lendvest.fragments.SuperFragment
import com.example.lendvest.utils.MathUtil
import com.example.lendvest.utils.getResourceColor
import com.example.lendvest.utils.setViewTitle
import com.github.mikephil.charting.charts.HorizontalBarChart
import com.github.mikephil.charting.components.YAxis
import com.github.mikephil.charting.data.BarData
import com.github.mikephil.charting.data.BarDataSet
import com.github.mikephil.charting.data.BarEntry
import com.github.mikephil.charting.formatter.ValueFormatter
import com.github.mikephil.charting.interfaces.datasets.IBarDataSet
import java.text.DecimalFormat

/**
 * A simple [Fragment] subclass.
 */
class LoanDetailFragment : SuperFragment() {

    /************ Begin loan analytics variables ****************/
    private val MAX_X_VALUE = 2
    private val MAX_Y_VALUE = 25
    private val MIN_Y_VALUE = 5
    private val SET_LABEL = "Average Temperature"
    private val DAYS =
        arrayOf("P", "L")

    private var chartHorizontal: HorizontalBarChart? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view =  inflater.inflate(R.layout.fragment_loan_detail, container, false)
        chartHorizontal = view.findViewById(R.id.horizontal_bar_chart_loan_anlytics_details)

        val data: BarData = createChartData()
        configureChartAppearance()
        prepareChartData(data)

        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setViewTitle(activity=getFragmentActivity(), title="Detail")
    }

    private fun configureChartAppearance() {
        chartHorizontal!!.description.isEnabled = false
        val xAxis = chartHorizontal!!.xAxis
        xAxis.setDrawGridLines(false)
        xAxis.valueFormatter = object : ValueFormatter() {
            override fun getFormattedValue(value: Float): String {
                return ""
            }
        }
        val leftAxis: YAxis = chartHorizontal!!.axisLeft
        leftAxis.valueFormatter = FormatterYAxisValue()

        chartHorizontal!!.axisRight.isEnabled = false
    }

    private fun createChartData(): BarData {

        val labels = arrayOf("To pay back", "Loan amount")

        val entries1: ArrayList<BarEntry> = ArrayList()
        val entries2: ArrayList<BarEntry> = ArrayList()

//        for (i in 0 until MAX_X_VALUE) {
//            val x = i.toFloat()
//            val y: Float = MathUtil.rand(MIN_Y_VALUE, MAX_Y_VALUE).toFloat()
//            entries1.add(BarEntry(x, y))
//        }
        var x = 0f
        var y: Int = MathUtil.rand(MIN_Y_VALUE, 10000)
        entries1.add(BarEntry(x.toFloat(), y.toFloat()))
        x = 1f
        y = MathUtil.rand(MIN_Y_VALUE, 10000)
        entries2.add(BarEntry(x.toFloat(), y.toFloat()))

        val set1 = BarDataSet(entries1, labels[0])
        val set2 = BarDataSet(entries2, labels[1])
        set1.color = context?.let { getResourceColor(it, R.color.colorAccent) }!!
        set2.color = context?.let { getResourceColor(it, R.color.colorPrimary) }!!
        val dataSets: ArrayList<IBarDataSet> = ArrayList()
        dataSets.add(set2)
        dataSets.add(set1)
        return BarData(dataSets)
    }

    private fun prepareChartData(data: BarData) {
        data.setValueTextSize(12f)
        chartHorizontal!!.data = data
        chartHorizontal!!.invalidate()
    }

    class FormatterYAxisValue : ValueFormatter() {
        private var mFormat: DecimalFormat? = null
        init{
            // format values to 1 decimal digit
            mFormat = DecimalFormat("###,###,##0")
        }
        override fun getFormattedValue(value: Float): String {
            return "€ "  + mFormat!!.format(value.toInt())
        }

    }


}
