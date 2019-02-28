package com.sanswai.achieve.response.reviewdetails

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName
import java.io.Serializable

class UserDatum :Serializable {

    @SerializedName("question")
    @Expose
    var question: String? = null
    @SerializedName("rating_point")
    @Expose
    var ratingPoint: String? = null
    @SerializedName("rating_review")
    @Expose
    var ratingReview: String? = null

}
