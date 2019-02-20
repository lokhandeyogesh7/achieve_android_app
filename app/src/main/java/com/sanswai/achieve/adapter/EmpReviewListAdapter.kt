package com.sanswai.achieve.adapter

import android.content.Context
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import com.sanswai.achieve.R
import com.sanswai.achieve.fragment.Education
import com.sanswai.achieve.model.EmployeeRevList

class EmpReviewListAdapter (val mContext: Context, private val empReviewList: ArrayList<EmployeeRevList>?) : RecyclerView.Adapter<EmpReviewListAdapter.MyViewHolder>() {

    inner class MyViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        var tvDateRevList: TextView = view.findViewById(R.id.tvDateRevList)
        var tvFeedbackEmployee: TextView = view.findViewById(R.id.tvFeedbackEmployee)
        var tvPerStatusEmployee: TextView = view.findViewById(R.id.tvPerStatusEmployee)

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val itemView = LayoutInflater.from(parent.context)
                .inflate(R.layout.row_employer_review_list, parent, false)

        return MyViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        val education = empReviewList!![position]
        holder.tvDateRevList.text = education.date
        holder.tvFeedbackEmployee.text = education.feedback
        holder.tvPerStatusEmployee.text = education.perStatus
    }

    override fun getItemCount(): Int {
        return empReviewList!!.size
    }
}