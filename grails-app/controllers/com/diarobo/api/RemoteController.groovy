package com.diarobo.api

import com.diarobo.CommonUtils
import com.diarobo.admin.FoodPackage
import com.diarobo.admin.GroupItem
import com.diarobo.enums.ActiveStatus
import com.diarobo.enums.MasterKeyValue
import grails.converters.JSON
import grails.plugin.springsecurity.annotation.Secured

@Secured("permitAll")
class RemoteController {
    def groupItemService
    def masterKeyService

    def listGrupItemDropDown(){
        if (!request.method.equals('POST')) {
            redirect(action: 'index')
            return
        }
        LinkedHashMap result = new LinkedHashMap()
        String outPut

        def groupItemList = groupItemService.parentDropDownList(params.itemType)

        if(!groupItemList){
            result.put(CommonUtils.IS_ERROR, Boolean.TRUE)
            result.put(CommonUtils.MESSAGE, "No item added")
            outPut = result as JSON
            render outPut
            return
        }
        result.put(CommonUtils.IS_ERROR, Boolean.FALSE)
        result.put('groupItemList', groupItemList)
        outPut = result as JSON
        render outPut
        return
    }


    def groupTypeDropDown(){
        if (!request.method.equals('POST')) {
            redirect(action: 'index')
            return
        }
        LinkedHashMap result = new LinkedHashMap()
        String outPut

       def groupTypeList = masterKeyService.keyDropDownList(MasterKeyValue.GROUPITEM_TYPE.key)

        if(!groupTypeList){
            result.put(CommonUtils.IS_ERROR, Boolean.TRUE)
            result.put(CommonUtils.MESSAGE, "No item added")
            outPut = result as JSON
            render outPut
            return
        }
        result.put(CommonUtils.IS_ERROR, Boolean.FALSE)
        result.put('groupItemList', groupTypeList)
        outPut = result as JSON
        render outPut
        return
    }

    def addGroupType(){

        if (!request.method.equals('POST')) {
            redirect(action: 'index')
            return
        }
        GroupItem groupItem
        LinkedHashMap result = new LinkedHashMap()
        String outPut
        String message="Save Successfully"
        groupItem = new GroupItem()
        groupItem.itemType=params.itemType
        groupItem.name=params.itemType
        groupItem.parentId=params.long('parentId')

          if(groupItem.hasErrors() || !groupItem.save(Flush:true)){
            message = "Save Fail"
            result.put(CommonUtils.IS_ERROR, Boolean.TRUE)
            result.put(CommonUtils.MESSAGE,message)
            outPut=result as JSON
            render outPut
           return

        }

        result.put(CommonUtils.IS_ERROR,Boolean.FALSE)
        result.put(CommonUtils.MESSAGE,message)
        outPut = result as JSON
        render outPut
    }
    def static foodPackageList(){
             return FoodPackage.findAllByActiveStatus(ActiveStatus.ACTIVE)
     }

    def loadModal(String modalName){
        render(view: modalName)
    }
}
