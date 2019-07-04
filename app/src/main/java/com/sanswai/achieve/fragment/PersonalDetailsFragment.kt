package com.sanswai.achieve.fragment

import android.Manifest
import android.annotation.SuppressLint
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.provider.MediaStore
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import com.android.volley.error.VolleyError
import com.google.gson.Gson
import com.nabinbhandari.android.permissions.PermissionHandler
import com.nabinbhandari.android.permissions.Permissions
import com.sanswai.achieve.R
import com.sanswai.achieve.activity.EditPersonalDetailsActivity
import com.sanswai.achieve.activity.EmpProfileActivity
import com.sanswai.achieve.global.BaseActivity
import com.sanswai.achieve.global.Preferences
import com.sanswai.achieve.network.VolleyService
import com.sanswai.achieve.response.employeedetails.EmployeeDetails
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.activity_emp_profile.*
import kotlinx.android.synthetic.main.fragment_personal_details.*
import kotlinx.android.synthetic.main.layout_profile.*
import java.io.File
import java.io.IOException


class PersonalDetailsFragment : Fragment(), VolleyService.SetResponse {

    var services: VolleyService? = null
    var preferences: Preferences? = null
    val GALLERY = 789

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_personal_details, container, false)
    }

    @SuppressLint("SetTextI18n", "RestrictedApi")
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        services = VolleyService(activity!!)
        preferences = Preferences.getInstance(activity!!)

        if (preferences?.getPreferencesString(getString(R.string.user_type)) == "employee") {
            fabPersonalDetai.visibility = View.VISIBLE
        } else {
            fabPersonalDetai.visibility = View.GONE
        }

        ivUploadPic.setOnClickListener {
            Permissions.check(activity/*context*/, Manifest.permission.READ_EXTERNAL_STORAGE, null, object : PermissionHandler() {
                override fun onGranted() {
                    // do your task.
                    val galleryIntent = Intent(Intent.ACTION_PICK,
                            MediaStore.Images.Media.EXTERNAL_CONTENT_URI)
                    startActivityForResult(galleryIntent, GALLERY)
                }
            })
        }

        val jsonResponse = preferences?.getPreferencesString(getString(R.string.pref_employee_details))
        val responseObject = Gson().fromJson(jsonResponse, EmployeeDetails::class.java)
        println("response object is Male \n" +
                " single \n" +
                " Nashik" + (responseObject.personalDetails?.response == "false"))

        if (responseObject != null && responseObject.personalDetails?.response == "true") {
            rlPerInfo.visibility = View.VISIBLE
            rlPersnalInfo.visibility = View.VISIBLE
            rlCurrAddress.visibility = View.VISIBLE
            rlPerAddress.visibility = View.VISIBLE
            tvPersonalInfo.text = responseObject.personalDetails?.data?.gender + "\n" + responseObject.personalDetails?.data?.marriatalStatus + "\n" + responseObject.personalDetails?.data?.hometown + "\n" + responseObject.personalDetails?.data?.dateOfBirth
            tvCurrentAddress.text = responseObject.personalDetails?.data?.residentialAddressOne + "\n" +
                    responseObject.personalDetails?.data?.residentialAddressTwo + "\n" +
                    responseObject.personalDetails?.data?.residentialPinCode
            tvPerAddress.text = responseObject.personalDetails?.data?.permanentAddressOne + "\n" +
                    responseObject.personalDetails?.data?.permanentAddressTwo + "\n" +
                    responseObject.personalDetails?.data?.pinCode

            println("inside if")
        } else {
            println("inside else")
        }
        if ((responseObject).users?.data?.profilePic != null) {
            Picasso.get().load((responseObject).users?.data?.profilePic).centerInside().resize(200, 200)
                    .onlyScaleDown().error(activity!!.getDrawable(R.mipmap.ic_launcher)).placeholder(activity!!.getDrawable(R.mipmap.ic_launcher)).into(ivProfile)
        }
        tvTelephone.text = responseObject.users?.data?.mobileNumber
        tvEmail.text = responseObject.users?.data?.email

        if (responseObject.personalDetails?.response != "false") {
            fabPersonalDetai.setImageResource(R.drawable.ic_pencil_edit_button)
        } else {
            fabPersonalDetai.setImageResource(R.drawable.ic_plus_black_symbol)
        }

        fabPersonalDetai.setOnClickListener {
            println("on click reference is " + (activity as EmpProfileActivity).viewPager.currentItem)
            if ((activity as EmpProfileActivity).viewPager.currentItem == 0) {
                startActivity(Intent(activity, EditPersonalDetailsActivity::class.java).putExtra(getString(R.string.employee_id), (activity as EmpProfileActivity).employee_id))
            }
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {

        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == GALLERY) {
            if (data != null) {
                val contentURI = data.data
                try {
                    val file = File(getPath(contentURI))

                    //val bitmap = MediaStore.Images.Media.getBitmap(activity!!.contentResolver, contentURI)
                    // imageView.setImageBitmap(bitmap);
                    println("image is " + file.absolutePath)
                    if (file.length() > 2000000) {
                        (activity as BaseActivity).showToast("Try File less than 2 MB")
                    } else {
                        services!!.postMultipartRequest(getString(R.string.api_profile_pic_upload) + ((activity as EmpProfileActivity).employee_id), file.absolutePath)
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
        var res: String = ""
        val proj = arrayOf(MediaStore.Images.Media.DATA)
        val cursor = activity!!.contentResolver.query(uri, proj, null, null, null)
        if (cursor.moveToFirst()) {
            val column_index = cursor.getColumnIndexOrThrow(MediaStore.Images.Media.DATA)
            res = cursor.getString(column_index)
        }
        cursor.close()
        return res
    }

    override fun onSuccess(methodName: String, response: Any) {
        ((activity as EmpProfileActivity).getTheDetails(0))
    }

    override fun onFailure(methodName: String, volleyError: VolleyError) {
    }

}
