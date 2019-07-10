package com.sanswai.achieve.activity

import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import com.android.volley.error.VolleyError
import com.google.gson.Gson
import com.sanswai.achieve.R
import com.sanswai.achieve.global.BaseActivity
import com.sanswai.achieve.global.Preferences
import com.sanswai.achieve.network.VolleyService
import com.sanswai.achieve.response.employeedetails.EmployeeDetails
import kotlinx.android.synthetic.main.activity_add_edit_employment.*
import org.json.JSONObject
import java.util.*


class AddEditEmploymentActivity : BaseActivity(), VolleyService.SetResponse {

    private var services: VolleyService? = null
    private var preferences: Preferences? = null
    var selectedNoticePeriod: String? = null
    var startYear: String? = null
    var startMonth: String? = null
    var endYear: String? = null
    var endMonth: String? = null
    var endWork: String? = null
    var currentOrganization: String? = null
    var employmentID: String? = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(com.sanswai.achieve.R.layout.activity_add_edit_employment)

        services = VolleyService(this)
        preferences = Preferences.getInstance(this)

        supportActionBar!!.title = "Add/Edit Employment Details"
        supportActionBar!!.setDisplayHomeAsUpEnabled(true)
        supportActionBar!!.setDisplayShowHomeEnabled(true)


        val arrNoticePeriod = ArrayList<String>()
        arrNoticePeriod.add("15 Days")
        arrNoticePeriod.add("1 Month")
        arrNoticePeriod.add("2 Months")
        arrNoticePeriod.add("3 Months")
        arrNoticePeriod.add("More than 3 Months")

        val adapter1 = ArrayAdapter(this, android.R.layout.simple_spinner_dropdown_item, arrNoticePeriod)
        spNoticePeriod.adapter = adapter1
        spNoticePeriod.onItemSelectedListener =
                object : AdapterView.OnItemSelectedListener {
                    override fun onNothingSelected(parent: AdapterView<*>?) {
                    }

