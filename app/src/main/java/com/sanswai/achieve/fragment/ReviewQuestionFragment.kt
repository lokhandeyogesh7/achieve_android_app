package com.sanswai.achieve.fragment


import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.widget.DefaultItemAnimator
import android.support.v7.widget.LinearLayoutManager
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup

import com.sanswai.achieve.R
import com.sanswai.achieve.adapter.ReviewQuestionAdapter
import com.sanswai.achieve.model.ReviewQuestion
import kotlinx.android.synthetic.main.fragment_review_question.*

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 *
 */
class ReviewQuestionFragment : Fragment() {

    var revQuestionList: ArrayList<ReviewQuestion>? = null
    var adapter: ReviewQuestionAdapter? = null

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_review_question, container, false)
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        revQuestionList = ArrayList()
        adapter = ReviewQuestionAdapter(activity!!, revQuestionList)

        val mLayoutManager = LinearLayoutManager(activity, LinearLayoutManager.VERTICAL, false)
        rvRevQuestions.layoutManager = mLayoutManager
        rvRevQuestions.itemAnimator = DefaultItemAnimator()
        rvRevQuestions.adapter = adapter

        prepareProjectList()
    }

    private fun prepareProjectList() {

        for (i in 0 until 3) {
            var projects= ReviewQuestion("Who Are you ${i+1}","Performance Status : Good")
            revQuestionList!!.add(projects)
        }

        adapter!!.notifyDataSetChanged()

    }

}
