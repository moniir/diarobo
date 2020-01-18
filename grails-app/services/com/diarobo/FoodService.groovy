package com.diarobo

import com.diarobo.admin.Composition
import com.diarobo.admin.FoodLibrary
import com.diarobo.admin.GroupItem
import com.diarobo.admin.Nutrition
import com.diarobo.enums.ActiveStatus
import com.diarobo.enums.GroupItems
import grails.converters.JSON
import grails.web.servlet.mvc.GrailsParameterMap

class FoodService {
    static transactional = false

    static final String[] sortColumns = ['id', 'name', 'weightMeasure', 'measureUnit','approximateWeight','']
    LinkedHashMap paginationList(GrailsParameterMap params) {
        Map serverParams = CommonUtils.getPaginationParams(params, sortColumns)
        int iDisplayStart= serverParams.iDisplayStart.toInteger();
        int  iDisplayLength= serverParams.iDisplayLength.toInteger()

        String sSortDir=serverParams.sSortDir;
        String sortColumn=serverParams.sortColumn
        String sSearch=serverParams.sSearch

        def c = FoodLibrary.createCriteria()
        def results = c.list(max: iDisplayLength, offset: iDisplayStart) {

            and {
                eq("activeStatus", ActiveStatus.ACTIVE)
            }

            if (sSearch) {
                or {
                    ilike('name', sSearch)
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
                dataReturns.add([DT_RowId: obj.id, 0: serial, 1: obj.name, 2: obj.weightMeasure, 3: obj.measureUnit, 4:obj.approximateWeight, 5:''])
            }

        }
        return [totalCount:totalCount, results:dataReturns]
    }

    def parentDropDownList(String itemType){
        def keyItems = FoodLibrary.findAllByName(itemType)

        List dataReturns = new ArrayList()
        for (obj in keyItems) {
            dataReturns.add([id: obj.id, name: obj.name])
        }
        return dataReturns
    }

    def searchNameByKeyword(String searchStr, String item_type) {
        LinkedHashMap result = new LinkedHashMap()

        def listItem = FoodLibrary.findAllByNameIsNotNull("%"+searchStr+"%", item_type)
        result.put('isError', Boolean.FALSE)
        result.put('results', listItem)
        String outPut = result as JSON
        return outPut
    }

    def groupListByItemTypeAndName(String itemType, String name) {
        List dataReturns = new ArrayList()
        FoodLibrary foodLibrary = FoodLibrary.findByName(itemType, name)
        def groupItemList = FoodLibrary.findAllByName(foodLibrary.id)
        for (obj in groupItemList) {
            dataReturns.add([id: obj.id, name: obj.name])
        }
        dataReturns
    }

    def deleteNutrationUnnecessary(ArrayList<Nutrition> nutritionArrayList) {
        if(nutritionArrayList.size() > 0) {
            def nutrationList = Nutrition.findAllByFoodLibraryIdAndActiveStatus(nutritionArrayList.get(0).foodLibraryId, ActiveStatus.ACTIVE)
            if(nutrationList) {
                for (Nutrition eachNutration : nutrationList) {
                    boolean needTobeDelete = true
                    for(Nutrition nutrition : nutritionArrayList) {
                        if(nutrition.name == eachNutration.name) {
                            needTobeDelete = false
                            break
                        }
                    }
                    if(needTobeDelete) {
                        eachNutration.activeStatus = ActiveStatus.DELETED
                        eachNutration.save(flush: true)
                    }
                }
            }
        }
    }


    def deleteCompositionUnnecessary(ArrayList<Composition> compositionArrayList) {
        if(compositionArrayList.size() > 0) {
            def compositionList = Composition.findAllByFoodLibraryIdAndActiveStatus(compositionArrayList.get(0).foodLibraryId, ActiveStatus.ACTIVE)
            if(compositionList) {
                for (Composition eachComposition : compositionList) {
                    boolean needTobeDelete = true
                    for(Composition composition : compositionArrayList) {
                        if(composition.name == eachComposition.name) {
                            needTobeDelete = false
                            break
                        }
                    }
                    if(needTobeDelete) {
                        eachComposition.activeStatus = ActiveStatus.DELETED
                        eachComposition.save(flush: true)
                    }
                }
            }
        }
    }
}
