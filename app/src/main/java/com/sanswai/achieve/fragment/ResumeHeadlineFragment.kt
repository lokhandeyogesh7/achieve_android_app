package com.sanswai.achieve.fragment

import android.Manifest
import android.annotation.SuppressLint
import android.app.Activity
import android.content.ContentUris
import android.content.Intent
import android.database.Cursor
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.os.Environment
import android.provider.DocumentsContract
import android.provider.MediaStore
import android.provider.OpenableColumns
import android.support.v4.app.Fragment
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import com.android.volley.error.VolleyError
import com.google.gson.Gson
import com.nabinbhandari.android.permissions.PermissionHandler
import com.nabinbhandari.android.permissions.Permissions
import com.sanswai.achieve.FilePath
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
import java.util.regex.Pattern


class ResumeHeadlineFragment : Fragment(), VolleyService.SetResponse {

    var services: VolleyService? = null
    var preferences: Preferences? = null
    val GALLERY = 987
    private var employeeId: String? = null

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_resume_headline, container, false)
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
                    //val uri = Uri.parse(Environment.getDownloadCacheDirectory().getPath().toString())
                    intent.setType("file/*")
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
                    val filePath = getPath(contentURI)

                    println("filepath is " + filePath)
                    val file = File(filePath)

                    println("image is real paths " + filePath)
                    if (file.length() > 2000000) {
                        ((activity as BaseActivity)).showToast("Try File less than 2 MB")
                    } else {
                        services!!.postMultipartRequest(getString(com.sanswai.achieve.R.string.api_resume_upload) + (employeeId), filePath)
                        services!!.mResponseInterface = this
                    }
                } catch (e: IOException) {
                    e.printStackTrace()
                    Toast.makeText(activity, "Failed!", Toast.LENGTH_SHORT).show()
                }
            }
        }
    }

    fun getPath(uri: Uri): String {
        var filePath = "";

        val p = Pattern.compile("(\\d+)$");
        val m = p.matcher(uri.toString());
        if (!m.find()) {
            println("path not found m")
            return filePath;
        }
        val imgId = m.group();

        val column = arrayOf(MediaStore.Images.Media.DATA)
        val sel = MediaStore.Images.Media._ID + "=?";

        val cursor = activity!!.getContentResolver().query(MediaStore.Images.Media.EXTERNAL_CONTENT_URI,
                column, sel, arrayOf(imgId), null)

        val columnIndex = cursor.getColumnIndex(column[0])

        if (cursor.moveToFirst()) {
            filePath = cursor.getString(columnIndex);
        }
        cursor.close()

        return filePath
    }

    override fun onSuccess(methodName: String, response: Any) {
        println("reposnse is " + methodName + ">>> " + response)
    }

    override fun onFailure(methodName: String, volleyError: VolleyError) {
        println("reposnse is " + methodName + ">>> " + volleyError.message)
    }
}
