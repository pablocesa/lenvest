package com.example.lendvest.fragments

import android.graphics.drawable.Drawable
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import com.example.lendvest.R
import com.example.lendvest.services.session.SessionManager

open class SuperFragment: Fragment() {

    protected var sessionManager: SessionManager? = null

    fun getFragmentActivity(): AppCompatActivity {
        return activity as AppCompatActivity
    }

    fun getDrawable(resourceId: Int) : Drawable? {
        return ContextCompat.getDrawable(getFragmentActivity(), resourceId)
    }

    open fun onBackPressed() {}
}