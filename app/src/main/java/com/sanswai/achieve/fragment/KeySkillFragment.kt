package com.sanswai.achieve.fragment

import android.annotation.SuppressLint
import android.app.Dialog
import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.widget.DefaultItemAnimator
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.android.volley.error.VolleyError
import com.google.gson.Gson
import com.sanswai.achieve.R
import com.sanswai.achieve.activity.EmpProfileActivity
import com.sanswai.achieve.adapter.CheckboxAdapter
import com.sanswai.achieve.adapter.SkillsAdapter
import com.sanswai.achieve.global.BaseActivity
import com.sanswai.achieve.global.Preferences
import com.sanswai.achieve.network.VolleyService
import com.sanswai.achieve.response.employeedetails.Datum____
import com.sanswai.achieve.response.employeedetails.Datum_____
import com.sanswai.achieve.response.employeedetails.EmployeeDetails
import kotlinx.android.synthetic.main.activity_emp_profile.*
import kotlinx.android.synthetic.main.dialog_editbox.*
import kotlinx.android.synthetic.main.fragment_key_kills.*
import kotlinx.android.synthetic.main.fragment_personal_details.*
import org.json.JSONArray
import org.json.JSONObject


class KeySkillFragment : Fragment(), VolleyService.SetResponse {

    private var keySkills: ArrayList<Datum____>? = null
    private var userSkills: ArrayList<Datum_____>? = null
    var adapter: SkillsAdapter? = null
    var services: VolleyService? = null
    var preferences: Preferences? = null
    var responseObject = EmployeeDetails()
    lateinit var  dialog : Dialog
    private val currentSelectedItems = ArrayList<Datum____>()

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(com.sanswai.achieve.R.layout.fragment_key_kills, container, false)
    }

    @SuppressLint("RestrictedApi")
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        keySkills = ArrayList()
        userSkills = ArrayList()
        services = VolleyService(activity!!)
        preferences = Preferences.getInstance(activity!!)

        if (preferences?.getPreferencesString(getString(R.string.user_type)) == "employee") {
            fabKeySkill.visibility = View.VISIBLE
        } else {
            fabKeySkill.visibility = View.GONE
        }

        prepareProjectList()
    }

    override fun onResume() {
        super.onResume()
        //((activity as EmpProfileActivity).getEmployeeDetails())
    }

    @SuppressLint("RestrictedApi")
    private fun prepareProjectList() {
        val jsonResponse = preferences?.getPreferencesString(getString(com.sanswai.achieve.R.string.pref_employee_details))
        if (jsonResponse != null) {
            responseObject = Gson().fromJson(jsonResponse, EmployeeDetails::class.java)
            keySkills = responseObject?.mstSkill?.data as ArrayList<Datum____>?
            userSkills = responseObject?.userSkill?.data as ArrayList<Datum_____>?
            println("userskills is " + userSkills)
            if (userSkills != null) {
                adapter = SkillsAdapter(activity!!, userSkills!!)
                val mLayoutManager = LinearLayoutManager(activity, LinearLayoutManager.VERTICAL, false)
                rvUSerSkills.layoutManager = mLayoutManager
                rvUSerSkills.itemAnimator = DefaultItemAnimator() as RecyclerView.ItemAnimator?
                rvUSerSkills.adapter = adapter
            }
            if (userSkills == null || userSkills?.isEmpty()!!) {
                fabKeySkill.setImageResource(com.sanswai.achieve.R.drawable.ic_plus_black_symbol)
            } else {
                fabKeySkill.setImageResource(com.sanswai.achieve.R.drawable.ic_pencil_edit_button)
            }

            fabKeySkill.setOnClickListener {
                println("on click key skill is " + (activity as EmpProfileActivity).viewPager.currentItem)
                if ((activity as EmpProfileActivity).viewPager.currentItem == 1) {
                    addorEditSkill()
                }
            }
        }
    }

    fun addorEditSkill() {
        dialog = Dialog(activity!!)
        dialog.setContentView(com.sanswai.achieve.R.layout.dialog_editbox)
        val adapter1 = CheckboxAdapter(activity!!, (responseObject.mstSkill!!.data as ArrayList<Datum____>?)!!, object : CheckboxAdapter.OnItemCheckListener {
            override fun onItemCheck(item: Datum____) {
                currentSelectedItems.add(item)
            }

            override fun onItemUncheck(item: Datum____) {
                currentSelectedItems.remove(item)
            }
        })
        val mLayoutManager = LinearLayoutManager(activity, LinearLayoutManager.VERTICAL, false)
        dialog.rvSkills.layoutManager = mLayoutManager
        dialog.rvSkills.itemAnimator = DefaultItemAnimator()
        dialog.rvSkills.adapter = adapter1

        dialog.show()
        dialog.tvSubmitSkill.setOnClickListener {
            if (currentSelectedItems.isEmpty()) {
                ((activity as BaseActivity).showToast("Please Select Skill"))
            } else {
                val jsonArray = JSONArray()

                for (i in 0 until currentSelectedItems.size) {
                    val jsonObject = JSONObject()
                    jsonObject.put("skill_id", currentSelectedItems[i].id)
                    jsonObject.put("user_id", preferences!!.getPreferencesInt(getString(R.string.user_id),0).toString())
                    jsonArray.put(jsonObject)
                }
                val jsonObject1 = JSONObject()
                jsonObject1.put("arr", jsonArray)
                services!!.callJsonObjectRequest(getString(R.string.api_key_skill) + preferences!!.getPreferencesInt(getString(R.string.user_id),0), jsonObject1)
                services!!.mResponseInterface = this
            }
        }
    }

    override fun onSuccess(methodName: String, response: Any) {
        println("resposne is " + response)
        if (methodName.contains(getString(R.string.api_key_skill))){
            if (dialog.isShowing){
                dialog.dismiss()
                ((activity as EmpProfileActivity).getEmployeeDetails())
            }
        }
    }

    override fun onFailure(methodName: String, volleyError: VolleyError) {
        println("resposne is " + volleyError)
    }

}
