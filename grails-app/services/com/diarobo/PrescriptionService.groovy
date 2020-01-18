package com.diarobo

import com.diarobo.admin.ClinicalCondition
import com.diarobo.admin.Doctor
import com.diarobo.admin.ExerciseLibrary
import com.diarobo.admin.ExercisePackage
import com.diarobo.admin.ExercisePackageElement
import com.diarobo.admin.FoodLibrary
import com.diarobo.admin.FoodPackage
import com.diarobo.admin.FoodPackageElement
import com.diarobo.admin.GeneralRule
import com.diarobo.admin.GeneralRuleExercisePlan
import com.diarobo.admin.GeneralRuleMealPlan
import com.diarobo.admin.MedBrand
import com.diarobo.admin.Medication
import com.diarobo.admin.Prescription
import com.diarobo.admin.RecommendExercise
import com.diarobo.admin.RecommendMeal
import com.diarobo.admin.ReferTo
import com.diarobo.admin.Reminder
import com.diarobo.admin.Specialist
import com.diarobo.enums.ActiveStatus
import com.diarobo.enums.SyncStatus
import grails.converters.JSON
import grails.transaction.Transactional
import org.grails.web.json.JSONArray
import org.grails.web.json.JSONObject
import org.joda.time.DateTime
import org.joda.time.Period
import org.joda.time.format.DateTimeFormat

@Transactional
class PrescriptionService {

    def springSecurityService
    def notificationService

    def patientNameByKeyword(String searchStr) {
        searchStr = "%"+searchStr+"%"
        LinkedHashMap result = new LinkedHashMap()
        List<User> patientList = User.findAllByFullNameIlikeOrUsernameIlike(searchStr, searchStr)
        result.put('isError', Boolean.FALSE)
        result.put('results', patientList)
        String outPut = result as JSON
        return outPut
    }

    def searchDoctorByName(String searchStr) {
        searchStr = "%"+searchStr+"%"
        LinkedHashMap result = new LinkedHashMap()
        List<Doctor> doctorList = Doctor.findAllByNameIlikeOrPhoneNumberIlike(searchStr, searchStr)
        result.put('isError', Boolean.FALSE)
        result.put('results', doctorList)
        String outPut = result as JSON
        return outPut
    }

    def saveBasicInformation(JSONObject basicInfoJson) {
        Doctor doctor = Doctor.findByPhoneNumber(basicInfoJson.doctorPhoneNumber)
        User currentUser = springSecurityService.currentUser
        def responseData
        if(!doctor) {
            doctor = new Doctor(name: basicInfoJson.doctorName, phoneNumber: basicInfoJson.doctorPhoneNumber,
                designation: basicInfoJson.doctorDesignation, hospitalName: basicInfoJson.doctorHospitalChamber, createdBy: currentUser.username)
            def savedDoctor = doctor.save(flush: true)
            if (!savedDoctor) {
                responseData = ['hasError': true, 'message': 'ERROR']
                return responseData
            }
        }
        Prescription prescription
        if(!basicInfoJson.prescriptionId) {
            User patient = User.findByUsername(basicInfoJson.patientUsername)
            if(patient && doctor) {
                prescription = createPrescription(patient, doctor, DateTime.parse(basicInfoJson.prescriptionDate))
            }
        } else {
            prescription = Prescription.findById(basicInfoJson.prescriptionId)
        }
        responseData = ['hasError': false, 'message': 'SUCCESS', doctorId: doctor.id, prescriptionId: prescription.id,
                            prescriptionDate: prescription.createdDate.format('dd/MM/YYYY')]   //@TODO need to replace lastUpdatedDate
        responseData
    }

