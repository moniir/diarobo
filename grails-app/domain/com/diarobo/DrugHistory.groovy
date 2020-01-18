package com.diarobo

class DrugHistory {
    User user
    String drugType
    String drugName
    String drugTakenFrom
    String drugTakenTo
    String drugFrequency
    Boolean activeStatus = true

    static constraints = {
    }
}
