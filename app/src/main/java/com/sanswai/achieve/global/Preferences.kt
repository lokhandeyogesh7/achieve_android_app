package com.sanswai.achieve.global

import android.annotation.SuppressLint
import android.app.Activity
import android.content.Context
import android.content.SharedPreferences
import com.sanswai.achieve.R
import java.io.Serializable

class Preferences : Serializable {

    fun getPreferencesFloat(key: String, type: Float): Float {
        return appSharedPrefs!!.getFloat(key, type)
    }

    fun getPreferencesBoolean(key: String, type: Boolean): Boolean {
        return appSharedPrefs!!.getBoolean(key, type)
    }

    fun setPreferencesBody(key: String, text: String) {
        prefsEditor!!.putString(key, text)
        prefsEditor!!.commit()
    }

    fun setPreferencesBody(key: String, state: Boolean) {
        prefsEditor!!.putBoolean(key, state)
        prefsEditor!!.commit()
    }

    fun setPreferencesBody(key: String, value: Float) {
        prefsEditor!!.putFloat(key, value)
        prefsEditor!!.commit()
    }

    fun setPreferencesBody(key: String, value: Long) {
        prefsEditor!!.putLong(key, value)
        prefsEditor!!.commit()
    }

    fun setPreferencesBody(key: String, value: Double) {
        prefsEditor!!.putLong(key, java.lang.Double.doubleToRawLongBits(value))
        prefsEditor!!.commit()
    }

    fun getPreferencesDouble(key: String, value: Double): Double {
        return java.lang.Double.longBitsToDouble(getPreferencesLong(key, java.lang.Double.doubleToLongBits(value)))
    }

    fun getPreferencesLong(key: String, type: Long): Long {
        return appSharedPrefs!!.getLong(key, type)
    }

    fun setPreferencesBody(arrayName: String, array: Array<String>): Boolean {
        setPreferencesBody(arrayName + "_size", array.size)
        for (i in array.indices)
            prefsEditor!!.putString(arrayName + "_" + i, array[i])
        return prefsEditor!!.commit()
    }

    fun setPreferencesBody(key: String, value: Int) {
        prefsEditor!!.putInt(key, value)
        prefsEditor!!.commit()
    }

    fun getPreferencesInt(key: String, type: Int): Int {
        return appSharedPrefs!!.getInt(key, type)
    }

    fun getPreferencesString(key: String): String? {
        return appSharedPrefs!!.getString(key, "")
    }

    operator fun contains(key: String): Boolean {
        return appSharedPrefs!!.contains(key)
    }

    fun clearPreferences() {
        prefsEditor!!.clear()
        prefsEditor!!.commit()
    }

    @SuppressLint("StaticFieldLeak")
    companion object {
        private val serialVersionUID = 1L
        private var context: Context? = null
        private var appSharedPrefs: SharedPreferences? = null
        private var prefsEditor: SharedPreferences.Editor? = null
        private var instance: Preferences? = null

        fun getContext(): Context? {
            return context
        }

        fun setContext(context: Context) {
            Preferences.context = context
        }

        fun getInstance(): Preferences {
            if (null == instance) {
                instance = Preferences()
            }

            if (context == null) {
                return instance as Preferences
            }
            appSharedPrefs = context!!.getSharedPreferences(context!!.getString(R.string.preference_name), Activity
                    .MODE_PRIVATE)
            prefsEditor = appSharedPrefs!!.edit()

            return instance as Preferences
        }

        fun getInstance(context: Context): Preferences {
            if (null == instance) {
                instance = Preferences()
            }

            appSharedPrefs = context.getSharedPreferences(context!!.getString(R.string.preference_name), Activity
                    .MODE_PRIVATE)
            prefsEditor = appSharedPrefs!!.edit()

            return instance as Preferences
        }
    }

}