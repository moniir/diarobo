package com.diarobo

import com.diarobo.admin.GroupItem
import com.diarobo.admin.ListItem
import com.diarobo.enums.ActiveStatus
import grails.converters.JSON
import grails.web.servlet.mvc.GrailsParameterMap

class ListItemService {
    static transactional = false

    static final String[] sortColumns = ['id', 'libraryType', 'name', 'keyName']
    LinkedHashMap paginationList(GrailsParameterMap params) {
        Map serverParams = CommonUtils.getPaginationParams(params, sortColumns)
        int iDisplayStart= serverParams.iDisplayStart.toInteger();
        int  iDisplayLength= serverParams.iDisplayLength.toInteger()

        String sSortDir=serverParams.sSortDir;
        String sortColumn=serverParams.sortColumn
        String sSearch=serverParams.sSearch

        def c = ListItem.createCriteria()
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
            for (obj in results) {
                if (serverParams.sSortDir.equals(CommonUtils.SORT_ORDER_ASC)) {
                    serial++
                } else {
                    serial--
                }
                dataReturns.add([DT_RowId: obj.id, 0: serial, 1: obj.libraryType, 2: obj.name, 3: obj.keyName, 4:''])
            }

        }
        return [totalCount:totalCount, results:dataReturns]
    }

    def parentDropDownList(String itemType){
        def keyItems = ListItem.findAllByLibraryTypeAndKeyName(itemType)

        List dataReturns = new ArrayList()
        for (obj in keyItems) {
            dataReturns.add([id: obj.id, name: obj.name])
        }
        return dataReturns
    }

    def searchNameByKeyword(String searchStr, String item_type) {
        LinkedHashMap result = new LinkedHashMap()

        def listItem = ListItem.findAllByNameLikeAndLibraryTypeAndKeyNameIsNotNull("%"+searchStr+"%", item_type)
        result.put('isError', Boolean.FALSE)
        result.put('results', listItem)
        String outPut = result as JSON
        return outPut
    }

    def getGroupItemByName(String name) {
        return ListItem.findByName(name);
    }


    def groupListByItemTypeAndName(String itemType, String name) {
        List dataReturns = new ArrayList()
        ListItem listItem = ListItem.findByLibraryTypeAndName(itemType, name)
        def groupItemList = ListItem.findAllByKeyName(listItem.id)
        for (obj in groupItemList) {
            dataReturns.add([id: obj.id, name: obj.name])
        }
        dataReturns
    }
}
