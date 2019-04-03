package com.sanswai.achieve.response.employeedetails

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

class Data {

    @SerializedName("id")
    @Expose
    var id: Int? = null
    @SerializedName("name")
    @Expose
    var name: String? = null
    @SerializedName("email")
    @Expose
    var email: String? = null
    @SerializedName("user_type")
    @Expose
    var userType: String? = null
    @SerializedName("mobile_number")
    @Expose
    var mobileNumber: String? = null
    @SerializedName("location")
    @Expose
    var location: Any? = null
    @SerializedName("resume_file")
    @Expose
    var resumeFile: String? = null
    @SerializedName("profile_pic")
    @Expose
    var profilePic: String? = null

}
