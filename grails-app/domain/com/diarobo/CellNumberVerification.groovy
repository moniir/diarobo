package com.diarobo

class CellNumberVerification {
    User user
    String verificationCode
    String requestedFrom
    Integer status
    Date createdDate = new Date()
    Date expiredDate

    static constraints = {
    }
}
