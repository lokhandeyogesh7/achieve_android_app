@file:Suppress("RECEIVER_NULLABILITY_MISMATCH_BASED_ON_JAVA_ANNOTATIONS")

package com.sanswai.achieve.network

import android.annotation.SuppressLint
import android.app.Dialog
import android.content.Context
import android.view.Window
import android.widget.Toast
import com.android.volley.DefaultRetryPolicy
import com.android.volley.Request
import com.android.volley.Response
import com.android.volley.error.VolleyError
import com.android.volley.request.JsonObjectRequest
import com.android.volley.request.SimpleMultiPartRequest
import com.sanswai.achieve.R
import com.sanswai.achieve.global.AchieveApplication
import com.sanswai.achieve.global.Preferences
import org.json.JSONException
import org.json.JSONObject


class VolleyService(val context: Context) {

    lateinit var mResponseInterface: SetResponse

    interface SetResponse {
        fun onSuccess(methodName: String, response: Any)
        fun onFailure(methodName: String, volleyError: VolleyError)
    }

    @SuppressLint("InflateParams")
    fun callJsonObjectRequest(methodName: String, jsonObject: JSONObject) {

        val dialog = Dialog(context)
        dialog.window.requestFeature(Window.FEATURE_NO_TITLE)
        dialog.setContentView(com.sanswai.achieve.R.layout.layout_loading)
        if(dialog !=null) {
            if(dialog.isShowing) {
                dialog.dismiss()
            }
            dialog.show()
        }


        dialog.setCancelable(false)

        val requestUrl = context.getString(com.sanswai.achieve.R.string.base_url) + methodName

        println("requested url is $requestUrl jsonobject is $jsonObject")

        val jsonObjReq = JsonObjectRequest(Request.Method.POST,
                requestUrl, jsonObject,
                Response.Listener<JSONObject> {
                    println("success $it")
                    dialog.dismiss()
                    mResponseInterface.onSuccess(methodName, it)
                },
                Response.ErrorListener {
                    println("failed ${it.localizedMessage}")
                    dialog.dismiss()
                    mResponseInterface.onFailure(methodName, it)
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

        val requestUrl = context.getString(com.sanswai.achieve.R.string.base_url) + methodName

        println("requested url is $requestUrl jsonobject is $jsonObject")

        val jsonObjReq = JsonObjectRequest(Request.Method.GET,
                requestUrl, jsonObject,
                Response.Listener<JSONObject> {
                    val preferences = Preferences.getInstance(context)
                    val jsonResponse = preferences?.getPreferencesString(context.getString(R.string.pref_employee_details))
                    println(" success pref "+jsonResponse)
                    println("success $it")
                    dialog.dismiss()
                    mResponseInterface.onSuccess(methodName, it)
                },
                Response.ErrorListener {
                    println("failed ${it.networkResponse}")
                    dialog.dismiss()
                    mResponseInterface.onFailure(methodName, it)
                })
        jsonObjReq.retryPolicy = DefaultRetryPolicy(
                30000,
                DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                DefaultRetryPolicy.DEFAULT_BACKOFF_MULT)
        AchieveApplication.instance?.addToRequestQueue(jsonObjReq, methodName)
    }

    fun postMultipartRequest(url: String, imagePath: String) {

        val dialog = Dialog(context)
        dialog.window.requestFeature(Window.FEATURE_NO_TITLE)
        dialog.setContentView(com.sanswai.achieve.R.layout.layout_loading)
        dialog.show()

        println("resume path "+imagePath)

        val requestUrl = context.getString(com.sanswai.achieve.R.string.base_url) + url
        val smr = SimpleMultiPartRequest(Request.Method.POST, requestUrl,
                Response.Listener { response ->
                    try {
                        println("response is image upload"+response)
                        dialog.dismiss()
                        mResponseInterface.onSuccess(url, response)

                    } catch (e: JSONException) {
                        // JSON error
                        e.printStackTrace()
                        dialog.dismiss()
                        Toast.makeText(context, "Json error: " + e.message, Toast.LENGTH_LONG).show()
                    }
                }, Response.ErrorListener { error ->
            dialog.dismiss()
            Toast.makeText(context, error.message, Toast.LENGTH_LONG).show() })

        if (url.contains("resume")){
            smr.addFile("resume_name", imagePath)
        }else {
            smr.addFile("image_name", imagePath)
        }
        AchieveApplication.instance?.addToRequestQueue(smr)

    }

}