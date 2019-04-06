package com.sanswai.achieve.response.functionalarea

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName
import android.R.attr.name



class Datum {

    @SerializedName("id")
    @Expose
    var id: Int? = null
    @SerializedName("functional_area_name")
    @Expose
    lateinit var functionalAreaName: String

    override fun toString(): String {
        return this.functionalAreaName            // What to display in the Spinner list.
    }

}
