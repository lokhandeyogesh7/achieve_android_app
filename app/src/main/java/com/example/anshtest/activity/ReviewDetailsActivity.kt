package com.example.anshtest.activity

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.design.widget.TabLayout
import android.support.v4.app.Fragment
import android.support.v4.app.FragmentManager
import android.support.v4.app.FragmentPagerAdapter
import android.view.MenuItem
import android.view.View
import com.example.anshtest.R
import com.example.anshtest.fragment.*
import kotlinx.android.synthetic.main.activity_emp_profile.*
import kotlinx.android.synthetic.main.activity_review_details.*
import java.util.ArrayList

class ReviewDetailsActivity : AppCompatActivity() {

    var tabLayout: TabLayout? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_review_details)
        setSupportActionBar(toolbarReview)
        supportActionBar!!.setDisplayShowHomeEnabled(true)
        supportActionBar!!.setDisplayHomeAsUpEnabled(true)

        supportActionBar!!.title = "Review Details"

        supportActionBar!!.setDisplayHomeAsUpEnabled(true)
        supportActionBar!!.setDisplayShowHomeEnabled(true)

        val adapter = ViewPagerAdapter(supportFragmentManager)
        adapter.addFragment(ReviewDetailsFragment())
        adapter.addFragment(ReviewQuestionFragment())
        viewpagerReview.adapter = adapter
        tabLayout = findViewById<View>(R.id.tabsLayout) as TabLayout
        tabLayout!!.setupWithViewPager(viewpagerReview)
        tabLayout!!.getTabAt(0)!!.text = "Review Details"
        tabLayout!!.getTabAt(1)!!.text = "Review Questions"
    }


    internal inner class ViewPagerAdapter(manager: FragmentManager) : FragmentPagerAdapter(manager) {
        val mFragmentList = ArrayList<Fragment>()
        override fun getItem(position: Int): Fragment {
            return mFragmentList.get(position)
        }

        override fun getCount(): Int {
            return mFragmentList.size
        }

        fun addFragment(fragment: Fragment) {
            mFragmentList.add(fragment)
        }
    }

    override fun onOptionsItemSelected(item: MenuItem?): Boolean {
        onBackPressed()
        return super.onOptionsItemSelected(item)
    }
}
