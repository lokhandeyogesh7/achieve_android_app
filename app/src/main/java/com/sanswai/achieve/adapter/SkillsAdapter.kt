package com.sanswai.achieve.adapter

import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import com.sanswai.achieve.R
import com.sanswai.achieve.response.employeedetails.Datum____
import com.sanswai.achieve.response.employeedetails.Datum_____

class SkillsAdapter (private val skill: String, private val educationList: ArrayList<Datum____>?,private val userSkills: ArrayList<Datum_____>) : RecyclerView.Adapter<SkillsAdapter.MyViewHolder>() {

    inner class MyViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        var tvSkillName: TextView = view.findViewById(R.id.tvSkillName)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val itemView = LayoutInflater.from(parent.context)
                .inflate(R.layout.row_skills, parent, false)

        return MyViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        if (skill=="key") {
            val education = educationList!![position]
            holder.tvSkillName.text = education.skillName
        }else{
            val education = userSkills!![position]
            holder.tvSkillName.text = education.skillName
        }
    }

    override fun getItemCount(): Int {
        if (skill=="key") {
            return educationList!!.size
        }else{
            return userSkills!!.size
        }
    }
}