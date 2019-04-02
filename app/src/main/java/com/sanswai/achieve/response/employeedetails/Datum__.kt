package com.sanswai.achieve.response.employeedetails

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

class Datum__ {

    @SerializedName("id")
    @Expose
    var id: Int? = null
    @SerializedName("project_name")
    @Expose
    var projectName: String? = null
    @SerializedName("project_description")
    @Expose
    var projectDescription: String? = null

}
