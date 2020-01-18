package com.diarobo.admin

import com.diarobo.enums.ActiveStatus

class ExerciseLibrary extends Library{
    Long id
    Double calorieBurn
    Double perInMeasure

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