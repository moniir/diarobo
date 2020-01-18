package com.diarobo

class PatientProfile extends CommonProfile{
    User user
    String fathersName
    String mothersName
    Double height
    Double weight
    String highestEducation
    Integer socialFinancialStatus

    static constraints = {
        user blank: false, nullable: false
        fathersName blank: true, nullable: true
        mothersName blank: true, nullable: true
        height blank: true, nullable: true
        weight blank: true, nullable: true
        highestEducation blank: true, nullable: true
        socialFinancialStatus blank: true, nullable: true
    }
}
