package com.sanswai.achieve.fragment


import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.widget.DefaultItemAnimator
import android.support.v7.widget.LinearLayoutManager
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.google.gson.Gson
import com.sanswai.achieve.R
import com.sanswai.achieve.activity.EditPersonalDetailsActivity
import com.sanswai.achieve.activity.EditResumeHeadlineActivity
import com.sanswai.achieve.activity.EmpProfileActivity
import com.sanswai.achieve.adapter.ProjectsAdapter
import com.sanswai.achieve.global.Preferences
import com.sanswai.achieve.network.VolleyService
import com.sanswai.achieve.response.employeedetails.Datum__
import com.sanswai.achieve.response.employeedetails.EmployeeDetails
import kotlinx.android.synthetic.main.activity_emp_profile.*
import kotlinx.android.synthetic.main.fragment_personal_details.*
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

    @SuppressLint("RestrictedApi")
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        projectsList = ArrayList()

        services = VolleyService(activity!!)
        preferences = Preferences.getInstance(activity!!)

        if (preferences?.getPreferencesString(getString(R.string.user_type)) == "employee") {
            fabProjects.visibility = View.VISIBLE
        } else {
            fabProjects.visibility = View.GONE
        }


        prepareProjectList()
    }

    private fun prepareProjectList() {
        val jsonResponse = preferences?.getPreferencesString(getString(R.string.pref_employee_details))
        println("project $jsonResponse")
        val responseObject = Gson().fromJson(jsonResponse, EmployeeDetails::class.java)
        projectsList = responseObject?.project?.data as ArrayList<Datum__>?

        if (projectsList != null) {
            adapter = ProjectsAdapter(activity!!, projectsList!!)

            val mLayoutManager = LinearLayoutManager(activity, LinearLayoutManager.VERTICAL, false)
            rvProjects.layoutManager = mLayoutManager
            rvProjects.itemAnimator = DefaultItemAnimator()
            rvProjects.adapter = adapter
        }
        fabProjects.bringToFront()
        fabProjects.setImageResource(R.drawable.ic_plus_black_symbol)

        (fabProjects.setOnClickListener {
            println("on click reference is " + (activity as EmpProfileActivity).viewPager.currentItem)
            if ((activity as EmpProfileActivity).viewPager.currentItem == 4) {
                startActivity(Intent(activity, EditResumeHeadlineActivity::class.java).putExtra(getString(R.string.fromProjects), true).putExtra(getString(R.string.project_id), "new"))
            }
        })

    }
}
