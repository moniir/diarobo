package com.diarobo.admin

import com.diarobo.CommonUtils
import com.diarobo.User
import com.diarobo.enums.ActiveStatus
import grails.converters.JSON
import grails.transaction.Transactional
import grails.web.servlet.mvc.GrailsParameterMap
import org.grails.web.json.JSONArray
import org.grails.web.json.JSONObject

@Transactional
class ExercisePackageService {

    def springSecurityService

    static final String[] sortColumns = ['id','packageName']

    def saveExercisePackage(JSONObject exercisePackageJson) {
        def responseData
        if(exercisePackageJson.packageName) {
            User currentUser = springSecurityService.currentUser
            Long exercisePackageId = exercisePackageJson.optLong('exercisePackageId')
            ExercisePackage exercisePackage = ExercisePackage.findById(exercisePackageId)
            if(!exercisePackage) {
                exercisePackage = new ExercisePackage(packageName: exercisePackageJson.packageName, createdBy: currentUser.username)
                JSONArray exerciseList = new JSONArray(exercisePackageJson.exerciseList)
                if(exerciseList) {
                    if(checkingAllExerciseValid(exerciseList)) {
                        exercisePackage.save(flush: true)
                        for (JSONObject exercise : exerciseList) {
                            ExerciseLibrary exerciseLibrary = ExerciseLibrary.findByName(exercise.exerciseName)
                            ExercisePackageElement exercisePackageElement = new ExercisePackageElement(exerciseLibraryId: exerciseLibrary.id,
                                    quantity: exercise.optDouble('quantity'), packageId: exercisePackage.id, createdBy: currentUser.username)
                            exercisePackageElement.save(flush: true)
                        }
                        responseData = ['hasError': false, 'message': 'Successfully saved.']
                        return responseData
                    } else {
                        responseData = ['hasError': true, 'message': 'Not Allow Custom exercise exercise.']
                        return responseData
                    }
                } else {
                    responseData = ['hasError': true, 'message': 'First Add Exercise & then try again.']
                    return responseData
                }
            } else {
                exercisePackage.packageName = exercisePackageJson.packageName
                exercisePackage.lastUpdatedBy = currentUser.username
                exercisePackage.lastUpdatedDate = new Date()
                exercisePackage.save(flush: true)
                JSONArray exerciseList = new JSONArray(exercisePackageJson.exerciseList)
                for (JSONObject exercise : exerciseList) {
                    ExerciseLibrary exerciseLibrary = ExerciseLibrary.findById(exercise.exerciseLibraryId)
                    ExercisePackageElement element
                    element = ExercisePackageElement.findByExerciseLibraryIdAndPackageId(exerciseLibrary.id, exercisePackage.id)
                    if(element) {
                        element.quantity = exercise.optDouble('quantity')
                        element.lastUpdatedBy = currentUser.username
                        element.lastUpdatedDate = new Date()
                        element.save()
                    } else {
                        element = new ExercisePackageElement(exerciseLibraryId: exerciseLibrary.id, quantity: exercise.quantity,
                                packageId: exercisePackage.id, createdBy: currentUser.username)
                        element.save()
                    }
                }
                def exerciseElementList = ExercisePackageElement.findAllByPackageIdAndActiveStatus(exercisePackage.id, ActiveStatus.ACTIVE)
                for (ExercisePackageElement packageElement: exerciseElementList) {
                    boolean needTobeDelete = true
                    for (JSONObject exercise : exerciseList) {
                        if(exercise.exerciseLibraryId == packageElement.exerciseLibraryId) {
                            needTobeDelete = false
                        }
                    }
                    if (needTobeDelete) {
                        packageElement.activeStatus = ActiveStatus.DELETED
                        packageElement.lastUpdatedBy = currentUser.username
                        packageElement.lastUpdatedDate = new Date()
                        packageElement.save(flush: true)
                    }
                }
                responseData = ['hasError': false, 'message': 'Successfully Updated.']
                return responseData
            }
        }
        responseData = ['hasError': true, 'message': 'Package name must be inserted.']
        responseData
    }

    def checkingAllExerciseValid(def exerciseList) {
        if(exerciseList) {
            for (JSONObject exercise : exerciseList) {
                ExerciseLibrary exerciseLibrary = ExerciseLibrary.findByName(exercise.exerciseName)
                if(!exerciseLibrary)
                    return false
            }
        }
        return true
    }

    def searchExerciseName(String query){
        LinkedHashMap result = new LinkedHashMap()
        def exerciseNameList = ExerciseLibrary.findAllByActiveStatusAndNameIlike(ActiveStatus.ACTIVE, "%"+query+"%", [max: 10])
        result.put('isError', Boolean.FALSE)
        result.put('results', exerciseNameList)
        String outPut = result as JSON
        return outPut
    }

    LinkedHashMap paginationList(GrailsParameterMap params) {
        Map serverParams = CommonUtils.getPaginationParams(params, sortColumns)
        int iDisplayStart= serverParams.iDisplayStart.toInteger();
        int  iDisplayLength= serverParams.iDisplayLength.toInteger()

        String sSortDir=serverParams.sSortDir;
        String sortColumn=serverParams.sortColumn
        String sSearch=serverParams.sSearch
        def c = ExercisePackage.createCriteria()
        def results = c.list(max: iDisplayLength, offset: iDisplayStart) {

            and{
                eq('activeStatus', ActiveStatus.ACTIVE)
            }
            if (sSearch) {
                or {

                    ilike('packageName', sSearch)
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
                dataReturns.add([DT_RowId: obj.id, 0: serial, 1:obj.packageName, 2:""])
            }
        }
        return [totalCount:totalCount, results:dataReturns]
    }

    def getExerciseListByPackage(Integer exercisePackageId) {
        def responseData
        List<Map> exerciseList = new ArrayList<Map>()
        if(exercisePackageId) {
            ExercisePackage exercisePackage = ExercisePackage.findById(exercisePackageId)
            def exerciseElementList = ExercisePackageElement.findAllByPackageId(exercisePackageId)
            for(ExercisePackageElement packageElement: exerciseElementList) {
                ExerciseLibrary exerciseLibrary = ExerciseLibrary.findById(packageElement.exerciseLibraryId)
                def exercise = [
                        exerciseLibraryId: exerciseLibrary.id,
                        exerciseName: exerciseLibrary.name,
                        quantity: packageElement.quantity,
                        measurementUnit:  'Hour',
                        weightMeasure: exerciseLibrary.weightMeasure
                ]
                exerciseList.add(exercise)
            }
            responseData = [hasError: false, exerciseList: exerciseList, exercisePackageName: exercisePackage.packageName]
        } else {
            responseData = [hasError: true, exerciseList: exerciseList, exercisePackageName: null]
        }
        responseData
    }
}