    def saveClinicalCondition(JSONObject json){
        def responseData
        if(json) {
            if(json.doctorId && json.patientUsername && json.prescriptionDate) {
                User currentUser = springSecurityService.currentUser
                DateTime prescriptionDate = DateTime.parse(json.prescriptionDate)
                Prescription prescription
                if(!json.prescriptionId) {
                    User patient = User.findByUsername(json.patientUsername)
                    Doctor doctor = Doctor.findById(json.doctorId)
                    if(patient && doctor) {
                        prescription = createPrescription(patient, doctor, prescriptionDate)
                    }
                } else {
                    prescription = Prescription.findById(json.prescriptionId)
                }
                ClinicalCondition clinicalCondition = new ClinicalCondition(beforeBreakFast: json.beforeBreakFast, afterBreakFast: json.afterBreakFast,
                beforeLunch: json.beforeLunch, afterLunch: json.afterLunch, beforeDinar: json.beforeDinar, afterDinar: json.afterDinar,
                randomTime: json.randomTime, ogtt: json.ogtt, hbaic: json.hbaic, sugar: json.sugar, albumin: json.albumin, acetone: json.acetone,
                createdBy: currentUser.username, createdDate: prescriptionDate)
                def savedClinicalCondition = clinicalCondition.save()
                prescription.clinicalCondition = savedClinicalCondition
                prescription.lastUpdatedBy = currentUser.username
                prescription.lastUpdatedDate = new Date()
                prescription.save()
                responseData = ['hasError': false, 'message': 'SUCCESS', prescriptionId: prescription.id]
            } else {
                responseData = ['hasError': true, 'message': 'Please First fill up basic information & try again']
            }
        } else {
            responseData = ['hasError': true, 'message': 'Please First fill up basic information & try again']
        }
        responseData
    }

    def saveMedication(JSONObject json) {
        def responseData
        if(json) {
            if(json.doctorId && json.patientUsername && json.prescriptionDate) {
                User currentUser = springSecurityService.currentUser
                DateTime prescriptionDate = DateTime.parse(json.prescriptionDate, DateTimeFormat.forPattern('dd/MM/YYYY'))
                Prescription prescription
                if(!json.prescriptionId) {
                    User patient = User.findByUsername(json.patientUsername)
                    Doctor doctor = Doctor.findById(json.doctorId)
                    if(patient && doctor) {
                        prescription = createPrescription(patient, doctor, prescriptionDate)
                    }
                } else {
                    prescription = Prescription.findById(json.prescriptionId)
                }
                prescription.lastUpdatedBy = currentUser.username
                prescription.lastUpdatedDate = new Date()
                prescription.save()
                JSONArray drugList = new JSONArray(json.drugList)
                for (JSONObject drug : drugList) {
                    Medication medication
                    if(!drug.id) {
                        medication = new Medication(prescriptionId: prescription.id, type: drug.drugType, name: drug.medicineName,
                                weight: drug.drugWeight, period: drug.period, time: drug.drugTime, quantity1: drug.quantity1, quantity2: drug.quantity2,
                                quantity3: drug.quantity3, quantity4: drug.quantity4, quantity5: drug.quantity5, drugTime1: drug.drugTime1, drugTime2: drug.drugTime2,
                                drugTime3: drug.drugTime3, drugTime4: drug.drugTime4, drugTime5: drug.drugTime5, amBm: drug.medicineAmBm, instruction:  drug.instruction,
                                createdDate: prescriptionDate, createdBy: currentUser.username)
                    } else {
                        medication = Medication.findById(drug.id)
                        medication.prescriptionId = prescription.id
                        medication.type = drug.drugType
                        medication.name = drug.medicineName
                        medication.weight = drug.drugWeight
                        medication.weight = drug.drugWeight
                        medication.period = drug.period
                        medication.time = drug.drugTime
                        medication.quantity1 = drug.quantity1
                        medication.quantity2 = drug.quantity2
                        medication.quantity3 = drug.quantity3
                        medication.quantity4 = drug.quantity4
                        medication.quantity5 = drug.quantity5
                        medication.drugTime1 = drug.drugTime1
                        medication.drugTime2 = drug.drugTime2
                        medication.drugTime3 = drug.drugTime3
                        medication.drugTime4 = drug.drugTime4
                        medication.drugTime5 = drug.drugTime5
                        medication.amBm = drug.medicineAmBm
                        medication.instruction = drug.instruction
                        medication.lastUpdatedDate = new Date()
                        medication.lastUpdatedBy = currentUser.username
                    }
                    medication.save()
                }
                responseData = ['hasError': false, 'message': 'SUCCESS']
            } else {
                responseData = ['hasError': true, 'message': 'Please First fill up basic information & try again']
            }
        } else {
            responseData = ['hasError': true, 'message': 'Please First fill up basic information & try again']
        }
        responseData
    }

    def createPrescription(User patient, Doctor doctor, DateTime prescriptionDate) {
        User currentUser = springSecurityService.currentUser
        Prescription prescription = new Prescription(createdBy: currentUser.username,
                createdDate: prescriptionDate.toDate(), lastUpdatedDate: new Date(), patient: patient, doctor: doctor)
        def savedPrescription = prescription.save()
        savedPrescription
    }

    def getFoodPackageList() {
        def foodPackageList = FoodPackage.findAllByActiveStatus(ActiveStatus.ACTIVE)
        foodPackageList
    }

