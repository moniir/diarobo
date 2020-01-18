package com.diarobo.enums

/**
 * Created by rakib on 12/7/2016.
 */
enum ActiveStatus {
    DELETED,ACTIVE,DRAFT,OLD

    static Collection<ActiveStatus> workingStatus(){
        return [ACTIVE, DRAFT, OLD]
    }
}
