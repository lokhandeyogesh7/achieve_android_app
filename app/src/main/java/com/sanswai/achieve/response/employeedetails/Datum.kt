package com.sanswai.achieve.response.employeedetails

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

class Datum {

    @SerializedName("id")
    @Expose
    var id: Int? = null
    @SerializedName("education_id")
    @Expose
    var educationId: String? = null
    @SerializedName("education_name")
    @Expose
    var educationName: String? = null
    @SerializedName("course_id")
    @Expose
    var courseId: String? = null
    @SerializedName("course_name")
    @Expose
    var courseName: String? = null
    @SerializedName("specialization_id")
    @Expose
    var specializationId: String? = null
    @SerializedName("spec_name")
    @Expose
    var specName: String? = null
    @SerializedName("institute_name")
    @Expose
    var instituteName: String? = null
    @SerializedName("course_type")
    @Expose
    var courseType: String? = null
    @SerializedName("passing_year")
    @Expose
    var passingYear: String? = null
    @SerializedName("grading_system_id")
    @Expose
    var gradingSystemId: String? = null
    @SerializedName("grade_system")
    @Expose
    var gradeSystem: String? = null

}
