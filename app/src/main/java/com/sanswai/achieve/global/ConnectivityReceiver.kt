package com.sanswai.achieve.global

import android.annotation.SuppressLint
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.net.ConnectivityManager

/**
 * Created by yogesh.lokhande on 15-03-2018.
 */
class ConnectivityReceiver : BroadcastReceiver() {

    @SuppressLint("UnsafeProtectedBroadcastReceiver")
    override fun onReceive(context: Context, arg1: Intent) {

        if (connectivityReceiverListener != null) {
            try {
                connectivityReceiverListener!!.onNetworkConnectionChanged(isConnectedOrConnecting(context))
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
    }

    private fun isConnectedOrConnecting(context: Context): Boolean {
        return try {
            val connMgr = context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
            val networkInfo = connMgr.activeNetworkInfo
            status = networkInfo != null && networkInfo.isConnectedOrConnecting
            status
        }catch (e:Exception){
            e.printStackTrace()
            false
        }
    }

    interface ConnectivityReceiverListener {
        fun onNetworkConnectionChanged(isConnected: Boolean)
    }
        companion object {
        var connectivityReceiverListener: ConnectivityReceiverListener? = null
        @JvmStatic var status = false
    }
}