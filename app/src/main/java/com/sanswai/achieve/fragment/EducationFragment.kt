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
import com.sanswai.achieve.adapter.EducationAdapter
import com.sanswai.achieve.global.Preferences
import com.sanswai.achieve.network.VolleyService
import com.sanswai.achieve.response.employeedetails.Datum
import com.sanswai.achieve.response.employeedetails.EmployeeDetails
import kotlinx.android.synthetic.main.activity_emp_profile.*
import kotlinx.android.synthetic.main.fragment_education.*

class EducationFragment : Fragment() {

    private var educationList: ArrayList<Datum>? = null
    var adapter: EducationAdapter? = null
    var services: VolleyService? = null
    var preferences: Preferences? = null

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_education, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        educationList = ArrayList()


        services= VolleyService(activity!!)
        preferences = Preferences.getInstance(activity!!)

        prepareProjectList()
    }

    private fun prepareProjectList() {
        val jsonResponse = preferences?.getPreferencesString(getString(R.string.pref_employee_details))
        val responseObject = Gson().fromJson(jsonResponse, EmployeeDetails::class.java)
        educationList = responseObject?.education?.data as ArrayList<Datum>?
        if (educationList != null) {
            adapter = EducationAdapter(activity!!, educationList)
            val mLayoutManager = LinearLayoutManager(activity, LinearLayoutManager.VERTICAL, false)
            rvEducation.layoutManager = mLayoutManager
            rvEducation.itemAnimator = DefaultItemAnimator()
            rvEducation.adapter = adapter
        }
        if (responseObject.education?.response == "false") {
            fabEducation.setImageResource(R.drawable.ic_plus_black_symbol)
        }else{
            fabEducation.setImageResource(R.drawable.ic_pencil_edit_button)
        }
    }
}
