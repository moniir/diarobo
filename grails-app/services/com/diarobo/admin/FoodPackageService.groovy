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
class FoodPackageService {

    def springSecurityService

    static final String[] sortColumns = ['id','packageName']

    def saveFoodPackage(JSONObject foodPackageJson) {
        def responseData
        if(foodPackageJson.packageName) {
            User currentUser = springSecurityService.currentUser
            Long foodPackageId = foodPackageJson.optLong('foodPackageId')
            FoodPackage foodPackage = FoodPackage.findById(foodPackageId)
            if(!foodPackage) {
                foodPackage = new FoodPackage(packageName: foodPackageJson.packageName, createdBy: currentUser.username)
                JSONArray foodList = new JSONArray(foodPackageJson.foodList)
                if(foodList) {
                    if(checkingAllFoodValid(foodList)) {
                        foodPackage.save(flush: true)
                        for (JSONObject food : foodList) {
                            FoodLibrary foodLibrary = FoodLibrary.findById(food.foodLibraryId)
                            FoodPackageElement element = new FoodPackageElement(foodLibraryId: foodLibrary.id, quantity: food.quantity,
                                    packageId: foodPackage.id, createdBy: currentUser.username)
                            element.save()
                            responseData = ['hasError': false, 'message': 'Successfully saved.']
                            return responseData
                        }
                    } else {
                        responseData = ['hasError': true, 'message': 'Not Allow Custom food name.']
                        return responseData
                    }
                } else {
                    responseData = ['hasError': true, 'message': 'First Add Food & then try again.']
                    return responseData
                }
            } else {
                foodPackage.packageName = foodPackageJson.packageName
                foodPackage.lastUpdatedBy = currentUser.username
                foodPackage.lastUpdatedDate = new Date()
                foodPackage.save(flush: true)
                JSONArray foodList = new JSONArray(foodPackageJson.foodList)
                for (JSONObject food : foodList) {
                    FoodLibrary foodLibrary = FoodLibrary.findById(food.foodLibraryId)
                    FoodPackageElement element
                    element = FoodPackageElement.findByFoodLibraryIdAndPackageId(foodLibrary.id, foodPackage.id)
                    if(element) {
                        element.quantity = food.quantity
                        element.lastUpdatedBy = currentUser.username
                        element.lastUpdatedDate = new Date()
                        element.save()
                    } else {
                        element = new FoodPackageElement(foodLibraryId: foodLibrary.id, quantity: food.quantity,
                                packageId: foodPackage.id, createdBy: currentUser.username)
                        element.save()
                    }
                }
                def foodElementList = FoodPackageElement.findAllByPackageIdAndActiveStatus(foodPackage.id, ActiveStatus.ACTIVE)
                for (FoodPackageElement packageElement: foodElementList) {
                    boolean needTobeDelete = true
                    for (JSONObject food : foodList) {
                        if(food.foodLibraryId == packageElement.foodLibraryId) {
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
    def checkingAllFoodValid(def foodList) {
        if(foodList) {
            for (JSONObject food : foodList) {
                FoodLibrary foodLibrary = FoodLibrary.findByName(food.foodName)
                if(!foodLibrary)
                    return false
            }
        }
        return true
    }

    def searchFoodName(String query){
        LinkedHashMap result = new LinkedHashMap()
        def foodNameList = FoodLibrary.findAllByActiveStatusAndNameIlike(ActiveStatus.ACTIVE, "%"+query+"%", [max: 10])
        result.put('isError', Boolean.FALSE)
        result.put('results', foodNameList)
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
        def c = FoodPackage.createCriteria()
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

    def getFoodListByPackage(Integer foodPackageId) {
        def responseData
        List<Map> foodList = new ArrayList<Map>()
        if(foodPackageId) {
            FoodPackage foodPackage = FoodPackage.findById(foodPackageId)
            def foodElementList = FoodPackageElement.findAllByPackageId(foodPackageId)
            for(FoodPackageElement packageElement: foodElementList) {
                FoodLibrary foodLibrary = FoodLibrary.findById(packageElement.foodLibraryId)
                def compositionList = Composition.findAllByFoodLibraryId(foodLibrary.id)
                def food = [
                        foodLibraryId: foodLibrary.id,
                        foodName: foodLibrary.name,
                        quantity: packageElement.quantity,
                        measurementUnit: foodLibrary.measureUnit,
                        weightMeasure: foodLibrary.weightMeasure,
                        compositionList: compositionList
                ]
                foodList.add(food)
            }
            responseData = [hasError: false, foodList: foodList, foodPackageName: foodPackage.packageName]
        } else {
            responseData = [hasError: true, foodList: foodList, foodPackageName: null]
        }
        responseData
    }
}
