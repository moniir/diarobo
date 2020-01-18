package com.diarobo.admin

import com.diarobo.CommonUtils
import com.diarobo.api.RemoteController
import com.diarobo.enums.ActiveStatus
import grails.converters.JSON;
import grails.plugin.springsecurity.annotation.Secured
import org.grails.web.json.JSONObject

@Secured(CommonUtils.ROLE_ADMIN)
class DiabeticRuleController {
    def springSecurityService
    def diabeticRuleService

    def index(){

    }

    def create() {
        render(view:'create', model: [step:1]);
    }

    def list() {
        LinkedHashMap gridData
        String result
        LinkedHashMap resultMap = diabeticRuleService.paginationList(params)

        if(!resultMap || resultMap.totalCount== 0){
            gridData = [iTotalRecords: 0, iTotalDisplayRecords: 0, aaData: []]
            result = gridData as JSON
            render result
            return
        }
        int totalCount =resultMap.totalCount
        gridData = [iTotalRecords: totalCount, iTotalDisplayRecords: totalCount, aaData: resultMap.results]
        result = gridData as JSON
        render result
    }

    def saveBasic(){
        GeneralRule generalRule
        if(params.basicInfoId){
            generalRule = GeneralRule.get(params.long('basicInfoId'))
            generalRule.diabeticType = params.diabeticType
            generalRule.bmiRangeFrom = params.float('bmiRangeFrom')
            generalRule.bmiRangeTo = params.float('bmiRangeTo')
            generalRule.ageRangeFrom = params.int('ageRangeFrom')
            generalRule.ageRangeTo =  params.int('ageRangeTo')

        }else {
            generalRule = new GeneralRule();
            generalRule.diabeticType = params.diabeticType
            generalRule.bmiRangeFrom = params.float('bmiRangeFrom')
            generalRule.bmiRangeTo = params.float('bmiRangeTo')
            generalRule.ageRangeFrom = params.int('ageRangeFrom')
            generalRule.ageRangeTo =  params.int('ageRangeTo')
            generalRule.stepLevel = 1
            generalRule.createdBy = springSecurityService.principal.username
        }

        if(!generalRule.hasErrors() && generalRule.save(flush:true)){
            flash.message = "Save Successfully"
            def mealPlan
            if(params.basicInfoId){
                mealPlan = GeneralRuleMealPlan.findByGeneralRuleIdAndActiveStatus(generalRule.id,ActiveStatus.ACTIVE)
            }
            render(view:'create', model: [step:2 , generalRuleId: generalRule.id,mealPlan:mealPlan]);
            return
        }

        flash.error = "Please fill  Required Field"
        render(view:'create', model: [step:1]);

    }
 def saveMealPlan(){
     if(params.masterId) {
         def editDos
         def editDonts
         GeneralRuleMealPlan mealPlan
         long generalRuleId = params.long('masterId')
         try {
             mealPlan = GeneralRuleMealPlan.findByGeneralRuleIdAndActiveStatus(generalRuleId,ActiveStatus.ACTIVE)

         if(params.mealPlanId){
             mealPlan = GeneralRuleMealPlan.get(params.long('mealPlanId'))
             mealPlan.breakFastFoodId = params.long('breakFastFoodId')
             mealPlan.breakFastFoodTime = params.breakFastFoodTime
             mealPlan.snackBeforeLunchFoodId = params.long('snackBeforeLunchFoodId')
             mealPlan.snackBeforeLunchFoodTime = params.snackBeforeLunchFoodTime
             mealPlan.lunchFoodId = params.long('lunchFoodId')
             mealPlan.lunchFoodTime = params.lunchFoodTime
             mealPlan.snackBeforeDinnerFoodId = params.long('snackBeforeDinnerFoodId')
             mealPlan.snackBeforeDinnerFoodTime = params.snackBeforeDinnerFoodTime
             mealPlan.dinnerFoodId = params.long('dinnerFoodId')
             mealPlan.dinnerFoodTime = params.dinnerFoodTime
             mealPlan.beforeSleepFoodId = params.long('beforeSleepFoodId')
             mealPlan.beforeSleepFoodTime = params.beforeSleepFoodTime


         }else{
             mealPlan = new GeneralRuleMealPlan()
             mealPlan.generalRuleId = generalRuleId
             mealPlan.breakFastFoodId = params.long('breakFastFoodId')
             mealPlan.breakFastFoodTime = params.breakFastFoodTime
             mealPlan.snackBeforeLunchFoodId = params.long('snackBeforeLunchFoodId')
             mealPlan.snackBeforeLunchFoodTime = params.snackBeforeLunchFoodTime
             mealPlan.lunchFoodId = params.long('lunchFoodId')
             mealPlan.lunchFoodTime = params.lunchFoodTime
             mealPlan.snackBeforeDinnerFoodId = params.long('snackBeforeDinnerFoodId')
             mealPlan.snackBeforeDinnerFoodTime = params.snackBeforeDinnerFoodTime
             mealPlan.dinnerFoodId = params.long('dinnerFoodId')
             mealPlan.dinnerFoodTime = params.dinnerFoodTime
             mealPlan.beforeSleepFoodId = params.long('beforeSleepFoodId')
             mealPlan.beforeSleepFoodTime = params.beforeSleepFoodTime
             mealPlan.createdBy = springSecurityService.principal.username
         }



         }catch (Exception ex){
             flash.error = "Save fail"
             render(view:'create', model: [step:2, generalRuleId: mealPlan.generalRuleId,mealPlan:mealPlan]);
             return
         }

         if (!mealPlan.hasErrors() && mealPlan.save(flush:true)) {
             flash.message = "Save Successfully"
             if(params.mealPlanId){
                 editDos = GeneralRuleDosDonts.findAllByGeneralRuleIdAndActiveStatusAndDosDoNtsFlag(generalRuleId,ActiveStatus.ACTIVE,'Dos')
                 editDonts = GeneralRuleDosDonts.findAllByGeneralRuleIdAndActiveStatusAndDosDoNtsFlag(generalRuleId,ActiveStatus.ACTIVE,'Donts')
             }
             render(view: 'create', model: [step: 3, generalRuleId: mealPlan.generalRuleId,editDos:editDos,editDonts:editDonts]);
             return
         } else {
             flash.error = "Save fail"
             render(view:'create', model: [step:2, generalRuleId: mealPlan.generalRuleId,mealPlan:mealPlan]);
             return
         }
     } else {
         flash.error = "Start From Basic Information"
         render(view:'create', model: [step:1]);
         return
     }

 }