    def searchSpecialist(String search) {
        search = "%" + search + "%";
        LinkedHashMap result = new LinkedHashMap()
        List<Specialist> specialists = Specialist.findAllByActiveStatusAndNameIlike(ActiveStatus.ACTIVE, search)
        result.put('isError', Boolean.FALSE)
        result.put('results', specialists)
        String outPut = result as JSON
        return outPut
    }

    def saveReferTo(JSONObject json) {
        def responseData
        if(json) {
            if(json.doctorId && json.patientUsername && json.prescriptionDate) {
                User currentUser = springSecurityService.currentUser
                DateTime prescriptionDate = DateTime.parse(json.prescriptionDate)
                Prescription prescription
                if (!json.prescriptionId) {
                    User patient = User.findByUsername(json.patientUsername)
                    Doctor doctor = Doctor.findById(json.doctorId)
                    if (patient && doctor) {
                        prescription = createPrescription(patient, doctor, prescriptionDate)
                    }
                } else {
                    prescription = Prescription.findById(json.prescriptionId)
                }
                prescription.lastUpdatedBy = currentUser.username
                prescription.lastUpdatedDate = new Date()
                prescription.save()
                JSONArray specialistList = new JSONArray(json.specialistList)
                for(JSONObject specialistJson : specialistList) {
                    Specialist specialist
                    if(specialistJson.id == 0) {
                        specialist = new Specialist(name: specialistJson.name, createdBy: currentUser.username)
                        specialist.save()
                    } else {
                        specialist = Specialist.findById(specialistJson.id)
                    }
                    ReferTo referTo = new ReferTo(specialistId: specialist.id, prescriptionId: prescription.id,
                            createdBy: currentUser.username)
                    referTo.save()
                }
                responseData = ['hasError': false, 'message': 'SUCCESS']
            } else {
                responseData = ['hasError': true, 'message': 'Please First fill up basic information & try again']
            }
        } else {
            responseData = ['hasError': true, 'message': 'Please First fill up basic information & try again']
        }
        responseData
    }

    def getPatientDetails(JSONObject patientJson) {
        String username = patientJson.username
        def responseData = [:]
        User patient = User.findByUsername(username)
        if(patient) {
            PatientProfile patientProfile = PatientProfile.findByUser(patient)
            if(patientProfile.dateOfBirth) {
                def now = new DateTime()
                def dateOfBirth = new DateTime(patientProfile.dateOfBirth)
                responseData['age'] = new Period(dateOfBirth, now).getYears()
            }
            Double weight = patientProfile.weight
            Double height = patientProfile.height
            if(weight && height) {
                responseData['bmi'] = CommonUtils.getBmi(weight, height)
            }
            responseData['weight'] = weight
            if(patientProfile.gender) {
                responseData['gender'] = MasterKeySetup.findById(patientProfile.gender).keyValue
            } else {
                responseData['gender'] = ''
            }
            responseData['patientProfileId'] = patientProfile.id
            Picture picture = Picture.findByEntityIdAndEntityName(patientProfile.id, 'profile_picture')
            if(picture) {
                responseData['hasProfilePicture'] = true
            } else {
                responseData['hasProfilePicture'] = false
            }
            DiabeticHistory diabeticHistory = DiabeticHistory.findByUser(patient)
            if(diabeticHistory) {
                responseData['diabeticType'] = diabeticHistory.diabeticType
            }

            Prescription prescription = Prescription.findByActiveStatusAndPatient(ActiveStatus.DRAFT, patient)
            if(prescription) {
                responseData = getMealExerciseList(responseData, prescription, patientProfile, patient)
            } else {
                prescription = Prescription.findByActiveStatusAndPatient(ActiveStatus.ACTIVE, patient)
                if(prescription) {
                    responseData = getMealExerciseList(responseData, prescription, patientProfile, patient)
                }
            }
        }
        return responseData
    }

