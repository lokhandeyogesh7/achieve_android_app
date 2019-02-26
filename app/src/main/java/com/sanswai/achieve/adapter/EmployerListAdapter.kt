package com.sanswai.achieve.adapter

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.TextView
import android.support.v7.widget.RecyclerView
import android.view.View
import android.widget.RatingBar
import com.sanswai.achieve.R
import com.sanswai.achieve.model.Result


class EmployerListAdapter(private val fountainList: List<Result>,private val listener: (Result) -> Unit) : RecyclerView.Adapter<EmployerListAdapter.MyViewHolder>() {

    inner class MyViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val tvEmplName = view.findViewById<TextView>(R.id.tvPerformanceStatus)!!
        val tvDate = view.findViewById<TextView>(R.id.tvDate)!!
        val rbRating = view.findViewById<RatingBar>(R.id.rbEmployer)!!
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val itemView = LayoutInflater.from(parent.context)
                .inflate(R.layout.row_employer, parent, false)

        return MyViewHolder(itemView)
    }

    @SuppressLint("SetTextI18n")
    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        val fountain = fountainList[position]
        println("fountain item is ${fountain.name}")
        holder.tvEmplName.text = "${fountain.name}"
        holder.tvDate.text = "From Date: ${fountain.operator}  To Date: ${fountain.uid}"
        holder.rbRating.rating = fountain.degreeOfLatitude?.toFloat()!!

        holder.itemView.setOnClickListener {
            listener(fountain)
        }
    }
    override fun getItemCount(): Int {
        return fountainList.size
    }
}