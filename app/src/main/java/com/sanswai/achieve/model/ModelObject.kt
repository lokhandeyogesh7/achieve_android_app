package com.sanswai.achieve.model


import com.sanswai.achieve.R

enum class ModelObject constructor(val titleResId: Int, val layoutResId: Int) {

    REVIEW_DETAILS(R.string.review_details, R.layout.activity_review_details),
    REVIWEW_QUESTIONS(R.string.review_questions, R.layout.layout_review_questions)

}
