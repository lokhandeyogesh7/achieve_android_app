package com.sanswai.achieve.adapter

import android.content.Context
import android.content.Intent
import android.support.v4.content.ContextCompat.startActivity
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import com.sanswai.achieve.R
import com.sanswai.achieve.activity.AddEditEmploymentActivity
import com.sanswai.achieve.global.Preferences
import com.sanswai.achieve.response.employeedetails.Datum_
import kotlinx.android.synthetic.main.fragment_employment.*

class EmploymentAdapter(private val mContext: Context, private val projectsList: List<Datum_>) : RecyclerView.Adapter<EmploymentAdapter.MyViewHolder>() {

    inner class MyViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        var title: TextView = view.findViewById(R.id.tvCompanyName)
        var tvDesignation: TextView = view.findViewById(R.id.tvDesignation)
        var tvStartEndDate: TextView = view.findViewById(R.id.tvStartEndDate)
        var tvCurrentEmployment: TextView = view.findViewById(R.id.tvCurrentEmployment)
        var tvJobDescription: TextView = view.findViewById(R.id.tvJobDescription)
        var ivEditEmployment: ImageView = view.findViewById(R.id.ivEditEmployment)
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
        holder.tvStartEndDate.text = "Start Date: " + projects.workingStartDate + "  \nTo Date: " + projects.workedTillDate
        val preferences = Preferences.getInstance(mContext)
        if (preferences!!.getPreferencesString(mContext.getString(R.string.user_type)) != "employee") {
            holder.ivEditEmployment.visibility = View.GONE
        }
        holder.ivEditEmployment.setOnClickListener {
                mContext.startActivity(Intent(mContext, AddEditEmploymentActivity::class.java).putExtra("employment_id",projects.id))
        }
    }

    override fun getItemCount(): Int {
        return projectsList.size
    }
}