package com.example.anshtest.network

import com.android.volley.Request
import com.android.volley.Response
import com.android.volley.VolleyError
import com.android.volley.toolbox.JsonObjectRequest
import com.example.anshtest.global.AchieveApplication
import org.json.JSONObject

class VolleyService {

    lateinit var mResponseInterface: setResponse

    interface setResponse {
        fun onSuccess(response: Any)
        fun onFailure(volleyError: VolleyError)
    }

    fun callJsonObjectRequest(url: String, methodName: String) {
        val jsonObjReq = JsonObjectRequest(Request.Method.GET,
                url, null,
                Response.Listener<JSONObject> { response ->
                    println("success $response")
                    mResponseInterface.onSuccess(response)
                },
                Response.ErrorListener { volleyError ->
                    println("failed ${volleyError.networkResponse}")
                    mResponseInterface.onFailure(volleyError)
                })
        AchieveApplication.instance?.addToRequestQueue(jsonObjReq, methodName)
    }
}