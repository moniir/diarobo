package com.diarobo.admin

import com.diarobo.CommonUtils
import com.diarobo.enums.ActiveStatus
import grails.converters.JSON
import grails.plugin.springsecurity.annotation.Secured
import org.grails.web.json.JSONObject

@Secured(CommonUtils.ROLE_ADMIN)
class FoodPackageController {

    def foodPackageService

    def index() { }

    def create(){
        switch (request.method) {
            case 'GET':
                Integer foodPackageId = params.getInt('foodPackageId')
                def foodPackageElementList
                def foodPackageList = new ArrayList<Map>()
                FoodPackage foodPackage
                if(foodPackageId) {
                    foodPackage = FoodPackage.findById(foodPackageId)
                    if(foodPackage) {
                        foodPackageElementList= FoodPackageElement.findAllByPackageId(foodPackageId)
                        for(FoodPackageElement element: foodPackageElementList) {
                            FoodLibrary foodLibrary = FoodLibrary.findById(element.foodLibraryId)
                            def food = [
                                    foodName: foodLibrary.name,
                                    quantity: element.quantity,
                                    measurementUnit: foodLibrary.measureUnit
                            ]
                            foodPackageList.add(food)
                        }
                    }
                }
                render(view: 'create', model: [foodPackageId: foodPackageId, packageName: foodPackage?.packageName, foodPackageElementList: foodPackageList])
                break
            case 'POST':
                JSONObject foodPackageJson = JSON.parse(request.JSON.toString())
                def responseData = foodPackageService.saveFoodPackage(foodPackageJson)
                render responseData as JSON
                break
        }
    }

    def getPackageList() {

    }

    def searchFoodName() {
        def outPut = foodPackageService.searchFoodName(params.q)
        render outPut
    }

    def list() {
        LinkedHashMap gridData
        String result
        LinkedHashMap resultMap = foodPackageService.paginationList(params)

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

    def delete(Long id) {
        LinkedHashMap result = new LinkedHashMap()
        String outPut
        FoodPackage foodPackage = FoodPackage.get(id)
        if (!foodPackage) {
            result.put(CommonUtils.IS_ERROR,Boolean.TRUE)
            result.put(CommonUtils.MESSAGE,"Object not found. Please refresh browser and try again")
            outPut = result as JSON
            render outPut
            return
        }
        String message
        foodPackage.activeStatus=ActiveStatus.DELETED
        foodPackage.save(flush: true)
        result.put(CommonUtils.IS_ERROR, Boolean.FALSE)
        result.put(CommonUtils.MESSAGE, "Food Package deleted successfully.")
        outPut = result as JSON
        render outPut
    }

    def getFoodListByPackage(Integer foodPackageId) {
        def responseData = foodPackageService.getFoodListByPackage(foodPackageId)
        render responseData as JSON
    }
}
