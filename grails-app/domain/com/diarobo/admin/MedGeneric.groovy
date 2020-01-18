package com.diarobo.admin

import com.diarobo.enums.ActiveStatus

class MedGeneric {
    String genericName
    String medicineType
    String sideEffect
    ActiveStatus activeStatus = ActiveStatus.ACTIVE

    static constraints = {
        sideEffect nullable: true
    }
}
