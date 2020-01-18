package com.diarobo

import com.diarobo.admin.Audio
import com.diarobo.enums.ActiveStatus
import grails.transaction.Transactional
import grails.web.servlet.mvc.GrailsParameterMap

@Transactional
class AudioService {

    def save( def file, String name) {
        Audio audio = Audio.findByName(name)
        if(!audio) {
            audio.save(flush: true)
        }
    }

    static final String[] sortColumns = ['id', 'name']

    LinkedHashMap paginationList(GrailsParameterMap params) {
        Map serverParams = CommonUtils.getPaginationParams(params, sortColumns)
        int iDisplayStart= serverParams.iDisplayStart.toInteger();
        int  iDisplayLength= serverParams.iDisplayLength.toInteger()

        String sSortDir=serverParams.sSortDir;
        String sortColumn=serverParams.sortColumn
        String sSearch=serverParams.sSearch

        def c = Audio.createCriteria()
        def results = c.list(max: iDisplayLength, offset: iDisplayStart) {
            and {
                eq("activeStatus", ActiveStatus.ACTIVE)
            }
            if (params.groupType){
                and {
                   // eq("itemType", params.groupType)
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
                dataReturns.add([DT_RowId: obj.id, 0: serial, 1: obj.name, 2: ''])

            }

        }
        return [totalCount:totalCount, results:dataReturns]
    }
}
