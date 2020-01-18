package com.diarobo.admin

import com.diarobo.CommonUtils
import com.diarobo.enums.ActiveStatus
import com.diarobo.enums.MasterKeyValue
import grails.converters.JSON
import grails.plugin.springsecurity.annotation.Secured
import org.springframework.dao.DataIntegrityViolationException

@Secured(CommonUtils.ROLE_ADMIN)
class SingleItemController {
    def masterKeyService
    def listItemService
    private com.diarobo.enums.ActiveStatus deleted


    def index() { }

    def create(ListItem listItemInstance){
        def listType = masterKeyService.keyDropDownList(MasterKeyValue.GROUPITEM_TYPE.key)
        switch (request.method) {
            case 'GET':
                render(view: 'create', model: [groupTypeList: listType])
                return
                break
            case 'POST':
                println(params)
                if (listItemInstance.hasErrors()) {
                    render(view: 'create', model: [listItemInstance: listItemInstance, listType: listType])
                    return
                }
                ListItem savedItem = listItemInstance.save(flush: true)
                render(view: 'create', model: [listType: listType])
                break
        }
    }

    def list() {
        LinkedHashMap gridData
        String result
        LinkedHashMap resultMap =listItemService.paginationList(params)

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
        ListItem listItem = ListItem.get(id)
        if (!listItem) {
            result.put(CommonUtils.IS_ERROR,Boolean.TRUE)
            result.put(CommonUtils.MESSAGE,"Object not found. Please refresh browser and try again")
            outPut = result as JSON
            render outPut
            return
        }
        String message
        listItem.activeStatus=ActiveStatus.DELETED
        listItem.save(flush: true)
        result.put(CommonUtils.IS_ERROR, Boolean.FALSE)
        result.put(CommonUtils.MESSAGE, "listItem deleted successfully.")
        outPut = result as JSON
        render outPut
    }
}