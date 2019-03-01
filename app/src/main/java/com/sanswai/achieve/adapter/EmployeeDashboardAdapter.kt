package com.sanswai.achieve.adapter

import android.content.Context
import android.content.Intent
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.RatingBar
import android.widget.TextView
import com.sanswai.achieve.R
import com.sanswai.achieve.activity.EmpProfileActivity
import com.sanswai.achieve.activity.ReviewDetailsActivity
import com.sanswai.achieve.response.employerdashboard.Datum

class EmployeeDashboardAdapter(private val mContext: Context, private val employeeList: ArrayList<Datum>?) : RecyclerView.Adapter<EmployeeDashboardAdapter.MyViewHolder>(){


    inner class MyViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        var tvEmployeeName: TextView = view.findViewById(R.id.tvEmployeeName)
        var tvEmail: TextView = view.findViewById(R.id.tvEmail)
        var tvPhone: TextView = view.findViewById(R.id.tvPhone)
        var rbEmployee: RatingBar = view.findViewById(R.id.rbEmployee)
        var tvPerStatusEmployee: TextView = view.findViewById(R.id.tvPerStatusEmployee)
        var tvRecordDetails: TextView = view.findViewById(R.id.tvRecordDetails)
        var tvEmployeeProfile: TextView = view.findViewById(R.id.tvEmployeeProfile)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val itemView = LayoutInflater.from(parent.context)
                .inflate(R.layout.row_employee_list, parent, false)

        return MyViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        val education = employeeList!![position]
        holder.tvEmployeeName.text = education.name
        holder.tvEmail.text = education.email
        holder.tvPhone.text = education.mobileNumber
        holder.rbEmployee.rating = education.userRating!!
        holder.tvPerStatusEmployee.text = education.performanceStatus

        holder.tvRecordDetails.setOnClickListener{
            println("name is "+education.name)
            val intent = Intent(mContext,ReviewDetailsActivity::class.java)
            intent.putExtra("isEmployer",true)
            intent.putExtra(mContext.getString(R.string.employer_id),education.id)
            intent.putExtra(mContext.getString(R.string.employee_name),education.name)
            mContext.startActivity(intent)
        }

        holder.tvEmployeeProfile.setOnClickListener{
            val intent = Intent(mContext,EmpProfileActivity::class.java)
            mContext.startActivity(intent)
        }
    }

    override fun getItemCount(): Int {
        return employeeList!!.size
    }

}