package com.sanswai.achieve.response.employee_login

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName
import java.io.Serializable

class Data:Serializable {

    @SerializedName("id")
    @Expose
    var id: Int? = null
    @SerializedName("name")
    @Expose
    var name: String? = null
    @SerializedName("user_type")
    @Expose
    var userType: String? = null
    @SerializedName("email")
    @Expose
    var email: String? = null
    @SerializedName("mobile_number")
    @Expose
    var mobileNumber: String? = null
    @SerializedName("is_active")
    @Expose
    var isActive: String? = null
    @SerializedName("location")
    @Expose
    var location: Any? = null
    @SerializedName("resume_file")
    @Expose
    var resumeFile: Any? = null
    @SerializedName("profile_pic")
    @Expose
    var profilePic: Any? = null
    @SerializedName("resume_description")
    @Expose
    var resumeDescription: Any? = null
    @SerializedName("created_at")
    @Expose
    var createdAt: String? = null
    @SerializedName("updated_at")
    @Expose
    var updatedAt: String? = null

}
