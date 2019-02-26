package com.sanswai.achieve.fragment


import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.widget.DefaultItemAnimator
import android.support.v7.widget.LinearLayoutManager
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.sanswai.achieve.R
import com.sanswai.achieve.adapter.EmpReviewListAdapter
import com.sanswai.achieve.model.EmployeeRevList
import kotlinx.android.synthetic.main.fragment_employer_review_list.*


/**
 * A simple [Fragment] subclass.
 *
 */
class EmployerReviewListFragment : Fragment(), View.OnClickListener {

    private var employeeRevList: ArrayList<EmployeeRevList>? = null
    var adapter: EmpReviewListAdapter? = null

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_employer_review_list, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        employeeRevList = ArrayList()
        adapter = EmpReviewListAdapter(activity!!, employeeRevList!!)

        val mLayoutManager = LinearLayoutManager(activity, LinearLayoutManager.VERTICAL, false)
        rvEmployerRevList.layoutManager = mLayoutManager
        rvEmployerRevList.itemAnimator = DefaultItemAnimator()
        rvEmployerRevList.adapter = adapter

        btnAddReview.setOnClickListener(this@EmployerReviewListFragment)

        prepareProjectList()
    }

    private fun prepareProjectList() {
        for (i in 0 until 5) {
            val projects= EmployeeRevList("Start Date 15/02/2017  End Date 25/2/2019","Good in communication","Excellent")
            employeeRevList!!.add(projects)
        }
        adapter!!.notifyDataSetChanged()
    }


    override fun onClick(v: View) {
        when(v.id){
            R.id.btnAddReview->{

            }
        }
    }
}
