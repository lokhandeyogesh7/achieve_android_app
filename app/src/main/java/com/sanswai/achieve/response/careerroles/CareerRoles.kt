package com.sanswai.achieve.response.careerroles

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

class CareerRoles {

    @SerializedName("response")
    @Expose
    lateinit var response: String
    @SerializedName("data")
    @Expose
    var data: List<Datum>? = null
}
