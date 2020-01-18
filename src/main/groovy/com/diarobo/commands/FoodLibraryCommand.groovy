package com.diarobo.commands

import grails.validation.Validateable

/**
 * Created by Lenovo on 1/11/2017.
 */
class FoodLibraryCommand implements Validateable {
    Long id
    String foodGroup
    String name
    Double approximateWeight
    static constraints = {
        approximateWeight nullable: true
        id nullable: true
    }
}