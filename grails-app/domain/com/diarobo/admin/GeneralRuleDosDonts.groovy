package com.diarobo.admin

import com.diarobo.enums.ActiveStatus

class GeneralRuleDosDonts {
    long generalRuleId
    String description
    String dosDoNtsFlag
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
