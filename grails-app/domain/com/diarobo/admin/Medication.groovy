package com.diarobo.admin

import com.diarobo.enums.ActiveStatus

class Medication {
    Long prescriptionId
    String type
    String name
    String weight
    String time
    String period
    String amBm
    String drugTime1
    String drugTime2
    String drugTime3
    String drugTime4
    String drugTime5
    Double quantity1
    Double quantity2
    Double quantity3
    Double quantity4
    Double quantity5
    String instruction
    String createdBy
    Date createdDate
    String lastUpdatedBy
    Date lastUpdatedDate
    ActiveStatus activeStatus = ActiveStatus.ACTIVE

    static constraints = {
        lastUpdatedBy nullable: true
        lastUpdatedDate nullable: true
        period nullable: true
        instruction nullable: true
        drugTime1 nullable: true
        drugTime2 nullable: true
        drugTime3 nullable: true
        drugTime4 nullable: true
        drugTime5 nullable: true
    }
}
