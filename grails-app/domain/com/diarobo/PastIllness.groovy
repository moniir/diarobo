package com.diarobo

class PastIllness {
    User user
    String drugName
    Boolean activeStatus =true
    static constraints = {
        drugName blank: true, nullable: true
        activeStatus blank: true, nullable: true
    }
}
