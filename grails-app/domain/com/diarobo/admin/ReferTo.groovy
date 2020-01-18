package com.diarobo.admin

import com.diarobo.enums.ActiveStatus

class ReferTo {
    Long specialistId
    Long prescriptionId
    String createdBy
    Date createdDate = new Date()
    String lastUpdatedBy
    Date lastUpdatedDate
    ActiveStatus activeStatus = ActiveStatus.DRAFT

    static constraints = {
        lastUpdatedBy nullable: true
        lastUpdatedDate nullable: true
    }
}
