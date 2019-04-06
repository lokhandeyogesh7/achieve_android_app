package com.sanswai.achieve.fragment

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.support.v4.app.Fragment
import android.text.Html
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.google.gson.Gson
import com.sanswai.achieve.R
import com.sanswai.achieve.activity.EditPersonalDetailsActivity
import com.sanswai.achieve.activity.EmpProfileActivity
import com.sanswai.achieve.global.Preferences
import com.sanswai.achieve.network.VolleyService
import com.sanswai.achieve.response.employeedetails.EmployeeDetails
import kotlinx.android.synthetic.main.activity_emp_profile.*
import kotlinx.android.synthetic.main.fragment_personal_details.*

class PersonalDetailsFragment : Fragment() {

    var services: VolleyService? = null
    var preferences: Preferences? = null

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_personal_details, container, false)
    }

    @SuppressLint("SetTextI18n")
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val htmlString = "Male \n single \n Nashik"
        tvPersonalInfo.text = Html.fromHtml(htmlString)
        services = VolleyService(activity!!)
        preferences = Preferences.getInstance(activity!!)

        val jsonResponse = preferences?.getPreferencesString(getString(R.string.pref_employee_details))
        val responseObject = Gson().fromJson(jsonResponse, EmployeeDetails::class.java)
        println("response object is Male \n" +
                " single \n" +
                " Nashik" + (responseObject.personalDetails?.response == "false"))

        if (responseObject != null && responseObject.personalDetails?.response == "true") {
            rlPerInfo.visibility = View.VISIBLE
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


        (activity as EmpProfileActivity).fabPersonalDetails.bringToFront()

        if (responseObject.personalDetails?.response != "false") {
            (activity as EmpProfileActivity).fabPersonalDetails.setImageResource(R.drawable.ic_pencil_edit_button)
        } else {
            (activity as EmpProfileActivity).fabPersonalDetails.setImageResource(R.drawable.ic_plus_black_symbol)
        }


        ((activity as EmpProfileActivity).fabPersonalDetails.setOnClickListener {
            println("on click reference is " + (activity as EmpProfileActivity).viewPager.currentItem)
            if ((activity as EmpProfileActivity).viewPager.currentItem == 0) {
                startActivity(Intent(activity, EditPersonalDetailsActivity::class.java).putExtra(getString(R.string.employee_id), (activity as EmpProfileActivity).employee_id))
            }
        })
    }
}
