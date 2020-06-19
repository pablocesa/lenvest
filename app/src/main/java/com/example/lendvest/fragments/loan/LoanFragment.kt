package com.example.lendvest.fragments.loan


import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.res.ResourcesCompat
import androidx.fragment.app.DialogFragment
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import androidx.viewpager.widget.PagerTabStrip
import androidx.viewpager.widget.ViewPager
import com.example.lendvest.R
import com.example.lendvest.fragments.SuperFragment
import com.example.lendvest.models.contracts.LoanDisplay
import com.example.lendvest.models.contracts.LoanDisplayType
import com.example.lendvest.models.data.Role
import com.example.lendvest.models.ui.SharedViewModel
import com.example.lendvest.services.session.SessionManager
import com.example.lendvest.utils.*
import com.example.tripfi.adapters.LoanAdapter
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.leinardi.android.speeddial.SpeedDialActionItem
import com.leinardi.android.speeddial.SpeedDialView
import kotlinx.android.synthetic.main.layout_view_toolbar.*

/**
 * A simple [Fragment] subclass.
 */
class LoanFragment : SuperFragment() {

    lateinit var layoutToolbarView: View
    lateinit var viewPagerAdapter: LoanAdapter
    lateinit var viewPager: ViewPager
    lateinit var recyclerViewLoanLayout: View

    //lateinit var model                   : ExpenseViewModel
    private var speedDialView: SpeedDialView? = null

    private val TARGET_FRAGMENT_REQUEST_CODE = 1

    private var imageViewFilter: ImageView? = null

