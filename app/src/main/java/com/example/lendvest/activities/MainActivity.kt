package com.example.lendvest.activities

import android.os.Bundle
import android.view.LayoutInflater
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.core.view.GravityCompat
import androidx.drawerlayout.widget.DrawerLayout
import androidx.navigation.NavController
import androidx.navigation.NavOptions
import androidx.navigation.findNavController
import androidx.navigation.ui.navigateUp
import androidx.navigation.ui.setupActionBarWithNavController
import androidx.navigation.ui.setupWithNavController
import com.example.lendvest.R
import com.example.lendvest.models.contracts.ui.OnAuthenticatedComplete
import com.example.lendvest.models.data.Role
import com.example.lendvest.models.ui.WelcomeViewModel
import com.example.lendvest.services.session.SessionManager
import com.example.lendvest.utils.ToolbarAction
import com.example.lendvest.utils.getViewModel
import com.example.lendvest.utils.setToolbarFilterIcon
import com.google.android.material.bottomnavigation.BottomNavigationMenuView
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.google.android.material.navigation.NavigationView
import com.stepstone.stepper.StepperLayout
import com.stepstone.stepper.VerificationError
import java.lang.String


interface OnLaunchScreenComplete{
    fun showBottomNavigationView(value: Boolean)
}


class MainActivity : AppCompatActivity(), OnLaunchScreenComplete, StepperLayout.StepperListener,
    OnAuthenticatedComplete {

    val drawerLayout by lazy { findViewById<DrawerLayout>(R.id.drawer_layout) }
    val navController by lazy { findNavController(R.id.navigation_host_fragment) }
    val navigationView by lazy { findViewById<NavigationView>(R.id.navigationView) }
    val bottomNavigationView by lazy { findViewById<BottomNavigationView>(R.id.bottom_navigation_view) }
    private var sessionManager: SessionManager? = null
    var textCartItemCount: TextView? = null
    var mCartItemCount = 10
    var notificationBadge: View? = null




    val welcomeViewModel by lazy {
        getViewModel { WelcomeViewModel() }
    }

//    fun makeHomeStart(role: Role){
//        navController.graph.startDestination = R.id.dashboardPartnerFragment
//    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val toolbar = findViewById<Toolbar>(R.id.toolbar)
        setSupportActionBar(toolbar)

        // Show and Manage the Drawer and Back Icon
        setupActionBarWithNavController(navController, drawerLayout)
//
//        // Handle Navigation item clicks
//        // This works with no further action on your part if the menu and destination id’s match.
        navigationView.setupWithNavController(navController)

        sessionManager = SessionManager(this)

        setupNavDrawer()
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.setDisplayShowHomeEnabled(true)

        showBottomNavigationView(false)


        navController.navigate(R.id.welcomeFragment)

        bottomNavigationView.setOnNavigationItemSelectedListener(onBottomNavigationItemSelectedListener)


//        val menuItem: MenuItem = bottomNavigationView.menu.findItem(R.id.menu_item_toolbar_notification_badge)
//        val actionView: View = menuItem.actionView
//        textCartItemCount = actionView.findViewById<View>(R.id.bottom_nav_menu_item_notification) as TextView

//        getBadge()
//        textCartItemCount = notificationBadge!!.findViewById<View>(R.id.textview_toolbar_noticiation_badge) as TextView
//        setupBadge()

//        actionView.setOnClickListener {
//            //onOptionsItemSelected(menuItem)
//            navController.navigate(R.id.notificationFragment)
//        }
        val badge = bottomNavigationView.getOrCreateBadge(R.id.bottom_nav_menu_item_notification)
        badge.isVisible = true
        badge.number = 12

        setToolbarFilterIcon(this, ToolbarAction.HIDE)
    }

    override fun onSupportNavigateUp(): Boolean {
        // Allows NavigationUI to support proper up navigation or the drawer layout
        // drawer menu, depending on the situation
        return navController.navigateUp(drawerLayout)
    }

    private fun setupNavDrawer() {
        navigationView.setNavigationItemSelectedListener {
            //            Toast.makeText(this@MainActivity, "Hello", Toast.LENGTH_SHORT).show()
            when (it.itemId) {
                R.id.menu_drawer_item_account      -> navController.navigate(R.id.accountFragment)
                R.id.menu_drawer_item_loan_request -> navController.navigate(R.id.loanApplicationFragment)
                R.id.menu_drawer_item_logout      -> navController.navigate(R.id.loginFragment)
            }
            drawerLayout.closeDrawer(GravityCompat.START)
            true
        }
    }

