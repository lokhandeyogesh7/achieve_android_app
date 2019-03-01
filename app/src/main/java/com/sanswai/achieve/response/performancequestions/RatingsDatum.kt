package com.sanswai.achieve.response.performancequestions

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName
import java.io.Serializable

class RatingsDatum : Serializable {

    @SerializedName("id")
    @Expose
    var id: Int? = null
    @SerializedName("name")
    @Expose
    var name: String? = null
    @SerializedName("points")
    @Expose
    var points: Int? = null

}
