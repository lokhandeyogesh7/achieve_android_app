package com.sanswai.achieve.response.industries

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

class Industries {

    @SerializedName("response")
    @Expose
    lateinit var response: String
    @SerializedName("data")
    @Expose
    var data: List<Datum>? = null

}
