package com.diarobo.admin

import com.diarobo.enums.ActiveStatus

class MedBrand {
    String brandName
    MedCompany medCompany
    MedGeneric medGeneric
    ActiveStatus activeStatus = ActiveStatus.ACTIVE

    static constraints = {
    }
}
