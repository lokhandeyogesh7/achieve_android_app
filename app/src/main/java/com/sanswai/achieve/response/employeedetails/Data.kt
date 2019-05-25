package com.sanswai.achieve.response.employeedetails

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

class Data {

    @SerializedName("id")
    @Expose
    var id: Int? = null
    @SerializedName("tittle")
    @Expose
    var tittle: String? = null
    @SerializedName("description")
    @Expose
    var description: String? = null

}
