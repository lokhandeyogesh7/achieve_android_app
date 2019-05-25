package com.sanswai.achieve.adapter

import android.content.Context
import android.content.Intent
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import com.sanswai.achieve.R
import com.sanswai.achieve.activity.EditResumeHeadlineActivity
import com.sanswai.achieve.global.Preferences
import com.sanswai.achieve.response.employeedetails.Datum__


class ProjectsAdapter(private val mContext: Context, private val projectsList: List<Datum__>) : RecyclerView.Adapter<ProjectsAdapter.MyViewHolder>() {

    inner class MyViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        var title: TextView = view.findViewById(R.id.tvProjectTitle)
        var description: TextView = view.findViewById(R.id.tvProjectDescription)
        var ivEditProject: ImageView = view.findViewById(R.id.ivEditProject)

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
        val preferences = Preferences.getInstance(mContext)
        if (preferences!!.getPreferencesString(mContext.getString(R.string.user_type)) != "employee") {
            holder.ivEditProject.visibility = View.GONE
        }

        holder.ivEditProject.setOnClickListener {
            println("adapter podsition si " + projects.id)
            val intent = Intent(mContext, EditResumeHeadlineActivity::class.java)
            intent.putExtra(mContext.getString(R.string.project_id), projects.id.toString())
            intent.putExtra(mContext.getString(R.string.fromProjects), true)
            mContext.startActivity(intent)
         /*   mContext.startActivity(Intent(mContext, EditResumeHeadlineActivity::class.java).putExtra(mContext.getString(R.string.fromProjects), true).putExtra(mContext
                    .getString(R.string.project_id), projects.id))*/
        }
    }

    override fun getItemCount(): Int {
        println("getItemCount " + projectsList.size)
        return projectsList.size
    }
}