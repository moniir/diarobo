package com.diarobo.admin

import com.diarobo.Picture
import com.diarobo.User
import com.diarobo.enums.ActiveStatus
import com.diarobo.enums.SyncStatus

/**
 * Created by rakib on 3/18/2017.
 */
class Reminder {
    User patient
    Picture picture
    Audio audio
    Long libraryId
    String message
    String options
    String libraryType
    Integer packageId
    Date dateTime
    String question
    Double quantity
    Boolean yesFeedback = false
    Boolean needToComplain = false
    SyncStatus syncStatus = SyncStatus.NEW
    ActiveStatus activeStatus = ActiveStatus.ACTIVE

    static constraints = {
        audio nullable: true
        packageId nullable: true
    }
}