    private val model: SharedViewModel by activityViewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_loan, container, false)

        sessionManager = context?.let { SessionManager(it) }

        createViewPager(view, container, context)

        speedDialView = view.findViewById(R.id.speed_dial_loan)
        context?.let { configureSpeedDial(it) }

        imageViewFilter = getToolbarIcon()

        return view
    }

    private fun configureSpeedDial(context: Context) {
        speedDialView!!.addActionItem(
            SpeedDialActionItem.Builder(R.id.fab_no_label, R.drawable.ic_add_black_24dp)
                .setLabel(getString(R.string.label_new_loan))
                .setFabSize(FloatingActionButton.SIZE_NORMAL)
//                .setFabBackgroundColor(ResourcesCompat.getColor(resources, R.color.white, context.theme))
                .setFabImageTintColor(ResourcesCompat.getColor(resources, R.color.white, context.theme))
                .create()
        )

        speedDialView!!.setOnActionSelectedListener(SpeedDialView.OnActionSelectedListener { actionItem ->
            when (actionItem.id) {
                R.id.fab_no_label -> {
                    findNavController().navigate(R.id.action_loanFragment_to_loanApplicationFragment)
                    speedDialView!!.close() // To close the Speed Dial with animation
                    return@OnActionSelectedListener true // false will close it without animation
                }
            }
            false
        })

//        var drawable = getDrawable(context, R.drawable.ic_custom_color)
//        speedDialView!!.addActionItem(SpeedDialActionItem.Builder(R.id.fab_custom_theme, R.drawable.ic_add_black_24dp)
//            .setLabel(getString(R.string.label_custom_theme))
//            .setTheme(R.style.AppTheme_Purple)
//            .create())

//        speedDialView.addActionItem(SpeedDialActionItem.Builder(R.id.fab_long_label, R.drawable
//            .ic_lorem_ipsum)
//            .setFabSize(FloatingActionButton.SIZE_NORMAL)
//            .setLabel("Lorem ipsum dolor sit amet, consectetur adipiscing elit, sed do eiusmod tempor " +
//                    "incididunt ut labore et dolore magna aliqua.")
//            .create())
//
//        drawable = getDrawable(this@MainActivity, R.drawable.ic_add_white_24dp)
//        speedDialView.addActionItem(SpeedDialActionItem.Builder(R.id.fab_add_action, drawable)
//            .setFabBackgroundColor(ResourcesCompat.getColor(resources, R.color.material_green_500, theme))
//            .setLabel(R.string.label_add_action)
//            .setLabelBackgroundColor(Color.TRANSPARENT)
//            .create())
//
//        speedDialView.addActionItem(SpeedDialActionItem.Builder(R.id.fab_custom_theme, R.drawable
//            .ic_theme_white_24dp)
//            .setLabel(getString(R.string.label_custom_theme))
//            .setTheme(R.style.AppTheme_Purple)
//            .create())
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setViewTitle(activity=(activity as AppCompatActivity), title="Loans")




        imageViewFilter!!.setOnClickListener{
            val fragmentManager: FragmentManager = getFragmentActivity().supportFragmentManager
            val dialog: DialogFragment = LoanFilterDialogFragment()
            dialog.isCancelable = true
            dialog.show(fragmentManager, "dialog")
        }

//        updateToolbar()
    }

    private fun getToolbarIcon() : ImageView {
        var imageView: ImageView? = null
        val toolbar = getFragmentActivity().toolbar
        for (i in 0 until toolbar.childCount) {
            val child: View = toolbar.getChildAt(i)

            // assuming that the title is the first instance of TextView
            // you can also check if the title string matches
            if (child is ImageView) {
                imageView = child as ImageView
                break
            }
        }
        return imageView!!
    }



    fun createViewPager(parentView: View, container: ViewGroup?, context: Context?) {
        recyclerViewLoanLayout =
            layoutInflater.inflate(R.layout.layout_view_recycler_view_loans, null)

        viewPager = parentView.findViewById(R.id.view_pager_loans)

        val pagerTabStrip = parentView.findViewById(R.id.pager_title_strip) as PagerTabStrip
        pagerTabStrip.drawFullUnderline = false
        pagerTabStrip.tabIndicatorColor =
            context?.let { getResourceColor(it, R.color.colorAccent) }!!
        pagerTabStrip.setTextColor(getColor(getFragmentActivity(), id = R.color.colorPrimary))

//        val expenseTypes = ArrayList<String>()
        val expenseTypes = ArrayList<LoanDisplay>()
        expenseTypes.add(LoanDisplay(LoanDisplayType.OFFERS, "Offers"))
        expenseTypes.add(LoanDisplay(LoanDisplayType.LOAN_REQUEST, "Pending"))
        if (sessionManager!!.getRole() === Role.PARTNER) {
            expenseTypes.add(LoanDisplay(LoanDisplayType.MY_DEBTORS, "My Debtors"))
        }
        expenseTypes.add(LoanDisplay(LoanDisplayType.DEBTS, "My Loans"))

        viewPagerAdapter = sessionManager?.let {
            LoanAdapter(
                context,
                it, expenseTypes, container
            )
        }!!

        viewPager.adapter = viewPagerAdapter
        viewPager.offscreenPageLimit = 3
        viewPager.addOnPageChangeListener(object : ViewPager.SimpleOnPageChangeListener() {
            override fun onPageSelected(position: Int) {
                viewPagerAdapter.updatePosition(position)
                if(position == 1){
                    if (sessionManager!!.getRole() == Role.PARTNER){
                        setToolbarFilterIcon(
                            getFragmentActivity(),
                            ToolbarAction.SHOW
                        )
                    }
                }else{
                    setToolbarFilterIcon(
                        getFragmentActivity(),
                        ToolbarAction.HIDE
                    )
                }
//                adapter.setData(data);
//                viewPagerAdapter.notifyDataSetChanged()
                viewPager.invalidate()
            }
        })
    }

//    fun updateToolbar() {
//        val params = ActionBar.LayoutParams(
//            Gravity.RIGHT
//        )
//        (activity as AppCompatActivity).supportActionBar!!.displayOptions =
//            ActionBar.DISPLAY_SHOW_CUSTOM or ActionBar.DISPLAY_USE_LOGO or ActionBar.DISPLAY_SHOW_HOME
//        (activity as AppCompatActivity).supportActionBar!!.setCustomView(layoutToolbarView, params)
//        (activity as AppCompatActivity).supportActionBar!!.setDisplayUseLogoEnabled(false);
//        (activity as AppCompatActivity).supportActionBar!!.setDisplayShowCustomEnabled(true)
//        (activity as AppCompatActivity).supportActionBar!!.setDisplayHomeAsUpEnabled(true)
//
//        val toolbar =
//            (activity as AppCompatActivity).findViewById<androidx.appcompat.widget.Toolbar>(R.id.toolbar)
//        val textView = toolbar.findViewById<TextView>(R.id.toolbar_title)
//
//        textView.text = "Loans"
//
//        (activity as AppCompatActivity).supportActionBar!!.setDisplayShowTitleEnabled(false)
//    }

    override fun onPause() {
        super.onPause()
        (activity as AppCompatActivity).supportActionBar!!.setDisplayShowCustomEnabled(false)
//        constraintLayout(DisplayAction.HIDE)
        setToolbarFilterIcon(getFragmentActivity(), ToolbarAction.HIDE)
    }



}
