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
import com.sanswai.achieve.adapter.SkillsAdapter
import com.sanswai.achieve.global.Preferences
import com.sanswai.achieve.network.VolleyService
import com.sanswai.achieve.response.employeedetails.Datum____
import com.sanswai.achieve.response.employeedetails.Datum_____
import com.sanswai.achieve.response.employeedetails.EmployeeDetails
import kotlinx.android.synthetic.main.activity_emp_profile.*
import kotlinx.android.synthetic.main.fragment_key_kills.*

class KeySkillFragment : Fragment() {

    private var keySkills: ArrayList<Datum____>? = null
    private var userSkills: ArrayList<Datum_____>? = null
    var adapter: SkillsAdapter? = null
    var services: VolleyService? = null
    var preferences: Preferences? = null

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_key_kills, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        keySkills = ArrayList()
        userSkills = ArrayList()
        services = VolleyService(activity!!)
        preferences = Preferences.getInstance(activity!!)

        prepareProjectList()
    }

    private fun prepareProjectList() {
        val jsonResponse = preferences?.getPreferencesString(getString(R.string.pref_employee_details))
        val responseObject = Gson().fromJson(jsonResponse, EmployeeDetails::class.java)
        keySkills = responseObject?.mstSkill?.data as ArrayList<Datum____>?
        userSkills = responseObject?.userSkill?.data as ArrayList<Datum_____>
        if (keySkills != null) {
            adapter = SkillsAdapter("key", keySkills, userSkills!!)
            val mLayoutManager = LinearLayoutManager(activity, LinearLayoutManager.VERTICAL, false)
            rvKeySkills.layoutManager = mLayoutManager
            rvKeySkills.itemAnimator = DefaultItemAnimator()
            rvKeySkills.adapter = adapter
        }
        if (userSkills != null) {
            adapter = SkillsAdapter("user", keySkills, userSkills!!)
            val mLayoutManager = LinearLayoutManager(activity, LinearLayoutManager.VERTICAL, false)
            rvUSerSkills.layoutManager = mLayoutManager
            rvUSerSkills.itemAnimator = DefaultItemAnimator()
            rvUSerSkills.adapter = adapter
        }
        if (responseObject.mstSkill?.response == "false") {
            (activity as EmpProfileActivity).fabPersonalDetails.setImageResource(R.drawable.ic_plus_black_symbol)
        }else{
            (activity as EmpProfileActivity).fabPersonalDetails.setImageResource(R.drawable.ic_pencil_edit_button)
        }
    }
}
