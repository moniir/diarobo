package com.diarobo.enums

/**
 * Created by rakib on 12/7/2016.
 */
enum DiabeticType {
    TYPE_I('Type I'),
    TYPE_II('Type II'),
    GESTATIONAL('Gestational')


    final String value
    public DiabeticType(String value) {
        this.value = value
    }
    public String getValue() {
        value
    }
    public String getKey() {
        name()
    }
    public String toString() {
        value
    }
}