    def saveDosDonts(){
        if(params.masterId) {
            List moreDosDescription
            List moreDoNTsDescription
            GeneralRuleDosDonts doDonts
            GeneralRuleDosDonts dosDontsSave
            Long generalRuleId =  params.long('masterId')

            List dosAddIds = []
            List dontsAddIds = []
            def dosAddedList = GeneralRuleDosDonts.findAllByGeneralRuleIdAndActiveStatusAndDosDoNtsFlag(generalRuleId,ActiveStatus.ACTIVE,'Dos')
            def dontsAddedList = GeneralRuleDosDonts.findAllByGeneralRuleIdAndActiveStatusAndDosDoNtsFlag(generalRuleId,ActiveStatus.ACTIVE,'Donts')

            if(params.moreDosDescription){
                long id
                moreDosDescription = JSON.parse(params.moreDosDescription)
                moreDosDescription.each{ dosDes ->
                    if(dosDes.id){
                        id = Long.parseLong(dosDes.id)
                        doDonts =  GeneralRuleDosDonts.get(id)
                        doDonts.generalRuleId = generalRuleId
                        doDonts.description = dosDes.description
                        doDonts.dosDoNtsFlag = 'Dos'
                        doDonts.createdBy = springSecurityService.principal.username
                        dosDontsSave = doDonts.save(flush:true)
                        dosAddIds.add(id)

                    }else{
                        doDonts = new GeneralRuleDosDonts()
                        doDonts.generalRuleId = generalRuleId
                        doDonts.description = dosDes.description
                        doDonts.dosDoNtsFlag = 'Dos'
                        doDonts.createdBy = springSecurityService.principal.username
                        dosDontsSave = doDonts.save()

                    }
                }

         }
            if(params.moreDontsDescription){
                long id
                moreDoNTsDescription = JSON.parse(params.moreDontsDescription)
                moreDoNTsDescription.each{ dontsDes ->
                    if(dontsDes.id){
                        id = Long.parseLong(dontsDes.id)
                        doDonts =  GeneralRuleDosDonts.get(id)
                        doDonts.generalRuleId = generalRuleId
                        doDonts.description = dontsDes.description
                        doDonts.dosDoNtsFlag = 'Donts'
                        doDonts.createdBy = springSecurityService.principal.username
                        dosDontsSave = doDonts.save()
                        dontsAddIds.add(id)
                    }else {
                        doDonts = new GeneralRuleDosDonts()
                        doDonts.generalRuleId = generalRuleId
                        doDonts.description = dontsDes.description
                        doDonts.dosDoNtsFlag = 'Donts'
                        doDonts.createdBy = springSecurityService.principal.username
                        dosDontsSave = doDonts.save()

                        }
               }

           }

            if(params.dosDescription){
                doDonts = new GeneralRuleDosDonts()
                doDonts.generalRuleId = params.long('masterId')
                doDonts.description = params.dosDescription
                doDonts.dosDoNtsFlag = 'Dos'
                doDonts.createdBy = springSecurityService.principal.username
                dosDontsSave = doDonts.save()
            }
            if(params.dontsDescription){
                doDonts = new GeneralRuleDosDonts()
                doDonts.generalRuleId = params.long('masterId')
                doDonts.description = params.dontsDescription
                doDonts.dosDoNtsFlag = 'Donts'
                doDonts.createdBy = springSecurityService.principal.username
                dosDontsSave = doDonts.save()
            }


           if(dosAddedList){
               diabeticRuleService.dosDelete(dosAddedList,dosAddIds)
            }
            if(dontsAddedList){
                diabeticRuleService.dontsDelete(dontsAddedList,dontsAddIds)
            }
            def complicationList = GeneralRuleComplications.findAllByGeneralRuleIdAndActiveStatus(generalRuleId,ActiveStatus.ACTIVE)

          if (dosDontsSave) {
                flash.message = "Save Successfully"
                 render(view: 'create', model: [step: 4, generalRuleId: doDonts.generalRuleId,complicationList:complicationList]);
                return
            }else {
              flash.error = "Save fail"
              render(view:'create', model: [step:3, generalRuleId: doDonts.generalRuleId,complicationList:complicationList]);
              return
          }

        }else {
            flash.error = "Start From Basic Information"
            render(view:'create', model: [step:1]);
            return
        }
    }

