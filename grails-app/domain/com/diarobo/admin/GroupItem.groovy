package com.diarobo.admin

import com.diarobo.enums.ActiveStatus

class GroupItem {
    Long id
    Long parentId
    String name
    String itemType
    ActiveStatus activeStatus = ActiveStatus.ACTIVE
    static constraints = {
        parentId nullable: true
        id nullable: true
    }
}
