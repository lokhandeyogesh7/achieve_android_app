package com.sanswai.achieve.response.employeedetails

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

class CreatedAt {

    @SerializedName("date")
    @Expose
    var date: String? = null
    @SerializedName("timezone_type")
    @Expose
    var timezoneType: Int? = null
    @SerializedName("timezone")
    @Expose
    var timezone: String? = null

}