    def getMealExerciseList(LinkedHashMap responseData, Prescription prescription, PatientProfile patientProfile, User patient) {
        def generalRules
        if(prescription.recommendMeal) {
            responseData['recommendMeal'] = prescription.recommendMeal
        } else {
            generalRules = GeneralRule.findAllByActiveStatusAndDiabeticType(ActiveStatus.ACTIVE, responseData['diabeticType'])
           /* generalRules = c.list() {
                and {
                    eq("activeStatus", ActiveStatus.ACTIVE)
                }
                if (responseData['diabeticType']){
                    and {
                        eq("diabeticType", responseData['diabeticType'])
                    }
                }
                if (responseData['age']) {
                    and {
                        ge('ageRangeFrom', responseData['age'])
                    }
                    and {
                        le('ageRangeTo', responseData['age'])
                    }
                }
                if (responseData['bmi']) {
                    and {
                        ge('bmiRangeFrom', responseData['bmi'])
                    }
                    and {
                        le('bmiRangeTo', responseData['bmi'])
                    }
                }
            }*/
            if(generalRules) {
                GeneralRuleMealPlan generalRuleMealPlan = GeneralRuleMealPlan.findByGeneralRuleId(generalRules.get(0).id)
                if (generalRuleMealPlan) {
                    def meal = [
                            breakfastPackage : generalRuleMealPlan.breakFastFoodId ? generalRuleMealPlan.breakFastFoodId.toString() : '',
                            beforeLunchPackage : generalRuleMealPlan.snackBeforeLunchFoodId ? generalRuleMealPlan.snackBeforeLunchFoodId.toString() : '',
                            lunchPackage : generalRuleMealPlan.lunchFoodId ? generalRuleMealPlan.lunchFoodId.toString() : '',
                            beforeDinnerPackage : generalRuleMealPlan.snackBeforeDinnerFoodId ? generalRuleMealPlan.snackBeforeDinnerFoodId.toString() : '',
                            dinnerPackage : generalRuleMealPlan.dinnerFoodId ? generalRuleMealPlan.dinnerFoodId.toString() : '',
                            beforeSleepPackage : generalRuleMealPlan.beforeSleepFoodId ? generalRuleMealPlan.beforeSleepFoodId.toString() : ''
                        ]
                    responseData['recommendMeal'] = meal
                }
            }

        }
        def recommendExerciseList = RecommendExercise.findAllByActiveStatusAndPrescriptionId(ActiveStatus.ACTIVE, prescription.id)
        if(recommendExerciseList) {
            ArrayList<Map> exerciseList = new ArrayList<Map>()
            for(RecommendExercise recommendExercise: recommendExerciseList) {
                ExercisePackage exercisePackage = ExercisePackage.findByIdAndActiveStatus(recommendExercise.exerciseId, ActiveStatus.ACTIVE)
                if(exercisePackage) {
                    def exerciseEach = [id: recommendExercise.id,
                                        exerciseId: recommendExercise.exerciseId,
                                        exerciseText: exercisePackage.packageName,
                                        time: recommendExercise.time,
                                        period: recommendExercise.period]
                    exerciseList.add(exerciseEach)
                }
            }
            responseData['exerciseList'] = exerciseList
        } else {
            generalRules = GeneralRule.findAllByActiveStatusAndDiabeticType(ActiveStatus.ACTIVE, responseData['diabeticType'])
            def exercisePlanList = GeneralRuleExercisePlan.findAllByGeneralRuleId(generalRules.get(0).id)
            if(exercisePlanList) {
                ArrayList<Map> exerciseList = new ArrayList<Map>()
                for(GeneralRuleExercisePlan exercisePlan: exercisePlanList) {
                    ExercisePackage exercisePackage = ExercisePackage.findByIdAndActiveStatus(exercisePlan.exerciseId, ActiveStatus.ACTIVE)
                    if(exercisePackage) {
                        def exerciseEach = [id: '',
                                            exerciseId: exercisePackage.id,
                                            exerciseText: exercisePackage.packageName,
                                            time: '',
                                            period: exercisePlan.period]
                        exerciseList.add(exerciseEach)
                    }
                }
                responseData['exerciseList'] = exerciseList
            }
        }

        responseData['prescriptionId'] = prescription.id;
        responseData['prescriptionDate'] = prescription.createdDate.format('dd/MM/YYYY');  //@TODO need to replace lastUpdatedDate
        def medicationList = Medication.findAllByActiveStatusAndPrescriptionId(ActiveStatus.ACTIVE, prescription.id)
        ArrayList<Map> medicationArrayList = new ArrayList<Map>()
        for(Medication medication : medicationList) {
            def medicine = [
                    id: medication.id,
                    drugType: medication.type,
                    medicineName: medication.name,
                    drugWeight: medication.weight,
                    period: medication.period,
                    drugTime: medication.time,
                    quantity1: medication.quantity1,
                    quantity2: medication.quantity2,
                    quantity3: medication.quantity3,
                    quantity4: medication.quantity4,
                    quantity5: medication.quantity5,
                    medicineAmBm: medication.amBm,
                    instruction: medication.instruction,
                    drugTime1: medication.drugTime1,
                    drugTime2: medication.drugTime2,
                    drugTime3: medication.drugTime3,
                    drugTime4: medication.drugTime4,
                    drugTime5: medication.drugTime5
            ]
            medicationArrayList.add(medicine)
        }
        responseData['medication'] = medicationArrayList
        def prescriptionList = Prescription.findAllByPatient(patient)
        ArrayList<Map> prescriptionArrayList = new ArrayList<Map>()
        for (Prescription prescript : prescriptionList) {
            def eachPrescription = [
                    id: prescript.id,
                    prescriptionDate: prescript.createdDate,
                    doctorName: prescript.doctor.name,
                    activeStatus: prescript.activeStatus.name()
            ]
            prescriptionArrayList.add(eachPrescription)
        }
        responseData['prescriptionList'] = prescriptionArrayList
        responseData
    }

