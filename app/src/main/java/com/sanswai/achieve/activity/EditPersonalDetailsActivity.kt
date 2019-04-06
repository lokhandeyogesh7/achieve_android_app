package com.sanswai.achieve.activity

import android.annotation.SuppressLint
import android.app.DatePickerDialog
import android.os.Bundle
import android.view.MenuItem
import android.widget.DatePicker
import android.widget.EditText
import android.widget.RadioGroup
import com.android.volley.VolleyError
import com.google.gson.Gson
import com.sanswai.achieve.global.BaseActivity
import com.sanswai.achieve.global.Preferences
import com.sanswai.achieve.network.VolleyService
import com.sanswai.achieve.response.employeedetails.EmployeeDetails
import kotlinx.android.synthetic.main.activity_edit_personal_details.*
import org.json.JSONObject
import java.util.*
import android.widget.RadioButton




class EditPersonalDetailsActivity : BaseActivity(), DatePickerDialog.OnDateSetListener, VolleyService.SetResponse {

    private var services: VolleyService? = null
    private var preferences: Preferences? = null
    private var responseObject: EmployeeDetails? = null
    private var entryId: String? = ""
    private var gender: String? = null
    private var maritalStatus: String? = null
    private var etDob1: EditText? = null
    private var employeeId: String? = null

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
       // employeeId = intent.getIntExtra(getString(com.sanswai.achieve.R.string.employee_id), 0).toString()
        println("employee id is $employeeId")

        supportActionBar!!.title = "Edit Details"
        supportActionBar!!.setDisplayHomeAsUpEnabled(true)
        supportActionBar!!.setDisplayShowHomeEnabled(true)


        radioGrp.setOnCheckedChangeListener { group, checkedId ->
            val checkedRadioButton = group?.findViewById(checkedId) as RadioButton
            if (checkedRadioButton.isChecked)
                gender = checkedRadioButton.text.toString()
                //showToast("you just checked "+checkedRadioButton.text)
        }

        radioGrpMarital.setOnCheckedChangeListener { group, checkedId ->
            val checkedRadioButton = group?.findViewById(checkedId) as RadioButton
            if (checkedRadioButton.isChecked)
                maritalStatus = checkedRadioButton.text.toString()
            //showToast("you just checked "+checkedRadioButton.text)
        }

        tvSubmit.setOnClickListener {
            when {
                etHometown.text.toString().isEmpty() -> showToast("Enter hometown")
                etDob1?.text.toString().isEmpty() -> showToast("Enter Date of Birth")
                etResAddOne?.text.toString().isEmpty() -> showToast("Enter Residential Address")
                etResAddTwo?.text.toString().isEmpty() -> showToast("Enter Residential Address")
                etResAddPin?.text.toString().isEmpty() -> showToast("Enter Residential Address PIN")
                etPerAddOne?.text.toString().isEmpty() -> showToast("Enter Permanent Address")
                etPerAddTwo?.text.toString().isEmpty() -> showToast("Enter Permanent Address")
                etPerAddPin?.text.toString().isEmpty() -> showToast("Enter Permanent Address")
                else -> {
                    val jsonObject = JSONObject()
                    jsonObject.put("id", entryId)
                    jsonObject.put("residential_address_one", etResAddOne.text.toString())
                    jsonObject.put("residential_address_two", etResAddTwo.text.toString())
                    jsonObject.put("residential_pin_code", etResAddPin.text.toString())
                    jsonObject.put("permanent_address_one", etPerAddOne.text.toString())
                    jsonObject.put("permanent_address_two", etPerAddTwo.text.toString())
                    jsonObject.put("pin_code", etPerAddPin.text.toString())
                    jsonObject.put("gender", gender)
                    jsonObject.put("marriatal_status", maritalStatus)
                    jsonObject.put("hometown", etHometown.text.toString())
                    jsonObject.put("date_of_birth", etDob.text.toString())
                    jsonObject.put("user_id", employeeId)

                    services?.callJsonObjectRequest(getString(com.sanswai.achieve.R.string.api_employee_post), jsonObject)
                    services?.mResponseInterface = this
                }
            }
        }

        etDob1?.setOnClickListener {
            val c = Calendar.getInstance()
            val year = c.get(Calendar.YEAR)
            val month = c.get(Calendar.MONTH)
            val day = c.get(Calendar.DAY_OF_MONTH)
            val datePickerDialog = DatePickerDialog(
                    this, this@EditPersonalDetailsActivity, year, month, day)
            datePickerDialog.show()
        }
    }

    override fun onSuccess(methodName: String, response: Any) {
        println("success $methodName   >>>>>  $response")
        if ((response as JSONObject).get("respose")=="true"){
            onBackPressed()
        }else{
            showToast("Something went wrong")
        }
    }

    override fun onFailure(methodName: String, volleyError: VolleyError) {
    }

    private fun setPreviousData(jsonResponse: String?) {
        responseObject = Gson().fromJson(jsonResponse, EmployeeDetails::class.java)

        employeeId = responseObject?.users!!.data!!.id.toString()

        entryId = responseObject!!.personalDetails!!.data!!.id.toString()

        if (responseObject!!.personalDetails?.data?.gender.equals("male", true)) {
            radioM.isChecked = true
            gender = "Male"
        } else {
            radioF.isChecked = true
            gender = "Female"
        }

        when {
            responseObject?.personalDetails?.data?.marriatalStatus.equals("unmarried", true) -> radioSingle.isChecked = true
            responseObject?.personalDetails?.data?.marriatalStatus.equals("married", true) -> radioMarried.isChecked = true
            else -> radioDivorcee.isChecked = true
        }

        etResAddOne.setText(responseObject?.personalDetails?.data?.residentialAddressOne)
        etResAddTwo.setText(responseObject?.personalDetails?.data?.residentialAddressTwo)
        etResAddPin.setText(responseObject?.personalDetails?.data?.residentialPinCode)

        etPerAddOne.setText(responseObject?.personalDetails?.data?.permanentAddressOne)
        etPerAddTwo.setText(responseObject?.personalDetails?.data?.permanentAddressTwo)
        etPerAddPin.setText(responseObject?.personalDetails?.data?.pinCode)

        etDob.setText(responseObject?.personalDetails?.data?.dateOfBirth)
        etHometown.setText(responseObject?.personalDetails?.data?.hometown)
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

    @SuppressLint("SetTextI18n")
    override fun onDateSet(view: DatePicker?, year: Int, month: Int, dayOfMonth: Int) {
        etDob.setText("" + year.toString() + "-" + (month + 1).toString() + "-" + dayOfMonth.toString())
    }
}
