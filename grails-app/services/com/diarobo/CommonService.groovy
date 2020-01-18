package com.diarobo

import grails.transaction.Transactional
import org.grails.web.json.JSONObject

@Transactional
class CommonService {

    def convertForUrlParam(String txt) {
        txt = txt.replace(' ', '%20')
        txt= txt.replace('\n', '%0A')
        txt
    }

    def getJsonValue(JSONObject json, String key) {
        if(json.has(key)) {
            return json[key]
        } else {
            return null
        }
    }
}
