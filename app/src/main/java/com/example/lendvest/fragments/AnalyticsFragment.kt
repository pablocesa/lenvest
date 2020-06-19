package com.example.lendvest.fragments


import android.graphics.Color
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.navigation.Navigation.findNavController
import androidx.navigation.fragment.findNavController
import com.example.lendvest.R
import com.example.lendvest.models.data.Role
import com.example.lendvest.services.session.SessionManager
import com.example.lendvest.utils.*
import com.github.mikephil.charting.charts.HorizontalBarChart
import com.github.mikephil.charting.components.YAxis
import com.github.mikephil.charting.data.BarData
import com.github.mikephil.charting.data.BarDataSet
import com.github.mikephil.charting.data.BarEntry
import com.github.mikephil.charting.formatter.ValueFormatter
import com.github.mikephil.charting.interfaces.datasets.IBarDataSet
import kotlinx.android.synthetic.main.fragment_analytics.*
import lecho.lib.hellocharts.listener.ColumnChartOnValueSelectListener
import lecho.lib.hellocharts.model.*
import lecho.lib.hellocharts.view.ColumnChartView
import lecho.lib.hellocharts.view.PieChartView
import java.text.DecimalFormat


/**
 * A simple [Fragment] subclass.
 */
class AnalyticsFragment : SuperFragment() {

    /************ Begin Investment Variables ********************/
    private val chart: ColumnChartView? = null
    private var data: ColumnChartData? = null
    private val hasAxes = true
    private val hasAxesNames = true
    private val hasLabels = false
    private val hasLabelForSelected = false
    private val dataType: Int = 0
    /************ Investment Variables *************************/
    /************ Begin loan analytics variables ****************/
    private val MAX_X_VALUE = 2
    private val MAX_Y_VALUE = 25
    private val MIN_Y_VALUE = 5
    private val SET_LABEL = "Average Temperature"
    private val DAYS =
        arrayOf("P", "L")

    private var chartHorizontal: HorizontalBarChart? = null

    /************ End loan analytics variables ****************/
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        var view: View? = null

        sessionManager = context?.let { SessionManager(it) }

        if (sessionManager!!.getRole() == Role.PARTNER) {
            view = inflater.inflate(R.layout.fragment_analytics, container, false)
            createPieChart(view)
        } else {
            view = inflater.inflate(R.layout.fragment_loan_analytic, container, false)
        }

        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setViewTitle(activity = (activity as AppCompatActivity), title = "Analytics")

        if (sessionManager!!.getRole() == Role.PARTNER) {
            column_chart_analytics.onValueTouchListener = ValueTouchListener(view)

            button_analytics_expenses.setOnClickListener {
                findNavController().navigate(R.id.action_analyticsFragment_to_expensesFragment)
            }

            generateData()
            prepareDataAnimation()
            column_chart_analytics.startDataAnimation()
        }
    }

    /**
     * Generates columns with subcolumns, columns have larger separation than subcolumns.
     */
    private fun generateData() {
        val numOfSubcolumns = 3
        val numOfColumns = 12
        // Column can have many subcolumns, here I use 4 subcolumn in each of 8 columns.
        val columns: MutableList<Column> = ArrayList()
        var values: MutableList<SubcolumnValue?>
        val axisValues = ArrayList<AxisValue>()

        for (i in 0 until numOfColumns) {
            values = ArrayList()
            for (j in 0 until numOfSubcolumns) {
                values.add(
                    getColumnColor(j)?.let {
                        SubcolumnValue(
                            Math.random().toFloat() * 50f + 5,
                            it
                        )
                    }
                )
            }
            val column = Column(values)
            column.setHasLabels(hasLabels)
            column.setHasLabelsOnlyForSelected(hasLabelForSelected)
            columns.add(column)
            axisValues.add(AxisValue(i.toFloat()).setLabel(monthsAbbrviations[i]))
        }

        data = ColumnChartData(columns)
        if (hasAxes) {
            val axisX = Axis()
            val axisY: Axis = Axis().setHasLines(true)

            axisX.values = axisValues

            data!!.axisXBottom = axisX
            data!!.axisYLeft = axisY
        } else {
            data!!.axisXBottom = null
            data!!.axisYLeft = null
        }
        column_chart_analytics.columnChartData = data
    }

    fun getColumnColor(position: Int): Int? {
        when (position) {
            0 -> return context?.let { getResourceColor(it, R.color.colorPrimary) }
            1 -> return context?.let { getResourceColor(it, R.color.green) }
            2 -> return context?.let { getResourceColor(it, R.color.colorAccent) }
        }
        return 0
    }


    /**
     * To animate values you have to change targets values and then call [column_chart_analytics.startDataAnimation]
     * method(don't confuse with View.animate()).
     */
    private fun prepareDataAnimation() {
        for (column in data!!.columns) {
            for (value in column.values) {
                value.setTarget(Math.random().toFloat() * 100)
            }
        }
    }

    private class ValueTouchListener(val parentView: View?) : ColumnChartOnValueSelectListener {
        override fun onValueSelected(
            columnIndex: Int,
            subcolumnIndex: Int,
            value: SubcolumnValue
        ) {
            if (parentView != null) {
                findNavController(parentView).navigate(R.id.action_analyticsFragment_to_analyticDetailFragment)
            }
        }

        override fun onValueDeselected() { // TODO Auto-generated method stub
        }
    }

    private fun createPieChart(parentView: View) {

        val pieChart = parentView.findViewById<PieChartView>(R.id.pie_chart_analytics_expenses)

        val pieData = ArrayList<SliceValue>()
        pieData.add(SliceValue(40f, Color.BLUE))
        context?.let { getResourceColor(it, R.color.green) }?.let {
            SliceValue(
                22f,
                it
            )
        }?.let { pieData.add(it) }
        pieData.add(SliceValue(12f, getColor(getFragmentActivity(), id = R.color.colorAccent)))

        val pieChartData = PieChartData(pieData)
        pieChartData.setHasCenterCircle(true)
        pieChart.pieChartData = pieChartData
    }

    /************ loan analytics *******************************/

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
        set1.color = context?.let { getResourceColor(it, R.color.colorAccent)}!!
        set2.color = context?.let { getResourceColor(it, R.color.colorPrimary)}!!
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
