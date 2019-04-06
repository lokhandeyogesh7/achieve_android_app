package com.sanswai.achieve.fragment

import android.annotation.SuppressLint
import android.app.Dialog
import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.widget.DefaultItemAnimator
import android.support.v7.widget.LinearLayoutManager
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.android.volley.VolleyError
import com.google.gson.Gson
import com.sanswai.achieve.R
import com.sanswai.achieve.activity.EmpProfileActivity
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
import org.json.JSONObject

class KeySkillFragment : Fragment(), VolleyService.SetResponse {

    private var keySkills: ArrayList<Datum____>? = null
    private var userSkills: ArrayList<Datum_____>? = null
    var adapter: SkillsAdapter? = null
    var services: VolleyService? = null
    var preferences: Preferences? = null
    var responseObject = EmployeeDetails()

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_key_kills, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        keySkills = ArrayList()
        userSkills = ArrayList()
        services = VolleyService(activity!!)
        preferences = Preferences.getInstance(activity!!)

        prepareProjectList()
    }

    @SuppressLint("RestrictedApi")
    private fun prepareProjectList() {
        val jsonResponse = preferences?.getPreferencesString(getString(R.string.pref_employee_details))
        if (jsonResponse != null) {
            responseObject = Gson().fromJson(jsonResponse, EmployeeDetails::class.java)
            keySkills = responseObject?.mstSkill?.data as ArrayList<Datum____>?
            userSkills = responseObject?.userSkill?.data as ArrayList<Datum_____>?
            if (keySkills != null) {
                if (userSkills == null) {
                    userSkills = ArrayList()
                }
                adapter = SkillsAdapter(activity!!, "key", keySkills, userSkills!!)
                val mLayoutManager = LinearLayoutManager(activity, LinearLayoutManager.VERTICAL, false)
                rvKeySkills.layoutManager = mLayoutManager
                rvKeySkills.itemAnimator = DefaultItemAnimator()
                rvKeySkills.adapter = adapter
            }
            if (userSkills != null) {
                if (keySkills == null) {
                    keySkills = ArrayList()
                }
                adapter = SkillsAdapter(activity!!, "user", keySkills, userSkills!!)
                val mLayoutManager = LinearLayoutManager(activity, LinearLayoutManager.VERTICAL, false)
                rvUSerSkills.layoutManager = mLayoutManager
                rvUSerSkills.itemAnimator = DefaultItemAnimator()
                rvUSerSkills.adapter = adapter
            }
            if (userSkills == null || userSkills?.isEmpty()!!) {
                (activity as EmpProfileActivity).fabPersonalDetails.setImageResource(R.drawable.ic_plus_black_symbol)
            } else {
                (activity as EmpProfileActivity).fabPersonalDetails.visibility = View.GONE
            }

            (activity as EmpProfileActivity).fabPersonalDetails.setOnClickListener {
                println("on click key skill is " + (activity as EmpProfileActivity).viewPager.currentItem)
                if ((activity as EmpProfileActivity).viewPager.currentItem == 1) {
                    val datum = Datum_____()
                    addorEditSkill(datum)
                }
            }
        }
    }

    fun addorEditSkill(education: Datum_____) {
        val dialog = Dialog(activity!!)
        dialog.setContentView(R.layout.dialog_editbox)
        var skillId = ""
        if (education != null) {
            skillId = education.skillId!!
        }
        dialog.show()
        dialog.tvSubmitSkill.setOnClickListener {
            val strSkill = dialog.etSkillName.text.toString()
            if (strSkill.isEmpty()) {
                ((activity as BaseActivity).showToast("Please Enter Skill"))
            } else {
                val jsonObject = JSONObject()
                jsonObject.put("skill_id",skillId )
                jsonObject.put("user_id",responseObject.users!!.data!!.id)
                services!!.callJsonObjectRequest("key-skills/", jsonObject)
                services!!.mResponseInterface = this
            }
        }
    }

    override fun onSuccess(methodName: String, response: Any) {
        println("resposne is "+response)
    }

    override fun onFailure(methodName: String, volleyError: VolleyError) {
        println("resposne is "+volleyError)
    }

    fun addorEditKeySkill(education: Datum____) {
        val dialog = Dialog(activity!!)
        dialog.setContentView(R.layout.dialog_editbox)
        var skillId = ""
        if (education != null) {
            skillId = education.id.toString()!!
        }
        dialog.show()
        dialog.tvSubmitSkill.setOnClickListener {
            val strSkill = dialog.etSkillName.text.toString()
            if (strSkill.isEmpty()) {
                ((activity as BaseActivity).showToast("Please Enter Skill"))
            } else {
                val jsonObject = JSONObject()
                jsonObject.put("skill_id",skillId )
                jsonObject.put("user_id",responseObject.users!!.data!!.id)
                services!!.callJsonObjectRequest("key-skills/", jsonObject)
                services!!.mResponseInterface = this
            }
        }
    }
}
