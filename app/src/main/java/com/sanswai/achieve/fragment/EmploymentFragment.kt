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
import com.sanswai.achieve.adapter.EmploymentAdapter
import com.sanswai.achieve.global.Preferences
import com.sanswai.achieve.network.VolleyService
import com.sanswai.achieve.response.employeedetails.Datum_
import com.sanswai.achieve.response.employeedetails.EmployeeDetails
import kotlinx.android.synthetic.main.activity_emp_profile.*
import kotlinx.android.synthetic.main.fragment_employment.*

class EmploymentFragment : Fragment() {

    private var projectsList: ArrayList<Datum_>? = null
    var adapter: EmploymentAdapter? = null
    var services: VolleyService? = null
    var preferences: Preferences? = null

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_employment, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        projectsList = ArrayList()


        services = VolleyService(activity!!)
        preferences = Preferences.getInstance(activity!!)

        prepareProjectList()
    }

    private fun prepareProjectList() {
        val jsonResponse = preferences?.getPreferencesString(getString(R.string.pref_employee_details))
        val responseObject = Gson().fromJson(jsonResponse, EmployeeDetails::class.java)
        projectsList = responseObject?.employement?.data as ArrayList<Datum_>?

        if (projectsList != null) {
            adapter = EmploymentAdapter(projectsList!!)
            val mLayoutManager = LinearLayoutManager(activity, LinearLayoutManager.VERTICAL, false)
            rvEmployments.layoutManager = mLayoutManager
            rvEmployments.itemAnimator = DefaultItemAnimator()
            rvEmployments.adapter = adapter
        }
        if (responseObject.employement?.response == "false") {
            fabEmployment.setImageResource(R.drawable.ic_plus_black_symbol)
        }else{
            fabEmployment.setImageResource(R.drawable.ic_pencil_edit_button)
        }
    }
}
