package com.sanswai.achieve.response.coursedetails

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

class Datum {

    @SerializedName("id")
    @Expose
    var id: Int? = null
    @SerializedName("course_name")
    @Expose
    var courseName: String = ""

    override fun toString(): String {
        return this.courseName            // What to display in the Spinner list.
    }

}