    def searchMedicineName(String query){
        LinkedHashMap result = new LinkedHashMap()
        def medCineNameList = MedBrand.findAllByActiveStatusAndBrandNameIlike(ActiveStatus.ACTIVE, "%"+query+"%", [max: 10])
        result.put('isError', Boolean.FALSE)
        result.put('results', medCineNameList)
        String outPut = result as JSON
        return outPut

    }

    def saveRecommend(JSONObject json) {
        def responseData
        if(json) {
            if(json.doctorId && json.patientUsername && json.prescriptionDate) {
                User currentUser = springSecurityService.currentUser
                DateTime prescriptionDate = DateTime.parse(json.prescriptionDate, DateTimeFormat.forPattern('dd/MM/YYYY'))
                Prescription prescription
                RecommendMeal recommendMeal
                if (!json.prescriptionId) {
                    User patient = User.findByUsername(json.patientUsername)
                    Doctor doctor = Doctor.findById(json.doctorId)
                    if (patient && doctor) {
                        prescription = createPrescription(patient, doctor, prescriptionDate)
                    }
                } else {
                    prescription = Prescription.findById(json.prescriptionId)
                }
                if(!prescription.recommendMeal) {
                    recommendMeal = new RecommendMeal(
                            breakfastPackage: json.breakfastPackage,
                            breakfastTime: json.breakfastTime,
                            beforeLunchPackage: json.beforeLunchPackage,
                            beforeLunchTime: json.beforeLunchTime,
                            lunchPackage: json.lunchPackage,
                            lunchTime: json.lunchTime,
                            dinnerTime: json.dinnerTime,
                            dinnerPackage: json.dinnerPackage,
                            beforeDinnerPackage: json.beforeDinnerPackage,
                            beforeDinnerTime: json.beforeDinnerTime,
                            beforeSleepPackage: json.beforeSleepPackage,
                            beforeSleepTime: json.beforeSleepTime,
                            createdBy: currentUser.username)
                    recommendMeal.save()
                    prescription.recommendMeal = recommendMeal
                    prescription.save(flush: true)
                } else {
                    recommendMeal = prescription.recommendMeal
                    recommendMeal.breakfastPackage = json.breakfastPackage
                    recommendMeal.breakfastTime = json.breakfastTime
                    recommendMeal.beforeLunchPackage = json.beforeLunchPackage
                    recommendMeal.beforeLunchTime = json.beforeLunchTime
                    recommendMeal.lunchPackage = json.lunchPackage
                    recommendMeal.lunchTime = json.lunchTime
                    recommendMeal.dinnerTime = json.dinnerTime
                    recommendMeal.dinnerPackage = json.dinnerPackage
                    recommendMeal.beforeDinnerPackage = json.beforeDinnerPackage
                    recommendMeal.beforeDinnerTime = json.beforeDinnerTime
                    recommendMeal.beforeSleepPackage = json.beforeSleepPackage
                    recommendMeal.beforeSleepTime = json.beforeSleepTime
                    recommendMeal.lastUpdatedDate = new Date()
                    recommendMeal.lastUpdatedBy = currentUser.username
                    recommendMeal.save(flush: true)
                }
                JSONArray exerciseList = new JSONArray(json.exerciseList)
                def recommendExerciseList = RecommendExercise.findAllByPrescriptionIdAndActiveStatus(prescription.id, ActiveStatus.ACTIVE)
                if(recommendExerciseList) {
                    for(RecommendExercise recommendExercise : recommendExerciseList) {
                        boolean needTobeDelete = true
                        for(JSONObject jsonExercise : exerciseList) {
                            if(recommendExercise.exerciseId == jsonExercise.exerciseId) {
                                needTobeDelete = false
                                break
                            }
                        }
                        if(needTobeDelete) {
                            recommendExercise.activeStatus = ActiveStatus.DELETED
                            recommendExercise.save(flush: true)
                        }
                    }
                }
                for(JSONObject jsonExercise : exerciseList) {
                    RecommendExercise exercise = RecommendExercise.findById(jsonExercise.id)
                    if(!exercise) {
                        exercise = new RecommendExercise(
                                prescriptionId: prescription.id,
                                exerciseId: jsonExercise.exerciseId,
                                time: jsonExercise.time,
                                period: jsonExercise.period,
                                createdBy: currentUser.username
                        )
                        exercise.save(flush: true)
                    } else {
                        exercise.time = jsonExercise.time
                        exercise.period = jsonExercise.period
                        exercise.lastUpdatedBy = currentUser.username
                        exercise.lastUpdatedDate = new Date()
                        exercise.save(flush: true)
                    }
                }
                responseData = ['hasError': false, 'message': 'SUCCESS']
            } else {
                responseData = ['hasError': true, 'message': 'Please First fill up basic information & try again']
            }
        } else {
            responseData = ['hasError': true, 'message': 'Please First fill up basic information & try again']
        }
        return responseData
    }

