package com.diarobo

import com.diarobo.admin.ExerciseLibrary
import com.diarobo.admin.ExercisePackage
import com.diarobo.admin.GroupItem
import com.diarobo.enums.ActiveStatus
import grails.converters.JSON
import grails.web.servlet.mvc.GrailsParameterMap

class ExerciseService {
    static transactional = false

    static final String[] sortColumns = ['id', 'name', 'weightMeasure', 'measureUnit', 'calorieBurn', 'perInMeasure', 'notApplicable', 'image']

    LinkedHashMap paginationList(GrailsParameterMap params) {
        Map serverParams = CommonUtils.getPaginationParams(params, sortColumns)
        int iDisplayStart= serverParams.iDisplayStart.toInteger();
        int  iDisplayLength= serverParams.iDisplayLength.toInteger()

        String sSortDir=serverParams.sSortDir;
        String sortColumn=serverParams.sortColumn
        String sSearch=serverParams.sSearch

        def c = ExerciseLibrary.createCriteria()
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
                dataReturns.add([DT_RowId: obj.id, 0: serial, 1: obj.name, 2: obj.measureUnit, 3: obj.weightMeasure, 4: obj.calorieBurn, 5: obj.perInMeasure ,6:''])

            }

        }
        return [totalCount:totalCount, results:dataReturns]
    }

    def parentDropDownList(String itemType){
        def keyItems = ExerciseLibrary.findAllByName(itemType)

        List dataReturns = new ArrayList()
        for (obj in keyItems) {
            dataReturns.add([id: obj.id, name: obj.name])
        }
        return dataReturns
    }

    def searchNameByKeyword(String searchStr, String item_type) {
        LinkedHashMap result = new LinkedHashMap()

        def groupItem = ExerciseLibrary.findAllByName("%"+searchStr+"%", item_type)
        result.put('isError', Boolean.FALSE)
        result.put('results', groupItem)
        String outPut = result as JSON
        return outPut
    }

    def getGroupItemByName(String name) {
        return GroupItem.findByName(name);
    }


    def groupListByItemTypeAndName(String itemType, String name) {
        List dataReturns = new ArrayList()
        ExerciseLibrary groupItem = ExerciseLibrary.findByName(name)
        def groupItemList = ExerciseLibrary.findAllByName(name)
        for (obj in groupItemList) {
            dataReturns.add([id: obj.id, name: obj.name])
        }
        dataReturns
    }

    def getExerciseDropdownList() {
        ArrayList<Map> result = new ArrayList<Map>()
        def exerciseList = ExerciseLibrary.findAllByActiveStatus(ActiveStatus.ACTIVE)
        for(ExerciseLibrary library : exerciseList) {
            result.add([id: library.id, name: library.name + ' - ' + library.measureUnit])
        }
        result
    }

    def getExercisePackageList() {
        ArrayList<Map> result = new ArrayList<Map>()
        def exerciseList = ExercisePackage.findAllByActiveStatus(ActiveStatus.ACTIVE)
        for(ExercisePackage library : exerciseList) {
            result.add([id: library.id, name: library.packageName])
        }
        result
    }
}
