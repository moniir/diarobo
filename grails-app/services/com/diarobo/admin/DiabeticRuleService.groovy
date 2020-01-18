package com.diarobo.admin

import com.diarobo.CommonUtils
import com.diarobo.enums.ActiveStatus
import grails.converters.JSON
import grails.web.servlet.mvc.GrailsParameterMap
import org.grails.web.json.JSONArray
import org.grails.web.json.JSONObject

class DiabeticRuleService {
    def springSecurityService

    static transactional = false
    static final String[] sortColumns = ['id']

    LinkedHashMap paginationList(GrailsParameterMap params) {
        Map serverParams = CommonUtils.getPaginationParams(params, sortColumns)
        int iDisplayStart= serverParams.iDisplayStart.toInteger();
        int  iDisplayLength= serverParams.iDisplayLength.toInteger()

        String sSortDir=serverParams.sSortDir;
        String sortColumn=serverParams.sortColumn
        String sSearch=serverParams.sSearch

        def c = GeneralRule.createCriteria()
        def results = c.list(max: iDisplayLength, offset: iDisplayStart) {
            and {
                eq("activeStatus", ActiveStatus.ACTIVE)
            }

            if (sSearch) {
                or {
                    ilike('diabeticType', sSearch)
                }
            }
            order(sortColumn, sSortDir)
        }
        int totalCount = results.totalCount

        List dataReturns = new ArrayList()
        if(totalCount>0){
            int serial = iDisplayStart;
            if (sSortDir.equals(CommonUtils.SORT_ORDER_DESC)) {
                serial = (totalCount + 1) - iDisplayStart
            }
            for (obj in results) {
                if (serverParams.sSortDir.equals(CommonUtils.SORT_ORDER_ASC)) {
                    serial++
                } else {
                    serial--
                }
                dataReturns.add([DT_RowId: obj.id, 0: serial, 1: obj.diabeticType, 2: obj.bmiRangeFrom +" - "+obj.bmiRangeTo, 3: obj.ageRangeFrom + " - "+obj.ageRangeTo, 4:''])

            }

        }
        return [totalCount:totalCount, results:dataReturns]
    }

    def saveExercisePlan(JSONObject json) {
        def responseData
        if(json) {
            if(json.masterId) {
                JSONArray exerciseJsonList = new JSONArray(json.exerciseList)
                for(JSONObject jsonObject : exerciseJsonList) {
                    GeneralRuleExercisePlan exercisePlan
                    exercisePlan = GeneralRuleExercisePlan.findByExerciseIdAndActiveStatusAndGeneralRuleId(jsonObject.exerciseId, ActiveStatus.ACTIVE, jsonObject.generalRuleId)
                    if(!exercisePlan) {
                        exercisePlan = new GeneralRuleExercisePlan(generalRuleId: jsonObject.generalRuleId,
                                exerciseId: jsonObject.exerciseId, period: jsonObject.period, createdBy: springSecurityService.principal.username)
                    } else {
                        exercisePlan = GeneralRuleExercisePlan.findById(jsonObject.exerciseId)
                        exercisePlan.period = jsonObject.period
                        exercisePlan.lastUpdatedBy = springSecurityService.principal.username
                        exercisePlan.lastUpdatedDate = new Date()
                    }
                    exercisePlan.save(flush: true)
                    def exercisePlanList = GeneralRuleExercisePlan.findAllByActiveStatusAndGeneralRuleId(ActiveStatus.ACTIVE, jsonObject.generalRuleId)
                    if(exercisePlanList) {
                        for(GeneralRuleExercisePlan generalRuleExercisePlan : exercisePlanList) {
                            boolean needTobeDelete = true
                            for(JSONObject jsonExercise : exerciseJsonList) {
                                if(generalRuleExercisePlan.exerciseId == jsonExercise.exerciseId) {
                                    needTobeDelete = false
                                    break
                                }
                            }
                            if(needTobeDelete) {
                                generalRuleExercisePlan.activeStatus = ActiveStatus.DELETED
                                exercisePlan.lastUpdatedBy = springSecurityService.principal.username
                                exercisePlan.lastUpdatedDate = new Date()
                                generalRuleExercisePlan.save(flush: true)
                            }
                        }
                    }
                }
            }
            responseData = ['hasError': false, 'message': 'SUCCESS']
        } else {
            responseData = ['hasError': true, 'message': 'server error']
        }
        responseData
    }

    def dosDelete(List addedList, List addList){
        if(addedList){
            for(GeneralRuleDosDonts dos: addedList){
                if(addList){
                    boolean flag = addList.find{it == dos.id}
                    if(!flag){
                        GeneralRuleDosDonts dosRecord = GeneralRuleDosDonts.get(dos.id)
                        dosRecord.activeStatus = ActiveStatus.DELETED
                        dosRecord.save(flush:true)
                    }
                }else {
                    GeneralRuleDosDonts dosRecord = GeneralRuleDosDonts.get(dos.id)
                    dosRecord.activeStatus = ActiveStatus.DELETED
                    dosRecord.save(flush:true)
                }
            }
        }
    }

    def dontsDelete(List addedList, List addList){
        if(addedList){
            for(GeneralRuleDosDonts dos: addedList){
                if(addList){
                    boolean flag = addList.find{it == dos.id}
                    if(!flag){
                        GeneralRuleDosDonts dontsRecord = GeneralRuleDosDonts.get(dos.id)
                        dontsRecord.activeStatus = ActiveStatus.DELETED
                        dontsRecord.save(flush:true)
                    }
                }else {
                    GeneralRuleDosDonts dontsRecord = GeneralRuleDosDonts.get(dos.id)
                    dontsRecord.activeStatus = ActiveStatus.DELETED
                    dontsRecord.save(flush:true)
                }
            }
        }
    }

    def complicationDelete(List addedComplicationList, List addList){
        if(addedComplicationList){
            for(GeneralRuleComplications complication: addedComplicationList){
                if(addList){
                    boolean flag = addList.find{it == complication.id}
                    if(!flag){
                        GeneralRuleComplications compliRecord = GeneralRuleComplications.get(complication.id)
                        compliRecord.activeStatus = ActiveStatus.DELETED
                        compliRecord.save(flush:true)
                    }
                }else {
                    GeneralRuleComplications compliRecord = GeneralRuleComplications.get(complication.id)
                    compliRecord.activeStatus = ActiveStatus.DELETED
                    compliRecord.save(flush:true)
                }
            }
        }
    }
}
