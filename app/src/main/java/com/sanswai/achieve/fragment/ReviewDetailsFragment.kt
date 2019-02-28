package com.sanswai.achieve.fragment


import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup

import com.sanswai.achieve.R
import com.sanswai.achieve.response.reviewdetails.ReviewDetails

class ReviewDetailsFragment : Fragment() {

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.layout_review_details, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val bundle = getArguments();
        if (null != bundle) {
            val cardResult = bundle.getSerializable("review_data") as ReviewDetails
            println("review details is " + cardResult.reviewFeedback)
        }
    }
}
