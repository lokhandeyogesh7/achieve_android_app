package com.sanswai.achieve.fragment


import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.widget.DefaultItemAnimator
import android.support.v7.widget.LinearLayoutManager
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.sanswai.achieve.R
import kotlinx.android.synthetic.main.fragment_employment.*


// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 *
 */
class EmploymentFragment : Fragment() {

    var projectsList: ArrayList<Projects>? = null
    var adapter: ProjectsAdapter? = null

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_employment, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        projectsList = ArrayList()
        adapter = ProjectsAdapter(activity!!, projectsList!!)

        val mLayoutManager = LinearLayoutManager(activity, LinearLayoutManager.VERTICAL, false)
        rvEmployments.layoutManager = mLayoutManager
        rvEmployments.itemAnimator = DefaultItemAnimator()
        rvEmployments.adapter = adapter

        prepareProjectList()
    }

    private fun prepareProjectList() {

        for (i in 0 until 5) {
            var projects= Projects("Company name ${i + 1}","Company Description ${i + 1} lasdkl;askdl;askd;laskd;laskd;laskkkkkkkkkkkkkvhhhhhhhhhhhhhhkb jskgdfyuukgiuy ckagdbcy efgkuya gwevdcvswjeyvgfjcwsva cfd",i.toString())
            projectsList!!.add(projects)
        }

        adapter!!.notifyDataSetChanged()

    }

}
