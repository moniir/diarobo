package com.diarobo.admin

import com.diarobo.CommonUtils
import com.diarobo.MasterKeySetup
import com.diarobo.User
import com.diarobo.enums.MasterKeyValue
import grails.converters.JSON
import grails.plugin.springsecurity.annotation.Secured
import org.grails.web.json.JSONObject


@Secured(CommonUtils.ROLE_ADMIN)
class PrescriptionController {
    def prescriptionService
    def masterKeyService
    def springSecurityService
    def pictureService
    def exerciseService
    def foodPackageService
    def exercisePackageService
    def index() {
        User currentUser = springSecurityService.currentUser
        def drugTypes = masterKeyService.keyDropDownList(MasterKeyValue.DRUG_TYPE.value)
        def drugWeightList = masterKeyService.keyDropDownList(MasterKeyValue.DRUG_WEIGHT.value)
        def drugTimeList = masterKeyService.keyDropDownList(MasterKeyValue.DRUG_TIME.value)
        def foodPackageList = prescriptionService.getFoodPackageList()
        def exerciseList = exerciseService.getExercisePackageList()
        ArrayList<String> medicationTryTimes = new ArrayList<String>()
        medicationTryTimes.add(MasterKeySetup.findByKeyType(MasterKeyValue.BREAKFAST.value).keyValue)
        medicationTryTimes.add(MasterKeySetup.findByKeyType(MasterKeyValue.LUNCH.value).keyValue)
        medicationTryTimes.add(MasterKeySetup.findByKeyType(MasterKeyValue.DINNER.value).keyValue)
        ArrayList<String> mealSnackTimes = new ArrayList<String>()
        mealSnackTimes.add(MasterKeySetup.findByKeyType(MasterKeyValue.SNACK_BEFORE_LUNCH.value).keyValue)
        mealSnackTimes.add(MasterKeySetup.findByKeyType(MasterKeyValue.SNACK_BEFORE_DINNER.value).keyValue)
        mealSnackTimes.add(MasterKeySetup.findByKeyType(MasterKeyValue.SNACK_BEFORE_SLEEP.value).keyValue)
        ArrayList<String> medicationFourTimes = new ArrayList<String>()
        medicationFourTimes.add(MasterKeySetup.findByKeyType(MasterKeyValue.MEDICINE_41.value).keyValue)
        medicationFourTimes.add(MasterKeySetup.findByKeyType(MasterKeyValue.MEDICINE_42.value).keyValue)
        medicationFourTimes.add(MasterKeySetup.findByKeyType(MasterKeyValue.MEDICINE_43.value).keyValue)
        medicationFourTimes.add(MasterKeySetup.findByKeyType(MasterKeyValue.MEDICINE_44.value).keyValue)
        ArrayList<String> medicationFiveTimes = new ArrayList<String>()
        medicationFiveTimes.add(MasterKeySetup.findByKeyType(MasterKeyValue.MEDICINE_51.value).keyValue)
        medicationFiveTimes.add(MasterKeySetup.findByKeyType(MasterKeyValue.MEDICINE_52.value).keyValue)
        medicationFiveTimes.add(MasterKeySetup.findByKeyType(MasterKeyValue.MEDICINE_53.value).keyValue)
        medicationFiveTimes.add(MasterKeySetup.findByKeyType(MasterKeyValue.MEDICINE_54.value).keyValue)
        medicationFiveTimes.add(MasterKeySetup.findByKeyType(MasterKeyValue.MEDICINE_55.value).keyValue)
        ArrayList<String> exerciseTimes = new ArrayList<String>()
        exerciseTimes.add(MasterKeySetup.findByKeyType(MasterKeyValue.EXERCISE_MORNING.value).keyValue)
        exerciseTimes.add(MasterKeySetup.findByKeyType(MasterKeyValue.EXERCISE_AFTERNOON.value).keyValue)
        render(view: 'index', model: [drugTypes: drugTypes, drugWeightList: drugWeightList, drugTimeList: drugTimeList,
                                      foodPackageList: foodPackageList, exerciseList: exerciseList, medicationTryTimes: medicationTryTimes,
                                      mealSnackTimes: mealSnackTimes, medicationFourTimes: medicationFourTimes, medicationFiveTimes: medicationFiveTimes,
                                      exerciseTimes: exerciseTimes])
    }

    def searchPatientByNameOrPhoneNumber() {
       def output = prescriptionService.patientNameByKeyword(params.q)
        render output
    }

    def saveReferTo() {
        JSONObject json = JSON.parse(request.JSON.toString())
        def responseData = prescriptionService.saveReferTo(json)
        render responseData as JSON
    }

    def getPatientDetails() {
        JSONObject patient = JSON.parse(request.JSON.toString())
        def responseData = prescriptionService.getPatientDetails(patient)
        render responseData as JSON
    }

    def showProfileImage() {
        Integer patientProfileId = params.getInt('patientProfileId')
        if ( patientProfileId == null) {
            flash.message = "Profile Picture not found."
            redirect (action:'index')
        } else {
            byte[] buffer = pictureService.getPictureInByte(patientProfileId, 'profile_picture', response)
            if(buffer){
                response.outputStream << buffer
                response.outputStream.flush()
            }  else {
                flash.message = "Profile Picture not found."
                redirect (action:'index')
            }
        }
    }

    def saveMedication() {
        JSONObject json = JSON.parse(request.JSON.toString())
        def responseData = prescriptionService.saveMedication(json)
        render responseData as JSON
    }

    def saveClinicalCondition() {
        JSONObject json = JSON.parse(request.JSON.toString())
        def responseData = prescriptionService.saveClinicalCondition(json)
        render responseData as JSON
    }

    def searchDoctorByName() {
        def output = prescriptionService.searchDoctorByName(params.q)
        render output
    }

    def searchSpecialist() {
        def output = prescriptionService.searchSpecialist(params.q)
        render output
    }

    def saveBasicInformation() {
        JSONObject basicInfoJson = JSON.parse(request.JSON.toString())
        def responseData = prescriptionService.saveBasicInformation(basicInfoJson)
        render responseData as JSON
    }

    def saveRecommend() {
        JSONObject recommendJson = JSON.parse(request.JSON.toString())
        def responseData = prescriptionService.saveRecommend(recommendJson)
        render responseData as JSON
    }

    def searchMedicineName() {
        def outPut = prescriptionService.searchMedicineName(params.q)
        render outPut
    }

    def setActivePrescription(Integer prescriptionId, String patientUserName) {
        def responseData = prescriptionService.setActivePrescription(prescriptionId, patientUserName)
        render responseData as JSON
    }

    def copyPrescription(Integer prescriptionId) {
        def responseData = prescriptionService.copyPrescription(prescriptionId)
        render responseData as JSON
    }

    def getDetailsPackage(Integer packageId) {
        def responseData = foodPackageService.getFoodListByPackage(packageId)
        render(template: 'foodPackageDetails', model: [foodList: responseData.foodList, foodPackageName: responseData.foodPackageName])
    }

    def getExercisePackageDetails(Integer packageId) {
        def responseData = exercisePackageService.getExerciseListByPackage(packageId)
        render(template: 'exercisePackageDetails', model: [exerciseList: responseData.exerciseList, exercisePackageName: responseData.exercisePackageName])
    }
}
