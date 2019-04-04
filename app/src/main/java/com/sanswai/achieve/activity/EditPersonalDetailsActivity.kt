package com.sanswai.achieve.activity

import android.annotation.SuppressLint
import android.app.DatePickerDialog
import android.app.Dialog
import android.os.Bundle
import android.support.v4.app.DialogFragment
import android.view.MenuItem
import android.widget.DatePicker
import android.widget.EditText
import com.google.gson.Gson
import com.sanswai.achieve.global.BaseActivity
import com.sanswai.achieve.global.Preferences
import com.sanswai.achieve.network.VolleyService
import com.sanswai.achieve.response.employeedetails.EmployeeDetails
import com.sanswai.achieve.response.performancequestions.QuestionsDatum
import kotlinx.android.synthetic.main.activity_edit_personal_details.*
import java.util.*


class EditPersonalDetailsActivity : BaseActivity(),DatePickerDialog.OnDateSetListener {

    var services: VolleyService? = null
    var preferences: Preferences? = null
    lateinit var arrRevQuestions: ArrayList<QuestionsDatum>
    private var mYear: Int = 0
    var mMonth: Int = 0
    var mDay: Int = 0
    var employee_id: String? = null
    var employer_id: String? = null
    var etDob1: EditText? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(com.sanswai.achieve.R.layout.activity_edit_personal_details)
        etDob1 = findViewById(com.sanswai.achieve.R.id.etDob)

        services = VolleyService(this)
        preferences = Preferences.getInstance(this)

        val jsonResponse = preferences?.getPreferencesString(getString(com.sanswai.achieve.R.string.pref_employee_details))
        if (jsonResponse != null) {
            setPreviousData(jsonResponse)
        }

        supportActionBar!!.title = "Edit Details"
        supportActionBar!!.setDisplayHomeAsUpEnabled(true)
        supportActionBar!!.setDisplayShowHomeEnabled(true)

        tvSubmit.setOnClickListener {

        }

        etDob1?.setOnClickListener {
            /*val newFragment = DatePickerFragment()
            newFragment.show(supportFragmentManager, "datePicker")*/

            val c = Calendar.getInstance()
            val year = c.get(Calendar.YEAR)
            val month = c.get(Calendar.MONTH)
            val day = c.get(Calendar.DAY_OF_MONTH)
            val datePickerDialog = DatePickerDialog(
                    this, this@EditPersonalDetailsActivity, year, month, day)
            datePickerDialog.show();
        }

    }

    private fun setPreviousData(jsonResponse: String?) {
        val responseObject = Gson().fromJson(jsonResponse, EmployeeDetails::class.java)

        if (responseObject.personalDetails?.data?.gender.equals("male", true)) {
            radioM.isChecked = true
        } else {
            radioF.isChecked = true
        }

        when {
            responseObject.personalDetails?.data?.marriatalStatus.equals("unmarried", true) -> radioSingle.isChecked = true
            responseObject.personalDetails?.data?.marriatalStatus.equals("married", true) -> radioMarried.isChecked = true
            else -> radioDivorcee.isChecked = true
        }

        etResAddOne.setText(responseObject?.personalDetails?.data?.residentialAddressOne)
        etResAddTwo.setText(responseObject?.personalDetails?.data?.residentialAddressTwo)
        etResAddPin.setText(responseObject?.personalDetails?.data?.residentialPinCode)

        etPerAddOne.setText(responseObject.personalDetails?.data?.permanentAddressOne)
        etPerAddTwo.setText(responseObject.personalDetails?.data?.permanentAddressTwo)
        etPerAddPin.setText(responseObject.personalDetails?.data?.pinCode)

        etDob.setText(responseObject.personalDetails?.data?.dateOfBirth)
        etHometown.setText(responseObject.personalDetails?.data?.hometown)
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

    override fun onDateSet(view: DatePicker?, year: Int, month: Int, dayOfMonth: Int) {
        etDob.setText("" + year.toString() + "-" + (month + 1).toString() + "-" + dayOfMonth.toString())
    }
}