    def setActivePrescription(Integer prescriptionId, String patientUserName) {
        def responseData
        if(prescriptionId) {
            Prescription prescription = Prescription.findByActiveStatus(ActiveStatus.ACTIVE)
            if(prescription) {
                prescription.activeStatus = ActiveStatus.OLD
                prescription.save(flush: true)
            }
            prescription = Prescription.findById(prescriptionId)
            prescription.activeStatus = ActiveStatus.ACTIVE
            prescription.save(flush: true)
            modifyReminder(patientUserName)
            responseData = ['hasError': false, 'message': 'SUCCESS']
        } else {
            responseData = ['hasError': true, 'message': 'Can not find prescription id.']
        }
        responseData
    }

    def modifyReminder(String patientUserName) {
        User patient = User.findByUsername(patientUserName)
        Prescription prescription = Prescription.findByActiveStatus(ActiveStatus.ACTIVE)
        RecommendMeal recommendMeal = prescription.recommendMeal
        def recommendExerciseList = RecommendExercise.findAllByActiveStatusAndPrescriptionId(ActiveStatus.ACTIVE, prescription.id)
        def medicationList = Medication.findAllByActiveStatusAndPrescriptionId(ActiveStatus.ACTIVE, prescription.id)
        ArrayList<Integer> needSyncReminderIdList = new ArrayList<Integer>()
        ArrayList<Integer> reminderIdList
        Integer reminderId
        int needUpdateDay = 1
        DateTime dateTime
        for(int plusDay = 0; plusDay <= needUpdateDay; plusDay++) {
            dateTime = new DateTime().plusHours(plusDay)
            if(recommendMeal.breakfastPackage) {
                reminderIdList = setMealReminder(recommendMeal.breakfastPackage, recommendMeal.breakfastTime, dateTime, patient)
                needSyncReminderIdList.addAll(reminderIdList)
            }
            if(recommendMeal.beforeLunchPackage) {
                reminderIdList = setMealReminder(recommendMeal.beforeLunchPackage, recommendMeal.beforeLunchTime, dateTime, patient)
                needSyncReminderIdList.addAll(reminderIdList)
            }
            if(recommendMeal.lunchPackage) {
                reminderIdList = setMealReminder(recommendMeal.lunchPackage, recommendMeal.lunchTime, dateTime, patient)
                needSyncReminderIdList.addAll(reminderIdList)
            }
            if(recommendMeal.beforeDinnerPackage) {
                reminderIdList = setMealReminder(recommendMeal.beforeDinnerPackage, recommendMeal.beforeDinnerTime, dateTime, patient)
                needSyncReminderIdList.addAll(reminderIdList)
            }
            if(recommendMeal.dinnerPackage) {
                reminderIdList = setMealReminder(recommendMeal.dinnerPackage, recommendMeal.dinnerTime, dateTime, patient)
                needSyncReminderIdList.addAll(reminderIdList)
            }
            if(recommendMeal.beforeSleepPackage) {
                reminderIdList = setMealReminder(recommendMeal.beforeSleepPackage, recommendMeal.beforeSleepTime, dateTime, patient)
                needSyncReminderIdList.addAll(reminderIdList)
            }
            for (RecommendExercise recommendExercise: recommendExerciseList) {
                ExercisePackage exercisePackage = ExercisePackage.findByIdAndActiveStatus(recommendExercise.exerciseId, ActiveStatus.ACTIVE)
                def exerciseElementList = ExercisePackageElement.findAllByPackageIdAndActiveStatus(exercisePackage.id, ActiveStatus.ACTIVE)
                for(ExercisePackageElement packageElement: exerciseElementList) {
                    ExerciseLibrary exerciseLibrary = ExerciseLibrary.findByIdAndActiveStatus(packageElement.exerciseLibraryId, ActiveStatus.ACTIVE)
                    if(exerciseLibrary) {
                        Picture picture = Picture.findByEntityIdAndEntityName(exerciseLibrary.id, CommonUtils.EXERCISE_LIBRARY)
                        reminderId = setReminder(null, CommonUtils.EXERCISE_LIBRARY, recommendExercise.time, dateTime, exerciseLibrary.id,
                                recommendExercise.period, picture, patient, 'Yes,No,Cancel', 'Have you done this?', 'This is the time to exercise')
                        if(reminderId) {
                            needSyncReminderIdList.add(reminderId)
                        }
                    }
                }
            }
            for (Medication medication: medicationList) {
                MedBrand medBrand = MedBrand.findByBrandNameAndActiveStatus(medication.name, ActiveStatus.ACTIVE)
                Picture picture = Picture.findByEntityIdAndEntityName(medBrand.id, CommonUtils.MEDICINE_LIBRARY)
                if(medication.drugTime1) {
                    reminderId = setReminder(null, CommonUtils.MEDICINE_LIBRARY, medication.drugTime1, dateTime, medBrand.id, medication.quantity1, picture,
                            patient, 'Yes,No,Cancel', 'Have you done this?', 'This is the time to medicine')
                    if(reminderId) {
                        needSyncReminderIdList.add(reminderId)
                    }
                }
                if(medication.drugTime2) {
                    reminderId = setReminder(null, CommonUtils.MEDICINE_LIBRARY, medication.drugTime2, dateTime, medBrand.id, medication.quantity2, picture,
                            patient, 'Yes,No,Cancel', 'Have you done this?', 'This is the time to medicine')
                    if(reminderId) {
                        needSyncReminderIdList.add(reminderId)
                    }
                }
                if(medication.drugTime3) {
                    reminderId = setReminder(null, CommonUtils.MEDICINE_LIBRARY, medication.drugTime3, dateTime, medBrand.id, medication.quantity3, picture,
                            patient, 'Yes,No,Cancel', 'Have you done this?', 'This is the time to medicine')
                    if(reminderId) {
                        needSyncReminderIdList.add(reminderId)
                    }
                }
                if(medication.drugTime4) {
                    reminderId = setReminder(null, CommonUtils.MEDICINE_LIBRARY, medication.drugTime4, dateTime, medBrand.id, medication.quantity4, picture,
                            patient, 'Yes,No,Cancel', 'Have you done this?', 'This is the time to medicine')
                    if(reminderId) {
                        needSyncReminderIdList.add(reminderId)
                    }
                }
                if(medication.drugTime5) {
                    reminderId = setReminder(null, CommonUtils.MEDICINE_LIBRARY, medication.drugTime5, dateTime, medBrand.id, medication.quantity5, picture,
                            patient, 'Yes,No,Cancel', 'Have you done this?', 'This is the time to medicine')
                    if(reminderId) {
                        needSyncReminderIdList.add(reminderId)
                    }
                }
            }
        }
        if(needSyncReminderIdList.size() > 0) {
            notificationService.pushFCMNotification(patient.notificationKey, 'UPDATE')
        }
    }

