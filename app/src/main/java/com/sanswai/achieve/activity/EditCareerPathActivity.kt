package com.sanswai.achieve.activity

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.view.Menu
import android.view.MenuItem
import android.widget.ArrayAdapter
import android.widget.RadioButton
import android.widget.RadioGroup
import android.widget.Spinner
import com.android.volley.error.VolleyError
import com.google.gson.Gson
import com.sanswai.achieve.R
import com.sanswai.achieve.global.Preferences
import com.sanswai.achieve.network.VolleyService
import com.sanswai.achieve.response.careerroles.CareerRoles
import com.sanswai.achieve.response.employeedetails.EmployeeDetails
import com.sanswai.achieve.response.functionalarea.Datum
import com.sanswai.achieve.response.functionalarea.FunctionalArea
import com.sanswai.achieve.response.industries.Industries
import com.sanswai.achieve.response.locationlist.Location
import kotlinx.android.synthetic.main.activity_edit_career_path.*
import kotlinx.android.synthetic.main.activity_emp_profile.*
import org.json.JSONObject
import java.lang.Exception
import android.R.attr.data
import android.content.Intent



class EditCareerPathActivity : AppCompatActivity(), VolleyService.SetResponse, RadioGroup.OnCheckedChangeListener {

    private var services: VolleyService? = null
    private var preferences: Preferences? = null
    var functionalArea: String? = null
    var industries: String? = null
    var role: String? = null
    private lateinit var jobType: String
    private lateinit var empType: String
    private lateinit var shiftType: String
    private lateinit var salaryType: String
    private lateinit var desiredLocation: String
    private lateinit var jsonResponse: String
    private var careerId: String = ""
    var functionalResponse: FunctionalArea? = null
    var insustriesRespone: Industries? = null
    var rolesResponse: CareerRoles? = null
    var locationResponse: Location? = null
    var responseObject: EmployeeDetails? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_edit_career_path)

        services = VolleyService(this)
        preferences = Preferences.getInstance(this)

        jsonResponse = preferences?.getPreferencesString(getString(R.string.pref_employee_details))!!
        if (jsonResponse != null) {
            responseObject = Gson().fromJson(jsonResponse, EmployeeDetails::class.java)
        }


        getFunctionArea()
        getIndustries()
        getCareerRoles()
        getLocaionList()

        supportActionBar!!.title = "Edit Career Profile"
        supportActionBar!!.setDisplayHomeAsUpEnabled(true)
        supportActionBar!!.setDisplayShowHomeEnabled(true)




        tvSubmitCareerPath.setOnClickListener {
            functionalArea = ((spFunctionalArea as Spinner).selectedItem as Datum).id.toString()
            role = ((spRole as Spinner).selectedItem as com.sanswai.achieve.response.careerroles.Datum).id.toString()
            industries = ((spIndustry as Spinner).selectedItem as com.sanswai.achieve.response.industries.Datum).id.toString()
            desiredLocation = ((spLocation as Spinner).selectedItem as com.sanswai.achieve.response.locationlist.Datum).id.toString()

            val jsonObject = JSONObject()
            jsonObject.put("id", careerId)
            jsonObject.put("functional_area_id", functionalArea)
            jsonObject.put("career_role_id", role)
            jsonObject.put("desired_location_id", desiredLocation)
            jsonObject.put("desired_industry_id", industries)
            jsonObject.put("job_type", jobType)
            jsonObject.put("employment_type", empType)
            jsonObject.put("desired_shift", shiftType)
            jsonObject.put("expected_salary_type", salaryType)
            jsonObject.put("expected_salary_amount", etExpSalary.text.toString())
            jsonObject.put("user_id", preferences!!.getPreferencesInt(getString(R.string.user_id), 0).toString())

            services!!.callJsonObjectRequest(getString(R.string.api_desired_profile), jsonObject)
            services!!.mResponseInterface = this
        }

        radioJobType.setOnCheckedChangeListener(this)
        radioEmpType.setOnCheckedChangeListener(this)
        radioShifts.setOnCheckedChangeListener(this)
        radioSalary.setOnCheckedChangeListener(this)

        val value = radioJobType.checkedRadioButtonId
        println("cehcked button is " + value)
        val text = radioJobType.findViewById(value) as RadioButton
        jobType = text.text.toString()
        val value1 = radioEmpType.checkedRadioButtonId
        val text1 = radioEmpType.findViewById(value1) as RadioButton
        empType = text1.text.toString()
        val value2 = radioShifts.checkedRadioButtonId
        val text2 = radioShifts.findViewById(value2) as RadioButton
        shiftType = text2.text.toString()
        val value3 = radioSalary.checkedRadioButtonId
        val text3 = radioSalary.findViewById(value3) as RadioButton
        salaryType = text3.text.toString()

    }

    override fun onResume() {
        super.onResume()
        prepopulateData()
    }

    private fun prepopulateData() {
        try {
            careerId = responseObject!!.desiredProfile!!.data!!.get(0)!!.id.toString()
            val selectedProject = responseObject!!.desiredProfile!!.data!!.get(0)!!
            if (jsonResponse != null) {
                for (j in 0 until functionalResponse!!.data!!.size) {
                    if (selectedProject.functionalAreaId.toString() == functionalResponse!!.data!![j].id.toString()) {
                        spFunctionalArea.setSelection(functionalResponse!!.data!!.indexOf(functionalResponse!!.data!![j]))
                    }
                }
                for (j in 0 until insustriesRespone!!.data!!.size) {
                    if (selectedProject.desiredIndustryId.toString() == insustriesRespone!!.data!![j].id.toString()) {
                        spIndustry.setSelection(insustriesRespone!!.data!!.indexOf(insustriesRespone!!.data!![j]))
                    }
                }
                for (j in 0 until rolesResponse!!.data!!.size) {
                    if (selectedProject.careerRoleId.toString() == rolesResponse!!.data!![j].id.toString()) {
                        spRole.setSelection(rolesResponse!!.data!!.indexOf(rolesResponse!!.data!![j]))
                    }
                }
                for (j in 0 until locationResponse!!.data!!.size) {
                    if (selectedProject.desiredLocationId.toString() == locationResponse!!.data!![j].id.toString()) {
                        spLocation.setSelection(locationResponse!!.data!!.indexOf(locationResponse!!.data!![j]))
                    }
                }
                if (selectedProject.jobType!!.contains("perm", true)) {
                    radioPermanent.isChecked = true
                } else {
                    radioContract.isChecked = true
                }

                if (selectedProject.employmentType!!.contains("full", true)) {
                    radioFT.isChecked = true
                } else {
                    radioPT.isChecked = true
                }

                when {
                    selectedProject.desiredShift!!.contains("day", true) -> radioDay.isChecked = true
                    selectedProject.desiredShift!!.contains("night", true) -> radioNight.isChecked = true
                    else -> radioFlexi.isChecked = true
                }
                etExpSalary.setText(selectedProject.expectedSalaryAmount)
                if (selectedProject.expectedSalaryType!!.contains("indian", true)) {
                    radioINR.isChecked = true
                } else {
                    radioDollar.isChecked = true
                }
            }
            if (careerId == null) {
                careerId = ""
            }
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

    private fun getLocaionList() {
        services!!.callJsonGETRequest(getString(R.string.api_location), JSONObject())
        services!!.mResponseInterface = this
    }

    private fun getCareerRoles() {
        services!!.callJsonGETRequest(getString(R.string.api_career_roles), JSONObject())
        services!!.mResponseInterface = this
    }

    private fun getIndustries() {
        services!!.callJsonGETRequest(getString(R.string.api_industries), JSONObject())
        services!!.mResponseInterface = this
    }

    private fun getFunctionArea() {
        services!!.callJsonGETRequest(getString(R.string.api_functional_area), JSONObject())
        services!!.mResponseInterface = this
    }

    override fun onSuccess(methodName: String, response: Any) {
        when (methodName) {
            getString(R.string.api_functional_area) -> {
                functionalResponse = Gson().fromJson(response.toString(), FunctionalArea::class.java)
                val dataAdapter = ArrayAdapter<Datum>(this, android.R.layout.simple_spinner_dropdown_item, functionalResponse!!.data!!.toList())
                spFunctionalArea.adapter = dataAdapter
                prepopulateData()
            }
            getString(R.string.api_industries) -> {
                insustriesRespone = Gson().fromJson(response.toString(), Industries::class.java)
                val dataAdapter = ArrayAdapter<com.sanswai.achieve.response.industries.Datum>(this, android.R.layout.simple_spinner_dropdown_item, insustriesRespone!!.data!!.toList())
                spIndustry.adapter = dataAdapter
                prepopulateData()
            }
            getString(R.string.api_career_roles) -> {
                rolesResponse = Gson().fromJson(response.toString(), CareerRoles::class.java)
                val dataAdapter = ArrayAdapter<com.sanswai.achieve.response.careerroles.Datum>(this, android.R.layout.simple_spinner_dropdown_item, rolesResponse!!.data!!.toList())
                spRole.adapter = dataAdapter
                prepopulateData()
            }
            getString(R.string.api_location) -> {
                locationResponse = Gson().fromJson(response.toString(), Location::class.java)
                println("location response "+response.toString())
                if (locationResponse!!.response!="false") {
                    val dataAdapter = ArrayAdapter<com.sanswai.achieve.response.locationlist.Datum>(this, android.R.layout.simple_spinner_dropdown_item, locationResponse!!.data!!.toList())
                    spLocation.adapter = dataAdapter
                }
                prepopulateData()
            }
            getString(R.string.api_desired_profile) -> {
                if ((response as JSONObject).getString("response") == "true") {
                    onBackPressed()
                }
            }
        }
    }

    override fun onFailure(methodName: String, volleyError: VolleyError) {
    }

    override fun onCheckedChanged(group: RadioGroup?, checkedId: Int) {
        when (group!!.id) {
            R.id.radioJobType -> {
                val checkedRadioButton = group?.findViewById(checkedId) as RadioButton
                if (checkedRadioButton.isChecked)
                    jobType = checkedRadioButton.text.toString()
            }
            R.id.radioEmpType -> {
                val checkedRadioButton = group?.findViewById(checkedId) as RadioButton
                if (checkedRadioButton.isChecked)
                    empType = checkedRadioButton.text.toString()
            }
            R.id.radioShifts -> {
                val checkedRadioButton = group?.findViewById(checkedId) as RadioButton
                if (checkedRadioButton.isChecked)
                    shiftType = checkedRadioButton.text.toString()
            }
            R.id.radioSalary -> {
                val checkedRadioButton = group?.findViewById(checkedId) as RadioButton
                if (checkedRadioButton.isChecked)
                    salaryType = checkedRadioButton.text.toString()
            }
        }
    }

    override fun onOptionsItemSelected(item: MenuItem?): Boolean {
        when (item!!.itemId) {
            android.R.id.home -> {
                //EmpProfileActivity().getTheDetails(2)
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

    override fun onBackPressed() {
        super.onBackPressed()
        val intent = Intent()
        intent.putExtra("MyData", 2)
        setResult(101, intent)
    }

}
