package com.sanswai.achieve.response.employeedetails

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

class DesiredProfile {

    @SerializedName("response")
    @Expose
    var response: String? = null
    @SerializedName("data")
    @Expose
    var data: List<Datum___>? = null

}
