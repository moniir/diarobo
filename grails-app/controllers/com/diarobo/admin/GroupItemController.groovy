package com.diarobo.admin

import com.diarobo.CommonUtils
import com.diarobo.enums.ActiveStatus
import com.diarobo.enums.MasterKeyValue
import grails.converters.JSON
import grails.plugin.springsecurity.annotation.Secured

@Secured(CommonUtils.ROLE_ADMIN)
class GroupItemController {
    def masterKeyService
    def groupItemService

    def index() {
        def groupTypeList = masterKeyService.keyDropDownList(MasterKeyValue.GROUPITEM_TYPE.key)
        render(view: 'index', model: [groupTypeList: groupTypeList])
    }

    def create(GroupItem groupItemInstance){
        def groupTypeList = masterKeyService.keyDropDownList(MasterKeyValue.GROUPITEM_TYPE.key)
        switch (request.method) {
            case 'GET':
                render(view: 'create', model: [groupTypeList: groupTypeList])
                return
                break
            case 'POST':
                if (groupItemInstance.hasErrors()) {
                    render(view: 'create', model: [groupItemInstance: groupItemInstance, groupTypeList: groupTypeList])
                    return
                }
                groupItemInstance.save(flush: true)
                render(view: 'create', model: [groupTypeList: groupTypeList])
                break
        }
    }
    def list() {
        LinkedHashMap gridData
        String result
        LinkedHashMap resultMap =groupItemService.paginationList(params)

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

    def delete(Long id) {

        LinkedHashMap result = new LinkedHashMap()
        String outPut
        GroupItem groupItem =GroupItem.get(id)
        if (!groupItem) {
            result.put(CommonUtils.IS_ERROR,Boolean.TRUE)
            result.put(CommonUtils.MESSAGE,"Object not found. Please refresh browser and try again")
            outPut = result as JSON
            render outPut
            return
        }
        String message
        groupItem.activeStatus=ActiveStatus.DELETED
        groupItem.save(flush: true)
        result.put(CommonUtils.IS_ERROR, Boolean.FALSE)
        result.put(CommonUtils.MESSAGE, "GroupItem deleted successfully.")
        outPut = result as JSON
        render outPut
    }

    def edit(Long id) {
        if (!request.method.equals('POST')) {
            redirect(action: 'index')
            return
        }
        LinkedHashMap result = new LinkedHashMap()
        String outPut
        GroupItem groupItem = GroupItem.read(id)
        if (!groupItem) {
            result.put(CommonUtils.IS_ERROR, Boolean.TRUE)
            result.put(CommonUtils.MESSAGE, " Object not found. Please refresh browser and try again ")
            outPut = result as JSON
            render outPut
            return
        }
        result.put(CommonUtils.IS_ERROR, Boolean.FALSE)
        result.put('obj', groupItem)
        outPut = result as JSON
        render outPut
    }

}
