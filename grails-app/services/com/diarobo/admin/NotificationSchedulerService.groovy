package com.diarobo.admin

import com.diarobo.CommonUtils
import com.diarobo.User
import grails.transaction.Transactional
import groovy.sql.Sql
import org.joda.time.DateTime

@Transactional
class NotificationSchedulerService {

    def customQueryNotificationList(Integer max, Integer offset) {
        def reminderList = Reminder.executeQuery('from Reminder', [ max: max, offset: offset])
        reminderList
    }

    def customQueryNotificationList(Integer max, Integer offset, def dateTime) {
        def reminderList = Reminder.executeQuery('from Reminder r where r.dateTime >= ?', [dateTime?.toDate()],[ max: max, offset: offset])
        reminderList
    }

    def customQueryNotificationList(Integer max, Integer offset, def dateTime, def type) {
        def reminderList = Reminder.executeQuery('from Reminder r where r.dateTime>=? and libraryType=?', [dateTime?.toDate(), type],[ max: max, offset: offset])
        reminderList
    }

    def customQueryNotificationList(Integer max, Integer offset, def dateTime, def type, User patient) {
        def reminderList = Reminder.executeQuery('from Reminder r where r.dateTime>=? and libraryType=? and patient=?', [dateTime?.toDate(), type, patient],[ max: max, offset: offset])
        reminderList
    }

    def list(Integer max, Integer offset, User patient, def dateTime, String type) {
        def reminderList
        def respondData
        if(dateTime && type && patient) {
            reminderList = customQueryNotificationList(max, offset, dateTime, type, patient)
        } else if(dateTime && type) {
            reminderList = customQueryNotificationList(max, offset, dateTime, type)
        } else if(dateTime) {
            reminderList = customQueryNotificationList(max, offset, dateTime)
        } else {
            reminderList = customQueryNotificationList(max, offset)
        }


        /*List reminderList = Reminder.createCriteria().list({
            if(patient) {
                eq("patient", patient)
            }
            if(type) {
                eq("libraryType", type)
            }
            if(dateTime) {
                ge("dateTime", dateTime.toDate())
            }
            maxResults(max)
            firstResult(offset)
            order('dateTime', 'desc')
        })*/
        List reminderListTotal = Reminder.createCriteria().list({
            order('dateTime', 'desc')
            if(patient) {
                eq("patient", patient)
            }
            if(type) {
                eq("libraryType", type)
            }
            if(dateTime) {
                ge("dateTime", dateTime.toDate())
            }
            order('dateTime', 'desc')
        })
        respondData = [reminderList: reminderList, totalCount: reminderListTotal.size()]
        respondData
    }
}
