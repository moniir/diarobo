package com.diarobo.admin

import com.diarobo.CommonUtils
import com.diarobo.enums.ActiveStatus
import grails.converters.JSON
import grails.plugin.springsecurity.annotation.Secured
import org.grails.web.json.JSONObject

@Secured(CommonUtils.ROLE_ADMIN)
class ExercisePackageController {

    def exercisePackageService

    def index() {}

    def create(){
        switch (request.method) {
            case 'GET':
                Integer exercisePackageId = params.getInt('exercisePackageId')
                def exercisePackageElementList
                def exercisePackageList = new ArrayList<Map>()
                ExercisePackage exercisePackage
                if(exercisePackageId) {
                    exercisePackage = ExercisePackage.findById(exercisePackageId)
                    if(exercisePackage) {
                        exercisePackageElementList= ExercisePackageElement.findAllByPackageId(exercisePackageId)
                        for(ExercisePackageElement element: exercisePackageElementList) {
                            ExerciseLibrary exerciseLibrary = ExerciseLibrary.findById(element.exerciseLibraryId)
                            def exercise = [
                                    exerciseName: exerciseLibrary.name,
                                    quantity: element.quantity,
                                    measurementUnit: 'Hour'
                            ]
                            exercisePackageList.add(exercise)
                        }
                    }
                }
                render(view: 'create', model: [exercisePackageId: exercisePackageId, packageName: exercisePackage?.packageName, exercisePackageList: exercisePackageList])
                break
            case 'POST':
                JSONObject exercisePackageJson = JSON.parse(request.JSON.toString())
                def responseData = exercisePackageService.saveExercisePackage(exercisePackageJson)
                render responseData as JSON
                break
        }
    }

    def list() {
        LinkedHashMap gridData
        String result
        LinkedHashMap resultMap = exercisePackageService.paginationList(params)
        println(params);
        if (!resultMap || resultMap.totalCount == 0) {
            gridData = [iTotalRecords: 0, iTotalDisplayRecords: 0, aaData: []]
            result = gridData as JSON
            render result
            return
        }
        int totalCount = resultMap.totalCount
        gridData = [iTotalRecords: totalCount, iTotalDisplayRecords: totalCount, aaData: resultMap.results]
        result = gridData as JSON
        render result
    }

    def edit(Integer referenceId) {
        println(referenceId)
        render(view: 'edit')
    }

    def searchExerciseName(){
        def outPut = exercisePackageService.searchExerciseName(params.q)
        render outPut
    }

    def delete(Long id) {
        LinkedHashMap result = new LinkedHashMap()
        String outPut
        ExercisePackage exercisePackage = ExercisePackage.get(id)
        if (!exercisePackage) {
            result.put(CommonUtils.IS_ERROR,Boolean.TRUE)
            result.put(CommonUtils.MESSAGE,"Object not found. Please refresh browser and try again")
            outPut = result as JSON
            render outPut
            return
        }
        String message
        exercisePackage.activeStatus=ActiveStatus.DELETED
        exercisePackage.save(flush: true)
        result.put(CommonUtils.IS_ERROR, Boolean.FALSE)
        result.put(CommonUtils.MESSAGE, "Exercise Package deleted successfully.")
        outPut = result as JSON
        render outPut
    }

    def getExerciseListByPackage(Integer exercisePackageId) {
        def responseData = exercisePackageService.getExerciseListByPackage(exercisePackageId)
        render responseData as JSON
    }

    def searchNameByKeyword() {
        LinkedHashMap result = new LinkedHashMap()
        String searchStr = params.q
        def exerciseList = ExercisePackage.findAllByActiveStatusAndPackageNameIlike(ActiveStatus.ACTIVE, "%"+searchStr+"%")
        result.put('isError', Boolean.FALSE)
        result.put('results', exerciseList)
        String outPut = result as JSON
        render outPut
    }
}
