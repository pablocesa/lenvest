package com.example.lendvest.utils

import android.view.View
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.NavHostFragment
import com.example.lendvest.R
import com.google.android.material.bottomnavigation.BottomNavigationView

fun setViewTitle(activity: AppCompatActivity, title: String){
    val toolbar = activity.findViewById<androidx.appcompat.widget.Toolbar>(
        R.id.toolbar)
    val textView = toolbar.findViewById<TextView>(R.id.toolbar_title)

    textView.text = title
    activity.supportActionBar!!.setDisplayShowTitleEnabled(false)
}

fun getColor(activity: AppCompatActivity, id: Int): Int {
    return ContextCompat.getColor(activity, id)
}

fun showBottomBar(bottomNavigationView: BottomNavigationView, show: Boolean) {
    if (show) { // hide bottom bar
        bottomNavigationView.visibility = View.VISIBLE
    } else { // show bottom bar
        bottomNavigationView.visibility = View.GONE
    }
}

@Suppress("UNCHECKED_CAST")
fun <F : Fragment> AppCompatActivity.getFragment(fragmentClass: Class<F>): F? {
    val navHostFragment = this.supportFragmentManager.fragments.first() as NavHostFragment

    navHostFragment.childFragmentManager.fragments.forEach {
        if (fragmentClass.isAssignableFrom(it.javaClass)) {
            return it as F
        }
    }

    return null
}

