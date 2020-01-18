package com.diarobo.enums
/**
 * Created by rakib on 12/3/2016.
 */
enum DiseaseType {

    TIPICAL('Typical'),
    ATIPICAL('Atypical')

    final String value
    public DiseaseType(String value) {
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