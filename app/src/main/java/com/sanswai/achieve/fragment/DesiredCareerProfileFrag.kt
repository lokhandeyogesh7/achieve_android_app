package com.sanswai.achieve.fragment

import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.widget.DefaultItemAnimator
import android.support.v7.widget.LinearLayoutManager
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.google.gson.Gson
import com.sanswai.achieve.R
import com.sanswai.achieve.activity.EmpProfileActivity
import com.sanswai.achieve.adapter.DesiredCareerAdapter
import com.sanswai.achieve.global.BaseActivity
import com.sanswai.achieve.global.Preferences
import com.sanswai.achieve.network.VolleyService
import com.sanswai.achieve.response.employeedetails.Datum___
import com.sanswai.achieve.response.employeedetails.EmployeeDetails
import kotlinx.android.synthetic.main.activity_emp_profile.*
import kotlinx.android.synthetic.main.fragment_desired_career_path.*

class DesiredCareerProfileFrag : Fragment() {

    private var educationList: ArrayList<Datum___>? = null
    var adapter: DesiredCareerAdapter? = null
    var services: VolleyService? = null
    var preferences: Preferences? = null

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_desired_career_path, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        services= VolleyService(activity!!)
        preferences = Preferences.getInstance(activity!!)
        prepareProjectList()
    }

    private fun prepareProjectList() {
        val jsonResponse = preferences?.getPreferencesString(getString(R.string.pref_employee_details))
        val responseObject = Gson().fromJson(jsonResponse, EmployeeDetails::class.java)

        educationList = responseObject?.desiredProfile?.data as ArrayList<Datum___>?
        if (educationList != null) {
            adapter = DesiredCareerAdapter(activity!!, educationList)
            val mLayoutManager = LinearLayoutManager(activity, LinearLayoutManager.VERTICAL, false)
            rvDesiredPath.layoutManager = mLayoutManager
            rvDesiredPath.itemAnimator = DefaultItemAnimator()
            rvDesiredPath.adapter = adapter
        }

        ((activity as EmpProfileActivity).fabPersonalDetails.setOnClickListener {
            (activity as BaseActivity).showToast("clicked toast 123")
        })
        if (responseObject.desiredProfile?.response == "false") {
            (activity as EmpProfileActivity).fabPersonalDetails.setImageResource(R.drawable.ic_plus_black_symbol)
        }else{
            (activity as EmpProfileActivity).fabPersonalDetails.setImageResource(R.drawable.ic_pencil_edit_button)
        }
    }
}
