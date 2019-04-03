package com.sanswai.achieve.response.employeedetails

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

class Data__ {

    @SerializedName("id")
    @Expose
    var id: Int? = null
    @SerializedName("residential_address_one")
    @Expose
    var residentialAddressOne: String? = null
    @SerializedName("residential_address_two")
    @Expose
    var residentialAddressTwo: String? = null
    @SerializedName("residential_pin_code")
    @Expose
    var residentialPinCode: String? = null
    @SerializedName("permanent_address_one")
    @Expose
    var permanentAddressOne: String? = null
    @SerializedName("permanent_address_two")
    @Expose
    var permanentAddressTwo: String? = null
    @SerializedName("pin_code")
    @Expose
    var pinCode: String? = null
    @SerializedName("gender")
    @Expose
    var gender: String? = null
    @SerializedName("marriatal_status")
    @Expose
    var marriatalStatus: String? = null
    @SerializedName("hometown")
    @Expose
    var hometown: String? = null
    @SerializedName("date_of_birth")
    @Expose
    var dateOfBirth: String? = null
    @SerializedName("created_at")
    @Expose
    var createdAt: CreatedAt? = null
    @SerializedName("updated_at")
    @Expose
    var updatedAt: UpdatedAt? = null

}
