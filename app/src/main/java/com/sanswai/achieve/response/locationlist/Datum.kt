package com.sanswai.achieve.response.locationlist

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

class Datum {

    @SerializedName("id")
    @Expose
    var id: Int? = null
    @SerializedName("location_name")
    @Expose
    var locationName: String = ""

    override fun toString(): String {
        return this.locationName
    }

}
