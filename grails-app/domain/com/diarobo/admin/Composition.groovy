package com.diarobo.admin

import com.diarobo.enums.ActiveStatus

class Composition {
    Long foodLibraryId
    String name
    Double quantity
    ActiveStatus activeStatus = ActiveStatus.ACTIVE
    static constraints = {
    }
}
