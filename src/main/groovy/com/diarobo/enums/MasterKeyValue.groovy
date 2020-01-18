package com.diarobo.enums
/**
 * Created by rakib on 12/3/2016.
 */
enum MasterKeyValue {

    USER_TYPE('USER_TYPE'),
    GENDER('GENDER'),
    EDUCATION('EDUCATION'),
    FINANCIAL_STATUS('FINANCIAL_STATUS'),
    LIBRARY('LIBRARY'),
    FMU('FMU'),      //food measure unit
    FWU('FWU'),      //food weight unit
    EXERCISE_MU('EXERCISE_MU'),      //exercise measure unit
    EXERCISE_TYPE('EXERCISE_TYPE'),      //exercise type
    GROUPITEM_TYPE('GROUPITEM_TYPE'),      //group item type
    JOB_INDUSTRY('JOB_INDUSTRY'),
    JOB_NATURE('JOB_NATURE'),
    FAMILY_RELATION('FAMILY_RELATION'),
    DRUG_TYPE('DRUG_TYPE'),
    MEAL_TYPE('MEAL_TYPE'),
    PACKAGE_TYPE('PACKAGE_TYPE'),
    DRUG_WEIGHT('DRUG_WEIGHT'),
    DRUG_TIME('DRUG_TIME'),
    EXERCISE_NAME('EXERCISE_NAME'),
    BREAKFAST('BREAKFAST'),
    LUNCH('LUNCH'),
    DINNER('DINNER'),
    SNACK_BEFORE_LUNCH('SNACK_BEFORE_LUNCH'),
    SNACK_BEFORE_DINNER('SNACK_BEFORE_DINNER'),
    SNACK_BEFORE_SLEEP('SNACK_BEFORE_SLEEP'),
    EXERCISE_MORNING('EXERCISE_MORNING'),
    EXERCISE_AFTERNOON('EXERCISE_AFTERNOON'),
    MEDICINE_41('MEDICINE_41'),
    MEDICINE_42('MEDICINE_42'),
    MEDICINE_43('MEDICINE_43'),
    MEDICINE_44('MEDICINE_44'),
    MEDICINE_51('MEDICINE_51'),
    MEDICINE_52('MEDICINE_52'),
    MEDICINE_53('MEDICINE_53'),
    MEDICINE_54('MEDICINE_54'),
    MEDICINE_55('MEDICINE_55')


    final String value
    public MasterKeyValue(String value) {
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