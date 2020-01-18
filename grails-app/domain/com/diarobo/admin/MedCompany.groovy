package com.diarobo.admin

import com.diarobo.enums.ActiveStatus

class MedCompany {
    String companyName
    ActiveStatus activeStatus = ActiveStatus.ACTIVE

    static constraints = {
        companyName unique: true
    }
}
