package com.diarobo

import com.diarobo.admin.MedBrand
import com.diarobo.admin.MedCompany
import com.diarobo.admin.MedGeneric
import com.diarobo.enums.ActiveStatus
import grails.converters.JSON
import grails.transaction.Transactional

@Transactional
class MedCompanyService {

    static transactional = false

    def searchCompanyName(String query) {
        LinkedHashMap result = new LinkedHashMap()
        def medCompanyNameList = MedCompany.findAllByActiveStatusAndCompanyNameIlike(ActiveStatus.ACTIVE, "%"+query+"%", [max: 10])
        result.put('isError', Boolean.FALSE)
        result.put('results', medCompanyNameList)
        String outPut = result as JSON
        return outPut
    }

    def searchGenericName(String query) {
        LinkedHashMap result = new LinkedHashMap()
        def medGenericNameList = MedGeneric.findAllByActiveStatusAndGenericNameIlike(ActiveStatus.ACTIVE, "%"+query+"%", [max: 10])
        result.put('isError', Boolean.FALSE)
        result.put('results', medGenericNameList)
        String outPut = result as JSON
        return outPut
    }

    def searchBrandName(String query){
        LinkedHashMap result = new LinkedHashMap()
        def medBrandNameList = MedBrand.findAllByActiveStatusAndBrandNameIlike(ActiveStatus.ACTIVE, "%"+query+"%", [max: 10])
        result.put('isError', Boolean.FALSE)
        result.put('results', medBrandNameList)
        String outPut = result as JSON
        return outPut


    }
}
