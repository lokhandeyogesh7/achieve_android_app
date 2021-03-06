package com.sanswai.achieve.response.locationlist

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

class Location {

    @SerializedName("response")
    @Expose
    var response: String? = null
    @SerializedName("data")
    @Expose
    var data: List<Datum>? = null

}
