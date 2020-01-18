package com.diarobo.admin

import com.diarobo.CommonUtils
import com.diarobo.Picture
import com.diarobo.User
import com.diarobo.enums.ActiveStatus
import com.diarobo.enums.GroupItems
import com.diarobo.enums.MasterKeyValue
import grails.converters.JSON
import grails.plugin.springsecurity.annotation.Secured

@Secured(CommonUtils.ROLE_ADMIN)
class ExerciseController {
    def masterKeyService
    def groupItemService
    def springSecurityService
    def pictureService
    def exerciseService

    def index() { }
    def create(){
        def measurementUnits = masterKeyService.keyDropDownList(MasterKeyValue.EXERCISE_MU.value)
        def exerciseTypes = masterKeyService.keyDropDownList(MasterKeyValue.EXERCISE_TYPE.value)

        switch (request.method) {
            case 'GET':
                render(view: 'create', model: [measurementUnits: measurementUnits, exerciseTypes: exerciseTypes])
                return
                break
            case 'POST':
                def responseData
                ExerciseLibrary exercise = ExerciseLibrary.findById(params.id)
                if(!exercise) {
                    exercise = new ExerciseLibrary(name: params.name, measureUnit: params.measureUnit,
                            calorieBurn: params.calorieBurn, perInMeasure: params.perInMeasure, weightMeasure: params.weightMeasure)
                    exercise.save(flush: true)
                    if(params.notApplicableIds) {
                        def notApplicableArray = params.notApplicable.split('~')
                        def notApplicableIdArray = params.notApplicableIds.split('~')
                        for(int it = 0 ; it< notApplicableArray.size(); it++) {
                            if(notApplicableArray[it]) {
                                NotApplicableExercise applicableExercise = NotApplicableExercise.findById(notApplicableIdArray[it])
                                if(applicableExercise) {
                                    if(applicableExercise.activeStatus == false) {
                                        applicableExercise.activeStatus = true
                                        applicableExercise.save(flush: true)
                                    }
                                } else {
                                    applicableExercise = new NotApplicableExercise(exerciseId: exercise.id, name: notApplicableArray[it])
                                    applicableExercise.save(flush: true)
                                }
                            }
                        }
                    }
                    def file = request.getFile('image')
                    if(file) {
                        if(pictureService.uploadUnSucessful(exercise.id, 'exercise_library', 'library',file)) {
                            responseData = ['hasError': true, 'message': 'Upload Error.']
                            render responseData as JSON
                            return
                        }
                    }
                    responseData = ['hasError': false, 'message': 'Successfully Saved.']
                } else {
                    responseData = ['hasError': true, 'message': 'Already Exist']
                }
            render responseData as JSON
            break
        }
    }

