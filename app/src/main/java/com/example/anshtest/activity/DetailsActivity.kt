package com.example.anshtest.activity

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.view.MenuItem
import com.example.anshtest.R

class DetailsActivity : AppCompatActivity() {

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
