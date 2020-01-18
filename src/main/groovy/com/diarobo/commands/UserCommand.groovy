package com.diarobo.commands

import com.diarobo.MasterKeySetup
import grails.validation.Validateable

/**
 * Created by rakib on 12/3/2016.
 */
class UserCommand implements Validateable  {
    String username
    String nickName
    String fullName
    String userType

    static constraints = {
        username size: 8..14
    }

}
