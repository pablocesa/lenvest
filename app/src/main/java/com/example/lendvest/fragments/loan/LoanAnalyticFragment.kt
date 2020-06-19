package com.example.lendvest.fragments.loan


import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.Nullable
import androidx.fragment.app.Fragment
import com.example.lendvest.R
import com.example.lendvest.utils.MathUtil
import com.github.mikephil.charting.charts.HorizontalBarChart
import com.github.mikephil.charting.data.BarData
import com.github.mikephil.charting.data.BarDataSet
import com.github.mikephil.charting.data.BarEntry
import com.github.mikephil.charting.formatter.ValueFormatter
import com.github.mikephil.charting.interfaces.datasets.IBarDataSet


/**
 * A simple [Fragment] subclass.
 */
class LoanAnalyticFragment : Fragment() {

    private val MAX_X_VALUE = 7
    private val MAX_Y_VALUE = 45
    private val MIN_Y_VALUE = 5
    private val SET_LABEL = "Average Temperature"
    private val DAYS =
        arrayOf("SUN", "MON", "TUE", "WED", "THU", "FRI", "SAT")
    private var chart: HorizontalBarChart? = null

    override fun onCreate(@Nullable savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_loan_analytic, container, false)

        chart = view.findViewById(R.id.horizontal_bar_chart_loan_anlytics)

        val data: BarData = createChartData()
        configureChartAppearance()
        prepareChartData(data)


        return view
    }

    private fun configureChartAppearance() {
        chart!!.description.isEnabled = false
        val xAxis = chart!!.xAxis
        xAxis.valueFormatter = object : ValueFormatter() {
            override fun getFormattedValue(value: Float): String {
                return DAYS[value.toInt()]
            }
        }
    }

    private fun createChartData(): BarData {
        val values: ArrayList<BarEntry> = ArrayList()
        for (i in 0 until MAX_X_VALUE) {
            val x = i.toFloat()
            val y: Float = MathUtil.rand(MIN_Y_VALUE, MAX_Y_VALUE).toFloat()
            values.add(BarEntry(x, y))
        }
        val set1 = BarDataSet(values, SET_LABEL)
        val dataSets: ArrayList<IBarDataSet> = ArrayList()
        dataSets.add(set1)
        return BarData(dataSets)
    }

    private fun prepareChartData(data: BarData) {
        data.setValueTextSize(12f)
        chart!!.data = data
        chart!!.invalidate()
    }


}
