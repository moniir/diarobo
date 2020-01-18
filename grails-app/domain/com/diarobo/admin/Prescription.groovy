package com.diarobo.admin

import com.diarobo.User
import com.diarobo.enums.ActiveStatus

class Prescription {
    RecommendMeal recommendMeal
    ClinicalCondition clinicalCondition
    User patient
    Doctor doctor

    String createdBy
    Date createdDate
    String lastUpdatedBy
    Date lastUpdatedDate
    ActiveStatus activeStatus = ActiveStatus.DRAFT

    static constraints = {
        lastUpdatedBy nullable: true
        lastUpdatedDate nullable: true
        clinicalCondition nullable: true
        recommendMeal nullable: true
    }
}
