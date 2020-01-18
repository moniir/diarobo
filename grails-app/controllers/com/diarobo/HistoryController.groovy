package com.diarobo

import com.diarobo.enums.DiseaseCategory
import com.diarobo.enums.DiseaseType
import com.diarobo.enums.GroupItems
import com.diarobo.enums.MasterKeyValue
import grails.converters.JSON
import grails.plugin.springsecurity.annotation.Secured
import org.grails.web.json.JSONArray
import org.grails.web.json.JSONObject

@Secured([CommonUtils.ROLE_PATIENT,CommonUtils.ROLE_CAREGIVER,CommonUtils.ROLE_DOCTOR])
class HistoryController {

    def springSecurityService
    def masterKeyService
    def groupItemService
    def historyService

    def index() {
        redirect(action:'patient')
    }

    def diabetic() {
        def diabeticAssociatedDisease = null
        User currentUser = springSecurityService.currentUser
        def familyRelation = masterKeyService.keyDropDownList(MasterKeyValue.FAMILY_RELATION.value)
        def microvascularDisease = groupItemService.groupListByItemTypeAndName(GroupItems.DISEASE.value, 'Microvascular')
        def macrovascularDisease = groupItemService.groupListByItemTypeAndName(GroupItems.DISEASE.value, 'Macrovascular')
        DiabeticHistory diabeticHistory = DiabeticHistory.findByUser(currentUser)
        if(diabeticHistory) {
            diabeticAssociatedDisease = PatientDisease.findAllByDiabeticHistoryIdAndGrandParentTypeAndActiveStatus(diabeticHistory.id, DiseaseCategory.DIABETIC_ASSOCIATED.value, true)
        }
        def familyHistoryList = FamilyHistory.findAllByUserAndActiveStatus(currentUser, true)
        List familyHistoryReturnList = new ArrayList()
        for (obj in familyRelation) {
            boolean previousPresence = false
            for(family in familyHistoryList) {
                if(obj.id == family.name) {
                    previousPresence = true
                }
            }
            familyHistoryReturnList.add([id: obj.id, name: obj.name, previousPresence: previousPresence])
        }
        render(view: 'diabetic', model: [familyRelation: familyHistoryReturnList, microvascularDisease: microvascularDisease,
                                         macrovascularDisease: macrovascularDisease, diabeticHistory: diabeticHistory, diabeticAssociatedDisease: diabeticAssociatedDisease])
    }

    def saveDiabetic() {
        JSONObject dataJson = JSON.parse(params.data)
        User currentUser = springSecurityService.currentUser
        def familyHistoryJson = dataJson.familyHistory
        historyService.saveFamilyHistory(familyHistoryJson,currentUser)
        DiabeticHistory diabeticHistory = DiabeticHistory.findByUser(currentUser)
        if(!diabeticHistory) {
            diabeticHistory = new DiabeticHistory(user: currentUser)
            diabeticHistory.save(flush: true)
        }
        def diabeticHistoryList = dataJson.diabeticHistory
        historyService.saveDeletePatientDiseases(diabeticHistory, diabeticHistoryList)

        def diabeticTye = dataJson.diabeticTye
        if(diabeticTye) {
            diabeticHistory.diabeticType = diabeticTye
        }
        diabeticHistory.save(flush: true)
        redirect(action: 'diabetic')
    }


    def drugHistory() {
        User currentUser = springSecurityService.currentUser
        def drugTypes = masterKeyService.keyDropDownList(MasterKeyValue.DRUG_TYPE.value)
        def drugList = DrugHistory.findAllByUserAndActiveStatus(currentUser, true)
        render(view: 'drugHistory', model: [drugTypes: drugTypes, drugList: drugList])
    }

    def saveDrugHistory() {
        JSONObject drugJson = JSON.parse(request.JSON.toString())
        String message = 'Not Successfully Saved. Try Again.';
        if(drugJson) {
            User currentUser = springSecurityService.currentUser
            JSONArray drugList = new JSONArray(drugJson.drugList)
            message = historyService.saveDrugHistory(drugList, currentUser)
        }
        def responseData = ['hasError': false, 'message': message]
        render responseData as JSON
    }

