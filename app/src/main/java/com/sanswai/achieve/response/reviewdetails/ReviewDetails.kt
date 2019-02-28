package com.sanswai.achieve.response.reviewdetails

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName
import java.io.Serializable

class ReviewDetails : Serializable {

    @SerializedName("response")
    @Expose
    var response: String? = null
    @SerializedName("avg_ratings")
    @Expose
    var avgRatings: Float? = null
    @SerializedName("avg_review")
    @Expose
    var avgReview: String? = null
    @SerializedName("start_date")
    @Expose
    var startDate: String? = null
    @SerializedName("end_date")
    @Expose
    var endDate: String? = null
    @SerializedName("review_feedback")
    @Expose
    var reviewFeedback: String? = null
    @SerializedName("user_data")
    @Expose
    var userData: List<UserDatum>? = null

}
