package com.sanswai.achieve.activity

import android.os.Bundle
import android.view.MenuItem
import com.sanswai.achieve.R
import com.sanswai.achieve.global.BaseActivity

class DetailsActivity : BaseActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_details)

        setupActionBar()
    }

    private fun setupActionBar() {
        //set title to the activity
        supportActionBar!!.title = "Dummy Details Screen"

        //make home back button enable
        supportActionBar!!.setDisplayHomeAsUpEnabled(true)
    }

    override fun onOptionsItemSelected(item: MenuItem?): Boolean {
        //action to only action menu button
        onBackPressed()
        return super.onOptionsItemSelected(item)
    }
}
