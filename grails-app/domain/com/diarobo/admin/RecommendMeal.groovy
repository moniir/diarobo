package com.diarobo.admin

import com.diarobo.enums.ActiveStatus

class RecommendMeal {
    String breakfastPackage
    String breakfastTime
    String beforeLunchPackage
    String beforeLunchTime
    String lunchPackage
    String lunchTime
    String beforeDinnerPackage
    String beforeDinnerTime
    String dinnerPackage
    String dinnerTime
    String beforeSleepPackage
    String beforeSleepTime

    String createdBy
    Date createdDate = new Date()
    String lastUpdatedBy
    Date lastUpdatedDate
    ActiveStatus activeStatus = ActiveStatus.ACTIVE

    static constraints = {
        breakfastTime nullable: true
        beforeLunchTime nullable: true
        lunchTime nullable: true
        beforeDinnerTime nullable: true
        dinnerTime nullable: true
        beforeSleepTime nullable: true
        lastUpdatedBy nullable: true
        lastUpdatedDate nullable: true
    }
}
