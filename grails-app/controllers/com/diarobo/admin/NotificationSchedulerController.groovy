package com.diarobo.admin

import com.diarobo.CommonUtils
import com.diarobo.User
import grails.plugin.springsecurity.annotation.Secured
import org.joda.time.DateTime

/**
 * Created by rakib on 3/21/2017.
 */
@Secured(CommonUtils.ROLE_ADMIN)
class NotificationSchedulerController {
    def notificationSchedulerService
    def exerciseService
    def prescriptionService
    def index() {
        Integer maxSize = 200
        Integer max = params.getInt('max')
        Integer offset = params.getInt('offset')
        max = Math.min(max ?: 20, 100)
        def reminderCount = Reminder.count()
        reminderCount = reminderCount < maxSize ? reminderCount : maxSize
        if(offset) {
            if(offset > maxSize){
                offset = maxSize
            }
        } else {
            offset = 0
        }
        DateTime dateTime
        if(params.date) {
            dateTime = CommonUtils.getDateFromString(params.date)
        }
        User patient = User.findByUsername(params.patientUserName)
        println(params)
        def respondData = notificationSchedulerService.list(max, offset, patient, dateTime, params.type)
        if(respondData) {
            reminderCount = respondData.totalCount
        }
        String patientUserNameMobile
        if(patient) {
            patientUserNameMobile = patient?.fullName + ' - ' + patient?.username
        }
        def exercisePackageList = exerciseService.getExercisePackageList()
        def foodPackageList = prescriptionService.getFoodPackageList()
        render(view: 'index', model: [foodPackageList: foodPackageList, reminderList: respondData?.reminderList, type: params.type, selectedDate: dateTime?.toDate(), patientUserName: patient?.username, patientUserNameMobile: patientUserNameMobile, reminderCount: reminderCount])
    }
}
