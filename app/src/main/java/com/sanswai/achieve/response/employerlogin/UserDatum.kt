package com.sanswai.achieve.response.employerlogin

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName
import java.io.Serializable

class UserDatum : Serializable {

    @SerializedName("id")
    @Expose
    var id: Int? = null
    @SerializedName("name")
    @Expose
    var name: String? = null
    @SerializedName("email")
    @Expose
    var email: String? = null
    @SerializedName("mobile_number")
    @Expose
    var mobileNumber: String? = null
    @SerializedName("user_rating")
    @Expose
    var userRating: Float? = null
    @SerializedName("performance_status")
    @Expose
    var performanceStatus: String? = null

}
