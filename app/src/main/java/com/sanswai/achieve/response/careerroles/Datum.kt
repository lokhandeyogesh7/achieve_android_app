package com.sanswai.achieve.response.careerroles

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

class Datum {

    @SerializedName("id")
    @Expose
    var id: Int? = null
    @SerializedName("career_role_name")
    @Expose
    lateinit var careerRoleName: String

    override fun toString(): String {
        return this.careerRoleName            // What to display in the Spinner list.
    }

}
