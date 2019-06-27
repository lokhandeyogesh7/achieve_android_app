package com.sanswai.achieve.fragment

import android.Manifest
import android.annotation.SuppressLint
import android.app.Activity
import android.content.Intent
import android.database.Cursor
import android.net.Uri
import android.os.Bundle
import android.os.Environment
import android.provider.MediaStore
import android.provider.OpenableColumns
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import com.google.gson.Gson
import com.nabinbhandari.android.permissions.PermissionHandler
import com.nabinbhandari.android.permissions.Permissions
import com.sanswai.achieve.R
import com.sanswai.achieve.activity.EditResumeHeadlineActivity
import com.sanswai.achieve.activity.EmpProfileActivity
import com.sanswai.achieve.global.BaseActivity
import com.sanswai.achieve.global.Preferences
import com.sanswai.achieve.network.VolleyService
import com.sanswai.achieve.response.employeedetails.EmployeeDetails
import kotlinx.android.synthetic.main.activity_emp_profile.*
import kotlinx.android.synthetic.main.fragment_resume_headline.*
import java.io.File
import java.io.IOException
import java.lang.Exception


class ResumeHeadlineFragment : Fragment() {

    var services: VolleyService? = null
    var preferences: Preferences? = null
    val GALLERY = 987
    private var employeeId: String? = null

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(com.sanswai.achieve.R.layout.fragment_resume_headline, container, false)
    }

    @SuppressLint("RestrictedApi")
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        services = VolleyService(activity!!)
        preferences = Preferences.getInstance(activity!!)
        val jsonResponse = preferences?.getPreferencesString(getString(com.sanswai.achieve.R.string.pref_employee_details))
        val responseObject = Gson().fromJson(jsonResponse, EmployeeDetails::class.java)
        employeeId = responseObject?.users!!.data!!.id.toString()
        lblResumeHeadline.text = responseObject?.resumeHeadline?.data?.tittle
        tvPersonalInfo.text = responseObject?.resumeHeadline?.data?.description

        if (responseObject.resumeHeadline!!.response == "false") {
            tvDownloadResume.visibility = View.GONE
            rlPersnInfo.visibility = View.GONE
        } else {
            tvDownloadResume.visibility = View.VISIBLE
            rlPersnInfo.visibility = View.VISIBLE
        }

        tvDownloadResume.setOnClickListener {
            if (responseObject?.users?.data?.resumeFile != null) {
                val browserIntent = Intent(Intent.ACTION_VIEW, Uri.parse(responseObject?.users?.data?.resumeFile))
                startActivity(browserIntent)
            }
        }
        println("resume headline " + responseObject.resumeHeadline?.response)
        println("resume headline employee type" + preferences?.getPreferencesString(getString(R.string.user_type)))


        if (preferences?.getPreferencesString(getString(R.string.user_type)) == "employee") {
            fabResume.visibility = View.VISIBLE
            tvUploadResume.visibility = View.VISIBLE
        } else {
            fabResume.visibility = View.GONE
            tvUploadResume.visibility = View.GONE
        }

        if (responseObject.resumeHeadline?.response == "false") {
            fabResume.setImageResource(R.drawable.ic_plus_black_symbol)
        } else {
            fabResume.setImageResource(R.drawable.ic_pencil_edit_button)
        }

        fabResume.setOnClickListener {
            if ((activity as EmpProfileActivity).viewPager.currentItem == 3) {
                startActivity(Intent(activity, EditResumeHeadlineActivity::class.java))
            }
        }

        tvUploadResume.setOnClickListener {
            val permissions = arrayOf(Manifest.permission.WRITE_EXTERNAL_STORAGE, Manifest.permission.READ_EXTERNAL_STORAGE)
            Permissions.check(activity, permissions, null, null, object : PermissionHandler() {
                @SuppressLint("InlinedApi")
                override fun onGranted() {
                    // do your task.
                    val intent = Intent()
                    intent.action = Intent.ACTION_GET_CONTENT
                    intent.addCategory(Intent.CATEGORY_OPENABLE)
                    val uri = Uri.parse(Environment.getDownloadCacheDirectory().getPath().toString())
                    intent.setDataAndType(uri, "file/*")
                    val mimetypes = arrayOf("application/vnd.openxmlformats-officedocument.wordprocessingml.document", "application/msword", "application/pdf")
                    intent.putExtra(Intent.EXTRA_MIME_TYPES, mimetypes)
                    startActivityForResult(intent, GALLERY)
                }
            })
        }
    }

    @SuppressLint("NewApi")
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        println("result code is " + resultCode)
        if (resultCode != Activity.RESULT_OK) {
            ((activity as BaseActivity)).showToast("Try Using Different Folder")
        } else if (requestCode == GALLERY) {
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
                            cursor = activity!!.getContentResolver().query(contentURI, null, null, null, null)
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
                        ((activity as BaseActivity)).showToast("Try File less than 2 MB")
                    } else {
                        services!!.postMultipartRequest(getString(com.sanswai.achieve.R.string.api_resume_upload) + (employeeId), fileNew.absolutePath)
                    }
                } catch (e: IOException) {
                    e.printStackTrace()
                    Toast.makeText(activity, "Failed!", Toast.LENGTH_SHORT).show()
                }
            }
        }
    }

    @SuppressLint("NewApi")
    private fun getPath(uri: Uri): String {
        /*val projection = arrayOf(MediaStore.MediaColumns.DATA)
        val cursor = activity!!.managedQuery(uri, projection, null, null, null)
        if (cursor != null) {
            val column_index = cursor.getColumnIndexOrThrow(MediaStore.MediaColumns.DATA)
            cursor.moveToFirst()
            return cursor.getString(column_index)
        } else
            return ""*/

        var res: String = ""
        val proj = arrayOf(MediaStore.Images.Media.DATA)
        val cursor = activity!!.contentResolver.query(uri, proj, null, null, null)
        if (cursor.moveToFirst()) {
            val column_index = cursor.getColumnIndexOrThrow(MediaStore.Images.Media.DATA)
            try {
                res = cursor.getString(column_index)
            } catch (e: Exception) {
                e.printStackTrace()
                ((activity as BaseActivity)).showToast("Try Using Different Folder")
            }
        }
        cursor.close()
        return res
    }
}
