package com.sanswai.achieve.adapter

import android.content.Context
import android.support.v7.widget.RecyclerView
import android.view.Gravity
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.RadioGroup
import android.widget.RatingBar
import android.widget.TextView
import com.sanswai.achieve.R
import com.sanswai.achieve.response.performancequestions.QuestionsDatum
import com.sanswai.achieve.response.performancequestions.RatingsDatum

class ReviewQuestionEmpAdapter(private val mContext: Context, private val revQuestionList: List<QuestionsDatum>?, private val ratingsData: List<RatingsDatum>?) : RecyclerView.Adapter<ReviewQuestionEmpAdapter.MyViewHolder>() {

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

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        val education = revQuestionList!![position]
        holder.tvQuestion.text = education.question
        holder.tvPerStatus.visibility = View.GONE
        holder.rbQuestion.visibility = View.VISIBLE
        holder.rbQuestion.gravity = Gravity.CENTER

        holder.radioRating.setOnCheckedChangeListener { group, checkedId ->
            println("checked id is ${checkedId.toInt()}")
            when(checkedId){
                R.id.radioOne->{
                    holder.rbQuestion.text = ratingsData!![0].name
                    education.rating = 1f
                }
                R.id.radioTwo->{
                    holder.rbQuestion.text = ratingsData!![1].name
                    education.rating = 2f
                }
                R.id.radioThree->{
                    holder.rbQuestion.text = ratingsData!![2].name
                    education.rating = 3f
                }
                R.id.radioFour->{
                    holder.rbQuestion.text = ratingsData!![3].name
                    education.rating = 4f
                }
                R.id.radioFive->{
                    holder.rbQuestion.text = ratingsData!![4].name
                    education.rating = 5f
                }
                else->{
                    holder.rbQuestion.text = ""
                    education.rating = 0f
                }
            }
        }
    }

    override fun getItemCount(): Int {
        return revQuestionList!!.size
    }
}