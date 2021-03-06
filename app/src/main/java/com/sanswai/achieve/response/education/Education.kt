package com.sanswai.achieve.response.education

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

class Education {

    @SerializedName("response")
    @Expose
    var response: String? = null
    @SerializedName("data")
    @Expose
    var data: List<Datum>? = null

}
