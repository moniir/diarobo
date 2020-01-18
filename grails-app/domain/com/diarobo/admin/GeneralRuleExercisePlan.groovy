package com.diarobo.admin

import com.diarobo.enums.ActiveStatus

class GeneralRuleExercisePlan {
    Long generalRuleId
    Long exerciseId
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
