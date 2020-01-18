package com.diarobo.enums
/**
 * Created by rakib on 12/3/2016.
 */
enum MeasureUnit {
    Piece('Piece'),
    Cup('Cup')

    final String value
    public MeasureUnit(String value) {
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