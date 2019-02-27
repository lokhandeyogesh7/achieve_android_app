package com.sanswai.achieve.adapter

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.TextView
import android.support.v7.widget.RecyclerView
import android.view.View
import android.widget.RatingBar
import com.sanswai.achieve.R
import com.sanswai.achieve.response.employeelogin.Datum


class EmployerListAdapter(private val fountainList: List<Datum>?, private val listener: (Datum) -> Unit) : RecyclerView.Adapter<EmployerListAdapter.MyViewHolder>() {

    inner class MyViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val tvEmplName = view.findViewById<TextView>(R.id.tvMainEmpName)!!
        val tvDate = view.findViewById<TextView>(R.id.tvDate)!!
        val rbRating = view.findViewById<RatingBar>(R.id.rbMainEmp)!!
        val tvPerformance = view.findViewById<TextView>(R.id.tvPerformance)!!
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val itemView = LayoutInflater.from(parent.context)
                .inflate(R.layout.row_employer, parent, false)

        return MyViewHolder(itemView)
    }

    @SuppressLint("SetTextI18n")
    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        val employer = fountainList!![position]
        holder.tvEmplName.text = "${employer.feedbackDetails}"
        holder.tvDate.text = "From Date: ${employer.startDate}  To Date: ${employer.endDate}"
        holder.rbRating.rating = employer.avgRating?.toFloat()!!
        holder.tvPerformance.text = employer.performanceStatus!!

        holder.itemView.setOnClickListener {
            listener(employer)
        }
    }
    override fun getItemCount(): Int {
        return fountainList!!.size
    }
}