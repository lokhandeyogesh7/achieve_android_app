package com.sanswai.achieve.adapter

import android.annotation.SuppressLint
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.RadioGroup
import android.widget.TextView
import com.sanswai.achieve.R
import com.sanswai.achieve.response.reviewdetails.UserDatum

class ReviewQuestionAdapter(private val revQuestionList: ArrayList<UserDatum>?) : RecyclerView.Adapter<ReviewQuestionAdapter.MyViewHolder>() {

    inner class MyViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        var tvQuestion: TextView = view.findViewById(R.id.tvQuestion)!!
        var tvPerStatus: TextView = view.findViewById(R.id.tvPerStatus)!!
        var rbQuestion: TextView = view.findViewById(R.id.rbQuestion)!!
        var radioRating: RadioGroup = view.findViewById(R.id.radioRating)!!
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val itemView = LayoutInflater.from(parent.context)
                .inflate(R.layout.row_question_review, parent, false)

        return MyViewHolder(itemView)
    }

    @SuppressLint("SetTextI18n")
    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        val education = revQuestionList!![position]
        holder.rbQuestion.visibility = View.VISIBLE
        holder.tvQuestion.text = education.question
        holder.tvPerStatus.text = education.ratingReview
        holder.radioRating.visibility = View.GONE
        println("rating bar is "+education.ratingPoint!!)
        holder.rbQuestion.text = "Rating: " + Math.abs(education.ratingPoint!!.toDouble())
    }

    override fun getItemCount(): Int {
        return revQuestionList!!.size
    }
}