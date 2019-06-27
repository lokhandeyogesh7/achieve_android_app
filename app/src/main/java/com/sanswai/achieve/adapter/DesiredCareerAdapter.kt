package com.sanswai.achieve.adapter

import android.content.Context
import android.support.v7.widget.RecyclerView
import android.text.Html
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import com.sanswai.achieve.R
import com.sanswai.achieve.response.employeedetails.Datum___

class DesiredCareerAdapter (val mContext: Context, private val educationList: ArrayList<Datum___>?) : RecyclerView.Adapter<DesiredCareerAdapter.MyViewHolder>() {

    inner class MyViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        var tvFunctionAreaName: TextView = view.findViewById(R.id.tvFunctionAreaName)
        var tvIndustryName: TextView = view.findViewById(R.id.tvIndustryName)
        var tvCareerRole: TextView = view.findViewById(R.id.tvCareerRole)
        var tvJobType: TextView = view.findViewById(R.id.tvJobType)
        var tvEmpType: TextView = view.findViewById(R.id.tvEmpType)
        var tvExpSalary: TextView = view.findViewById(R.id.tvExpSalary)
        var tvDesShift: TextView = view.findViewById(R.id.tvDesShift)
        var tvExpLocation: TextView = view.findViewById(R.id.tvExpLocation)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val itemView = LayoutInflater.from(parent.context)
                .inflate(R.layout.row_desired_career, parent, false)

        return MyViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        val education = educationList!![position]
        val funArea = "<b>Functional Area: </b>"+education.functionalAreaName
        val indName = "<b>Industry: </b>"+education.industryName
        val role = "<b>Role: </b>"+education.careerRoleName
        val jobtype = "<b>Job Type: </b>"+education.jobType
        val empType = "<b>Employment Type: </b>"+education.employmentType
        val salary = "<b>Expected Salary: </b>"+education.expectedSalaryAmount+"  "+education.expectedSalaryType
        val shift = "<b>Desired Shift: </b>"+education.desiredShift
        val locattion = "<b>Desired Location: </b>"+education.locationName


        holder.tvFunctionAreaName.text = Html.fromHtml(funArea)
        holder.tvIndustryName.text =Html.fromHtml(indName)
        holder.tvCareerRole.text = Html.fromHtml(role)
        holder.tvJobType.text = Html.fromHtml(jobtype)
        holder.tvEmpType.text = Html.fromHtml(empType)
        holder.tvExpSalary.text =Html.fromHtml(salary)
        holder.tvDesShift.text = Html.fromHtml(shift)
        holder.tvExpLocation.text = Html.fromHtml(locattion)
    }

    override fun getItemCount(): Int {
        return educationList!!.size
    }

}