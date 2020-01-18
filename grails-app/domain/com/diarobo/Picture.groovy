package com.diarobo

import com.diarobo.enums.ActiveStatus

class Picture {
    String entityName
    Long entityId


    String filename
    String fullPath
    String thumbPath
    String imageExtension

    Date dateCreated
    Long createdBy
    Date lastUpdated
    Long updatedBy
    ActiveStatus activeStatus = ActiveStatus.ACTIVE
    String identifier = UUID.randomUUID().toString().replace("-", "")[0..12]
    static constraints = {
        entityName nullable: true
        entityId nullable: true
        thumbPath nullable: true
        dateCreated nullable: true
        createdBy nullable: true
        lastUpdated nullable: true
        updatedBy nullable: true

    }

    public String createImagePath(String folderName, String imageExtension) {
        String ext = imageExtension ? imageExtension : this.imageExtension
        if (ext) {
            return "/" + folderName + '/' + identifier + '.' + ext
        } else {
            return null
        }
    }

    public String createThumbPath(String folderName, String imageExtension) {
        String ext = imageExtension ? imageExtension : this.imageExtension
        if (ext) {
            return "/" + folderName + '/' + identifier +'_thumb'+ '.' + ext
        } else {
            return null
        }
    }
}
