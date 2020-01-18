package com.diarobo

import grails.plugin.springsecurity.annotation.Secured

@Secured([CommonUtils.ROLE_ADMIN, CommonUtils.ROLE_PATIENT, CommonUtils.ROLE_CAREGIVER, CommonUtils.ROLE_DOCTOR])
class DashboardController {

    def index() {
        List<Map> patientList = new ArrayList<Map>()
        def patientProfileList = PatientProfile.list()
        def patient
        for(PatientProfile profile: patientProfileList) {
            Picture picture = Picture.findByEntityIdAndEntityName(profile?.id, 'profile_picture')
            patient = [phoneNumber: profile.user.username,
                       fullName: profile.user.fullName, dateOfBirth: profile.dateOfBirth]
            patientList.add(patient)
        }
        render(view: 'patientList', model: [patientList: patientList])
    }
}
