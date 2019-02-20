package com.sanswai.achieve.model

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

class Result {

    @SerializedName("uid")
    @Expose
    var uid: String? = null
    @SerializedName("name")
    @Expose
    var name: String? = null
    @SerializedName("operator")
    @Expose
    var operator: String? = null
    @SerializedName("degree_of_longitude")
    @Expose
    var degreeOfLongitude: String? = null
    @SerializedName("degree_of_latitude")
    @Expose
    var degreeOfLatitude: String? = null
}