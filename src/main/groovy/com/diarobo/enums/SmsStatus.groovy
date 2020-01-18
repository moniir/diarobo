package com.diarobo.enums

/**
 * Created by rakib on 12/7/2016.
 */
enum SmsStatus {
    INVALID(0),
    SUCCESS(1),
    ERROR(2)

    final int value
    public SmsStatus(int value) {
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
