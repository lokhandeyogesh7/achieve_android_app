package com.sanswai.achieve.adapter

import android.content.Context
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.CheckBox
import com.google.gson.Gson
import com.sanswai.achieve.global.Preferences
import com.sanswai.achieve.response.employeedetails.Datum____
import com.sanswai.achieve.response.employeedetails.Datum_____
import com.sanswai.achieve.response.employeedetails.EmployeeDetails


class CheckboxAdapter(val mContext: Context, private val educationList: ArrayList<Datum____>?, private val onItemCheckListener: OnItemCheckListener) : RecyclerView.Adapter<CheckboxAdapter.MyViewHolder>() {

    interface OnItemCheckListener {
        fun previousData(item: Datum____)
        fun onItemCheck(item: Datum____)
        fun onItemUncheck(item: Datum____)
    }

    inner class MyViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        var tvCheck: CheckBox = view.findViewById(com.sanswai.achieve.R.id.tvCheck)
        fun setOnClickListener(onClickListener: View.OnClickListener) {
            itemView.setOnClickListener(onClickListener)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val itemView = LayoutInflater.from(parent.context)
                .inflate(com.sanswai.achieve.R.layout.row_checkbox, parent, false)

        return MyViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        val education = educationList!![position]
        holder.tvCheck.text = education.skillName
       val preferences = Preferences.getInstance(mContext!!)
        val jsonResponse = preferences?.getPreferencesString(mContext.getString(com.sanswai.achieve.R.string.pref_employee_details))
        if (jsonResponse != null) {
           val responseObject = Gson().fromJson(jsonResponse, EmployeeDetails::class.java)
            val userSkills = responseObject?.userSkill?.data as ArrayList<Datum_____>?
            if (userSkills!=null && userSkills!!.isNotEmpty()) {
                for (i in 0 until userSkills?.size!!){
                    if (education.id==userSkills[i].skillId!!){
                        holder.tvCheck.isChecked = true
                        onItemCheckListener.onItemCheck(education)
                        onItemCheckListener.previousData(education)
                    }
                }
            }
        }
        holder.tvCheck.setOnCheckedChangeListener { buttonView, isChecked ->
            if (isChecked) {
                onItemCheckListener.onItemCheck(education)
            } else {
                onItemCheckListener.onItemUncheck(education)
            }
        }
    }

    override fun getItemCount(): Int {
        return educationList!!.size
    }

}