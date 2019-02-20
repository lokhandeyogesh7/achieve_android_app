package com.example.anshtest.activity

import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.view.MenuItem
import android.view.View
import com.example.anshtest.R
import kotlinx.android.synthetic.main.activity_profile.*

class ProfileActivity : AppCompatActivity(), View.OnClickListener {


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