    def surgeryHistory() {
        User currentUser = springSecurityService.currentUser
        def surgeryList = SurgeryHistory.findAllByUserAndActiveStatus(currentUser, true)
        render(view: 'surgeryHistory', model: [surgeryList: surgeryList])
    }

    def saveSurgeryHistory() {
        JSONObject drugJson = JSON.parse(request.JSON.toString())
        String message = 'Not Successfully Saved. Try Again.';
        if(drugJson) {
            User currentUser = springSecurityService.currentUser
            JSONArray surgeryHistoryList = new JSONArray(drugJson.surgeryHistoryList)
            message = historyService.saveSurgeryHistory(surgeryHistoryList, currentUser)
        }
        def responseData = ['hasError': false, 'message': message]
        render responseData as JSON
    }

    def socialFinancialStatus() {
        User currentUser = springSecurityService.currentUser
        PatientProfile patientProfile = PatientProfile.findByUser(currentUser)
        render(view: 'socialFinancialStatus', model: [socialFinancialStatus: patientProfile.socialFinancialStatus])
    }

    def saveSocialFinancialStatus() {
        Integer socialFinancial = params.getInt('socialFinancial')
        if(socialFinancial) {
            User currentUser = springSecurityService.currentUser
            PatientProfile patientProfile = PatientProfile.findByUser(currentUser)
            patientProfile.socialFinancialStatus = socialFinancial
            patientProfile.save(flush: true)
        }
        redirect(action: 'socialFinancialStatus')
    }

    def professionalHistory() {
        User currentUser = springSecurityService.currentUser
        def professionalHistoryList = ProfessionalHistory.findAllByUserAndActiveStatus(currentUser, true)
        render(view: 'professionalHistory', model: [professionalHistoryList: professionalHistoryList])
    }

    def saveProfessionalHistory() {
        JSONObject drugJson = JSON.parse(request.JSON.toString())
        String message = 'Not Successfully Saved. Try Again.';
        if(drugJson) {
            User currentUser = springSecurityService.currentUser
            JSONArray professionList = new JSONArray(drugJson.professionList)
            message = historyService.saveProfessionalHistory(professionList, currentUser)
        }
        def responseData = ['hasError': false, 'message': message]
        render responseData as JSON
    }

    def educationalStatus() {
        User currentUser = springSecurityService.currentUser
        PatientProfile patientProfile = PatientProfile.findByUser(currentUser)
        def educationList = masterKeyService.keyDropDownList(MasterKeyValue.EDUCATION.value)
        render(view: 'educationalStatus', model: [highestEducation: patientProfile.highestEducation, educationList: educationList])
    }

    def saveEducationalStatus() {
        String highestEducation = params.highestEducation
        if(highestEducation) {
            User currentUser = springSecurityService.currentUser
            PatientProfile patientProfile = PatientProfile.findByUser(currentUser)
            patientProfile.highestEducation = highestEducation
            PatientProfile savedProfile = patientProfile.save(flash: true)
        }
        redirect(action: 'educationalStatus')
    }

    def pastIllnessHistory() {
        User currentUser = springSecurityService.currentUser
        def pastIllnessList = PastIllness.findAllByUserAndActiveStatus(currentUser, true)
        render(view: 'pastIllnessHistory', model: [pastIllnessList: pastIllnessList])
    }

    def savePastIllness() {
        JSONObject drugJson = JSON.parse(request.JSON.toString())
        String message = 'Not Successfully Saved. Try Again.';
        if(drugJson) {
            User currentUser = springSecurityService.currentUser
            JSONArray illnessList = new JSONArray(drugJson.illnessList)
            message = historyService.savePastIllnessHistory(illnessList, currentUser)
        }
        def responseData = ['hasError': false, 'message': message]
        render responseData as JSON
    }

    def status() {
        render(view: 'status')
    }

    def searchDiseasesByKeyword() {
        def output = groupItemService.searchNameByKeyword(params.q, GroupItems.DISEASE.value)
        render output
    }

    def lifestyleHistory(){

    }
}
