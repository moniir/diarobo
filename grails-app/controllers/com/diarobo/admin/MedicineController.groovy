package com.diarobo.admin

import com.diarobo.CommonUtils
import com.diarobo.MedCompanyService
import com.diarobo.enums.ActiveStatus
import com.diarobo.enums.MasterKeyValue
import grails.converters.JSON
import grails.plugin.springsecurity.annotation.Secured

@Secured(CommonUtils.ROLE_ADMIN)
class MedicineController {
    def masterKeyService;
    def medCompanyService
    def medicineService

    def index() {}

    def create(MedGeneric medGeneric, MedBrand brand, MedCompany medCompany) {
        def medicineAdminList = masterKeyService.keyDropDownList(MasterKeyValue.DRUG_TYPE.value)
        switch (request.method) {
            case 'GET':
                render(view: 'create', model: [medicineAdminList: medicineAdminList, brand: brand])
                break
            case 'POST':
                def file = request.getFile('image')
                def responseData = medicineService.save(file, medGeneric, brand, medCompany)
                if(responseData.hasError) {
                    flash.message = responseData.message
                    render(view: 'create', model: [medicineAdminList: medicineAdminList, brand: brand])
                } else {
                    flash.message = responseData.message
                    redirect(action: 'index')
                }
                break
        }
    }

    def edit(Long id) {
        MedBrand medBrand = MedBrand.get(id)
        if (!medBrand) {
           redirect(action: 'index')
        } else {
            def medicineAdminList = masterKeyService.keyDropDownList(MasterKeyValue.DRUG_TYPE.value)
            render(view: 'edit', model: [medicineAdminList: medicineAdminList, brand: medBrand])
        }
    }

    def update(MedBrand brand) {
        def medicineAdminList = masterKeyService.keyDropDownList(MasterKeyValue.DRUG_TYPE.value)
        def file = request.getFile('image')
        def responseData = medicineService.update(file, brand, params)
        if(responseData.hasError) {
            flash.message = responseData.message
            render(view: 'create', model: [medicineAdminList: medicineAdminList, brand: brand])
        } else {
            flash.message = responseData.message
            redirect(action: 'index')
        }
    }

    def searchCompanyName() {
        def outPut = medCompanyService.searchCompanyName(params.q)
        render outPut
    }

    def searchGenericName() {
        def outPut = medCompanyService.searchGenericName(params.q)
        render outPut
    }

    def searchBrandName() {
        def outPut = medCompanyService.searchBrandName(params.q)
        render outPut
    }

    def list() {
        LinkedHashMap gridData
        String result
        LinkedHashMap resultMap = medicineService.paginationList(params)

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
        MedBrand medBrand = MedBrand.get(id)
        if (!medBrand) {
            result.put(CommonUtils.IS_ERROR,Boolean.TRUE)
            result.put(CommonUtils.MESSAGE,"Object not found. Please refresh browser and try again")
            outPut = result as JSON
            render outPut
            return
        }
        String message
        medBrand.activeStatus=ActiveStatus.DELETED
        medBrand.save(flush: true)
        result.put(CommonUtils.IS_ERROR, Boolean.FALSE)
        result.put(CommonUtils.MESSAGE, "Medicine  Brand deleted successfully.")
        outPut = result as JSON
        render outPut
    }
}

