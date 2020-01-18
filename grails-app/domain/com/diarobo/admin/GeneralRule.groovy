package com.diarobo.admin

import com.diarobo.enums.ActiveStatus

class GeneralRule {
    String diabeticType
    Double bmiRangeFrom
    Double bmiRangeTo
    Integer ageRangeFrom
    Integer ageRangeTo
    ActiveStatus activeStatus = ActiveStatus.ACTIVE
    String createdBy
    Date createdDate = new Date()
    String lastUpdatedBy
    Date lastUpdatedDate
    Integer stepLevel

    static constraints = {
        lastUpdatedBy nullable: true
        lastUpdatedDate nullable: true
        stepLevel nullable: true
    }
}
