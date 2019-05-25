package com.sanswai.achieve.response.coursedetails

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

class CourseDetails {

    @SerializedName("response")
    @Expose
    var response: String? = null
    @SerializedName("data")
    @Expose
    var data: List<Datum>? = null

}
