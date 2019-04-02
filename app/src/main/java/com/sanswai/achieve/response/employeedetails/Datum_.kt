package com.sanswai.achieve.response.employeedetails

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

class Datum_ {

    @SerializedName("id")
    @Expose
    var id: Int? = null
    @SerializedName("organization_name")
    @Expose
    var organizationName: String? = null
    @SerializedName("working_start_date")
    @Expose
    var workingStartDate: String? = null
    @SerializedName("worked_till_date")
    @Expose
    var workedTillDate: String? = null
    @SerializedName("job_profile_description")
    @Expose
    var jobProfileDescription: String? = null
    @SerializedName("notice_period")
    @Expose
    var noticePeriod: String? = null
    @SerializedName("designation")
    @Expose
    var designation: String? = null
    @SerializedName("is_current_employment")
    @Expose
    var isCurrentEmployment: String? = null

}
