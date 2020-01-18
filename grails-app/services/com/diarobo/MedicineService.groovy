package com.diarobo

import com.diarobo.admin.MedBrand
import com.diarobo.admin.MedCompany
import com.diarobo.admin.MedGeneric
import com.diarobo.enums.ActiveStatus
import grails.converters.JSON
import grails.transaction.Transactional
import grails.web.servlet.mvc.GrailsParameterMap

@Transactional
class MedicineService {
    def pictureService

    static final String[] sortColumns = ['id', 'medGenericAlias.genericName', 'brandName', 'medCompanyAlias.companyName','medGenericAlias.medicineType','medGenericAlias.sideEffect']

    def save( def file, MedGeneric medGeneric, MedBrand brand, MedCompany medCompany) {
        def responseData
        MedCompany medComp = MedCompany.findByCompanyName(medCompany.companyName)
        if(!medComp) {
            medCompany.save(flush: true)
            medComp = medCompany
        }

        MedGeneric generic = MedGeneric.findByGenericNameAndMedicineType(medGeneric.genericName, medGeneric.medicineType)
        if(!generic) {
            medGeneric.save(flush: true)
            generic = medGeneric
        }
        MedBrand medBrand = MedBrand.findByBrandName(brand.brandName)
        if(medBrand && medBrand.medGeneric == generic) {
            responseData = [hasError: true, message: 'Same brand & same generic name has already saved.']
            return responseData
        } else {
            brand.medCompany = medComp
            brand.medGeneric = generic
            brand.save(flush: true)
            if(file.filename) {
                if(pictureService.uploadUnSucessful(brand.id, 'medicine_library', 'library',file)) {
                    responseData = [hasError: true, message: 'Error in Upload']
                    return responseData
                }
            }
            responseData = [hasError: false, message: 'Successfully Saved']
            return responseData
        }
    }

    def update( def file, MedBrand brand, def params) {
        def responseData
        MedCompany medComp = MedCompany.findByCompanyName(params.companyName)
        MedGeneric generic = MedGeneric.findByGenericName(params.genericName)
        generic.sideEffect = params.sideEffect
        generic.medicineType = params.medicineType
        generic.save()
        if(brand) {
            brand.brandName = params.brandName
            if(file.filename) {
                if(pictureService.uploadUnSucessful(brand.id, 'medicine_library', 'library',file)) {
                    responseData = [hasError: true, message: 'Error in Upload']
                    return responseData
                }
            }
            responseData = [hasError: false, message: 'Successfully updated']
            return responseData
        } else {
            responseData = [hasError: true, message: 'Not Found']
            return responseData
        }
    }

    LinkedHashMap paginationList(GrailsParameterMap params) {
        Map serverParams = CommonUtils.getPaginationParams(params, sortColumns)
        int iDisplayStart= serverParams.iDisplayStart.toInteger();
        int  iDisplayLength= serverParams.iDisplayLength.toInteger()

        String sSortDir=serverParams.sSortDir;
        String sortColumn=serverParams.sortColumn
        String sSearch=serverParams.sSearch
        def c = MedBrand.createCriteria()
        def results = c.list(max: iDisplayLength, offset: iDisplayStart) {
            createAlias("medCompany","medCompanyAlias")
            createAlias("medGeneric","medGenericAlias")
            and{
                eq('activeStatus', ActiveStatus.ACTIVE)
            }
            if (sSearch) {
                or {
                    ilike('medCompanyAlias.companyName', sSearch)
                    ilike('medGenericAlias.genericName', sSearch)
                    ilike('brandName', sSearch)
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
                dataReturns.add([DT_RowId: obj.id, 0: serial, 1: obj.medGeneric.genericName, 2: obj.brandName, 3: obj.medCompany.companyName, 4:obj.medGeneric.medicineType, 5:obj.medGeneric.sideEffect, 6: ''])
            }
        }
        return [totalCount:totalCount, results:dataReturns]
    }
}
