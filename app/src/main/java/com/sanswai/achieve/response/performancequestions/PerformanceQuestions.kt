package com.sanswai.achieve.response.performancequestions

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName
import java.io.Serializable

class PerformanceQuestions : Serializable{

    @SerializedName("response")
    @Expose
    var response: String? = null
    @SerializedName("questions_data")
    @Expose
    var questionsData: List<QuestionsDatum>? = null
    @SerializedName("ratings_data")
    @Expose
    var ratingsData: List<RatingsDatum>? = null

}
