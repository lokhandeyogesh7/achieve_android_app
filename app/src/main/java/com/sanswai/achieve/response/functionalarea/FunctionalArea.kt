package com.sanswai.achieve.response.functionalarea

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

class FunctionalArea {

    @SerializedName("response")
    @Expose
    lateinit var response: String
    @SerializedName("data")
    @Expose
    var data: List<Datum>? = null

}
