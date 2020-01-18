package com.diarobo

import org.grails.web.json.JSONObject

class PictureService {
    def grailsApplication
    static transactional = false

    /*Picture upload(HttpServletRequest request, String fieldName, String uploadFolder, String fileName = null) throws FileUploadException {
        CommonsMultipartFile f = request.getFile(fieldName)
        if (f.empty) {
            return null
        }
        String originalFileName = f.originalFilename
        String extension = extensionFromPath(originalFileName)
//        todo: validate content type
        def allowedImageExtensions = ['png', 'jpg', 'jpeg', 'gif']
        if (!extension || !allowedImageExtensions.contains(extension.toLowerCase())) {
            return null
        }
        if (!fileName) fileName = fileNameFromPath(originalFileName)
        Picture picture = new Picture(imageExtension: extension, fileName: fileName)
        picture.fullPath = picture.createImagePath(uploadFolder, extension)
        def absolutePath = grailsApplication.config.uploadFolder + picture.fullPath
        File uploadedFile = new File(absolutePath)
        //create parent directory if it does not exist
        new File(uploadedFile.getParent()).mkdirs()

        InputStream inputStream = selectInputStream(request, fieldName)
        upload(inputStream, uploadedFile)

        if (!uploadedFile?.size() > 0) {
            picture.discard()
            return null
        }
        return picture
    }

    public String extensionFromPath(String path) {
        int index = path.lastIndexOf(".")
        if (index > -1) {
            return path.substring(index + 1)?.toLowerCase()
        }
        return null
    }
    public String fileNameFromPath(String path) {
        int index = path.lastIndexOf(".")
        if (index > -1) {
            return path.substring(0, index + 1)?.toLowerCase()
        }
        return null
    }
    public InputStream selectInputStream(HttpServletRequest request, String fileUploadField = "qqfile") {
        if (request instanceof MultipartHttpServletRequest) {
            MultipartFile uploadedFile = ((MultipartHttpServletRequest) request).getFile(fileUploadField)
            return uploadedFile.inputStream
        }
        return request.inputStream
    }

    void upload(InputStream inputStream, File file) throws FileUploadException {
        try {
            file << inputStream
        } catch (Exception e) {
            log.debug(e.getMessage())
        }

    }*/


    boolean uploadUnSucessful(Long itemId, String entityName, String type, def file) {
        Picture picture
        if(!file.empty) {
            String subFolderName = '/' + type + '/' + entityName + '/'
            String baseFolder = grailsApplication.config.uploadFolder
            String folderName = baseFolder + subFolderName
            if(makeFolder(folderName)) {
                picture = Picture.findByEntityIdAndEntityName(itemId, entityName)
                String extension = extensionFromPath(file.originalFilename)
                String fileName = subFolderName + itemId + '.' + extension
                if(picture) {
                    picture.fullPath = baseFolder + fileName
                    picture.filename = fileName
                    picture.imageExtension = extension
                    File uploadedFile = new File(picture.fullPath)
                    new File(uploadedFile.getParent()).mkdirs()
                    file.transferTo(uploadedFile)
                    picture.save(flash: true)
                    return false
                } else {
                    picture = new Picture(entityId: itemId, entityName: entityName, filename: fileName, fullPath: baseFolder + fileName,
                            imageExtension: extension)
                    File uploadedFile = new File(picture.fullPath)
                    new File(uploadedFile.getParent()).mkdirs()
                    file.transferTo(uploadedFile)
                    picture.save(flash: true)
                    return false
                }
            }
        }
        return true
    }


    boolean makeFolder(String path) {
        File pathAsFile = new File(path)
        if(!pathAsFile.exists()) {
            try {
                pathAsFile.mkdirs()
                return true
            } catch (Exception ex) {
                return false
            }
        } else {
            return true
        }
    }

    def getPictureInByte(Long itemId, String entityName, def response) {
        Picture picture = Picture.findByEntityIdAndEntityName(itemId, entityName)
        if(picture) {
            def file = new File(picture.fullPath)
            def fileInputStream = new FileInputStream(file)
            def outputStream = response.getOutputStream()
            byte[] buffer = new byte[10000000]
            int len;
            while ((len = fileInputStream.read(buffer)) > 0) {
                outputStream.write(buffer, 0, len);
            }
            outputStream.flush()
            outputStream.close()
            fileInputStream.close()
            return buffer
        }
        return null
    }

    String extensionFromPath(String path) {
        int index = path.lastIndexOf(".")
        if (index > -1) {
            return path.substring(index + 1)?.toLowerCase()
        }
        return null
    }
}