    def setMealReminder(String packageId, String reminderTime, DateTime dateTime, User patient) {
        ArrayList<Integer> needSyncReminderIdList = new ArrayList<Integer>()
        Integer reminderId
        FoodPackage foodPackage = FoodPackage.findById(Integer.parseInt(packageId))
        def foodElementList = FoodPackageElement.findAllByPackageId(foodPackage.id)
        for (FoodPackageElement element: foodElementList) {
            FoodLibrary foodLibrary = FoodLibrary.get(element.foodLibraryId)
            if(foodLibrary) {
                Picture picture = Picture.findByEntityIdAndEntityName(foodLibrary.id, CommonUtils.FOOD_LIBRARY)
                reminderId = setReminder(foodPackage.id, CommonUtils.FOOD_LIBRARY, reminderTime, dateTime, foodLibrary.id, element.quantity, picture,
                        patient, 'Yes,No,Cancel', 'Have you done this?', 'This is the time to take meal')
                if(reminderId) {
                    needSyncReminderIdList.add(reminderId)
                }
            }
        }
        needSyncReminderIdList
    }

    def setReminder(Long packageId, String libraryType, String time, DateTime dateTime, Long libraryId, Double quantity, Picture picture, User patient,
                    String options, String questions, String message) {
        Reminder reminder
        Date reminderDate = CommonUtils.addTimeToDate(time, dateTime).toDate()
        reminder = Reminder.findByActiveStatusAndPatientAndDateTimeAndLibraryId(ActiveStatus.ACTIVE, patient, reminderDate, libraryId)
        if(!reminder) {
            reminder = new Reminder()
            reminder.dateTime = CommonUtils.addTimeToDate(time, dateTime).toDate()
            reminder.libraryId = libraryId
            reminder.packageId = packageId
            reminder.libraryType = libraryType
            reminder.quantity = quantity
            reminder.picture = picture
            reminder.patient = patient
            reminder.options = options
            reminder.question = questions
            reminder.message = message
            reminder.save(flush: true)
            println('Notification Scheduler new' + reminder.id)
            return reminder.id
        } else {
            if(reminder.dateTime != CommonUtils.addTimeToDate(time, dateTime).toDate()
                   || reminder.question != questions || reminder.message != message) {
                reminder.dateTime = CommonUtils.addTimeToDate(time, dateTime).toDate()
                reminder.libraryId = libraryId
                reminder.packageId = packageId
                reminder.libraryType = libraryType
                reminder.quantity = quantity
                reminder.picture = picture
                reminder.patient = patient
                reminder.options = options
                reminder.question = questions
                reminder.message = message
                reminder.syncStatus = SyncStatus.MODIFIED
                reminder.save(flush: true)
                println('Modified Notification Scheduler' + reminder.id)
                return reminder.id
            }
        }
        return null
    }

