package com.sanswai.achieve.activity

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.view.MenuItem
import android.view.View
import android.widget.EditText
import com.android.volley.VolleyError
import com.google.gson.Gson
import com.sanswai.achieve.R
import com.sanswai.achieve.global.BaseActivity
import com.sanswai.achieve.global.Preferences
import com.sanswai.achieve.network.VolleyService
import com.sanswai.achieve.response.employeedetails.Datum__
import com.sanswai.achieve.response.employeedetails.EmployeeDetails
import com.sanswai.achieve.response.employeedetails.Project
import kotlinx.android.synthetic.main.activity_edit_resume_headline.*
import org.json.JSONObject

class EditResumeHeadlineActivity : BaseActivity(), VolleyService.SetResponse {

    private var services: VolleyService? = null
    private var preferences: Preferences? = null
    private var responseObject: EmployeeDetails? = null
    private var employeeId: String? = null
    private var resumeId: String = ""
    private var projectPosition: String = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_edit_resume_headline)

        services = VolleyService(this)
        preferences = Preferences.getInstance(this)

        if (intent.getBooleanExtra(getString(R.string.fromProjects), false)) {
            supportActionBar!!.title = "Edit Project Details"
            lblResumeTitle.text = "Project Title:"
            lblResumeDescription.text = "Project Description:"
            tvUploadResume.visibility = View.GONE
        } else {
            supportActionBar!!.title = "Edit Resume Headline"
        }
        supportActionBar!!.setDisplayHomeAsUpEnabled(true)
        supportActionBar!!.setDisplayShowHomeEnabled(true)
        val jsonResponse = preferences?.getPreferencesString(getString(com.sanswai.achieve.R.string.pref_employee_details))
        if (jsonResponse != null) {
            setPreviousData(jsonResponse)
        }

        tvSubmitResume.setOnClickListener {
            saveResumeHeadline()
        }

    }

    private fun saveResumeHeadline() {
        when {
            etResumetitle.text.toString().isNullOrEmpty() -> {
                showToast("Please Enter Title")
            }
            etResumeDescription.text.toString().isNullOrEmpty() -> {
                showToast("Please Enter Description")
            }
            else -> {
                if (intent.getBooleanExtra(getString(R.string.fromProjects), false)) {
                    val jsonObject = JSONObject()
                    jsonObject.put("project_name", etResumetitle.text.toString())
                    jsonObject.put("id", resumeId)
                    jsonObject.put("project_description", etResumeDescription.text.toString())
                    jsonObject.put("user_id", responseObject!!.users!!.data!!.id)
                    services!!.callJsonObjectRequest(getString(R.string.api_post_project_details), jsonObject)
                    services!!.mResponseInterface = this
                } else {
                    val jsonObject = JSONObject()
                    jsonObject.put("title", etResumetitle.text.toString())
                    jsonObject.put("id", resumeId)
                    jsonObject.put("description", etResumeDescription.text.toString())
                    jsonObject.put("user_id", responseObject!!.users!!.data!!.id)
                    services!!.callJsonObjectRequest(getString(R.string.api_post_resume_headline), jsonObject)
                    services!!.mResponseInterface = this
                }
            }
        }

    }

    private fun setPreviousData(jsonResponse: String) {
        responseObject = Gson().fromJson(jsonResponse, EmployeeDetails::class.java)

        employeeId = responseObject?.users!!.data!!.id.toString()
        if (intent.getBooleanExtra(getString(R.string.fromProjects), false)) {
            if (responseObject!!.project!!.response == "true") {
                projectPosition = intent.extras.getString(getString(R.string.project_id),"")
                println("project position is "+projectPosition)
                if (projectPosition != "new") {
                    var selectedProject = Datum__()
                    for(i in 0 until responseObject?.project!!.data!!.size){
                        if (projectPosition==responseObject?.project!!.data!![i].id.toString()){
                            selectedProject = responseObject?.project!!.data!![i]
                            resumeId = selectedProject.id.toString()
                            println("esume id for project is " + resumeId)
                            println("esume id for project is " + selectedProject.projectName)
                            etResumetitle.setText(selectedProject.projectName)
                            etResumeDescription.setText(selectedProject.projectDescription)
                        }
                    }
                }else{
                    etResumetitle.hint = "Title"
                    etResumeDescription.hint = "Description"
                }
            } else {
                etResumetitle.hint = "Title"
                etResumeDescription.hint = "Description"
            }
        } else {
            if (responseObject!!.resumeHeadline!!.response == "true") {
                resumeId = responseObject?.resumeHeadline!!.data!!.id.toString()
                etResumetitle.setText(responseObject!!.resumeHeadline!!.data!!.tittle)
                etResumeDescription.setText(responseObject!!.resumeHeadline!!.data!!.description)
            } else {
                etResumetitle.hint = "Title"
                etResumeDescription.hint = "Description"
            }
        }
    }

    override fun onOptionsItemSelected(item: MenuItem?): Boolean {
        when (item!!.itemId) {
            android.R.id.home -> {
                onBackPressed()
                return true
            }
        }
        return true
    }

    override fun onSuccess(methodName: String, response: Any) {
        println("response is " + response)
        if ((response as JSONObject).get("response") == "true") {
            onBackPressed()
        } else {
            showToast("Something went wrong")
        }
    }

    override fun onFailure(methodName: String, volleyError: VolleyError) {
        showToast("Something went wrong" + volleyError.networkResponse)
    }
}
