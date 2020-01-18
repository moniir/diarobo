package com.diarobo

class SurgeryHistory {
    User user
    String description
    Boolean activeStatus =true
    static constraints = {
        description blank: true, nullable: true
        activeStatus blank: true, nullable: true
    }
}
