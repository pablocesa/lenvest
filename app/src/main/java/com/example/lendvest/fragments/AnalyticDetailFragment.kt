package com.example.lendvest.fragments


import android.R.attr.data
import android.graphics.Color
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.navigation.Navigation
import androidx.navigation.findNavController
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.lendvest.BR
import com.example.lendvest.R
import com.example.lendvest.adapters.RecyclerBindableAdapter
import com.example.lendvest.models.contracts.NotificationType
import com.example.lendvest.models.data.Expense
import com.example.lendvest.models.data.Notification
import com.example.lendvest.models.ui.OnItemViewButtonClickListener
import com.example.lendvest.utils.*
import kotlinx.android.synthetic.main.fragment_analytic_detail.*
import kotlinx.android.synthetic.main.fragment_analytics.*
import lecho.lib.hellocharts.listener.ColumnChartOnValueSelectListener
import lecho.lib.hellocharts.model.*
import lecho.lib.hellocharts.util.ChartUtils
import lecho.lib.hellocharts.view.ColumnChartView
import java.math.BigDecimal
import java.time.LocalDateTime


/**
 * A simple [Fragment] subclass.
 */
class AnalyticDetailFragment : Fragment() {

    private val chart: ColumnChartView? = null
    private var data: ColumnChartData? = null
    private val hasAxes = true
    private val hasAxesNames = true
    private val hasLabels = false
    private val hasLabelForSelected = false
    private val dataType: Int = 0

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_analytic_detail, container, false)

        createRecyclerView(view)


        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setViewTitle(activity=(activity as AppCompatActivity), title="Analytic Details")

        column_chart_analytic_details.onValueTouchListener = ValueTouchListener(view)

        generateData()
        prepareDataAnimation()
        column_chart_analytic_details.startDataAnimation()

    }

    fun createRecyclerView(parentView: View){
        val expenses = ArrayList<Expense>()

//        val exTypes = arrayOf("Loan", "Contribution", "Investment")
        val iconTypes = arrayOf(
            context!!.getResIdByName("ic_loan", "drawable"),
            context!!.getResIdByName("ic_contributor", "drawable"),
            context!!.getResIdByName("ic_asset", "drawable")
        )

        for (i in 0 until 5){
            val num = MathUtil.rand(0, 2)
            val id = MathUtil.rand(1, 1000)
            val userId = MathUtil.rand(1, 1000)
            val expenseType = NotificationType.values()[num]

            val amount: BigDecimal? = MathUtil.rand(
                BigDecimal(20.21).setScale(2, BigDecimal.ROUND_HALF_UP),
                BigDecimal(100.28).setScale(2, BigDecimal.ROUND_HALF_UP)
            )
            expenses.add(Expense(id.toLong(), userId.toLong(), expenseType, "", amount, LocalDateTime.now(), iconTypes[num]))
        }


        val recyclerView = parentView.findViewById<RecyclerView>(R.id.recycler_view_analytics_details_expenses)

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
//                parentView.findNavController().navigate(R.id.action_expensesFragment_to_expenseDetailsFragment)
            }
        })
    }

    /**
     * Generates columns with stacked subcolumns.
     */
    private fun generateData() {
        val numSubcolumns = 3
        val numColumns = 3
        // Column can have many stacked subcolumns, here I use 4 stacke subcolumn in each of 4 columns.
        val columns: MutableList<Column> = ArrayList<Column>()
        var values: MutableList<SubcolumnValue?>
        val axisValues = ArrayList<AxisValue>()

        for (i in 0 until numColumns) {
            values = ArrayList()
            for (j in 0 until numSubcolumns) {
                values.add(
                    getColumnColor(j)?.let {
                        SubcolumnValue(
                            Math.random().toFloat() * 20f + 5,
                            it
                        )
                    }
                )
            }
            val column = Column(values)
            column.setHasLabels(hasLabels)
            column.setHasLabelsOnlyForSelected(hasLabelForSelected)
            columns.add(column)
            axisValues.add(AxisValue(i.toFloat()).setLabel(investmentTypes[i]))
        }
        data = ColumnChartData(columns)
        // Set stacked flag.
        data!!.isStacked = true
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
        column_chart_analytic_details.setColumnChartData(data)
    }

    fun getColumnColor(position: Int): Int? {
        when(position){
            0 -> return context?.let { getResourceColor(it, R.color.colorPrimary) }
            1 -> return context?.let { getResourceColor(it, R.color.green) }
            2 -> return Color.RED
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


    private class ValueTouchListener (val parentView: View?): ColumnChartOnValueSelectListener {
        override fun onValueSelected(
            columnIndex: Int,
            subcolumnIndex: Int,
            value: SubcolumnValue
        ) {
            if (parentView != null) {
//                Navigation.findNavController(parentView).navigate(R.id.action_analyticsFragment_to_analyticDetailFragment)
            }
        }

        override fun onValueDeselected() { // TODO Auto-generated method stub
        }
    }




}
