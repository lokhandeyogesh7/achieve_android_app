package com.sanswai.achieve.activity

import android.Manifest
import android.annotation.SuppressLint
import android.content.Intent
import android.database.Cursor
import android.net.Uri
import android.os.Bundle
import android.provider.DocumentsContract
import android.provider.MediaStore
import android.provider.OpenableColumns
import android.view.MenuItem
import android.widget.Toast
import com.android.volley.error.VolleyError
import com.google.gson.Gson
import com.nabinbhandari.android.permissions.PermissionHandler
import com.nabinbhandari.android.permissions.Permissions
import com.sanswai.achieve.R
import com.sanswai.achieve.RealFilePath
import com.sanswai.achieve.global.BaseActivity
import com.sanswai.achieve.global.Preferences
import com.sanswai.achieve.network.VolleyService
import com.sanswai.achieve.response.employeedetails.Datum__
import com.sanswai.achieve.response.employeedetails.EmployeeDetails
import kotlinx.android.synthetic.main.activity_edit_resume_headline.*
import org.json.JSONObject
import java.io.File
import java.io.IOException
import android.os.Environment.getDownloadCacheDirectory
import android.os.Environment
import android.provider.MediaStore.MediaColumns
import android.view.Menu


class EditResumeHeadlineActivity : BaseActivity(), VolleyService.SetResponse {

    private var services: VolleyService? = null
    private var preferences: Preferences? = null
    private var responseObject: EmployeeDetails? = null
    private var employeeId: String? = null
    private var resumeId: String = ""
    private var projectPosition: String = ""
    val GALLERY = 987

    @SuppressLint("SetTextI18n")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_edit_resume_headline)

        services = VolleyService(this)
        preferences = Preferences.getInstance(this)

        if (intent.getBooleanExtra(getString(R.string.fromProjects), false)) {
            supportActionBar!!.title = "Edit Project Details"
            lblResumeTitle.text = "Project Title:"
            lblResumeDescription.text = "Project Description:"
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
            etResumetitle.text.toString().isEmpty() -> {
                showToast("Please Enter Title")
            }
            etResumeDescription.text.toString().isEmpty() -> {
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
                projectPosition = intent.extras.getString(getString(R.string.project_id), "")
                println("project position is " + projectPosition)
                if (projectPosition != "new") {
                    var selectedProject = Datum__()
                    for (i in 0 until responseObject?.project!!.data!!.size) {
                        if (projectPosition == responseObject?.project!!.data!![i].id.toString()) {
                            selectedProject = responseObject?.project!!.data!![i]
                            resumeId = selectedProject.id.toString()
                            println("esume id for project is " + resumeId)
                            println("esume id for project is " + selectedProject.projectName)
                            etResumetitle.setText(selectedProject.projectName)
                            etResumeDescription.setText(selectedProject.projectDescription)
                        }
                    }
                } else {
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
        println("response is $response")
        if ((response as JSONObject).get("response") == "true") {
            onBackPressed()
        } else {
            showToast("Something went wrong")
        }
    }

    override fun onFailure(methodName: String, volleyError: VolleyError) {
        showToast("Something went wrong" + volleyError.networkResponse)
    }

    @SuppressLint("NewApi")
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == GALLERY) {
            if (data != null) {
                val contentURI = data.data
                try {
                    println("content uri is " + contentURI)
                    val fileNew = File(getPath(contentURI))
                    val file = File(contentURI.toString())
                    val path = file.getAbsolutePath()
                    var displayName: String? = null

                    if (contentURI.toString().startsWith("content://")) {
                        var cursor: Cursor? = null
                        try {
                            cursor = contentResolver.query(contentURI, null, null, null, null)
                            if (cursor != null && cursor!!.moveToFirst()) {
                                displayName = cursor!!.getString(cursor!!.getColumnIndex(OpenableColumns.DISPLAY_NAME))
                            }
                        } finally {
                            cursor!!.close()
                        }
                    } else if (contentURI.toString().startsWith("file://")) {
                        displayName = file.getName()
                    }
                    println("column name " + displayName)
                    println("image is real paths " + fileNew.absolutePath)
                    if (file.length() > 2000000) {
                        showToast("Try File less than 2 MB")
                    } else {
                        services!!.postMultipartRequest(getString(com.sanswai.achieve.R.string.api_resume_upload) + (employeeId), file.absolutePath)
                    }
                } catch (e: IOException) {
                    e.printStackTrace()
                    Toast.makeText(this, "Failed!", Toast.LENGTH_SHORT).show()
                }
            }
        }
    }

    @SuppressLint("NewApi")
    private fun getPath(uri: Uri): String {
        val projection = arrayOf(MediaColumns.DATA)
        val cursor = managedQuery(uri, projection, null, null, null)
        if (cursor != null) {
            val column_index = cursor.getColumnIndexOrThrow(MediaColumns.DATA)
            cursor.moveToFirst()
            return cursor.getString(column_index)
        } else
            return ""
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        // Inflate the menu; this adds items to the action bar if it is present.
        menuInflater.inflate(R.menu.menu_logo, menu)
        return true
    }
}
