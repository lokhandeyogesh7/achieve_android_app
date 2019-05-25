package com.sanswai.achieve.fragment


import android.annotation.SuppressLint
import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup

import com.sanswai.achieve.global.BaseActivity
import com.sanswai.achieve.response.employeeperformance.EmployeePerformace
import com.sanswai.achieve.response.reviewdetails.ReviewDetails
import kotlinx.android.synthetic.main.layout_review_details.*
import android.text.method.ScrollingMovementMethod



class ReviewDetailsFragment : Fragment() {

    lateinit var reviewDetails: ReviewDetails
    lateinit var employeePerformance: EmployeePerformace

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(com.sanswai.achieve.R.layout.layout_review_details, container, false)
    }

    @SuppressLint("SetTextI18n")
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val bundle = arguments
        if (null != bundle) {
            if (bundle.getBoolean(getString(com.sanswai.achieve.R.string.is_employer))) {
                cardEmployee.visibility = View.GONE
                cardEmployer.visibility = View.VISIBLE
                employeePerformance = EmployeePerformace()
                employeePerformance = bundle.getSerializable("review_data") as EmployeePerformace
                lblEmployeename.text = bundle.getString(getString(com.sanswai.achieve.R.string.employee_name))
                rbEmployeeReviewEmp.text = "Rating: "+Math.abs(employeePerformance.averageRating!!)
                tvPerReviewEmp.text = employeePerformance.performanceStatus
                tvPerReviewEmp.setMovementMethod(ScrollingMovementMethod())

            } else {
                cardEmployee.visibility = View.VISIBLE
                cardEmployer.visibility = View.GONE
                reviewDetails = ReviewDetails()
                reviewDetails = bundle.getSerializable("review_data") as ReviewDetails
                rbMainReview.text = "Rating: "+Math.abs(reviewDetails.avgRatings!!)
                tvMainAvgReview.text = reviewDetails.avgReview!!
                tvReviewFeedback.text = reviewDetails.reviewFeedback!!
                tvReviewFeedback.setMovementMethod(ScrollingMovementMethod())
                tvDate.text = "From date : ${reviewDetails.startDate!!}  \nTo date : ${reviewDetails.endDate}"
            }
        } else {
            (activity as BaseActivity).showToast("Unable to get data")
        }
    }
}
