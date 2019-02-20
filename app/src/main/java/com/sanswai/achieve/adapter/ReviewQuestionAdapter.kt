package com.sanswai.achieve.adapter

import android.content.Context
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.RatingBar
import android.widget.TextView
import com.sanswai.achieve.R
import com.sanswai.achieve.model.ReviewQuestion

class ReviewQuestionAdapter(val mContext: Context, private val revQuestionList: ArrayList<ReviewQuestion>?) : RecyclerView.Adapter<ReviewQuestionAdapter.MyViewHolder>() {

    inner class MyViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        var tvQuestion: TextView = view.findViewById(R.id.tvQuestion)
        var tvPerStatus: TextView = view.findViewById(R.id.tvPerStatus)
        var rbQuestion: RatingBar = view.findViewById(R.id.rbQuestion)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val itemView = LayoutInflater.from(parent.context)
                .inflate(R.layout.row_question_review, parent, false)

        return MyViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        val education = revQuestionList!![position]
        holder.tvQuestion.text = education.question
        holder.tvPerStatus.text = education.performanceStatus
    }

    override fun getItemCount(): Int {
        return revQuestionList!!.size
    }
}