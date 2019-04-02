package com.sanswai.achieve.adapter

import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import com.sanswai.achieve.R
import com.sanswai.achieve.response.employeedetails.Datum__


class ProjectsAdapter(private val projectsList: List<Datum__>) : RecyclerView.Adapter<ProjectsAdapter.MyViewHolder>() {

    inner class MyViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        var title: TextView = view.findViewById(R.id.tvProjectTitle)
        var description: TextView = view.findViewById(R.id.tvProjectDescription)

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val itemView = LayoutInflater.from(parent.context)
                .inflate(R.layout.row_projects, parent, false)

        return MyViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        val projects = projectsList[position]
        holder.title.text = projects.projectName
        holder.description.text = projects.projectDescription
    }

    override fun getItemCount(): Int {
        println("getItemCount "+projectsList.size)
        return projectsList.size
    }
}