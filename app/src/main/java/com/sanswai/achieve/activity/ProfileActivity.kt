package com.sanswai.achieve.activity

import android.content.Intent
import android.os.Bundle
import android.view.MenuItem
import android.view.View
import com.sanswai.achieve.R
import com.sanswai.achieve.global.BaseActivity
import kotlinx.android.synthetic.main.activity_profile.*

class ProfileActivity : BaseActivity(), View.OnClickListener {


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_profile)

        setupActionBar()

        tvSkills.setOnClickListener(this)
        tvEmployment.setOnClickListener(this)
        tvEducation.setOnClickListener(this)
        tvCareer.setOnClickListener(this)
        tvPersonalDetails.setOnClickListener(this)
    }

    private fun setupActionBar() {
        //set title to the activity
        supportActionBar!!.title = "Employee Profile"

        //make home back button enable
        supportActionBar!!.setDisplayHomeAsUpEnabled(true)
    }

    override fun onOptionsItemSelected(item: MenuItem?): Boolean {
        //action to only action menu button
        onBackPressed()
        return super.onOptionsItemSelected(item)
    }

    override fun onClick(p0: View?) {
        val intent = Intent(this@ProfileActivity, DetailsActivity::class.java)
        startActivity(intent)
    }
}
