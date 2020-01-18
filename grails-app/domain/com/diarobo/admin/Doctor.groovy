package com.diarobo.admin

import com.diarobo.enums.ActiveStatus

class Doctor {
    String name
    String phoneNumber
    String designation
    String hospitalName
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
