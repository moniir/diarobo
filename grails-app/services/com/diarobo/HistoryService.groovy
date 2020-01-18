package com.diarobo

import grails.transaction.Transactional
import org.grails.web.json.JSONArray
import org.grails.web.json.JSONObject
@Transactional
class HistoryService {
    def springSecurityService

    def saveDeletePatientDiseases(def diabeticHistory, def diabeticHistoryList) {
        def patientDiseases = PatientDisease.findAllByDiabeticHistoryId(diabeticHistory.id)
        diabeticHistoryList.each {
            boolean presentedPreviously = false
            def diabeticDetails = it.value
            Long diseaseId = Long.parseLong(diabeticDetails.diseaseId)
            for(PatientDisease patientDiseaseEach : patientDiseases) {
                if(patientDiseaseEach.diseaseId == diseaseId && patientDiseaseEach.parentType == diabeticDetails.parentType && patientDiseaseEach.grandParentType == diabeticDetails.grandParentType) {
                    presentedPreviously = true
                    if(patientDiseaseEach.activeStatus == false) {
                        patientDiseaseEach.activeStatus = true
                        patientDiseaseEach.save(flush: true)
                    }
                }
            }
            if(!presentedPreviously) {
                PatientDisease patientDisease = new PatientDisease(diseaseId: diseaseId, diabeticHistoryId: diabeticHistory.id, parentType: diabeticDetails.parentType, grandParentType: diabeticDetails.grandParentType).save(flush: true)
            }
        }
        for(PatientDisease patientDiseaseEach : patientDiseases) {
            boolean needTobeDeleted = true
            diabeticHistoryList.each {
                def diabeticDetails = it.value
                Long diseaseId = Long.parseLong(diabeticDetails.diseaseId)
                if(patientDiseaseEach.diseaseId == diseaseId && patientDiseaseEach.parentType == diabeticDetails.parentType && patientDiseaseEach.grandParentType == diabeticDetails.grandParentType) {
                    needTobeDeleted = false
                }
            }
            if(needTobeDeleted) {
                patientDiseaseEach.activeStatus = false
                patientDiseaseEach.save(flush: true)
            }
        }
    }


    def saveFamilyHistory(def familyHistoryJson, User currentUser) {
        familyHistoryJson.each {
            FamilyHistory familyHistory = FamilyHistory.findByUserAndName(currentUser, it.key)
            if(familyHistory) {
                familyHistory.activeStatus = true
            } else {
                familyHistory = new FamilyHistory(user: currentUser, name: it.key)
            }
            familyHistory.save(flush: true)
        }
        def familyHistoryList = FamilyHistory.findAllByUser(currentUser)
        for(FamilyHistory familyHistory: familyHistoryList) {
            boolean tobeDeleted = true
            familyHistoryJson.each {
                if(familyHistory.name.toString() == it.key.toString()) {
                    tobeDeleted = false
                }
            }
            if(tobeDeleted) {
                familyHistory.activeStatus = false
                familyHistory.save(flush: true)
            }
        }
    }

    def saveDrugHistory(JSONArray drugList, User currentUser) {
        if(drugList) {
            for (JSONObject drug : drugList) {
                DrugHistory drugHistory = DrugHistory.findByUserAndDrugNameAndDrugTypeAndDrugTakenFromAndDrugTakenTo(currentUser, drug.name, drug.drugType, drug.drugTakenFrom, drug.drugTakenTo)
                if(drugHistory) {
                    drugHistory.activeStatus = true
                    drugHistory.drugFrequency = drug.drugFrequency
                    drugHistory.save(flush: true)
                } else {
                    drugHistory = new DrugHistory(user: currentUser, drugType: drug.drugType, drugName: drug.name, drugTakenFrom: drug.drugTakenFrom, drugTakenTo: drug.drugTakenTo, drugFrequency: drug.drugFrequency)
                    drugHistory.save(flush: true)
                }
            }
            def drugHistoryList = DrugHistory.findAllByUser(currentUser)
            for (DrugHistory drugHistory : drugHistoryList) {
                boolean needTobeDeleted = true
                for (JSONObject drug : drugList) {
                    if(drug.name == drugHistory.drugName && drugHistory.drugType == drug.drugType && drugHistory.drugTakenFrom == drug.drugTakenFrom && drugHistory.drugTakenTo == drug.drugTakenTo && drugHistory.drugFrequency == drug.drugFrequency) {
                        needTobeDeleted = false
                    }
                }
                if(needTobeDeleted) {
                    drugHistory.activeStatus = false
                    drugHistory.save(flush: true)
                }
            }
            return 'Successfully Saved'
        }
        return 'Not Successfully Saved. Try Again.'
    }

