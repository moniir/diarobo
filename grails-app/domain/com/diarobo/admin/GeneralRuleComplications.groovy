package com.diarobo.admin

import com.diarobo.enums.ActiveStatus

class GeneralRuleComplications {
    long generalRuleId
    String description
    ActiveStatus activeStatus = ActiveStatus.ACTIVE
    String createdBy
    Date createdDate = new Date()
    String lastUpdatedBy
    Date lastUpdatedDate


    static constraints = {
        lastUpdatedBy nullable: true
        lastUpdatedDate nullable: true
    }
}
