package com.diarobo.admin

import com.diarobo.enums.ActiveStatus

class RecommendExercise {
    Long prescriptionId
    Long exerciseId
    String time
    Double period

    String createdBy
    Date createdDate = new Date()
    String lastUpdatedBy
    Date lastUpdatedDate
    ActiveStatus activeStatus = ActiveStatus.ACTIVE

    static constraints = {
        lastUpdatedBy nullable: true
        lastUpdatedDate nullable: true
    }
}
