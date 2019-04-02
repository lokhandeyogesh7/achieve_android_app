package com.sanswai.achieve.fragment

import android.os.Bundle
import android.support.v4.app.Fragment
import android.text.Html
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.google.gson.Gson
import com.sanswai.achieve.R
import com.sanswai.achieve.global.Preferences
import com.sanswai.achieve.network.VolleyService
import com.sanswai.achieve.response.employeedetails.EmployeeDetails
import kotlinx.android.synthetic.main.fragment_personal_details.*
import java.lang.Exception

class PersonalDetailsFragment : Fragment() {

    var services: VolleyService? = null
    var preferences: Preferences? = null

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_personal_details, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val htmlString = "Male \n single \n Nashik"
        tvPersonalInfo.text = Html.fromHtml(htmlString)
        services= VolleyService(activity!!)
        preferences = Preferences.getInstance(activity!!)

        val jsonResponse = preferences?.getPreferencesString(getString(R.string.pref_employee_details))
        val responseObject = Gson().fromJson(jsonResponse,EmployeeDetails::class.java)

        try {
            tvPersonalInfo.text = responseObject.personalDetails?.response
            tvCurrentAddress.text = responseObject.personalDetails?.response
            tvPerAddress.text = responseObject.personalDetails?.response
        }catch (e:Exception){
            e.printStackTrace()
        }

    }
}
