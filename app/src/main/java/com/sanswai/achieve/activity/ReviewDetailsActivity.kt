package com.sanswai.achieve.activity

import android.os.Bundle
import android.support.design.widget.TabLayout
import android.support.v4.app.Fragment
import android.support.v4.app.FragmentManager
import android.support.v4.app.FragmentPagerAdapter
import android.view.MenuItem
import android.view.View
import com.android.volley.VolleyError
import com.google.gson.Gson
import com.sanswai.achieve.R
import com.sanswai.achieve.fragment.EmployerReviewListFragment
import com.sanswai.achieve.fragment.ReviewDetailsFragment
import com.sanswai.achieve.fragment.ReviewQuestionFragment
import com.sanswai.achieve.global.BaseActivity
import com.sanswai.achieve.network.VolleyService
import com.sanswai.achieve.response.employeeperformance.EmployeePerformace
import com.sanswai.achieve.response.reviewdetails.ReviewDetails
import kotlinx.android.synthetic.main.activity_review_details.*
import org.json.JSONObject
import java.io.Serializable
import java.util.*


class ReviewDetailsActivity : BaseActivity(), VolleyService.SetResponse {
    var tabLayout: TabLayout? = null
    var isEmployer: Boolean = false
    var userId: String? = null
    var username: String? = null
    private var services: VolleyService? = null
    private var reviewDetails: ReviewDetails? = null
    lateinit var employeePerformance: EmployeePerformace

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(com.sanswai.achieve.R.layout.activity_review_details)
        setSupportActionBar(toolbarReview)
        supportActionBar!!.setDisplayShowHomeEnabled(true)
        supportActionBar!!.setDisplayHomeAsUpEnabled(true)

        supportActionBar!!.title = "Average Review"
        userId = intent.getIntExtra(getString(R.string.employee_id), 0).toString()
        username = intent.getStringExtra(getString(R.string.employee_name))
        services = VolleyService(this)


    }

    override fun onResume() {
        super.onResume()
        if (intent != null && intent.getBooleanExtra("isEmployer", false)) {
            getPerformanceReview()
        } else {
            getReviewDetails()
        }
    }

    private fun getReviewDetails() {
        services!!.callJsonObjectRequest(getString(R.string.api_performance_fetails) + userId, JSONObject())
        services!!.mResponseInterface = this
    }

    private fun getPerformanceReview() {
        services!!.callJsonObjectRequest(getString(R.string.api_employee_performance) + userId, JSONObject())
        services!!.mResponseInterface = this
    }

    override fun onSuccess(methodName: String, response: Any) {
        if (intent != null && intent.getBooleanExtra("isEmployer", false)) {
            isEmployer = true
            employeePerformance = Gson().fromJson(response.toString(), EmployeePerformace::class.java)
            val adapter = ViewPagerAdapter(supportFragmentManager)
            adapter.addFragment(ReviewDetailsFragment())
            adapter.addFragment(EmployerReviewListFragment())
            viewpagerReview.adapter = adapter
            tabLayout = findViewById<View>(com.sanswai.achieve.R.id.tabsLayout) as TabLayout
            tabLayout!!.setupWithViewPager(viewpagerReview)
            tabLayout!!.getTabAt(0)!!.text = "Review Details"
            tabLayout!!.getTabAt(1)!!.text = "Review List"
        } else {
            reviewDetails = Gson().fromJson(response.toString(), ReviewDetails::class.java)
            val adapter = ViewPagerAdapter(supportFragmentManager)
            adapter.addFragment(ReviewDetailsFragment())
            adapter.addFragment(ReviewQuestionFragment())
            viewpagerReview.adapter = adapter
            tabLayout = findViewById<View>(com.sanswai.achieve.R.id.tabsLayout) as TabLayout
            tabLayout!!.setupWithViewPager(viewpagerReview)
            tabLayout!!.getTabAt(0)!!.text = "Review Details"
            tabLayout!!.getTabAt(1)!!.text = "Review Questions"
        }
    }

    override fun onFailure(methodName: String, volleyError: VolleyError) {

    }

    internal inner class ViewPagerAdapter(manager: FragmentManager) : FragmentPagerAdapter(manager) {
        val mFragmentList = ArrayList<Fragment>()
        override fun getItem(position: Int): Fragment {
            if (intent != null && intent.getBooleanExtra("isEmployer", false)) {
                val bundle = Bundle()
                val weatherFragment = mFragmentList[position]
                bundle.putSerializable("review_data", employeePerformance as Serializable)
                bundle.putSerializable(getString(R.string.is_employer), true)
                bundle.putSerializable(getString(R.string.employee_name), username)
                bundle.putSerializable(getString(R.string.employee_id), userId)
                weatherFragment.arguments = bundle
                return mFragmentList[position]
            } else {
                val bundle = Bundle()
                val weatherFragment = mFragmentList[position]
                bundle.putSerializable("review_data", reviewDetails as Serializable)
                bundle.putSerializable(getString(R.string.is_employer), false)
                bundle.putSerializable(getString(R.string.employee_id), userId)
                weatherFragment.arguments = bundle
                return mFragmentList[position]
            }
        }

        override fun getCount(): Int {
            return mFragmentList.size
        }

        fun addFragment(fragment: Fragment) {
            mFragmentList.add(fragment)
        }
    }

    override fun onOptionsItemSelected(item: MenuItem?): Boolean {
        onBackPressed()
        return super.onOptionsItemSelected(item)
    }

}
