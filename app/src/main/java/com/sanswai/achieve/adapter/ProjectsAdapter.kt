package com.sanswai.achieve.adapter

import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import com.sanswai.achieve.R
import com.sanswai.achieve.fragment.Projects


class ProjectsAdapter(private val projectsList: List<Projects>) : RecyclerView.Adapter<ProjectsAdapter.MyViewHolder>() {

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
        holder.title.text = projects.prjTitle
        holder.description.text = projects.prjDescription
    }

    override fun getItemCount(): Int {
        return projectsList.size
    }
}