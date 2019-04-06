package com.sanswai.achieve.response.education

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

class Datum {

    @SerializedName("id")
    @Expose
    var id: Int? = null
    @SerializedName("education_name")
    @Expose
    var educationName: String? = null

}
