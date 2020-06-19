package com.example.lendvest.activities

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.appcompat.widget.Toolbar
import androidx.core.view.GravityCompat
import androidx.drawerlayout.widget.DrawerLayout
import androidx.navigation.findNavController
import androidx.navigation.ui.navigateUp
import androidx.navigation.ui.setupActionBarWithNavController
import androidx.navigation.ui.setupWithNavController
import com.example.lendvest.R
import com.example.lendvest.models.ui.WelcomeViewModel
import com.example.lendvest.utils.getViewModel
import com.example.lendvest.utils.showBottomBar
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.google.android.material.navigation.NavigationView

interface OnLaunchScreenComplete{
    fun showBottomNavigationView(value: Boolean)
}

class MainActivity : AppCompatActivity(), OnLaunchScreenComplete {

    val drawerLayout by lazy { findViewById<DrawerLayout>(R.id.drawer_layout) }
    val navController by lazy { findNavController(R.id.navigation_host_fragment) }
    val navigationView by lazy { findViewById<NavigationView>(R.id.navigationView) }
    val bottomNavigationView by lazy { findViewById<BottomNavigationView>(R.id.bottom_navigation_view) }

    val welcomeViewModel by lazy {
        getViewModel { WelcomeViewModel() }
    }

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

        setupNavDrawer()
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.setDisplayShowHomeEnabled(true)

//        showBottomNavigationView(false)
        navController.navigate(R.id.welcomeFragment)

        bottomNavigationView.setOnNavigationItemSelectedListener(onBottomNavigationItemSelectedListener)
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
            }
            drawerLayout.closeDrawer(GravityCompat.START)
            true
        }
    }

    private fun setUpEvents() {

    }

    private val onBottomNavigationItemSelectedListener = BottomNavigationView.OnNavigationItemSelectedListener { item ->
        when (item.itemId) {
            R.id.bottom_nav_menu_item_overview -> {
                navController.navigate(R.id.dashboardFragment)
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
}
