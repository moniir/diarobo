package com.diarobo.api

import com.diarobo.CommonUtils
import com.diarobo.MasterKeySetup
import com.diarobo.PatientProfile
import com.diarobo.User
import com.diarobo.admin.ExerciseLibrary
import com.diarobo.admin.FoodLibrary
import com.diarobo.admin.MedBrand
import com.diarobo.admin.Reminder
import com.diarobo.commands.CellNumberVerificationCommand
import com.diarobo.commands.UserCommand
import com.diarobo.enums.MasterKeyValue
import com.diarobo.enums.SmsStatus
import com.diarobo.enums.SyncStatus
import grails.converters.JSON
import grails.plugin.springsecurity.annotation.Secured
import org.grails.web.json.JSONObject
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.beans.factory.annotation.Qualifier
import org.springframework.security.authentication.BadCredentialsException
import org.springframework.security.core.authority.AuthorityUtils
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder
import groovy.json.JsonSlurper
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken
import org.springframework.security.core.Authentication
import org.springframework.security.core.context.SecurityContextHolder
import org.springframework.security.authentication.AuthenticationManager

import javax.servlet.http.HttpServletRequest

@Secured("permitAll")
class DiaroboApiController {
    def loginService
    def messageSource
    def springSecurityService
    def notificationService
    def diaroboApiService
    def pictureService
    AuthenticationManager authenticationManager

    def index(String mobileNo) {
        println(mobileNo)
        def result = [person: "aminul Haque"]
        render result as JSON
    }

    def login(String username, String password){
        println(username)
        println(password)
        println(params)
        User user = User.findByUsername(username)
        String passwordStored = user.password
        def responseData
        if(user) {
            User activeUser = user
            def password_old =password
            if    (!springSecurityService.passwordEncoder.isPasswordValid(activeUser.getPassword(), password_old, null)) {
                String errorMessage = message(code: 'login.username.password.invalid')
                responseData = [
                        'hasError': true, 'errorMsg': errorMessage, 'username': ''
                ]
            }else {
                responseData = [
                        'hasError': false, 'errorMsg': '', 'username': username
                ]
            }
        } else {
            String errorMessage = message(code: 'login.username.password.invalid')
            responseData = [
                    'hasError': true, 'errorMsg': errorMessage, 'username': ''
            ]
        }
        render responseData as JSON
    }

    def forgetPassword(String username) {
        def result
        def responseData
        String errorMessage

        boolean hasError = loginService.forgetPassword(username,request)
        if(hasError) {
            errorMessage = message(code: 'phone.doesNot.exist')
            responseData = [
                    'hasError': true, 'errorMsg': errorMessage,'username': ''
            ]
        } else {
            responseData = [
                    'hasError': false, 'errorMsg': errorMessage, 'username': username
            ]
        }
        render responseData as JSON
    }


    def reSendVerificationCode(String username){
        def responseData

        User user = User.findByUsername(username)
        if(user) {
            if(loginService.sendCellVerificationCode(user , request) == SmsStatus.SUCCESS.value) {
                responseData = [
                        'hasError': false, 'errorMsg': ''
                ]
            } else {
                responseData = [
                        'hasError': true, 'errorMsg':  message(code: 'server.error')
                ]
            }
        } else {
            responseData = [
                    'hasError': true, 'errorMsg':  message(code: 'server.error')
            ]
        }
        render responseData as JSON
    }

    // OK
    def phoneVerification(CellNumberVerificationCommand command) {
        def result
        def responseData
        String errorMessage

        if (command.hasErrors()) {
            def errorList = command?.errors?.allErrors?.collect{messageSource.getMessage(it,null)}
            errorMessage = errorList?.join('\n')
            responseData = [
                    'hasError': true, 'errorMsg': errorMessage, 'username': '', userType: ''
            ]
        } else {
            result =loginService.phoneVerification(command)
            if (result.hasError) {
                def errorList = command?.errors?.allErrors?.collect{messageSource.getMessage(it,null)}
                errorMessage = errorList?.join('\n')
                responseData = [
                        'hasError': true, 'errorMsg': errorMessage, 'username': '', userType: ''
                ]
            } else {
                String username = command.username
                responseData = [
                        'hasError': false, 'errorMsg': '', 'username': username, userType: result.userType
                ]
            }
        }
        render responseData as JSON
    }


    // OK
    def registration(UserCommand command){
        def result
        def responseData
        String errorMessage

        if (command.hasErrors()) {
            def errorList = command?.errors?.allErrors?.collect{messageSource.getMessage(it,null)}
            errorMessage = errorList?.join('\n')
            responseData = [
                    'hasError': true, 'errorMsg': errorMessage, 'username': ''
            ]
        } else {
            result = loginService.registration(command, request)
            if (result.hasError) {
                def errorList = command?.errors?.allErrors?.collect{messageSource.getMessage(it,null)}
                errorMessage = errorList?.join('\n')
                responseData = [
                        'hasError': true, 'errorMsg': errorMessage, 'username': ''
                ]
            } else {
                String username = command.username
                responseData = [
                        'hasError': false, 'errorMsg': '', 'username': username
                ]
            }
        }
        render responseData as JSON
    }
    def getUerTypeList() {
        def result = new ArrayList<Map>();
        def masterKeySetupList = MasterKeySetup.findAllByKeyName(MasterKeyValue.USER_TYPE.value)
        for(MasterKeySetup keySetup : masterKeySetupList) {
            result.add([keySetup.keyValue, keySetup.id])
        }
        def responseData = [
                'userTypeList': result, 'status': 200
        ]
        render responseData as JSON
    }

    def getProfilePage(String username, String password) {
        if(loginService.userLoginInternally(username, password)) {
            redirect controller: 'profile', action: 'index'
        }
    }

    def resendCode(String username){
        render(status: 200)
    }

    def registerWithNotificationKey(String username, String notificationKey) {
        User patient = User.findByUsername(username)
        def responseData
        if(patient) {
            patient.notificationKey = notificationKey
            patient.save(flush: true)
            def reminderArrayList = diaroboApiService.getReminderList(patient, g)
            println(username + ' ' + notificationKey + '   ' + notificationKey.length())
            responseData = ['message': 'success', 'hasError': false, reminderList: reminderArrayList]
        } else {
            responseData = ['message': 'User not found', 'hasError': true]
        }
        render responseData as JSON
    }

    def showImage(Integer libraryId, String libraryType) {
        if ( libraryId == null) {
            flash.message = "library not found."
            redirect (action:'index')
        } else {
            byte[] buffer = pictureService.getPictureInByte(libraryId, libraryType, response)
            if(buffer){
                response.outputStream << buffer
                response.outputStream.flush()
            }  else {
                flash.message = "library not found."
                redirect (action:'index')
            }
        }
    }

    def feedback() {
        JSONObject json = JSON.parse(request.JSON.toString())
        def responseData = diaroboApiService.setFeedback(json)
        render responseData as JSON
    }

    def updateReminderComplain() {
        JSONObject json = JSON.parse(request.JSON.toString())
        def responseData = diaroboApiService.updateReminderComplain(json)
        render responseData as JSON
    }
}
