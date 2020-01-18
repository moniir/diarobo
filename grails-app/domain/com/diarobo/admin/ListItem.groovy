package com.diarobo.admin

import com.diarobo.enums.ActiveStatus

class ListItem {
    Long id
    String libraryType //excercise
    String keyName  // not applicable
    String name
    ActiveStatus activeStatus = ActiveStatus.ACTIVE
    static constraints = {
        id nullable: true
    }
}