    def saveProfessionalHistory(JSONArray professionList, User currentUser) {
        if(professionList) {
            for (JSONObject profession : professionList) {
                ProfessionalHistory professionalHistory = ProfessionalHistory.findByUserAndId(currentUser, profession.id)
                if(professionalHistory) {
                    professionalHistory.activeStatus = true
                    professionalHistory.jobIndustry = profession.jobIndustry
                    professionalHistory.jobNature = profession.jobNature
                    professionalHistory.fromDate = profession.fromDate
                    professionalHistory.toDate = profession.toDate
                    professionalHistory.currentJob = profession.currentJob
                    professionalHistory.save(flush: true)
                } else {
                    professionalHistory = new ProfessionalHistory(user: currentUser, jobIndustry: profession.jobIndustry, jobNature: profession.jobNature, fromDate: profession.fromDate, toDate: profession.toDate, currentJob: profession.currentJob)
                    professionalHistory.save(flush: true)
                }
            }
            def professionalList = ProfessionalHistory.findAllByUser(currentUser)
            for (ProfessionalHistory professionalHistory : professionalList) {
                boolean needTobeDeleted = true
                for (JSONObject profession : professionList) {
                    if(profession.jobIndustry == professionalHistory.jobIndustry && profession.jobNature == professionalHistory.jobNature && profession.fromDate == professionalHistory.fromDate && profession.toDate == professionalHistory.toDate && profession.currentJob == professionalHistory.currentJob) {
                        needTobeDeleted = false
                    }
                }
                if(needTobeDeleted) {
                    professionalHistory.activeStatus = false
                    professionalHistory.save(flush: true)
                }
            }
            return 'Successfully Saved'
        }
        return 'Not Successfully Saved. Try Again.'
    }

    def savePastIllnessHistory(JSONArray illnessList, User currentUser) {
        if(illnessList) {
            for (JSONObject illness : illnessList) {
                PastIllness pastIllness = PastIllness.findByUserAndDrugName(currentUser, illness.drugName)
                if(pastIllness) {
                    pastIllness.activeStatus = true
                    pastIllness.drugName = illness.drugName
                } else {
                    if(illness.id) {
                        pastIllness = PastIllness.findById(illness.id)
                        pastIllness.activeStatus = true
                        pastIllness.drugName = illness.drugName
                    } else {
                        pastIllness = new PastIllness(user: currentUser, drugName: illness.drugName)
                    }
                }
                pastIllness.save(flush: true)
            }
            def pastIllnessList = PastIllness.findAllByUser(currentUser)
            for (PastIllness pastIllness: pastIllnessList) {
                boolean needTobeDeleted = true
                for (JSONObject illness : illnessList) {
                    if(pastIllness.drugName == illness.drugName) {
                        needTobeDeleted = false
                    }
                }
                if (needTobeDeleted) {
                    pastIllness.activeStatus = false
                    pastIllness.save(flush: true)
                }
            }
            return 'Successfully Saved'
        }
        return 'Not Successfully Saved. Try Again.'
    }

    def saveSurgeryHistory(JSONArray surgeryHistoryList, User currentUser) {
        if(surgeryHistoryList) {
            for (JSONObject surgery : surgeryHistoryList) {
                SurgeryHistory surgeryHistory = SurgeryHistory.findByUserAndDescription(currentUser, surgery.description)
                if(surgeryHistory) {
                    surgeryHistory.activeStatus = true
                    surgeryHistory.description = surgery.description
                } else {
                    if(surgery.id) {
                        surgeryHistory = SurgeryHistory.findById(surgery.id)
                        surgeryHistory.activeStatus = true
                        surgeryHistory.description = surgery.description
                    } else {
                        surgeryHistory = new SurgeryHistory(user: currentUser, description: surgery.description)
                    }
                }
                surgeryHistory.save(flush: true)
            }
            def surgeryHisList = SurgeryHistory.findAllByUser(currentUser)
            for (SurgeryHistory surgery: surgeryHisList) {
                boolean needTobeDeleted = true
                for (JSONObject surgeryEach : surgeryHistoryList) {
                    if(surgery.description == surgeryEach.description) {
                        needTobeDeleted = false
                    }
                }
                if (needTobeDeleted) {
                    surgery.activeStatus = false
                    surgery.save(flush: true)
                }
            }
            return 'Successfully Saved'
        }
        return 'Not Successfully Saved. Try Again.'
    }
}
