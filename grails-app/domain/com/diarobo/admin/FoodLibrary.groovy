package com.diarobo.admin

import com.diarobo.enums.ActiveStatus

class FoodLibrary {
    GroupItem groupItem     //group name: milk, Parent: Drink
    String name
    String measureUnit
    String weightMeasure

    Double approximateWeight
    ActiveStatus activeStatus = ActiveStatus.ACTIVE

    Date dateCreated
    Long createdBy
    Date lastUpdated
    Long updatedBy

    static constraints = {
        approximateWeight nullable: true
        dateCreated nullable: true
        createdBy nullable: true
        lastUpdated nullable: true
        updatedBy nullable: true
    }
}
