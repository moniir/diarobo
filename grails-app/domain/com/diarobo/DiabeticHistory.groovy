package com.diarobo

class DiabeticHistory {
    User user
    String diabeticType

    static constraints = {
        diabeticType nullable: true
    }
}
