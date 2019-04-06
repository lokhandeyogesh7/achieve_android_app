package com.sanswai.achieve.fragment

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.google.gson.Gson
import com.sanswai.achieve.R
import com.sanswai.achieve.activity.EditResumeHeadlineActivity
import com.sanswai.achieve.activity.EmpProfileActivity
import com.sanswai.achieve.global.BaseActivity
import com.sanswai.achieve.global.Preferences
import com.sanswai.achieve.network.VolleyService
import com.sanswai.achieve.response.employeedetails.EmployeeDetails
import kotlinx.android.synthetic.main.activity_emp_profile.*
import kotlinx.android.synthetic.main.fragment_resume_headline.*


class ResumeHeadlineFragment : Fragment() {

    var services: VolleyService? = null
    var preferences: Preferences? = null

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(com.sanswai.achieve.R.layout.fragment_resume_headline, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        services = VolleyService(activity!!)
        preferences = Preferences.getInstance(activity!!)
        val jsonResponse = preferences?.getPreferencesString(getString(com.sanswai.achieve.R.string.pref_employee_details))
        val responseObject = Gson().fromJson(jsonResponse, EmployeeDetails::class.java)
        lblResumeHeadline.text = responseObject?.resumeHeadline?.data?.tittle
        tvPersonalInfo.text = responseObject?.resumeHeadline?.data?.description

        if (responseObject.resumeHeadline!!.response == "false") {
            rlResumeHeadline.visibility = View.GONE
        }else{
            rlResumeHeadline.visibility = View.VISIBLE
        }

        tvDownloadResume.setOnClickListener {
            if (responseObject?.users?.data?.resumeFile != null) {
                val browserIntent = Intent(Intent.ACTION_VIEW, Uri.parse(responseObject?.users?.data?.resumeFile))
                startActivity(browserIntent)
            }
        }
        if (responseObject.resumeHeadline?.response == "false") {
            fabResume.setImageResource(R.drawable.ic_plus_black_symbol)
        } else {
            fabResume.setImageResource(R.drawable.ic_pencil_edit_button)
        }

        fabResume.setOnClickListener {
            if ((activity as EmpProfileActivity).viewPager.currentItem == 3) {
                startActivity(Intent(activity,EditResumeHeadlineActivity::class.java))
            }
        }
    }
}
