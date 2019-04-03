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
import com.sanswai.achieve.adapter.ProjectsAdapter
import com.sanswai.achieve.global.Preferences
import com.sanswai.achieve.network.VolleyService
import com.sanswai.achieve.response.employeedetails.Datum__
import com.sanswai.achieve.response.employeedetails.EmployeeDetails
import kotlinx.android.synthetic.main.fragment_projects.*

class ProjectsFragment : Fragment() {
    var projectsList: ArrayList<Datum__>? = null
    var adapter: ProjectsAdapter? = null
    var services: VolleyService? = null
    var preferences: Preferences? = null

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_projects, container, false)
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
        println("project $jsonResponse")
        val responseObject = Gson().fromJson(jsonResponse, EmployeeDetails::class.java)
        projectsList = responseObject?.project?.data as ArrayList<Datum__>?

        if (projectsList != null) {
            adapter = ProjectsAdapter(projectsList!!)

            val mLayoutManager = LinearLayoutManager(activity, LinearLayoutManager.VERTICAL, false)
            rvProjects.layoutManager = mLayoutManager
            rvProjects.itemAnimator = DefaultItemAnimator()
            rvProjects.adapter = adapter
        }

    }
}
