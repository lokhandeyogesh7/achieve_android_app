package com.sanswai.achieve.adapter

import android.content.Context
import android.media.Image
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import com.sanswai.achieve.R
import com.sanswai.achieve.fragment.KeySkillFragment
import com.sanswai.achieve.response.employeedetails.Datum____
import com.sanswai.achieve.response.employeedetails.Datum_____

class SkillsAdapter(private val mContext: Context, private val userSkills: ArrayList<Datum_____>) : RecyclerView.Adapter<SkillsAdapter.MyViewHolder>() {

    inner class MyViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        var tvSkillName: TextView = view.findViewById(R.id.tvSkillName)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val itemView = LayoutInflater.from(parent.context)
                .inflate(R.layout.row_skills, parent, false)

        return MyViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        val education = userSkills[position]
        holder.tvSkillName.text = education.skillName
    }

    override fun getItemCount(): Int {
        return userSkills.size
    }
}