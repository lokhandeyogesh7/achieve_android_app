package com.sanswai.achieve.response.employeelogin

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName
import java.io.Serializable

class LoginResponse:Serializable {

    @SerializedName("response")
    @Expose
    var response: Boolean? = null
    @SerializedName("response_msg")
    @Expose
    var responseMsg: String? = null
    @SerializedName("data")
    @Expose
    var data: Data? = null
    @SerializedName("user_type")
    @Expose
    var userType: String? = null
    @SerializedName("user_data")
    @Expose
    var userData: UserData? = null

}
