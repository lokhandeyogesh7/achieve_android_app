package com.sanswai.achieve.response.specialization

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

class Specialization {

    @SerializedName("response")
    @Expose
    var response: String? = null
    @SerializedName("data")
    @Expose
    var data: List<Datum>? = null

}
