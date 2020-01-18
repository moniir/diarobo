package com.diarobo

import com.diarobo.admin.ExerciseLibrary
import com.diarobo.admin.FoodLibrary
import com.diarobo.admin.MedBrand
import com.diarobo.admin.Reminder
import com.diarobo.enums.LibraryType
import com.diarobo.enums.SyncStatus
import grails.transaction.Transactional
import org.grails.web.json.JSONArray
import org.grails.web.json.JSONObject


@Transactional
class DiaroboApiService {

    def pictureService
    def commonService

    def getReminderList(User patient, def g) {
        def reminderList = Reminder.findAllBySyncStatusNotEqualAndPatientAndDateTimeGreaterThan(SyncStatus.SYNCED, patient, new Date())
        ArrayList<Map> reminderArrayList = new ArrayList<Map>()
        def eachReminder
        String itemName
        Long itemId
        String itemMeasureUnit
        String imageUrl
        for(Reminder reminder : reminderList) {
            if(reminder.libraryType == 'food_library') {
                FoodLibrary foodLibrary = FoodLibrary.findById(reminder.libraryId)
                imageUrl = g.createLink(controller: "diaroboApi", action:"showImage", params: [libraryId: foodLibrary.id, libraryType: LibraryType.FOOD_LIBRARY.value])
                itemName = foodLibrary.name
                itemId = foodLibrary.id
                itemMeasureUnit = foodLibrary.measureUnit
            } else if (reminder.libraryType == 'exercise_library'){
                ExerciseLibrary exerciseLibrary = ExerciseLibrary.findById(reminder.libraryId)
                imageUrl = g.createLink(controller: "diaroboApi", action:"showImage", params: [libraryId: exerciseLibrary.id, libraryType: LibraryType.EXERCISE_LIBRARY.value])
                itemName = exerciseLibrary.name
                itemId = exerciseLibrary.id
                itemMeasureUnit = exerciseLibrary.measureUnit
            }else if (reminder.libraryType == 'medicine_library'){
                MedBrand medBrand = MedBrand.findById(reminder.libraryId)
                imageUrl = g.createLink(controller: "diaroboApi", action:"showImage", params: [libraryId: medBrand.id, libraryType: LibraryType.MEDICINE_LIBRARY.value])
                itemName = medBrand.brandName
                itemId = medBrand.id
                itemMeasureUnit = 'piece'
            }
            eachReminder = [imageUrl: imageUrl, reminderId: reminder.id, syncStatus: reminder.syncStatus.name(), patientUsername: patient.username, hasSound: false, hasImage: false, message: reminder.message,
                            options: reminder.options, type: reminder.libraryType, dateTime: reminder.dateTime.format(CommonUtils.DATE_FORMAT_FULL), question: reminder.question,
                            srcUriSound: '', srcUrlSound: '', srcUriImage: '', itemName: itemName, itemId: itemId, itemValue: reminder.quantity, itemMeasureUnit: itemMeasureUnit]
            reminderArrayList.add(eachReminder)
            reminder.syncStatus = SyncStatus.SYNCED
        }
        reminderArrayList
    }

    def setFeedback(JSONObject json) {
        def responseData
        if(json) {
            User user = User.findByUsername(commonService.getJsonValue(json, 'username'))
            if(user) {
                Map reminderJsonList = new HashMap(commonService.getJsonValue(json, 'reminderList'))
                for (Map.Entry<Object, Object> e : reminderJsonList.entrySet()) {
                    Integer reminderId = e.getValue()
                    Reminder reminder = Reminder.get(reminderId)
                    reminder.yesFeedback = true
                    reminder.save(flush: true)
                }
                responseData = ['message': 'Feedback submitted', 'hasError': false]
            } else {
                responseData = ['message': 'User not found', 'hasError': true]
            }
        } else {
            responseData = ['message': 'Server Error', 'hasError': true]
        }
        responseData
    }

    def updateReminderComplain(JSONObject json) {
        def responseData
        if(json) {
            User user = User.findByUsername(commonService.getJsonValue(json, 'username'))
            if(user) {
                Map reminderJsonList = new HashMap(commonService.getJsonValue(json, 'reminderList'))
                for (Map.Entry<Object, Object> e : reminderJsonList.entrySet()) {
                    Integer reminderId = e.getValue()
                    Reminder reminder = Reminder.get(reminderId)
                    reminder.needToComplain = true
                    reminder.save(flush: true)
                }
                responseData = ['message': 'complain submitted', 'hasError': false]
            } else {
                responseData = ['message': 'User not found', 'hasError': true]
            }
        } else {
            responseData = ['message': 'Server Error', 'hasError': true]
        }
        responseData
    }
}
