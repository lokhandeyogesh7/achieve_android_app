package com.sanswai.achieve.response.employeeperformance

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName
import java.io.Serializable

class EmployeePerformace :Serializable {

    @SerializedName("average_rating")
    @Expose
    var averageRating: Double? = null
    @SerializedName("performance_status")
    @Expose
    var performanceStatus: String? = null
    @SerializedName("data")
    @Expose
    var data: List<Datum>? = null
    @SerializedName("response")
    @Expose
    var response: String? = null
    @SerializedName("add_record")
    @Expose
    var add_record: Boolean? = false

}
