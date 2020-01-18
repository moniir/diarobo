package com.diarobo.admin

import com.diarobo.Picture
import com.diarobo.enums.ActiveStatus
import com.diarobo.enums.WeightMeasure

class Library {
    String name
    String measureUnit
    String weightMeasure

    ActiveStatus activeStatus = ActiveStatus.ACTIVE    // o deleted, 1 active and verified, 2 user added but not verified
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
    static mapping = {
        tablePerHierarchy false
    }
}
