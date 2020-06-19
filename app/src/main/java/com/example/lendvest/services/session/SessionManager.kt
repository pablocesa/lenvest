package com.example.lendvest.services.session

import android.content.Context
import android.content.SharedPreferences
import android.preference.PreferenceManager
import android.util.Log
import com.example.lendvest.models.data.Role


class SessionManager(context: Context) {

    // Shared Preferences
    var sharedPrefer: SharedPreferences? = null

    // Editor for Shared preferences
    var editor: SharedPreferences.Editor? = null

    // Context
    var context: Context? = null

    init {
        this.context = context
        sharedPrefer = context.getSharedPreferences(Constants.PREF_NAME, Constants.PRIVATE_MODE)
        editor = sharedPrefer!!.edit()
    }

    /**
     * Set the Login Status
     * @param context
     * @param loggedIn
     */
    fun setLoggedIn(userId: String, userRole: String) {
        // Storing userId in pref
        editor!!.putString(Constants.KEY_USERID, userId)

        // Storing catId in pref
        editor!!.putString(Constants.KEY_USER_ROLE, userRole)

        // commit changes
        editor!!.commit()
    }

    fun destroy() {
        // Storing userId in pref
        editor!!.remove(Constants.KEY_USERID)

        // Storing catId in pref
        editor!!.remove(Constants.KEY_USER_ROLE)

        // commit changes
        editor!!.commit()
    }

    /**
     * Get the Login Status
     * @param context
     * @return boolean: login status
     */
    fun isLoggedIn(): Boolean {

        if (sharedPrefer != null) {
            return ((sharedPrefer!!.getString(Constants.KEY_USERID, "") != "")
                    && (sharedPrefer!!.getString(Constants.KEY_USER_ROLE, "") != ""))
        }
        return false
    }

    fun getRole() : Role? {
        return Role.valueOf(sharedPrefer!!.getString(Constants.KEY_USER_ROLE, ""))
    }
}