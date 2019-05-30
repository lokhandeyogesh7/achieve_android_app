package com.sanswai.achieve.activity

import android.R
import android.annotation.SuppressLint
import android.app.DatePickerDialog
import android.os.Bundle
import android.view.MenuItem
import android.widget.ArrayAdapter
import android.widget.DatePicker
import android.widget.EditText
import android.widget.RadioButton
import com.android.volley.error.VolleyError
import com.google.gson.Gson
import com.sanswai.achieve.global.BaseActivity
import com.sanswai.achieve.global.Preferences
import com.sanswai.achieve.network.VolleyService
import com.sanswai.achieve.response.employeedetails.EmployeeDetails
import kotlinx.android.synthetic.main.activity_edit_personal_details.*
import org.json.JSONObject
import java.util.*
import kotlin.collections.ArrayList


class EditPersonalDetailsActivity : BaseActivity(), DatePickerDialog.OnDateSetListener, VolleyService.SetResponse {

    private var services: VolleyService? = null
    private var preferences: Preferences? = null
    private var responseObject: EmployeeDetails? = null
    private var entryId: String? = ""
    private var gender: String? = null
    private var maritalStatus: String? = null
    private var etDob1: EditText? = null
    private var employeeId: String? = null
    private var arrLanguages: ArrayList<String>? = null
    private var arrProficiency: ArrayList<String>? = null

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

        arrLanguages = ArrayList()
        arrLanguages?.add("English")
        arrLanguages?.add("Hindi")
        arrLanguages?.add("Marathi")
        arrLanguages?.add("Other")
        val adapter1 = ArrayAdapter(this, R.layout.simple_spinner_dropdown_item, arrLanguages)
        spLanguage1.adapter = adapter1
        val adapter2 = ArrayAdapter(this, R.layout.simple_spinner_dropdown_item, arrLanguages)
        spLanguage2.adapter = adapter2
        val adapter3 = ArrayAdapter(this, R.layout.simple_spinner_dropdown_item, arrLanguages)
        spLanguage3.adapter = adapter3


        arrProficiency = ArrayList()
        arrProficiency?.add("Beginner")
        arrProficiency?.add("Proficient")
        arrProficiency?.add("Expert")
        val adapter4 = ArrayAdapter(this, R.layout.simple_spinner_dropdown_item, arrProficiency)
        spProficiency1.adapter = adapter4
        val adapter5 = ArrayAdapter(this, R.layout.simple_spinner_dropdown_item, arrProficiency)
        spProficiency2.adapter = adapter5
        val adapter6 = ArrayAdapter(this, R.layout.simple_spinner_dropdown_item, arrProficiency)
        spProficiency3.adapter = adapter6


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
            val selectedId = radioGrp.checkedRadioButtonId
            val selectedIdMarital = radioGrpMarital.checkedRadioButtonId
            val  radioButton  = findViewById(selectedId) as RadioButton
            val  radioButtonMarital  = findViewById(selectedIdMarital) as RadioButton

            gender = radioButton.text.toString().toLowerCase()
            maritalStatus = radioButtonMarital.text.toString().toLowerCase()
            println("gender is "+gender)

            when {
                    etHometown.text.toString().isEmpty()
                -> showToast("Enter hometown")
                etDob1?.text.toString().isEmpty() -> showToast("Enter Date of Birth")
                etResAddOne?.text.toString().isEmpty() -> showToast("Enter Residential Address")
                etResAddTwo?.text.toString().isEmpty() -> showToast("Enter Residential Address")
                etResAddPin?.text.toString().isEmpty() -> showToast("Enter Residential Address PIN")
                etPerAddOne?.text.toString().isEmpty() -> showToast("Enter Permanent Address")
                etPerAddTwo?.text.toString().isEmpty() -> showToast("Enter Permanent Address")
                etPerAddPin?.text.toString().isEmpty() -> showToast("Enter Permanent Address PIN")
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
        if ((response as JSONObject).get("response") == "true") {
            onBackPressed()
        } else {
            showToast("Something went wrong")
        }
    }

    override fun onFailure(methodName: String, volleyError: VolleyError) {
    }

    private fun setPreviousData(jsonResponse: String?) {
        responseObject = Gson().fromJson(jsonResponse, EmployeeDetails::class.java)

        employeeId = responseObject?.users!!.data!!.id.toString()

        if (responseObject!!.personalDetails!!.data != null) {
            entryId = responseObject!!.personalDetails!!.data!!.id.toString()

            if (responseObject!!.personalDetails?.data?.gender.equals("male", true)) {
                radioM.isChecked = true
                gender = "male"
            } else {
                radioF.isChecked = true
                gender = "female"
            }

            when {
                responseObject?.personalDetails?.data?.marriatalStatus.equals("single", true) -> radioSingle.isChecked = true
                responseObject?.personalDetails?.data?.marriatalStatus.equals("married", true) -> radioMarried.isChecked = true
                responseObject?.personalDetails?.data?.marriatalStatus.equals("widowed", true) -> radioWidowed.isChecked = true
                responseObject?.personalDetails?.data?.marriatalStatus.equals("other", true) -> radioOther.isChecked = true
                responseObject?.personalDetails?.data?.marriatalStatus.equals("separated", true) -> radioSeperated.isChecked = true
                // else -> radioDivorcee.isChecked = true
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
