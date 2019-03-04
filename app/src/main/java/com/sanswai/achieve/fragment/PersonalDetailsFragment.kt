package com.sanswai.achieve.fragment

import android.os.Bundle
import android.support.v4.app.Fragment
import android.text.Html
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.sanswai.achieve.R
import kotlinx.android.synthetic.main.fragment_personal_details.*

class PersonalDetailsFragment : Fragment() {

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_personal_details, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val htmlString = "Male \n single \n Nashik"
        tvPersonalInfo.text = Html.fromHtml(htmlString)
    }
}