    def saveComplications(){
        List moreComplicationList
        GeneralRuleComplications complications
        GeneralRuleComplications complicationsSave
        long generalRuleId = params.long('masterId')
        if(params.masterId){
            List addIds = []
            long id
            def editComplication = GeneralRuleComplications.findAllByGeneralRuleIdAndActiveStatus(generalRuleId,ActiveStatus.ACTIVE)

            if(params.moreComplications){
                moreComplicationList = JSON.parse(params.moreComplications)
                moreComplicationList.each {description ->
                    if(description.id){
                        id = Long.parseLong(description.id)
                        complications = GeneralRuleComplications.get(id)
                        complications.generalRuleId = generalRuleId
                        complications.description = description.complication
                        complicationsSave = complications.save(flush:true)
                        addIds.add(id)
                    }else {
                        complications = new GeneralRuleComplications()
                        complications.generalRuleId = generalRuleId
                        complications.description = description.complication
                        complications.createdBy = springSecurityService.principal.username
                        complicationsSave = complications.save(flush:true)
                    }
                }

            }

            if(params.complication){
                complications = new GeneralRuleComplications()
                complications.generalRuleId = params.long('masterId')
                complications.description = params.complication
                complications.createdBy = springSecurityService.principal.username
                complicationsSave = complications.save()
            }
            if(editComplication){
                diabeticRuleService.complicationDelete(editComplication,addIds)
            }


            if (complicationsSave) {
                flash.message = "Save Successfully"
                redirect(action:'index')
                return
            }else {
                flash.error = "Save fail"
                render(view:'create', model: [step:4, generalRuleId: complications.generalRuleId]);
                return
            }

        }else {
            flash.error = "Start From Basic Information"
            render(view:'create', model: [step:1]);
            return
        }
    }

