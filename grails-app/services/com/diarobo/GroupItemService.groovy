package com.diarobo

import com.diarobo.admin.Composition
import com.diarobo.admin.FoodLibrary
import com.diarobo.admin.GroupItem
import com.diarobo.admin.Nutrition
import com.diarobo.enums.ActiveStatus
import com.diarobo.enums.GroupItems
import grails.converters.JSON
import grails.web.servlet.mvc.GrailsParameterMap
import org.springframework.web.servlet.FlashMap

class GroupItemService {
    static transactional = false

    static final String[] sortColumns = ['id', 'parentId', 'name']
    LinkedHashMap paginationList(GrailsParameterMap params) {
        Map serverParams = CommonUtils.getPaginationParams(params, sortColumns)
        int iDisplayStart= serverParams.iDisplayStart.toInteger();
        int  iDisplayLength= serverParams.iDisplayLength.toInteger()

        String sSortDir=serverParams.sSortDir;
        String sortColumn=serverParams.sortColumn
        String sSearch=serverParams.sSearch
        def c = GroupItem.createCriteria()
        def results = c.list(max: iDisplayLength, offset: iDisplayStart) {
            and {
                eq("activeStatus", ActiveStatus.ACTIVE)
            }
            if (params.groupType){
                and {
                    eq("itemType", params.groupType)
                }
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
            GroupItem parentItem
            for (obj in results) {
                if (serverParams.sSortDir.equals(CommonUtils.SORT_ORDER_ASC)) {
                    serial++
                } else {
                    serial--
                }
                if (obj.parentId) {
                    parentItem = GroupItem.read(obj.parentId)
                }
                dataReturns.add([DT_RowId: obj.id, 0: serial, 1: obj.itemType, 2: parentItem ? parentItem.name: '', 3: obj.name, 4:''])
            }

        }
        return [totalCount:totalCount, results:dataReturns]
    }

    def parentDropDownList(String itemType){
        def keyItems = GroupItem.findAllByItemTypeAndParentIdIsNull(itemType)

        List dataReturns = new ArrayList()
        for (obj in keyItems) {
            dataReturns.add([id: obj.id, name: obj.name])
        }
        return dataReturns
    }
    def searchNameByKeyword(String searchStr, String item_type) {
        LinkedHashMap result = new LinkedHashMap()

        def groupItem = GroupItem.findAllByNameLikeAndItemTypeAndParentIdIsNull("%"+searchStr+"%", item_type)
        result.put('isError', Boolean.FALSE)
        result.put('results', groupItem)
        String outPut = result as JSON
        return outPut
    }
    def firstNodeNameByKeyword(String searchStr, String item_type) {
        LinkedHashMap result = new LinkedHashMap()
        def groupItem = GroupItem.findAllByNameLikeAndItemTypeAndParentIdIsNull("%"+searchStr+"%", item_type)
        result.put('isError', Boolean.FALSE)
        result.put('results', groupItem)
        String outPut = result as JSON
        return outPut
    }

    def searchFoodName(String searchStr) {
        LinkedHashMap result = new LinkedHashMap()
        def foodLibrary = FoodLibrary.findAllByNameIlikeAndActiveStatus("%"+searchStr+"%",ActiveStatus.ACTIVE)
        result.put('isError', Boolean.FALSE)
        result.put('results', foodLibrary)
        String outPut = result as JSON
        return outPut
    }

    def searchNutritionName(String searchStr) {
        LinkedHashMap result = new LinkedHashMap()
        def foodNutrition = Nutrition.findAllByNameIlikeAndActiveStatus("%"+searchStr+"%",ActiveStatus.ACTIVE)
        result.put('isError', Boolean.FALSE)
        result.put('results', foodNutrition)
        String outPut = result as JSON
        return outPut
    }

    def searchCompositionName(String searchStr) {
        LinkedHashMap result = new LinkedHashMap()
        def foodComposition = Composition.findAllByNameIlikeAndActiveStatus("%"+searchStr+"%",ActiveStatus.ACTIVE)
        result.put('isError', Boolean.FALSE)
        result.put('results', foodComposition)
        String outPut = result as JSON
        return outPut
    }

//    GroupItem findOrCreateFirstNode(String itemType, String name) {
//        GroupItem groupItem = GroupItem.findByNameAndItemTypeAndActiveStatus(name, itemType, ActiveStatus.ACTIVE);
//        if (groupItem) return groupItem
//        return new GroupItem(name: name, itemType: itemType).save()
//    }
    GroupItem findOrCreateFirstNode(String itemType, String paramName) {
        GroupItem groupItem = GroupItem.findByNameAndItemTypeAndActiveStatus(paramName, itemType, ActiveStatus.ACTIVE);
        if (groupItem) return groupItem
        return new GroupItem(name: paramName, itemType: itemType).save()
    }

    GroupItem findOrCreateFirstNodeExcercise(String itemType, String paramName) {
        GroupItem groupItem = GroupItem.findByNameAndItemTypeAndActiveStatus(paramName, itemType, ActiveStatus.ACTIVE);
        if (groupItem) return groupItem
        return new GroupItem(name: paramName, itemType: itemType).save()
    }

    def getGroupItemByName(String name ) {
       return GroupItem.findByName(name);
    }

    def groupListByItemTypeAndName(String itemType, String name) {
        List dataReturns = new ArrayList()
        GroupItem groupItem = GroupItem.findByItemTypeAndName(itemType, name)
        def groupItemList = GroupItem.findAllByParentId(groupItem.id)
        for (obj in groupItemList) {
            dataReturns.add([id: obj.id, name: obj.name])
        }
        dataReturns
    }
}
