package com.diarobo

class PatientDisease {
    Long diabeticHistoryId
    Long diseaseId
    String parentType
    String grandParentType
    Boolean activeStatus = true


    static constraints = {
        parentType nullable: true
        grandParentType nullable: true
    }
}
