package com.diarobo.enums

/**
 * Created by rakib on 3/27/2017.
 */
enum LibraryType {
    EXERCISE_LIBRARY('exercise_library'),
    MEDICINE_LIBRARY('medicine_library'),
    FOOD_LIBRARY('food_library')

    final String value
    public LibraryType(String value) {
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