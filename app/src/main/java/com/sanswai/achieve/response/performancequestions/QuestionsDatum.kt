package com.sanswai.achieve.response.performancequestions

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName
import java.io.Serializable

class QuestionsDatum : Serializable {

    @SerializedName("id")
    @Expose
    var id: Int? = null
    @SerializedName("question")
    @Expose
    var question: String? = null
    @SerializedName("rating")
    @Expose
    var rating: Float = 0.0F

}
