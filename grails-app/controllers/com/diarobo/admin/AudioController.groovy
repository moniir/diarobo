package com.diarobo.admin

import com.diarobo.CommonUtils
import com.diarobo.Picture
import com.diarobo.enums.ActiveStatus
import com.diarobo.enums.MasterKeyValue
import grails.converters.JSON
import grails.plugin.springsecurity.annotation.Secured

@Secured(CommonUtils.ROLE_ADMIN)
class AudioController {

    def masterKeyService
    def medicineService
    def audioService
    def pictureService

    def index() {

    }
    def create() {
        switch (request.method) {
            case 'GET':
                render(view: 'create')
                break
            case 'POST':
                String name = params.name

                def responseData
                Audio audio = Audio.findById(params.id)
                if(!audio) {
                    audio = new Audio(name: params.name)
                    audio.save(flush: true)

                    def file = request.getFile('uploadAudio')
                    if(file) {
                        if(pictureService.uploadUnSucessful(audio.id, 'audio_library', 'library',file)) {
                            responseData = ['hasError': true, 'message': 'Upload Error.']
                            render responseData as JSON
                            return
                        }
                    }
                    responseData = ['hasError': false, 'message': 'Successfully Saved.']
                } else {
                    responseData = ['hasError': true, 'message': 'Already Exist']
                }
                render responseData as JSON
                break
        }
    }
    def update(){
     //   Audio audio = Audio.findById(params.id)
        def audio = Audio.findById(params.getLong("id"))
        def responseData
        if(audio) {
            String massage
            audio.name = params.name
            audio.activeStatus = ActiveStatus.ACTIVE
            audio.save(flush: true)

            def file = request.getFile('uploadAudio')
            if(file) {
                if(pictureService.uploadUnSucessful(audio.id, 'audio_library', 'library',file)) {
                    responseData = ['hasError': true, 'message': 'Upload Error.']
                    render responseData as JSON
                    return
                }
            }
            responseData = ['hasError': false, 'message': 'Successfully Updated.']
        } else {
            responseData = ['hasError': true, 'message': 'Not Found']
        }
        render responseData as JSON
    }
    def list() {
        LinkedHashMap gridData
        String result
        LinkedHashMap resultMap =audioService.paginationList(params)

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
        Audio delAudio = Audio.get(id)
        if (!delAudio) {
            result.put(CommonUtils.IS_ERROR,Boolean.TRUE)
            result.put(CommonUtils.MESSAGE,"Object not found. Please refresh browser and try again")
            outPut = result as JSON
            render outPut
            return
        }
        String message
        delAudio.activeStatus=ActiveStatus.DELETED
        delAudio.save(flush: true)
        result.put(CommonUtils.IS_ERROR, Boolean.FALSE)
        result.put(CommonUtils.MESSAGE, "Audio deleted successfully.")
        outPut = result as JSON
        render outPut
    }
    def edit(Integer id) {
        Audio audioInstance = Audio.findById(id)
        if (!audioInstance) {
            redirect(action: 'index')
            return
        }
        render(view: 'edit', model: [audioInstance: audioInstance])
    }
}
