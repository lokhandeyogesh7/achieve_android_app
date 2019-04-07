package com.sanswai.achieve.activity

import android.os.Bundle
import android.view.MenuItem
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.RadioButton
import android.widget.Spinner
import com.android.volley.VolleyError
import com.google.gson.Gson
import com.sanswai.achieve.R
import com.sanswai.achieve.global.BaseActivity
import com.sanswai.achieve.global.Preferences
import com.sanswai.achieve.network.VolleyService
import com.sanswai.achieve.response.coursedetails.CourseDetails
import com.sanswai.achieve.response.coursedetails.Datum
import com.sanswai.achieve.response.education.Education
import com.sanswai.achieve.response.specialization.Specialization
import kotlinx.android.synthetic.main.activity_edit_career_path.*
import kotlinx.android.synthetic.main.activity_edit_education_details.*
import org.json.JSONObject
import java.lang.Exception
import java.util.*
import kotlin.collections.ArrayList


class EditEducationDetailsActivity : BaseActivity(), VolleyService.SetResponse, AdapterView.OnItemSelectedListener {

    private var services: VolleyService? = null
    private var preferences: Preferences? = null
    private lateinit var selectedEducation: String
    private lateinit var selectedCourse: String
    private lateinit var passingYear: String
    private lateinit var selectedSpecialization: String
    private lateinit var selectedGrade: String
    private lateinit var courseType: String

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_edit_education_details)

        services = VolleyService(this)
        preferences = Preferences.getInstance(this)

        supportActionBar!!.title = "Add/Edit Educational Details"
        supportActionBar!!.setDisplayHomeAsUpEnabled(true)
        supportActionBar!!.setDisplayShowHomeEnabled(true)

        getEducationType()



        tvSubmitEducation.setOnClickListener {
            val jsonObject = JSONObject()
            jsonObject.put("id", "")
            jsonObject.put("education_id", selectedEducation)
            jsonObject.put("course_id", selectedCourse)
            jsonObject.put("specialization_id", selectedSpecialization)
            jsonObject.put("institute_name", etCollege.text.toString())
            jsonObject.put("course_type", courseType)
            jsonObject.put("passing_year", passingYear)
            jsonObject.put("grading_system_id", selectedGrade)
            jsonObject.put("marks_percentage", etMarks.text.toString())
            jsonObject.put("user_id", preferences!!.getPreferencesInt(getString(R.string.user_id),0).toString())

            println("jsopnobject si "+jsonObject)
            services!!.callJsonObjectRequest(getString(R.string.api_post_educational_details), jsonObject)
            services!!.mResponseInterface = this
        }


        radiocType.setOnCheckedChangeListener { group, checkedId ->
            val checkedRadioButton = group?.findViewById(checkedId) as RadioButton
            if (checkedRadioButton.isChecked) {
                val strcourseType = checkedRadioButton.text.toString()
                when {
                    strcourseType.contains("Full") -> {
                        courseType = "1"
                    }
                    strcourseType.contains("Part") -> {
                        courseType = "0"
                    }
                    strcourseType.contains("Corr") -> {
                        courseType = "2"
                    }
                }
            }
        }

        when {
            radioFullTime.isChecked -> {
                courseType = "1"
            }
            radioPartTime.isChecked -> {
                courseType = "0"
            }
            radioDL.isChecked -> {
                courseType = "2"
            }
        }

        val years = ArrayList<String>()
        val thisYear = Calendar.getInstance().get(Calendar.YEAR)
        for (i in 1985..thisYear) {
            years.add(Integer.toString(i))
        }

        val adapter = ArrayAdapter(this, android.R.layout.simple_spinner_dropdown_item, years)
        spPassingYear.adapter = adapter

        spPassingYear.onItemSelectedListener =
                object : AdapterView.OnItemSelectedListener {
                    override fun onNothingSelected(parent: AdapterView<*>?) {
                    }

                    override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
                        passingYear = spPassingYear.selectedItem.toString()
                        println("selectedyear " + passingYear)
                    }
                }

        val arrGrades = ArrayList<String>()
        arrGrades.add("10 Grade")
        arrGrades.add("4 Grade")
        arrGrades.add("% marks out of 100")
        arrGrades.add("Course require to pass")
        val adapter1 = ArrayAdapter(this, android.R.layout.simple_spinner_dropdown_item, arrGrades)
        spGradeSystem.adapter = adapter1
        spGradeSystem.onItemSelectedListener =
                object : AdapterView.OnItemSelectedListener {
                    override fun onNothingSelected(parent: AdapterView<*>?) {
                    }

                    override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
                        selectedGrade = (position + 1).toString()
                        println("selectedyear " + selectedGrade)
                        println("selectedyear " + (position + 1))
                    }
                }


        spEducation.onItemSelectedListener = this

        spCourses.onItemSelectedListener =
                object : AdapterView.OnItemSelectedListener {
                    override fun onNothingSelected(parent: AdapterView<*>?) {
                    }

                    override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
                        val education = ((spCourses as Spinner).selectedItem as Datum)
                        selectedCourse = education!!.id.toString()!!
                        getSpecializationFromCourse(education.id.toString())
                    }

                }

        spSpecialization.onItemSelectedListener =
                object : AdapterView.OnItemSelectedListener {
                    override fun onNothingSelected(parent: AdapterView<*>?) {
                    }

                    override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
                        if ((spSpecialization as Spinner).selectedItem != null) {
                            val education = ((spSpecialization as Spinner).selectedItem as com.sanswai.achieve.response.specialization.Datum)
                            selectedSpecialization = education.id.toString()
                        }
                    }
                }
    }

    override fun onNothingSelected(parent: AdapterView<*>?) {
    }

    override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
        when (parent!!.id) {

            R.id.spEducation -> {
                val education = ((spEducation as Spinner).selectedItem as com.sanswai.achieve.response.education.Datum)
                selectedEducation = education.id.toString()
                getCourseFromEducation(education.id.toString())
            }
        }
    }

    private fun getSpecializationFromCourse(courseId: String) {
        services!!.callJsonGETRequest(getString(R.string.api_specialization) + "/" + courseId, JSONObject())
        services!!.mResponseInterface = this
    }

    private fun getCourseFromEducation(datum: String) {
        services!!.callJsonGETRequest(getString(R.string.api_course) + "/" + datum, JSONObject())
        services!!.mResponseInterface = this
    }


    private fun getEducationType() {
        services!!.callJsonGETRequest(getString(R.string.api_education), JSONObject())
        services!!.mResponseInterface = this
    }

    override fun onSuccess(methodName: String, response: Any) {
        when {
            methodName == getString(R.string.api_education) -> {
                val functionalResponse = Gson().fromJson(response.toString(), Education::class.java)
                val dataAdapter = ArrayAdapter<com.sanswai.achieve.response.education.Datum>(this, android.R.layout.simple_spinner_dropdown_item, functionalResponse.data!!.toList())
                spEducation.adapter = dataAdapter
                spEducation.prompt = "Select Education"
            }
            methodName.contains(getString(R.string.api_course)) -> {
                println("reponse for course is " + response)
                try {
                    if ((response as JSONObject).getString("response") == "false") {
                        spCourses.adapter = null
                    } else {
                        val functionalResponse = Gson().fromJson(response.toString(), CourseDetails::class.java)
                        val dataAdapter = ArrayAdapter<Datum>(this, android.R.layout.simple_spinner_dropdown_item, functionalResponse.data!!.toList())
                        spCourses.adapter = dataAdapter
                        spCourses.prompt = "Select Course"
                    }
                } catch (e: Exception) {
                    e.printStackTrace()
                }
            }
            methodName.contains(getString(R.string.api_specialization)) -> {
                println("reponse for api_specialization is " + response)
                try {
                    if ((response as JSONObject).getString("response") == "false") {
                        spSpecialization.adapter = null
                    } else {
                        val functionalResponse = Gson().fromJson(response.toString(), Specialization::class.java)
                        val dataAdapter = ArrayAdapter<com.sanswai.achieve.response.specialization.Datum>(this, android.R.layout.simple_spinner_dropdown_item, functionalResponse.data!!.toList())
                        spSpecialization.adapter = dataAdapter
                        spSpecialization.prompt = "Select Specialization"
                    }
                } catch (e: Exception) {
                    e.printStackTrace()
                }
            }
            methodName == getString(R.string.api_post_educational_details) -> {
                if ((response as JSONObject).getString("response") == "true") {
                    onBackPressed()
                }
            }
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

}
