package com.sanswai.achieve.response.specialization

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

class Datum {

    @SerializedName("id")
    @Expose
    var id: Int? = null
    @SerializedName("spec_name")
    @Expose
    var specName: String = ""

    override fun toString(): String {
        return this.specName
    }

}
