package com.sanswai.achieve.activity

import android.annotation.SuppressLint
import android.app.DatePickerDialog
import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.LinearLayout
import com.android.volley.VolleyError
import com.google.gson.Gson
import com.sanswai.achieve.R
import com.sanswai.achieve.adapter.ReviewQuestionEmpAdapter
import com.sanswai.achieve.global.BaseActivity
import com.sanswai.achieve.global.Preferences
import com.sanswai.achieve.network.VolleyService
import com.sanswai.achieve.response.performancequestions.PerformanceQuestions
import com.sanswai.achieve.response.performancequestions.QuestionsDatum
import kotlinx.android.synthetic.main.activity_add_review.*
import org.json.JSONArray
import org.json.JSONObject
import java.util.*


class AddReviewActivity : BaseActivity(), VolleyService.SetResponse, View.OnClickListener {

    var services: VolleyService? = null
    var preferences: Preferences? = null
    lateinit var arrRevQuestions: ArrayList<QuestionsDatum>
    private var mYear: Int = 0
    var mMonth: Int = 0
    var mDay: Int = 0
    var employee_id: String? = null
    var employer_id: String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(com.sanswai.achieve.R.layout.activity_add_review)
        services = VolleyService(this)
        preferences = Preferences.getInstance(this)

        tvStartDate.setOnClickListener(this)
        tvEndDate.setOnClickListener(this)

        supportActionBar!!.title = "Add Review"
        supportActionBar!!.setDisplayHomeAsUpEnabled(true)
        supportActionBar!!.setDisplayShowHomeEnabled(true)

        employee_id = intent.getStringExtra(getString(R.string.employee_id))
        employer_id = preferences!!.getPreferencesInt(getString(R.string.user_id),0).toString()

        getQuestionList()
    }

    private fun getQuestionList() {
        services!!.callJsonObjectRequest(getString(com.sanswai.achieve.R.string.api_performance_questions), JSONObject())
        services!!.mResponseInterface = this
    }

    override fun onFailure(methodName: String, volleyError: VolleyError) {
        println("request failed $methodName and rerror is ${volleyError.localizedMessage}")
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.menu_review, menu)
        return true
    }


    override fun onOptionsItemSelected(item: MenuItem?): Boolean {
        when (item!!.itemId) {
            R.id.action_save -> {

                var strStartDate = tvStartDate.text.toString()
                var strEndDate = tvEndDate.text.toString()

                checkForDate(strStartDate, strEndDate)
                return true
            }

            android.R.id.home -> {
                onBackPressed()
                return true
            }
        }
        return true
    }

    private fun checkForDate(strStartDate: String, strEndDate: String) {
        val jsonObject = JSONObject()
        jsonObject.put("employer_id", employer_id)
        jsonObject.put("start_date", strStartDate)
        jsonObject.put("end_date", strEndDate)

        services!!.callJsonObjectRequest(getString(R.string.api_validate_date) + employee_id, jsonObject)
        services!!.mResponseInterface = this
    }

    override fun onSuccess(methodName: String, response: Any) {
        println("method name is $methodName")
        if (methodName.contains(getString(R.string.api_add_review))) {
            println("response after add is ${response}")
            if ((response as JSONObject).getString("response") == "true") {
                showToast("Review Submitted Successfully.")
                onBackPressed()
            } else {
                showToast("Error In Processing Your Request.")
            }
        } else if (methodName.contains(getString(R.string.api_validate_date))) {
            if ((response as JSONObject).getString("response") == "false") {
                showToast("Record exist for the entered date, please check date.")
            } else {
                var strStartDate = tvStartDate.text.toString()
                var strEndDate = tvEndDate.text.toString()
                var strfeedbackDetails = etFeedbackDetails.text.toString()
                if (strStartDate == "Start Date" || strEndDate == "End Date") {
                    showToast("Please Select Start Date And End Date")
                } else if (strfeedbackDetails.trim().isEmpty()) {
                    showToast("Please Enter Feedback Details")
                } else {
                    var jsonObject = JSONObject()
                    jsonObject.put("user_id", employee_id)
                    jsonObject.put("employer_id", employer_id)
                    jsonObject.put("start_date", strStartDate)
                    jsonObject.put("end_date", strEndDate)
                    jsonObject.put("feedback_details", strfeedbackDetails)
                    var jsonArray = JSONArray()
                    for (i in 0 until arrRevQuestions.size) {
                        var jsonObject1 = JSONObject()
                        jsonObject1.put("question_id", arrRevQuestions[i].id.toString())
                        jsonObject1.put("points", arrRevQuestions[i].rating.toString())
                        jsonArray.put(jsonObject1)
                    }
                    jsonObject.put("ratings", jsonArray)

                    println("jsonObject post is $jsonObject")
                    services!!.callJsonObjectRequest(getString(R.string.api_add_review), jsonObject)
                    services!!.mResponseInterface = this
                }
            }
        } else {
            val revQuestionsResponse = Gson().fromJson(response.toString(), PerformanceQuestions::class.java)
            arrRevQuestions = revQuestionsResponse.questionsData as ArrayList<QuestionsDatum>
            for (i in 0 until arrRevQuestions.size) {
                arrRevQuestions[i].rating = 1f
            }
            rvRevQuestionEmp.layoutManager = LinearLayoutManager(this, LinearLayout.VERTICAL, false)
            rvRevQuestionEmp.adapter = ReviewQuestionEmpAdapter(this, arrRevQuestions, revQuestionsResponse.ratingsData)
        }
    }


    @SuppressLint("SetTextI18n")
    override fun onClick(v: View?) {
        when (v!!.id) {
            R.id.tvStartDate -> {
                val c = Calendar.getInstance()
                mYear = c.get(Calendar.YEAR)
                mMonth = c.get(Calendar.MONTH)
                mDay = c.get(Calendar.DAY_OF_MONTH)

                val datePickerDialog = DatePickerDialog(this,
                        DatePickerDialog.OnDateSetListener { view, year, monthOfYear, dayOfMonth -> tvStartDate.text = year.toString() + "-" + (monthOfYear + 1) + "-" + dayOfMonth.toString() }, mYear, mMonth, mDay)
                datePickerDialog.show()
            }

            R.id.tvEndDate -> {
                val c = Calendar.getInstance()
                mYear = c.get(Calendar.YEAR)
                mMonth = c.get(Calendar.MONTH)
                mDay = c.get(Calendar.DAY_OF_MONTH)

                val datePickerDialog = DatePickerDialog(this,
                        DatePickerDialog.OnDateSetListener { view, year, monthOfYear, dayOfMonth -> tvEndDate.text = year.toString() + "-" + (monthOfYear + 1) + "-" + dayOfMonth.toString() }, mYear, mMonth, mDay)
                datePickerDialog.show()
            }
        }
    }
}