    def copyPrescription(Integer prescriptionId) {
        User currentUser = springSecurityService.currentUser
        def responseData
        Prescription selectedPrescription = Prescription.get(prescriptionId)
        if(selectedPrescription) {
            Prescription copiedPrescription = new Prescription(selectedPrescription.properties)

            RecommendMeal selectedRecommendedMeal = selectedPrescription.recommendMeal
            if(selectedRecommendedMeal) {
                RecommendMeal copiedRecommendedMeal = new RecommendMeal(selectedRecommendedMeal.properties)
                copiedRecommendedMeal.createdBy = currentUser.username
                copiedRecommendedMeal.createdDate = new Date()
                copiedRecommendedMeal.save()
                copiedPrescription.recommendMeal = copiedRecommendedMeal
            }


            ClinicalCondition selectedClinicalCondition = selectedPrescription.clinicalCondition
            if(selectedClinicalCondition) {
                ClinicalCondition copiedClinicalCondition = new ClinicalCondition(selectedClinicalCondition.properties)
                copiedClinicalCondition.createdBy = currentUser.username
                copiedClinicalCondition.createdDate = new Date()
                copiedClinicalCondition.save()
                copiedPrescription.clinicalCondition = copiedClinicalCondition
            }
            copiedPrescription.createdBy = currentUser.username
            copiedPrescription.createdDate = new Date()
            copiedPrescription.activeStatus = ActiveStatus.DRAFT
            copiedPrescription.save(flush: true)

            def recommendExerciseList = RecommendExercise.findAllByPrescriptionIdAndActiveStatus(selectedPrescription.id, ActiveStatus.ACTIVE)

            for(RecommendExercise exercise : recommendExerciseList) {
                RecommendExercise recommendExercise = new RecommendExercise(exercise.properties)
                recommendExercise.prescriptionId = copiedPrescription.id
                recommendExercise.createdBy = currentUser.username
                recommendExercise.createdDate = new Date()
                recommendExercise.save()
            }

            def medicationList = Medication.findAllByPrescriptionIdAndActiveStatus(selectedPrescription.id, ActiveStatus.ACTIVE)

            for(Medication medication: medicationList) {
                Medication medicationDuplicate = new Medication(medication.properties)
                medicationDuplicate.prescriptionId = copiedPrescription.id
                medicationDuplicate.createdBy = currentUser.username
                medicationDuplicate.createdDate = new Date()
                medicationDuplicate.save()
            }

            responseData = ['hasError': false, 'message': 'sucess']
        } else {
            responseData = ['hasError': true, 'message': 'Can not find prescription id.']
        }
        responseData
    }
}
