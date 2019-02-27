package com.sanswai.achieve.response.employeelogin

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName
import java.io.Serializable

class UserData :Serializable{

    @SerializedName("average_rating")
    @Expose
    var averageRating: Float? = null
    @SerializedName("performance_status")
    @Expose
    var performanceStatus: String? = null
    @SerializedName("data")
    @Expose
    var data: List<Datum>? = null

}
