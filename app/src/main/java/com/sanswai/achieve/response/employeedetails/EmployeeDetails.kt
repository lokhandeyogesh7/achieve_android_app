package com.sanswai.achieve.response.employeedetails

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

class EmployeeDetails {

    @SerializedName("response")
    @Expose
    var response: String? = null
    @SerializedName("education")
    @Expose
    var education: Education? = null
    @SerializedName("employement")
    @Expose
    var employement: Employement? = null
    @SerializedName("project")
    @Expose
    var project: Project? = null
    @SerializedName("resume_headline")
    @Expose
    var resumeHeadline: ResumeHeadline? = null
    @SerializedName("desired_profile")
    @Expose
    var desiredProfile: DesiredProfile? = null
    @SerializedName("personal_details")
    @Expose
    var personalDetails: PersonalDetails? = null
    @SerializedName("mst_skill")
    @Expose
    var mstSkill: MstSkill? = null
    @SerializedName("user_skill")
    @Expose
    var userSkill: UserSkill? = null

}