                    override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
                        selectedNoticePeriod = (position + 1).toString()
                        println("selectedyear " + (position + 1))
                    }
                }

        val years = ArrayList<String>()
        val thisYear = Calendar.getInstance().get(Calendar.YEAR)
        for (i in 1985..thisYear) {
            years.add(Integer.toString(i))
        }
        val monthName = arrayOf("January", "February", "March", "April", "May", "June", "July", "August", "September", "October", "November", "December")

        val adapter = ArrayAdapter(this, android.R.layout.simple_spinner_dropdown_item, years.reversed())
        spStartYear.adapter = adapter
        spStartYear.onItemSelectedListener =
                object : AdapterView.OnItemSelectedListener {
                    override fun onNothingSelected(parent: AdapterView<*>?) {
                    }

                    override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
                        startYear = spStartYear.selectedItem.toString()
                        println("selectedyear " + startYear)
                    }
                }
        val monthAdapter = ArrayAdapter(this, android.R.layout.simple_spinner_dropdown_item, monthName)
        spStartMonth.adapter = monthAdapter

        spStartMonth.onItemSelectedListener =
                object : AdapterView.OnItemSelectedListener {
                    override fun onNothingSelected(parent: AdapterView<*>?) {
                    }

                    override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
                        startMonth = spStartMonth.selectedItem.toString()
                        println("selectedyear " + startMonth)
                    }
                }
        val adapterYear = ArrayAdapter(this, android.R.layout.simple_spinner_dropdown_item, years.reversed())
        spEndYear.adapter = adapterYear
        spEndYear.onItemSelectedListener =
                object : AdapterView.OnItemSelectedListener {
                    override fun onNothingSelected(parent: AdapterView<*>?) {
                    }

                    override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
                        endYear = spEndYear.selectedItem.toString()
                        println("selectedyear " + startYear)
                    }
                }
        val monthAdapter1 = ArrayAdapter(this, android.R.layout.simple_spinner_dropdown_item, monthName)
        spEndMonth.adapter = monthAdapter1

        spEndMonth.onItemSelectedListener =
                object : AdapterView.OnItemSelectedListener {
                    override fun onNothingSelected(parent: AdapterView<*>?) {
                    }

                    override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
                        endMonth = spEndMonth.selectedItem.toString()
                        println("selectedyear " + startMonth)
                    }
                }

        radiocOptions.setOnCheckedChangeListener { group, checkedId ->
            if(checkedId==R.id.radioYes){
                llWorkedtill.visibility = View.INVISIBLE
            }else{
                llWorkedtill.visibility = View.VISIBLE
            }
        }

        tvSubmitEmployment.setOnClickListener {
            println("selected ")
            if (radioYes.isChecked) {
                currentOrganization = "1"
                endWork = "Present"
            } else {
                currentOrganization = "0"
                endWork = endYear + "-" + endMonth
            }
            var workStart = startYear + "-" + startMonth

            println("end work is "+endWork+">>>"+currentOrganization)

            when {
                etOrgnizationName.text.toString().isNullOrEmpty() -> {
                    showToast("Enter Organization Name")
                }
                etDesignationName.text.toString().isNullOrEmpty() -> {
                    showToast("Enter Designation")
                }
                etJobDescription.text.toString().isNullOrEmpty() -> {
                    showToast("Enter Description")
                }
                startYear!!.toInt()>endYear!!.toInt()->{
                    showToast("Please check start and end year")
                }
                else -> {
                    val jsonObject = JSONObject()
                    jsonObject.put("id", employmentID)
                    jsonObject.put("is_current_employment", currentOrganization)
                    jsonObject.put("organization_name", etOrgnizationName.text.toString())
                    jsonObject.put("working_start_date", workStart)
                    jsonObject.put("worked_till_date", endWork)
                    jsonObject.put("job_profile_description", etJobDescription.text.toString())
                    jsonObject.put("notice_period", selectedNoticePeriod)
                    jsonObject.put("designation", etDesignationName.text.toString())
                    jsonObject.put("user_id", preferences!!.getPreferencesInt(getString(com.sanswai.achieve.R.string.user_id), 0).toString())

                    println("post json is " + jsonObject)
                    services!!.callJsonObjectRequest("employment", jsonObject)
                    services!!.mResponseInterface = this
                }
            }
        }

        if (intent.hasExtra("employment_id")) {
            employmentID = intent.getIntExtra("employment_id", -1).toString()
            if (employmentID == (-1).toString()) {
                employmentID = ""
            }
            println("employment iuds is " + employmentID)
            val jsonResponse = preferences?.getPreferencesString(getString(com.sanswai.achieve.R.string.pref_employee_details))
            val responseObject = Gson().fromJson(jsonResponse, EmployeeDetails::class.java)
            if (responseObject?.employement?.response != "false") {
                for (i in 0 until responseObject?.employement!!.data!!.size) {
                    if (employmentID == responseObject?.employement!!.data!![i].id.toString()) {
                        val selectedProject = responseObject?.employement!!.data!![i]
                        etOrgnizationName.setText(selectedProject.organizationName)
                        etDesignationName.setText(selectedProject.designation)
                        etJobDescription.setText(selectedProject.jobProfileDescription)
                        if (selectedProject.isCurrentEmployment == "1") {
                            radioYes.isChecked = true
                            endMonth="Present"
                            endYear=""
                            endWork = "Till Date"
                            llWorkedtill.visibility = View.INVISIBLE
                        } else {
                            radioNo.isChecked = true
                        }
                        spNoticePeriod.setSelection((selectedProject.noticePeriod?.toInt()!!.minus(1)))
                        val parts = selectedProject.workingStartDate?.split("-")
                        startYear = parts!![0]
                        spStartYear.setSelection(years.indexOf(startYear!!))
                        startMonth = parts[1]
                        spStartMonth.setSelection(years.indexOf(startMonth!!))
                        if (!selectedProject.workedTillDate!!.contains("present", true)) {
                            val parts1 = selectedProject.workedTillDate?.split("-")
                            endYear = parts1!![0]
                            spEndYear.setSelection(years.indexOf(endYear!!))
                            endMonth = parts1[1]
                            spEndMonth.setSelection(years.indexOf(endMonth!!))
                        }
                    }
                }
            }
        }else{
            endMonth="Present"
            endYear=""
            endWork = "Till Date"
            llWorkedtill.visibility = View.INVISIBLE
        }
    }

    override fun onSuccess(methodName: String, response: Any) {
        println("post json iresponse " + response)
        if ((response as JSONObject).getString("response") == "true") {
            onBackPressed()
        }
    }

    override fun onFailure(methodName: String, volleyError: VolleyError) {
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

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        // Inflate the menu; this adds items to the action bar if it is present.
        menuInflater.inflate(R.menu.menu_logo, menu)
        return true
    }

}
