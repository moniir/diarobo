package com.diarobo.admin

import com.diarobo.enums.ActiveStatus

class GeneralRuleMealPlan {
    long generalRuleId
    long breakFastFoodId
    String breakFastFoodTime
    long snackBeforeLunchFoodId
    String snackBeforeLunchFoodTime
    long lunchFoodId
    String lunchFoodTime
    long snackBeforeDinnerFoodId
    String snackBeforeDinnerFoodTime
    long dinnerFoodId
    String dinnerFoodTime
    long beforeSleepFoodId
    String beforeSleepFoodTime
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
