package com.sanswai.achieve.response.employeelogin

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName
import java.io.Serializable

class Datum :Serializable{

    @SerializedName("start_date")
    @Expose
    var startDate: String? = null
    @SerializedName("end_date")
    @Expose
    var endDate: String? = null
    @SerializedName("avg_rating")
    @Expose
    var avgRating: Float? = null
    @SerializedName("performance_status")
    @Expose
    var performanceStatus: String? = null
    @SerializedName("feedback_details")
    @Expose
    var feedbackDetails: String? = null
    @SerializedName("id")
    @Expose
    var id: Int? = null

}
