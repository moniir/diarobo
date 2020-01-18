package com.diarobo.enums
/**
 * Created by rakib on 12/3/2016.
 */
enum DiseaseCategory {

    MICROVASCULAR('Microvascular'),
    MACROVASCULAR('Macrovascular'),
    DIABETIC_ASSOCIATED('Diabetic Associated')

    final String value
    public DiseaseCategory(String value) {
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