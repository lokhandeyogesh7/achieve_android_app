package com.sanswai.achieve.fragment


import android.annotation.SuppressLint
import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup

import com.sanswai.achieve.R
import com.sanswai.achieve.global.BaseActivity
import com.sanswai.achieve.response.employeeperformance.EmployeePerformace
import com.sanswai.achieve.response.reviewdetails.ReviewDetails
import kotlinx.android.synthetic.main.layout_review_details.*

class ReviewDetailsFragment : Fragment() {

    lateinit var reviewDetails: ReviewDetails
    lateinit var employeePerformance: EmployeePerformace

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.layout_review_details, container, false)
    }

    @SuppressLint("SetTextI18n")
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val bundle = arguments
        if (null != bundle) {
            if (bundle.getBoolean(getString(R.string.is_employer))) {
                cardEmployee.visibility = View.GONE
                cardEmployer.visibility = View.VISIBLE
                employeePerformance = EmployeePerformace()
                employeePerformance = bundle.getSerializable("review_data") as EmployeePerformace
                lblEmployeename.text = bundle.getString(getString(R.string.employee_name))
                rbEmployeeReviewEmp.rating = employeePerformance.averageRating!!.toFloat()
                tvPerReviewEmp.text = employeePerformance.performanceStatus

            } else {
                cardEmployee.visibility = View.VISIBLE
                cardEmployer.visibility = View.GONE
                reviewDetails = ReviewDetails()
                reviewDetails = bundle.getSerializable("review_data") as ReviewDetails
                rbMainReview.rating = reviewDetails.avgRatings!!
                tvMainAvgReview.text = reviewDetails.avgReview!!
                tvReviewFeedback.text = reviewDetails.reviewFeedback!!
                tvDate.text = "From date : ${reviewDetails.startDate!!}  To date : ${reviewDetails.endDate}"
            }
        } else {
            (activity as BaseActivity).showToast("Unable to get data")
        }
    }
}
