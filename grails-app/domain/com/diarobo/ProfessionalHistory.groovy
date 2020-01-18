package com.diarobo

class ProfessionalHistory {
    User user
    String jobIndustry
    String jobNature
    String fromDate
    String toDate
    Boolean currentJob = false
    Boolean activeStatus = true

    static constraints = {
        toDate nullable: true
    }
}
