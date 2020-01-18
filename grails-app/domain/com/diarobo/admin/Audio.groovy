package com.diarobo.admin

import com.diarobo.enums.ActiveStatus

class Audio {

    String name
    ActiveStatus activeStatus = ActiveStatus.ACTIVE
    Date dateCreated
    String createdBy
    Date lastUpdated
    String updatedBy

    static constraints = {
        dateCreated nullable: true
        createdBy nullable: true
        lastUpdated nullable: true
        updatedBy nullable: true
    }
}
