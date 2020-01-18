package com.diarobo.enums

/**
 * Created by rakib on 3/22/2017.
 */
enum SyncStatus {
    SYNCED,MODIFIED,NEW

    static Collection<SyncStatus> workingStatus(){
        return [SYNCED,MODIFIED,NEW]
    }
}