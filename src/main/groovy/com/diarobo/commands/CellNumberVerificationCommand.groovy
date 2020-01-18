package com.diarobo.commands

import grails.validation.Validateable

/**
 * Created by rakib on 12/5/2016.
 */
class CellNumberVerificationCommand implements Validateable {
    String username
    String verificationCode

    static constraints = {
        verificationCode blank: true, nullable: true
        username size: 8..14
    }
}
