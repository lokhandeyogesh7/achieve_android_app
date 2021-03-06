@file:Suppress("RECEIVER_NULLABILITY_MISMATCH_BASED_ON_JAVA_ANNOTATIONS")

package com.sanswai.achieve.network

import android.annotation.SuppressLint
import android.app.Dialog
import android.content.Context
import android.view.Window
import com.android.volley.Request
import com.android.volley.Response
import com.android.volley.VolleyError
import com.android.volley.toolbox.JsonObjectRequest
import com.sanswai.achieve.global.AchieveApplication
import org.json.JSONObject
import com.android.volley.DefaultRetryPolicy


class VolleyService(val context: Context) {

    lateinit var mResponseInterface: SetResponse

    interface SetResponse {
        fun onSuccess(methodName:String,response: Any)
        fun onFailure(methodName:String,volleyError: VolleyError)
    }

    @SuppressLint("InflateParams")
    fun callJsonObjectRequest(methodName: String, jsonObject: JSONObject) {

        val dialog = Dialog(context)
        dialog.window.requestFeature(Window.FEATURE_NO_TITLE)
        dialog.setContentView(com.sanswai.achieve.R.layout.layout_loading)
        dialog.show()
        dialog.setCancelable(false)

        val requestUrl = context.getString(com.sanswai.achieve.R.string.base_url)+methodName

        println("requested url is $requestUrl jsonobject is $jsonObject")

        val jsonObjReq = JsonObjectRequest(Request.Method.POST,
                requestUrl, jsonObject,
                Response.Listener<JSONObject> {
                    println("success $it")
                    dialog.dismiss()
                    mResponseInterface.onSuccess(methodName,it)
                },
                Response.ErrorListener {
                    println("failed ${it.localizedMessage}")
                    dialog.dismiss()
                    mResponseInterface.onFailure(methodName,it)
                })
        jsonObjReq.retryPolicy = DefaultRetryPolicy(
                30000,
                DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                DefaultRetryPolicy.DEFAULT_BACKOFF_MULT)
        AchieveApplication.instance?.addToRequestQueue(jsonObjReq, methodName)
    }

    @SuppressLint("InflateParams")
    fun callJsonGETRequest(methodName: String, jsonObject: JSONObject) {

        val dialog = Dialog(context)
        dialog.window.requestFeature(Window.FEATURE_NO_TITLE)
        dialog.setContentView(com.sanswai.achieve.R.layout.layout_loading)
        dialog.show()

        val requestUrl = context.getString(com.sanswai.achieve.R.string.base_url)+methodName

        println("requested url is $requestUrl jsonobject is $jsonObject")

        val jsonObjReq = JsonObjectRequest(Request.Method.GET,
                requestUrl, jsonObject,
                Response.Listener<JSONObject> {
                    println("success $it")
                    dialog.dismiss()
                    mResponseInterface.onSuccess(methodName,it)
                },
                Response.ErrorListener {
                    println("failed ${it.networkResponse}")
                    dialog.dismiss()
                    mResponseInterface.onFailure(methodName,it)
                })
        jsonObjReq.retryPolicy = DefaultRetryPolicy(
                30000,
                DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                DefaultRetryPolicy.DEFAULT_BACKOFF_MULT)
        AchieveApplication.instance?.addToRequestQueue(jsonObjReq, methodName)
    }
}