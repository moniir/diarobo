package com.diarobo

import com.diarobo.commands.PatientProfileCommand
import grails.converters.JSON
import grails.plugin.springsecurity.SpringSecurityUtils
import grails.plugin.springsecurity.annotation.Secured

@Secured([CommonUtils.ROLE_PATIENT, CommonUtils.ROLE_CAREGIVER, CommonUtils.ROLE_DOCTOR])
class ProfileController {
    def pictureService
    def springSecurityService

    def index(PatientProfile patientProfileInstance) {
        User currentUser = springSecurityService.currentUser
        if(patientProfileInstance == null) {
            patientProfileInstance = PatientProfile.findByUser(currentUser)

        }
        Picture picture = Picture.findByEntityIdAndEntityName(patientProfileInstance?.id, 'profile_picture')

        String viewName = "index"
        if (SpringSecurityUtils.ifAnyGranted(CommonUtils.ROLE_DOCTOR)){
            viewName ="doctor"
            render(view: viewName)
            return
        } else if(SpringSecurityUtils.ifAnyGranted(CommonUtils.ROLE_CAREGIVER)){
            viewName ="caregiver"
            render(view: viewName)
            return
        } else {
            render(view: viewName, model: [patientProfileInstance: patientProfileInstance, hasPicture: (picture ? true : false)])
        }
    }

    def saveProfile(PatientProfile patientProfileInstance) {
        if (patientProfileInstance == null) {
            redirect(action: 'index')
            return
        } else if (patientProfileInstance.hasErrors()) {
            redirect(action: 'index')
            return
        }
        patientProfileInstance.save(flush: true)
        def file = request.getFile('profilePicture')
        if(!file.empty) {
            if(pictureService.uploadUnSucessful(patientProfileInstance.id, 'profile_picture', 'profile',file)) {
                redirect(action: 'index')
                return
            }
        }
        redirect(action: 'index')
    }

    def showProfileImage(PatientProfile patientProfileInstance) {
        if ( patientProfileInstance == null) {
            flash.message = "Profile Picture not found."
            redirect (action:'index')
        } else {
            byte[] buffer = pictureService.getPictureInByte(patientProfileInstance.id, 'profile_picture', response)
            if(buffer){
                response.outputStream << buffer
                response.outputStream.flush()
            }  else {
                flash.message = "Profile Picture not found."
                redirect (action:'index')
            }
        }
    }
}
