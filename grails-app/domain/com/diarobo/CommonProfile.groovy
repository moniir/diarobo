package com.diarobo

class CommonProfile {
    Date dateOfBirth
    Integer gender
    String presentAddressLine
    String permanentAddressLine
    District presentDistrict
    District permanentDistrict
    String facebook
    String skype
    String whatsApp

    static constraints = {
        dateOfBirth blank: true, nullable: true
        gender blank: true, nullable: true
        presentAddressLine blank: true, nullable: true
        permanentAddressLine blank: true, nullable: true
        presentDistrict blank: true, nullable: true
        permanentDistrict blank: true, nullable: true
        facebook blank: true, nullable: true
        skype blank: true, nullable: true
        whatsApp blank: true, nullable: true
    }
    static mapping = {
        tablePerHierarchy false
    }
}
