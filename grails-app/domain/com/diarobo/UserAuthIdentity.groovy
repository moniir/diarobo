package com.diarobo

class UserAuthIdentity {
    String providerName
    String fullName
    String nickName
    String userAccountId
    String username

    static constraints = {
        username nullable: true
    }
}
