package com.sanswai.achieve.fragment


import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.widget.DefaultItemAnimator
import android.support.v7.widget.LinearLayoutManager
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.sanswai.achieve.R
import com.sanswai.achieve.adapter.EducationAdapter
import kotlinx.android.synthetic.main.fragment_education.*


// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 *
 */
class EducationFragment : Fragment() {

    var educationList: ArrayList<Education>? = null
    var adapter: EducationAdapter? = null

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_education, container, false)
    }



    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        educationList = ArrayList()
        adapter = EducationAdapter(activity!!, educationList)

        val mLayoutManager = LinearLayoutManager(activity, LinearLayoutManager.VERTICAL, false)
        rvEducation.layoutManager = mLayoutManager
        rvEducation.itemAnimator = DefaultItemAnimator()
        rvEducation.adapter = adapter

        prepareProjectList()
    }

    private fun prepareProjectList() {

        for (i in 0 until 1) {
            var projects= Education("SSC","(57%)","SAB highschool","2014","SSC","-")
            educationList!!.add(projects)
        }

        adapter!!.notifyDataSetChanged()

    }

}