//    override fun onCreateOptionsMenu(menu: Menu): Boolean {
//        menuInflater.inflate(R.menu.menu, menu)
//        val menuItem: MenuItem = menu.findItem(R.id.menu_item_toolbar_notification_badge)
//        val actionView: View = menuItem.actionView
//        textCartItemCount = actionView.findViewById<View>(R.id.textview_toolbar_noticiation_badge) as TextView
//        setupBadge()
//        actionView.setOnClickListener {
//            //onOptionsItemSelected(menuItem)
//            navController.navigate(R.id.notificationFragment)
//        }
//        return true
//    }
    override fun onCreateOptionsMenu(menu: Menu): Boolean {
//        menuInflater.inflate(R.menu.menu, menu)
//        val menuItem: MenuItem = menu.findItem(R.id.menu_item_toolbar_notification_badge)
//        val actionView: View = menuItem.actionView
//        textCartItemCount = actionView.findViewById<View>(R.id.textview_toolbar_noticiation_badge) as TextView
//        setupBadge()
//        actionView.setOnClickListener {
//            //onOptionsItemSelected(menuItem)
//            navController.navigate(R.id.notificationFragment)
//        }
        return true
    }


    private fun setupBadge() {
        if (textCartItemCount != null) {
            if (mCartItemCount === 0) {
                if (textCartItemCount!!.visibility !== View.GONE) {
                    textCartItemCount!!.visibility = View.GONE
                }
            } else {
                textCartItemCount!!.text = String.valueOf(
                    Math.min(
                        mCartItemCount,
                        99
                    )
                )
                if (textCartItemCount!!.visibility !== View.VISIBLE) {
                    textCartItemCount!!.visibility = View.VISIBLE
                }
            }
        }
    }
//    override fun onCreateOptionsMenu(
//        menu: Menu?,
//        inflater: MenuInflater
//    ) {
//        inflater.inflate(R.menu.menu_sample, menu)
//        super.onCreateOptionsMenu(menu, inflater)
//    }

//    override fun onCreateOptionsMenu(menu: Menu): Boolean {
////        menuInflater.inflate(R.menu.menu, menu)
////        val menu_hotlist: View = menu.findItem(R.id.menu_hotlist).actionView
////        ui_hot = menu_hotlist.findViewById<View>(R.id.hotlist_hot) as TextView
////        updateHotCount(hot_number)
////        object : MyMenuItemStuffListener(menu_hotlist, "Show hot message") {
////            fun onClick(v: View?) {
////                onHotlistSelected()
////            }
////        }
//
//        var itemMessages = menu.findItem(R.id.menu_messages)
//
//        var badgeLayout = itemMessages.getActionView() as RelativeLayout
//        var itemMessagesBadgeTextView = badgeLayout.findViewById(R.id.badge_textView) as TextView
//        itemMessagesBadgeTextView.setVisibility(View.GONE) // initially hidden
//
//
//        var iconButtonMessages = badgeLayout.findViewById(R.id.badge_icon_button) as ImageView
////        iconButtonMessages.setText("{fa-envelope}")
////        iconButtonMessages.setTextColor(resources.getColor(R.color.colorAccent))
//
//        iconButtonMessages.setOnClickListener(View.OnClickListener {
////            if (HJSession.getSession().getSessionId() != null) {
////                val intent = Intent(getThis(), HJActivityMessagesContexts::class.java)
////                startActivityForResult(
////                    intent,
////                    HJRequestCodes.kHJRequestCodeActivityMessages.ordinal()
////                )
////            } else {
////                showLoginActivity()
////            }
//        })
//        return super.onCreateOptionsMenu(menu)
//    }

    private val onBottomNavigationItemSelectedListener = BottomNavigationView.OnNavigationItemSelectedListener { item ->
        when (item.itemId) {
            R.id.bottom_nav_menu_item_overview -> {
                navController.navigate(R.id.dashboardPartnerFragment)
                return@OnNavigationItemSelectedListener true
            }
            R.id.bottom_nav_menu_item_analytics -> {
                navController.navigate(R.id.analyticsFragment)
                return@OnNavigationItemSelectedListener true
            }
            R.id.bottom_nav_menu_item_investment -> {
                navController.navigate(R.id.investmentFragment)
                return@OnNavigationItemSelectedListener true
            }
            R.id.bottom_nav_menu_item_loans -> {
                navController.navigate(R.id.loanFragment)
                return@OnNavigationItemSelectedListener true
            }
            R.id.bottom_nav_menu_item_notification -> {
                navController.navigate(R.id.notificationFragment)
                return@OnNavigationItemSelectedListener true
            }
        }
        false
    }

    override fun showBottomNavigationView(value: Boolean) {
        if (value) { // hide bottom bar
            bottomNavigationView.visibility = View.VISIBLE
        } else { // show bottom bar
            bottomNavigationView.visibility = View.GONE
        }
    }

