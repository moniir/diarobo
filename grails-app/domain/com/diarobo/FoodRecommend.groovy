package com.diarobo

import com.diarobo.admin.FoodPackage
import com.diarobo.enums.ActiveStatus

class FoodRecommend {
    String mealType
    FoodPackage foodPackage
    String time
    ActiveStatus activeStatus = ActiveStatus.ACTIVE

    static constraints = {
    }
}
