package com.diarobo.commands

import grails.validation.Validateable

/**
 * Created by rakib on 12/6/2016.
 */
class ResetPasswordCommand implements Validateable{
    String username
    String newPassword
    String confirmNewPassword
    String verificationCode

    static constraints = {
        newPassword blank: false, nullable: false, minSize: 6, maxSize: 20, validator: {newPassword, obj ->
            def password2 = obj.confirmNewPassword
            password2 == newPassword ? true : ['newPassword.invalid.matchingPassword']
        }
    }
}
