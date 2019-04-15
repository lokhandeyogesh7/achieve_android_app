package com.sanswai.achieve.adapter

import android.content.Context
import android.content.Intent
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import com.sanswai.achieve.R
import com.sanswai.achieve.activity.ReviewDetailsActivity
import com.sanswai.achieve.response.employeeperformance.Datum

class EmpReviewListAdapter(private val mContext: Context, private val empReviewList: ArrayList<Datum>?, private val userId: Int) : RecyclerView.Adapter<EmpReviewListAdapter.MyViewHolder>() {

    inner class MyViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        var tvDateRevList: TextView = view.findViewById(R.id.tvDateRevList)!!
        var tvFeedbackEmployee: TextView = view.findViewById(R.id.tvFeedbackEmployee)!!
        var tvPerStatusEmployee: TextView = view.findViewById(R.id.tvPerStatusEmployee)!!
        var rbEmployeeReview: TextView = view.findViewById(R.id.rbEmployeeReview)!!
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val itemView = LayoutInflater.from(parent.context)
                .inflate(R.layout.row_employer_review_list, parent, false)

        return MyViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        val education = empReviewList!![position]
        holder.tvDateRevList.text = "From Date: ${education.startDate}  \nTo Date: ${education.endDate}"
        holder.tvFeedbackEmployee.text = education.feedbackDetails
        holder.tvPerStatusEmployee.text = education.performanceStatus
        holder.rbEmployeeReview.text = "Rating: " + Math.abs(education.avgRating!!)

        holder.itemView.setOnClickListener {
            val intent = Intent(mContext, ReviewDetailsActivity::class.java)
            intent.putExtra(mContext.getString(R.string.employee_id), education.id)
            mContext.startActivity(intent)
        }
    }

    override fun getItemCount(): Int {
        return empReviewList!!.size
    }
}