//    public fun showBottomNavigationViewvvv(value: Boolean) {
//        if (value) { // hide bottom bar
//            bottomNavigationView.visibility = View.VISIBLE
//        } else { // show bottom bar
//            bottomNavigationView.visibility = View.GONE
//        }
//    }

    private fun visibilityNavElements(navController: NavController) {
        navController.addOnDestinationChangedListener { _, destination, _ ->
            when (destination.id) {
                R.id.loginFragment -> bottomNavigationView?.visibility = View.GONE
                R.id.welcomeFragment -> bottomNavigationView?.visibility = View.GONE
                R.id.registrationFragment -> bottomNavigationView?.visibility = View.GONE
//                else -> bottom_nav?.visibility = View.VISIBLE
            }
        }
    }

    override fun onCompleted(completeButton: View?) {
        Toast.makeText(this, "onCompleted!", Toast.LENGTH_SHORT).show()
    }

    override fun onError(verificationError: VerificationError) {
        Toast.makeText(
            this,
            "onError! -> " + verificationError.errorMessage,
            Toast.LENGTH_SHORT
        ).show()
    }

    override fun onStepSelected(newStepPosition: Int) {
        Toast.makeText(this, "onStepSelected! -> $newStepPosition", Toast.LENGTH_SHORT).show()
    }

    override fun onReturn() {
        finish()
    }

    fun showBottomNav() {
        bottomNavigationView.visibility = View.VISIBLE

    }

    fun hideBottomNav() {
        bottomNavigationView.visibility = View.GONE

    }

    override fun setup(role: Role) {
//        val drawer = drawerLayout // findViewById<DrawerLayout>(R.id.drawer_layout)
//        val toggle = ActionBarDrawerToggle(
//            this,
//            drawer,
//            toolbar,
//            R.string.navigation_drawer_open,
//            R.string.navigation_drawer_close
//        )
//        drawer.addDrawerListener(toggle)
//        toggle.isDrawerIndicatorEnabled = true
//        toggle.syncState()
//        Log.d("Tag", "in setup")
        if (role === Role.USER) {
            bottomNavigationView.menu.findItem(R.id.bottom_nav_menu_item_analytics).isVisible  = false
            bottomNavigationView.menu.findItem(R.id.bottom_nav_menu_item_investment).isVisible = false
        }else {
            bottomNavigationView.menu.findItem(R.id.bottom_nav_menu_item_analytics).isVisible  = true
            bottomNavigationView.menu.findItem(R.id.bottom_nav_menu_item_investment).isVisible = true
        }
    }

    private fun getBadge() : View {
        if (notificationBadge != null ){
            return  notificationBadge!!
        }
        val bottomNavigationMenuView = bottomNavigationView.getChildAt(0) as BottomNavigationMenuView
        notificationBadge = LayoutInflater.from(this).inflate(R.layout.layout_toolbar_badge_layout, bottomNavigationMenuView, false)

        return notificationBadge!!
    }




}
