package com.diarobo.admin

import com.diarobo.enums.ActiveStatus

class ClinicalCondition {

    Double beforeBreakFast
    Double afterBreakFast
    Double beforeLunch
    Double afterLunch
    Double beforeDinar
    Double afterDinar
    Double randomTime
    Double hbaic
    Double ogtt
    Double sugar
    Double albumin
    Double acetone
    String createdBy
    Date createdDate
    String lastUpdatedBy
    Date lastUpdatedDate
    ActiveStatus activeStatus = ActiveStatus.ACTIVE

    static constraints = {
        beforeBreakFast nullable: true
        afterBreakFast nullable: true
        beforeLunch nullable: true
        afterLunch nullable: true
        beforeDinar nullable: true
        afterDinar nullable: true
        randomTime nullable: true
        hbaic nullable: true
        ogtt nullable: true
        sugar nullable: true
        albumin nullable: true
        acetone nullable: true
        lastUpdatedBy nullable: true
        lastUpdatedDate nullable: true
    }
}
