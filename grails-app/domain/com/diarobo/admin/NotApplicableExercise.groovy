package com.diarobo.admin

import com.diarobo.enums.ActiveStatus

class NotApplicableExercise {

    Long id
    Long exerciseId
    String name
    ActiveStatus activeStatus = ActiveStatus.ACTIVE
    static constraints = {
    }
}
