package com.diarobo

import com.diarobo.enums.MasterKeyValue
import grails.transaction.Transactional

class MasterKeyService {
    static transactional = false

    def keyDropDownList(String keyType){
        def keyItems = MasterKeySetup.findAllByKeyType(keyType)
        List dataReturns = new ArrayList()

        for (obj in keyItems) {
            dataReturns.add([id: obj.keyName, name: obj.keyValue])
        }
        return dataReturns
    }
    MasterKeySetup masterKey(String keyType, String keyName){
        MasterKeySetup.findByKeyTypeAndKeyName(keyType, keyName)
    }
}