    def saveRelatedFood(){
        List relatedFoodList
        GeneralRuleRelatedFood generalRuleRelatedFood
        GeneralRuleRelatedFood generalRuleRelatedFoodSave
        if(params.masterId){
            Long generalRuleId = params.long('masterId')
           if(params.relatedFoodList){
                relatedFoodList = JSON.parse(params.relatedFoodList)
                relatedFoodList.each {relatedFood ->
                    generalRuleRelatedFood = new GeneralRuleRelatedFood()
                    generalRuleRelatedFood.generalRuleId = generalRuleId
                    generalRuleRelatedFood.foodLibraryId = Long.parseLong(relatedFood.foodLibraryId)
                    generalRuleRelatedFood.foodCalories = Float.parseFloat(relatedFood.foodCalories)
                    generalRuleRelatedFood.createdBy = springSecurityService.principal.username
                    generalRuleRelatedFoodSave = generalRuleRelatedFood.save()
          }
          }else {
               flash.error = "please add related food"
               render(view:'create', model: [step:2,generalRuleId:generalRuleId]);
               return
             }
        }else {
            flash.error = "Start From Basic Information"
            render(view:'create', model: [step:1]);
            return

        }

        if(!generalRuleRelatedFoodSave.hasErrors() && generalRuleRelatedFoodSave){
            flash.message = "Save Successfully"
            render(view:'create', model: [step:3,generalRuleId: generalRuleRelatedFoodSave.generalRuleId]);
            return
        }
        flash.error = "Save fail"
        render(view:'create', model: [step:2]);
    }

    def editDiabeticRule(){
        def generalRule = GeneralRule.get(params.long('id'))
        def mealPlan = GeneralRuleMealPlan.findByGeneralRuleIdAndActiveStatus(generalRule.id,ActiveStatus.ACTIVE)
        def editDos = GeneralRuleDosDonts.findAllByGeneralRuleIdAndActiveStatusAndDosDoNtsFlag(generalRule.id,ActiveStatus.ACTIVE,'Dos')
        def editDonts = GeneralRuleDosDonts.findAllByGeneralRuleIdAndActiveStatusAndDosDoNtsFlag(generalRule.id,ActiveStatus.ACTIVE,'Donts')
        def complicationList = GeneralRuleComplications.findAllByGeneralRuleIdAndActiveStatus(generalRule.id,ActiveStatus.ACTIVE)
        def exercisePlaList = GeneralRuleExercisePlan.findAllByActiveStatusAndGeneralRuleId(ActiveStatus.ACTIVE, generalRule.id)
        ArrayList<Map> exerciseList = new ArrayList<Map>()
        for(GeneralRuleExercisePlan generalRuleExercisePlan : exercisePlaList) {
            ExerciseLibrary exerciseLibrary = ExerciseLibrary.read(generalRuleExercisePlan.exerciseId)
            def eachExercisePan = [ generalRuleId: generalRuleExercisePlan.generalRuleId,
                                    exerciseName: exerciseLibrary.name,
                                    exerciseId: generalRuleExercisePlan.exerciseId,
                                    period: generalRuleExercisePlan.period
            ]
            exerciseList.add(eachExercisePan)
        }
     render(view:'create', model: [exercisePlaList: exerciseList, basicInfo:generalRule,mealPlan:mealPlan,editDos:editDos,editDonts:editDonts,complicationList:complicationList]);
    }





    def saveExercisePlan() {
        JSONObject json = JSON.parse(request.JSON.toString())
        def responseData = diabeticRuleService.saveExercisePlan(json)
        render responseData as JSON
    }
}
