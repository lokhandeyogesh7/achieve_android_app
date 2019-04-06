package com.sanswai.achieve.response.industries

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

class Datum {

    @SerializedName("id")
    @Expose
    var id: Int? = null
    @SerializedName("industry_name")
    @Expose
    lateinit var industryName: String

    override fun toString(): String {
        return this.industryName            // What to display in the Spinner list.
    }

}
