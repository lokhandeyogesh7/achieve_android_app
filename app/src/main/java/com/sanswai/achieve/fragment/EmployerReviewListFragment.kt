package com.sanswai.achieve.fragment


import android.content.Intent
import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.widget.DefaultItemAnimator
import android.support.v7.widget.LinearLayoutManager
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.sanswai.achieve.R
import com.sanswai.achieve.activity.AddReviewActivity
import com.sanswai.achieve.adapter.EmpReviewListAdapter
import com.sanswai.achieve.global.BaseActivity
import com.sanswai.achieve.response.employeeperformance.Datum
import com.sanswai.achieve.response.employeeperformance.EmployeePerformace
import kotlinx.android.synthetic.main.fragment_employer_review_list.*


/**
 * A simple [Fragment] subclass.
 *
 */
class EmployerReviewListFragment : Fragment(), View.OnClickListener {

    private var employeeRevList: ArrayList<Datum>? = null
    var adapter: EmpReviewListAdapter? = null
    lateinit var employeePerformance: EmployeePerformace

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_employer_review_list, container, false)
    }

    private var userID: String? = null

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val bundle = arguments
        if (null != bundle) {
            employeePerformance = bundle.getSerializable("review_data") as EmployeePerformace
            userID = bundle.getString(getString(R.string.employee_id))

            if (employeePerformance.add_record!!) {
                btnAddReview.visibility = View.VISIBLE
            } else {
                btnAddReview.visibility = View.GONE
            }

            employeeRevList = ArrayList()
            employeeRevList = employeePerformance.data as ArrayList<Datum>?
            adapter = EmpReviewListAdapter(activity!!, employeeRevList, userID!!.toInt())

            val mLayoutManager = LinearLayoutManager(activity, LinearLayoutManager.VERTICAL, false)
            rvEmployerRevList.layoutManager = mLayoutManager
            rvEmployerRevList.itemAnimator = DefaultItemAnimator()
            rvEmployerRevList.adapter = adapter

            btnAddReview.setOnClickListener(this@EmployerReviewListFragment)
        } else {
            (activity as BaseActivity).showToast("Unable to get data")
        }
    }

    override fun onClick(v: View) {
        when (v.id) {
            R.id.btnAddReview -> {
                val intent = Intent(activity, AddReviewActivity::class.java)
                intent.putExtra(getString(R.string.employee_id),userID)
                startActivity(intent)
            }
        }
    }
}
