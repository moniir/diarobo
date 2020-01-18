package com.diarobo.enums
/**
 * Created by rakib on 12/3/2016.
 */
enum WeightMeasure {
    Gram('Gram'),
    Milliliter('Milliliter')

    final String value
    public WeightMeasure(String value) {
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