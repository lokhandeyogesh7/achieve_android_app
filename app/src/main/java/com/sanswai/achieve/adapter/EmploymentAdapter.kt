package com.sanswai.achieve.adapter

import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import com.sanswai.achieve.R
import com.sanswai.achieve.response.employeedetails.Datum_

class EmploymentAdapter(private val projectsList: List<Datum_>) : RecyclerView.Adapter<EmploymentAdapter.MyViewHolder>() {

    inner class MyViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        var title: TextView = view.findViewById(R.id.tvCompanyName)
        var tvDesignation: TextView = view.findViewById(R.id.tvDesignation)
        var tvStartEndDate: TextView = view.findViewById(R.id.tvStartEndDate)
        var tvCurrentEmployment: TextView = view.findViewById(R.id.tvCurrentEmployment)
        var tvJobDescription: TextView = view.findViewById(R.id.tvJobDescription)

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val itemView = LayoutInflater.from(parent.context)
                .inflate(R.layout.row_employment, parent, false)

        return MyViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        val projects = projectsList[position]
        holder.title.text = projects.organizationName
        holder.tvDesignation.text = projects.designation
        holder.tvCurrentEmployment.text = projects.isCurrentEmployment
        holder.tvJobDescription.text = projects.jobProfileDescription
        holder.tvStartEndDate.text = "Start Date: "+projects.workingStartDate+"  \nTo Date: "+projects.workedTillDate
    }

    override fun getItemCount(): Int {
        return projectsList.size
    }
}