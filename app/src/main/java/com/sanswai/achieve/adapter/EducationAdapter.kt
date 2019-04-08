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
import com.sanswai.achieve.activity.EditEducationDetailsActivity
import com.sanswai.achieve.global.BaseActivity
import com.sanswai.achieve.global.Preferences
import com.sanswai.achieve.response.employeedetails.Datum
import java.io.Serializable

class EducationAdapter(val mContext: Context, private val educationList: ArrayList<Datum>?) : RecyclerView.Adapter<EducationAdapter.MyViewHolder>() {

    inner class MyViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        var tvCourseName: TextView = view.findViewById(R.id.tvCourseName)
        var tvPercentage: TextView = view.findViewById(R.id.tvPercentage)
        var tvInstitute: TextView = view.findViewById(R.id.tvInstitute)
        var tvYear: TextView = view.findViewById(R.id.tvYear)
        var tvCourse: TextView = view.findViewById(R.id.tvCourse)
        var tvSpecialization: TextView = view.findViewById(R.id.tvSpecialization)
        var ivEditEducation: ImageView = view.findViewById(R.id.ivEditEducation)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val itemView = LayoutInflater.from(parent.context)
                .inflate(R.layout.row_education, parent, false)

        return MyViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        val education = educationList!![position]
        holder.tvCourseName.text = education.courseName
        holder.tvPercentage.text = education.gradeSystem
        holder.tvInstitute.text = education.instituteName
        holder.tvYear.text = education.passingYear
        holder.tvCourse.text = education.educationName
        holder.tvSpecialization.text = education.specName
        val preferences = Preferences.getInstance(mContext)
        if (preferences!!.getPreferencesString(mContext.getString(R.string.user_type)) != "employee") {
            holder.ivEditEducation.visibility = View.GONE
        }
        holder.ivEditEducation.setOnClickListener {
           // mContext.startActivity(Intent(mContext!!, EditEducationDetailsActivity::class.java).putExtra("education_id"),education as Serializable)
        }

    }

    override fun getItemCount(): Int {
        return educationList!!.size
    }
}