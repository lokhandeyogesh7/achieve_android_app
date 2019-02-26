package com.sanswai.achieve.global

import android.annotation.SuppressLint
import android.content.IntentFilter
import android.net.ConnectivityManager
import android.os.Bundle
import android.support.v7.app.AlertDialog
import android.support.v7.app.AppCompatActivity
import android.widget.Toast
import com.sanswai.achieve.network.VolleyService
import android.content.DialogInterface



@SuppressLint("Registered")
open class BaseActivity : AppCompatActivity(), ConnectivityReceiver.ConnectivityReceiverListener {

    private var mVolleyService: VolleyService? = null
    private val connectionBroadcast = ConnectivityReceiver()
    var isOnline: Boolean = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mVolleyService = VolleyService(this)
    }

    override fun onResume() {
        super.onResume()
        ConnectivityReceiver.connectivityReceiverListener = this
        registerReceiver(connectionBroadcast,
                IntentFilter(ConnectivityManager.CONNECTIVITY_ACTION))
    }

    override fun onNetworkConnectionChanged(isConnected: Boolean) {
        isOnline = isConnected
        if (isConnected) {
            //showToast("You are connected to internet")
        } else {
           // showToast("You are disconnected from internet")
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        try {
            if (true) {
                unregisterReceiver(connectionBroadcast)
            }
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

    /**
     * @param message
     */
    fun showToast(message: String) {
        Toast.makeText(this@BaseActivity, message, Toast.LENGTH_SHORT).show()
    }

    fun showNoInternetDialog(){
        AlertDialog.Builder(this)
                .setTitle("No Internet")
                .setMessage("You do not have internet connection, please connect to your internet.")
                .setPositiveButton(android.R.string.ok) { dialog, which ->
                    dialog.dismiss()
                }
                .setCancelable(false)
                .show()
    }
}