    def update(){
        ExerciseLibrary exercise = ExerciseLibrary.findById(params.id)
        def responseData
        if(exercise) {
            Double calorieBurn = params.getDouble("calorieBurn")
            Double perInMeasure = params.getDouble("perInMeasure")
            String massage
            exercise.name = params.name
            exercise.measureUnit= params.measureUnit
            exercise.calorieBurn= calorieBurn
            exercise.perInMeasure= perInMeasure
            exercise.weightMeasure= params.weightMeasure
            exercise.activeStatus = ActiveStatus.ACTIVE
            exercise.save(flush: true)
            if(params.notApplicableIds) {
                def notApplicableArray = params.notApplicable.split('~')
                def notApplicableIdArray = params.notApplicableIds.split('~')
                def notApplicableList = NotApplicableExercise.findAllByActiveStatusAndExerciseId(ActiveStatus.ACTIVE, exercise.id)
                for(NotApplicableExercise notApplicableExercise: notApplicableList) {
                    boolean needToBeDelete = true
                    for(int it = 0 ; it< notApplicableIdArray.size(); it++) {
                        String strId = notApplicableIdArray[it]
                        Integer notApplicableId = Integer.parseInt(strId)
                        if(notApplicableExercise.id == notApplicableId) {
                            needToBeDelete = false
                            break
                        }
                    }
                    if (needToBeDelete) {
                        notApplicableExercise.activeStatus = ActiveStatus.DELETED
                        notApplicableExercise.save(flush: true)
                    }
                }

                for(int it = 0 ; it< notApplicableArray.size(); it++) {
                    if(notApplicableArray[it]) {
                        NotApplicableExercise applicableExercise = NotApplicableExercise.findById(notApplicableIdArray[it])
                        if(applicableExercise) {
                            if(applicableExercise.activeStatus == false) {
                                applicableExercise.activeStatus = true
                                applicableExercise.save(flush: true)
                            }
                        } else {
                            applicableExercise = new NotApplicableExercise(exerciseId: exercise.id, name: notApplicableArray[it])
                            applicableExercise.save(flush: true)
                        }
                    }
                }
            }
            def file = request.getFile('image')
            if(file) {
                if(pictureService.uploadUnSucessful(exercise.id, 'exercise_library', 'library',file)) {
                    responseData = ['hasError': true, 'message': 'Upload Error.']
                    render responseData as JSON
                    return
                }
            }
            responseData = ['hasError': false, 'message': 'Successfully Updated.']
        } else {
            responseData = ['hasError': true, 'message': 'Not Found']
        }
        render responseData as JSON
    }
    def list() {
        LinkedHashMap gridData
        String result
        LinkedHashMap resultMap =exerciseService.paginationList(params)

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

    def searchNameByKeyword() {
        LinkedHashMap result = new LinkedHashMap()
        String searchStr = params.q
        def exerciseList = ExerciseLibrary.findAllByActiveStatusAndNameIlike(ActiveStatus.ACTIVE, "%"+searchStr+"%")
        result.put('isError', Boolean.FALSE)
        result.put('results', exerciseList)
        String outPut = result as JSON
        render outPut
    }

    def searchNotApplicableByKeyword() {
        String searchStr = params.q
        LinkedHashMap result = new LinkedHashMap()
        def notApplicableList = NotApplicableExercise.findAllByActiveStatusAndNameIlike(ActiveStatus.ACTIVE, "%"+searchStr+"%")
        result.put('isError', Boolean.FALSE)
        result.put('results', notApplicableList)
        String outPut = result as JSON
        render outPut
    }

    def delete(Long id) {
        LinkedHashMap result = new LinkedHashMap()
        String outPut
        ExerciseLibrary excerciseLibrary = ExerciseLibrary.get(id)
        if (!excerciseLibrary) {
            result.put(CommonUtils.IS_ERROR,Boolean.TRUE)
            result.put(CommonUtils.MESSAGE,"Object not found. Please refresh browser and try again")
            outPut = result as JSON
            render outPut
            return
        }
        String message
        excerciseLibrary.activeStatus=ActiveStatus.DELETED
        excerciseLibrary.save(flush: true)
        result.put(CommonUtils.IS_ERROR, Boolean.FALSE)
        result.put(CommonUtils.MESSAGE, "ExerciseLibrary deleted successfully.")
        outPut = result as JSON
        render outPut
    }
    def edit(Integer exerciseId) {
        ExerciseLibrary exerciseLibraryInstance = ExerciseLibrary.findById(exerciseId)
        if (!exerciseLibraryInstance) {
            redirect(action: 'index')
            return
        }
        def natApplicableList = NotApplicableExercise.findAllByExerciseIdAndActiveStatus(exerciseLibraryInstance.id, ActiveStatus.ACTIVE)
        def measurementUnits = masterKeyService.keyDropDownList(MasterKeyValue.EXERCISE_MU.value)
        def exerciseTypes = masterKeyService.keyDropDownList(MasterKeyValue.EXERCISE_TYPE.value)

        render(view: 'edit', model: [measurementUnits: measurementUnits, exerciseTypes: exerciseTypes, exerciseLibraryInstance: exerciseLibraryInstance, natApplicableList: natApplicableList])
    }

    def showImage(ExerciseLibrary exerciseLibrary) {
        if ( exerciseLibrary == null) {
            flash.message = "Exercise library not found."
            redirect (action:'index')
        } else {
            byte[] buffer = pictureService.getPictureInByte(exerciseLibrary.id, 'exercise_library', response)
            if(buffer){
                response.outputStream << buffer
                response.outputStream.flush()
            }  else {
                flash.message = "Exercise library not found."
                redirect (Exercise:'index')
            }
        }
    }

}
