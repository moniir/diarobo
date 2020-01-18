package com.diarobo.enums

/**
 * Created by rakib on 12/4/2016.
 */
enum VerificationStatus {
    EXPIRED(0),
    VERIFIED(1),
    SENT(2),
    PREPARED(3)

    final int value
    public VerificationStatus(int value) {
        this.value = value
    }
    public int getValue() {
        value
    }
    public String getKey() {
        name()
    }
    public String toString() {
        value
    }
}
