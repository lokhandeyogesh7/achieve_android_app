package com.sanswai.achieve.fragment


import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.widget.*
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.sanswai.achieve.R
import com.sanswai.achieve.adapter.ProjectsAdapter
import kotlinx.android.synthetic.main.fragment_projects.*

class ProjectsFragment : Fragment() {
    var projectsList: ArrayList<Projects>? = null
    var adapter: ProjectsAdapter? = null

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_projects, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        projectsList = ArrayList()
        adapter = ProjectsAdapter(projectsList!!)

        val mLayoutManager = LinearLayoutManager(activity, LinearLayoutManager.VERTICAL, false)
        rvProjects.layoutManager = mLayoutManager
        rvProjects.itemAnimator = DefaultItemAnimator()
        rvProjects.adapter = adapter

        prepareProjectList()
    }

    private fun prepareProjectList() {
        for (i in 0 until 5) {
            val projects= Projects("Project Title ${i + 1}","Project Description ${i + 1} lasdkl;askdl;askd;laskd;laskd;laskkkkkkkkkkkkkvhhhhhhhhhhhhhhkb jskgdfyuukgiuy ckagdbcy efgkuya gwevdcvswjeyvgfjcwsva cfd",i.toString())
            projectsList!!.add(projects)
        }
        adapter!!.notifyDataSetChanged()
    }
}