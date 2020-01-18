package com.diarobo.admin

import com.diarobo.enums.ActiveStatus

class Nutrition {
    Long foodLibraryId
    String name
    ActiveStatus activeStatus = ActiveStatus.ACTIVE
    static constraints = {
    }
}
