package com.diarobo.commands

import com.diarobo.District
import grails.validation.Validateable

/**
 * Created by rakib on 12/14/2016.
 */
class PatientProfileCommand implements Validateable{
    String name
    String fathersName
    String mothersName
    Date dateOfBirth
    Integer gender
    Double height
    Double weight
    String presentAddressLine
    Integer presentDistrictId
    String permanentAddressLine
    Integer permanentDistrictId
    String phoneNumber
    String facebook
    String skype
    String whatsApp

    static constraints = {
        name blank: true, nullable: true
        fathersName blank: true, nullable: true
        mothersName blank: true, nullable: true
        dateOfBirth blank: true, nullable: true
        gender blank: true, nullable: true
        height blank: true, nullable: true
        weight blank: true, nullable: true
        presentAddressLine blank: true, nullable: true
        presentDistrictId blank: true, nullable: true
        permanentAddressLine blank: true, nullable: true
        permanentDistrictId blank: true, nullable: true
        phoneNumber blank: true, nullable: true
        facebook blank: true, nullable: true
        skype blank: true, nullable: true
        whatsApp blank: true, nullable: true

    }
}
