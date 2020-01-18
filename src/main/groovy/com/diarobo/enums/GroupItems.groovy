package com.diarobo.enums
/**
 * Created by rakib on 12/3/2016.
 */
enum GroupItems {

    FOOD('Food'),
    EXERCISE('Exercise'),
    DISEASE('Disease')


    final String value
    public GroupItems(String value) {
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