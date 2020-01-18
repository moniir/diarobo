package diarobo

import com.diarobo.*
import com.diarobo.admin.GroupItem
import com.diarobo.enums.GroupItems
import com.diarobo.admin.MedCompany
import com.diarobo.enums.MasterKeyValue

class BootStrap {

    def init = { servletContext ->
        Role.AvailableRoles.values().each {
            if (!Role.findByAuthority(it.value())) {
                new Role(authority: it.value()).save()
            }
        }

        def roleAdmin = Role.findByAuthority(Role.AvailableRoles.ADMIN.value())
        //Super Admin user
        User admin = User.findByUsername('admin') ?: new User(username: 'admin', password: 'diarobo', nickName: 'admin', fullName: 'admin', userType: 'admin' ).save(flush: true)
        if (!UserRole.findByUserAndRole(admin, roleAdmin)) {
            UserRole.create(admin, roleAdmin)
        }
        //Super Admin user
       def rolePatient = Role.findByAuthority(Role.AvailableRoles.PATIENT.value())
        User patient = User.findByUsername('01821449288') ?: new User(username: '01821449288', password: 'diarobo', nickName: 'Rakib', fullName: 'Rakib Hasan', userType: 'Patient' ).save(flush: true)
        if (!UserRole.findByUserAndRole(patient, rolePatient)) {
            UserRole.create(patient, rolePatient)
            PatientProfile patientProfile = PatientProfile.findByUser(patient) ?: new PatientProfile(user: patient).save(flush:true)
        }
        MasterKeySetup.findByKeyTypeAndKeyName(MasterKeyValue.USER_TYPE.value, 'Patient') ?: new MasterKeySetup(keyType: MasterKeyValue.USER_TYPE.value, keyName: 'Patient', keyValue: 'Patient').save(flush: true)
        MasterKeySetup.findByKeyTypeAndKeyName(MasterKeyValue.USER_TYPE.value, 'Caregiver') ?: new MasterKeySetup(keyType: MasterKeyValue.USER_TYPE.value, keyName: 'Caregiver', keyValue: 'Caregiver').save(flush: true)
        MasterKeySetup.findByKeyTypeAndKeyName(MasterKeyValue.USER_TYPE.value, 'Doctor') ?: new MasterKeySetup(keyType: MasterKeyValue.USER_TYPE.value, keyName: 'Doctor', keyValue: 'Doctor').save(flush: true)

        MasterKeySetup.findByKeyTypeAndKeyName(MasterKeyValue.GENDER.value, 'Male') ?: new MasterKeySetup(keyType: MasterKeyValue.GENDER.value, keyName: 'Male', keyValue: 'Male').save(flush: true)
        MasterKeySetup.findByKeyTypeAndKeyName(MasterKeyValue.GENDER.value, 'Female') ?: new MasterKeySetup(keyType: MasterKeyValue.GENDER.value, keyName: 'Female', keyValue: 'Female').save(flush: true)
        MasterKeySetup.findByKeyTypeAndKeyName(MasterKeyValue.GENDER.value, 'Other') ?: new MasterKeySetup(keyType: MasterKeyValue.GENDER.value, keyName: 'Other', keyValue: 'Other').save(flush: true)

        MasterKeySetup.findByKeyTypeAndKeyName(MasterKeyValue.EDUCATION.value, 'None') ?: new MasterKeySetup(keyType: MasterKeyValue.EDUCATION.value, keyName: 'None', keyValue: 'None').save(flush: true)
        MasterKeySetup.findByKeyTypeAndKeyName(MasterKeyValue.EDUCATION.value, 'PSC') ?: new MasterKeySetup(keyType: MasterKeyValue.EDUCATION.value, keyName: 'PSC', keyValue: 'PSC').save(flush: true)
        MasterKeySetup.findByKeyTypeAndKeyName(MasterKeyValue.EDUCATION.value, 'JSC') ?: new MasterKeySetup(keyType: MasterKeyValue.EDUCATION.value, keyName: 'JSC', keyValue: 'JSC').save(flush: true)
        MasterKeySetup.findByKeyTypeAndKeyName(MasterKeyValue.EDUCATION.value, 'SSC') ?: new MasterKeySetup(keyType: MasterKeyValue.EDUCATION.value, keyName: 'SSC', keyValue: 'SSC').save(flush: true)
        MasterKeySetup.findByKeyTypeAndKeyName(MasterKeyValue.EDUCATION.value, 'HSC') ?: new MasterKeySetup(keyType: MasterKeyValue.EDUCATION.value, keyName: 'HSC', keyValue: 'HSC').save(flush: true)
        MasterKeySetup.findByKeyTypeAndKeyName(MasterKeyValue.EDUCATION.value, 'Diploma') ?: new MasterKeySetup(keyType: MasterKeyValue.EDUCATION.value, keyName: 'Diploma', keyValue: 'Diploma').save(flush: true)
        MasterKeySetup.findByKeyTypeAndKeyName(MasterKeyValue.EDUCATION.value, 'Hons') ?: new MasterKeySetup(keyType: MasterKeyValue.EDUCATION.value, keyName: 'Hons', keyValue: 'BSc/Hons/Equal Level').save(flush: true)
        MasterKeySetup.findByKeyTypeAndKeyName(MasterKeyValue.EDUCATION.value, 'Masters') ?: new MasterKeySetup(keyType: MasterKeyValue.EDUCATION.value, keyName: 'Masters', keyValue: 'Masters').save(flush: true)
        MasterKeySetup.findByKeyTypeAndKeyName(MasterKeyValue.EDUCATION.value, 'PHD') ?: new MasterKeySetup(keyType: MasterKeyValue.EDUCATION.value, keyName: 'PHD', keyValue: 'PHD').save(flush: true)
        //Library setup data
        MasterKeySetup.findByKeyTypeAndKeyName(MasterKeyValue.LIBRARY.value, 'DISEASE') ?: new MasterKeySetup(keyType: MasterKeyValue.LIBRARY.value, keyName: 'DISEASE', keyValue: 'DISEASE').save(flush: true)
        MasterKeySetup.findByKeyTypeAndKeyName(MasterKeyValue.LIBRARY.value, 'FOOD') ?: new MasterKeySetup(keyType: MasterKeyValue.LIBRARY.value, keyName: 'FOOD', keyValue: 'FOOD').save(flush: true)
        MasterKeySetup.findByKeyTypeAndKeyName(MasterKeyValue.LIBRARY.value, 'MEDICINE') ?: new MasterKeySetup(keyType: MasterKeyValue.LIBRARY.value, keyName: 'MEDICINE', keyValue: 'MEDICINE').save(flush: true)

        //Food Libreary Measure Unit
        MasterKeySetup.findByKeyTypeAndKeyName(MasterKeyValue.FMU.value, 'Piece') ?: new MasterKeySetup(keyType: MasterKeyValue.FMU.value, keyName: 'Piece', keyValue: 'Piece').save(flush: true)
        MasterKeySetup.findByKeyTypeAndKeyName(MasterKeyValue.FMU.value, 'Cup') ?: new MasterKeySetup(keyType: MasterKeyValue.FMU.value, keyName: 'Cup', keyValue: 'Cup').save(flush: true)
        //Food weight unit
        MasterKeySetup.findByKeyTypeAndKeyName(MasterKeyValue.FWU.value, 'gm') ?: new MasterKeySetup(keyType: MasterKeyValue.FWU.value, keyName: 'gm', keyValue: 'gm').save(flush: true)
        MasterKeySetup.findByKeyTypeAndKeyName(MasterKeyValue.FWU.value, 'ml') ?: new MasterKeySetup(keyType: MasterKeyValue.FWU.value, keyName: 'ml', keyValue: 'ml').save(flush: true)
        //Exercise Measure Unit
        MasterKeySetup.findByKeyTypeAndKeyName(MasterKeyValue.EXERCISE_MU.value, 'Duration') ?: new MasterKeySetup(keyType: MasterKeyValue.EXERCISE_MU.value, keyName: 'Duration', keyValue: 'Duration').save(flush: true)
        MasterKeySetup.findByKeyTypeAndKeyName(MasterKeyValue.EXERCISE_MU.value, 'Times') ?: new MasterKeySetup(keyType: MasterKeyValue.EXERCISE_MU.value, keyName: 'Times', keyValue: 'Times').save(flush: true)
        //Exercise Type
        MasterKeySetup.findByKeyTypeAndKeyName(MasterKeyValue.EXERCISE_TYPE.value, 'Light') ?: new MasterKeySetup(keyType: MasterKeyValue.EXERCISE_TYPE.value, keyName: 'Light', keyValue: 'Light').save(flush: true)
        MasterKeySetup.findByKeyTypeAndKeyName(MasterKeyValue.EXERCISE_TYPE.value, 'Moderate') ?: new MasterKeySetup(keyType: MasterKeyValue.EXERCISE_TYPE.value, keyName: 'Moderate', keyValue: 'Moderate').save(flush: true)
        MasterKeySetup.findByKeyTypeAndKeyName(MasterKeyValue.EXERCISE_TYPE.value, 'Extraneous') ?: new MasterKeySetup(keyType: MasterKeyValue.EXERCISE_TYPE.value, keyName: 'Extraneous', keyValue: 'Extraneous').save(flush: true)
       //medicine Administration
        MasterKeySetup.findByKeyTypeAndKeyName(MasterKeyValue.DRUG_TYPE.value, 'Tablet') ?: new MasterKeySetup(keyType: MasterKeyValue.DRUG_TYPE.value, keyName: 'Tablet', keyValue: 'Tablet').save(flush: true)
        MasterKeySetup.findByKeyTypeAndKeyName(MasterKeyValue.DRUG_TYPE.value, 'Injection') ?: new MasterKeySetup(keyType: MasterKeyValue.DRUG_TYPE.value, keyName: 'Injection', keyValue: 'Injection').save(flush: true)
        MasterKeySetup.findByKeyTypeAndKeyName(MasterKeyValue.DRUG_TYPE.value, 'Syrup') ?: new MasterKeySetup(keyType: MasterKeyValue.DRUG_TYPE.value, keyName: 'Syrup', keyValue: 'Syrup').save(flush: true)
        MasterKeySetup.findByKeyTypeAndKeyName(MasterKeyValue.DRUG_TYPE.value, 'Suppository') ?: new MasterKeySetup(keyType: MasterKeyValue.DRUG_TYPE.value, keyName: 'Suppository', keyValue: 'Suppository').save(flush: true)
        MasterKeySetup.findByKeyTypeAndKeyName(MasterKeyValue.DRUG_TYPE.value, 'Inhaler') ?: new MasterKeySetup(keyType: MasterKeyValue.DRUG_TYPE.value, keyName: 'Inhaler', keyValue: 'Inhaler').save(flush: true)
        MasterKeySetup.findByKeyTypeAndKeyName(MasterKeyValue.DRUG_TYPE.value, 'Spray') ?: new MasterKeySetup(keyType: MasterKeyValue.DRUG_TYPE.value, keyName: 'Spray', keyValue: 'Spray').save(flush: true)
        MasterKeySetup.findByKeyTypeAndKeyName(MasterKeyValue.DRUG_TYPE.value, 'Drop') ?: new MasterKeySetup(keyType: MasterKeyValue.DRUG_TYPE.value, keyName: 'Drop', keyValue: 'Drop').save(flush: true)
        MasterKeySetup.findByKeyTypeAndKeyName(MasterKeyValue.DRUG_TYPE.value, 'Ointment') ?: new MasterKeySetup(keyType: MasterKeyValue.DRUG_TYPE.value, keyName: 'Ointment', keyValue: 'Ointment').save(flush: true)
        MasterKeySetup.findByKeyTypeAndKeyName(MasterKeyValue.DRUG_TYPE.value, 'Powder') ?: new MasterKeySetup(keyType: MasterKeyValue.DRUG_TYPE.value, keyName: 'Powder', keyValue: 'Powder').save(flush: true)
        MasterKeySetup.findByKeyTypeAndKeyName(MasterKeyValue.DRUG_TYPE.value, 'Suspension') ?: new MasterKeySetup(keyType: MasterKeyValue.DRUG_TYPE.value, keyName: 'Suspension', keyValue: 'Suspension').save(flush: true)
        MasterKeySetup.findByKeyTypeAndKeyName(MasterKeyValue.DRUG_TYPE.value, 'Capsule') ?: new MasterKeySetup(keyType: MasterKeyValue.DRUG_TYPE.value, keyName: 'Capsule', keyValue: 'Capsule').save(flush: true)

        MasterKeySetup.findByKeyTypeAndKeyName(MasterKeyValue.DRUG_TIME.value, 'OD') ?: new MasterKeySetup(keyType: MasterKeyValue.DRUG_TIME.value, keyName: 'OD', keyValue: 'OD').save(flush: true)
        MasterKeySetup.findByKeyTypeAndKeyName(MasterKeyValue.DRUG_TIME.value, 'BD') ?: new MasterKeySetup(keyType: MasterKeyValue.DRUG_TIME.value, keyName: 'BD', keyValue: 'BD').save(flush: true)
        MasterKeySetup.findByKeyTypeAndKeyName(MasterKeyValue.DRUG_TIME.value, 'TDS') ?: new MasterKeySetup(keyType: MasterKeyValue.DRUG_TIME.value, keyName: 'TDS', keyValue: 'TDS').save(flush: true)
        MasterKeySetup.findByKeyTypeAndKeyName(MasterKeyValue.DRUG_TIME.value, 'QDS') ?: new MasterKeySetup(keyType: MasterKeyValue.DRUG_TIME.value, keyName: 'QDS', keyValue: 'QDS').save(flush: true)
        MasterKeySetup.findByKeyTypeAndKeyName(MasterKeyValue.DRUG_TIME.value, '5 Times') ?: new MasterKeySetup(keyType: MasterKeyValue.DRUG_TIME.value, keyName: '5 Times', keyValue: '5 Times').save(flush: true)
       //group item type
        for(String groupItem : GroupItems.values()) {
            MasterKeySetup.findByKeyTypeAndKeyName(MasterKeyValue.GROUPITEM_TYPE.value, groupItem) ?: new MasterKeySetup(keyType: MasterKeyValue.GROUPITEM_TYPE.value, keyName: groupItem, keyValue: groupItem).save(flush: true)
        }

        MasterKeySetup.findByKeyTypeAndKeyName(MasterKeyValue.DRUG_WEIGHT.value, '500mg') ?: new MasterKeySetup(keyType: MasterKeyValue.DRUG_WEIGHT.value, keyName: '500mg', keyValue: '500mg').save(flush: true)
        MasterKeySetup.findByKeyTypeAndKeyName(MasterKeyValue.DRUG_WEIGHT.value, '250mg') ?: new MasterKeySetup(keyType: MasterKeyValue.DRUG_WEIGHT.value, keyName: '250mg', keyValue: '250mg').save(flush: true)
        MasterKeySetup.findByKeyTypeAndKeyName(MasterKeyValue.DRUG_WEIGHT.value, '10mg') ?: new MasterKeySetup(keyType: MasterKeyValue.DRUG_WEIGHT.value, keyName: '10mg', keyValue: '10mg').save(flush: true)


        MasterKeySetup.findByKeyTypeAndKeyValue(MasterKeyValue.FINANCIAL_STATUS.value, 'below 50,000') ?: new MasterKeySetup(keyType: MasterKeyValue.FINANCIAL_STATUS.value, keyName: 'below 50,000', keyValue: 'below 50,000').save(flush: true)
        MasterKeySetup.findByKeyTypeAndKeyValue(MasterKeyValue.FINANCIAL_STATUS.value, '50,000 - 1,00,000') ?: new MasterKeySetup(keyType: MasterKeyValue.FINANCIAL_STATUS.value, keyName: '50,000 - 1,00,000', keyValue: '50,000 - 1,00,000').save(flush: true)
        MasterKeySetup.findByKeyTypeAndKeyValue(MasterKeyValue.FINANCIAL_STATUS.value, '1,00,000 - 5,00,000') ?: new MasterKeySetup(keyType: MasterKeyValue.FINANCIAL_STATUS.value, keyName: '1,00,000 - 5,00,000', keyValue: '1,00,000 - 5,00,000').save(flush: true)
        MasterKeySetup.findByKeyTypeAndKeyValue(MasterKeyValue.FINANCIAL_STATUS.value, 'above 5,00,000') ?: new MasterKeySetup(keyType: MasterKeyValue.FINANCIAL_STATUS.value, keyName: 'above 5,00,000', keyValue: 'above 5,00,000').save(flush: true)

        MasterKeySetup.findByKeyTypeAndKeyValue(MasterKeyValue.JOB_INDUSTRY.value, 'Job') ?: new MasterKeySetup(keyType: MasterKeyValue.JOB_INDUSTRY.value, keyName: 'Job', keyValue: 'Job').save(flush: true)
        MasterKeySetup.findByKeyTypeAndKeyValue(MasterKeyValue.JOB_INDUSTRY.value, 'Business') ?: new MasterKeySetup(keyType: MasterKeyValue.JOB_INDUSTRY.value, keyName: 'Business', keyValue: 'Business').save(flush: true)
        MasterKeySetup.findByKeyTypeAndKeyValue(MasterKeyValue.JOB_INDUSTRY.value, 'Housewife') ?: new MasterKeySetup(keyType: MasterKeyValue.JOB_INDUSTRY.value, keyName: 'Housewife', keyValue: 'Housewife').save(flush: true)
        MasterKeySetup.findByKeyTypeAndKeyValue(MasterKeyValue.JOB_INDUSTRY.value, 'Retirement') ?: new MasterKeySetup(keyType: MasterKeyValue.JOB_INDUSTRY.value, keyName: 'Retirement', keyValue: 'Retirement').save(flush: true)

        MasterKeySetup.findByKeyTypeAndKeyValue(MasterKeyValue.JOB_NATURE.value, 'Desk') ?: new MasterKeySetup(keyType: MasterKeyValue.JOB_NATURE.value, keyName: 'Desk', keyValue: 'Desk').save(flush: true)
        MasterKeySetup.findByKeyTypeAndKeyValue(MasterKeyValue.JOB_NATURE.value, 'Moving') ?: new MasterKeySetup(keyType: MasterKeyValue.JOB_NATURE.value, keyName: 'Moving', keyValue: 'Moving').save(flush: true)



        MasterKeySetup.findByKeyTypeAndKeyValue(MasterKeyValue.FAMILY_RELATION.value, 'Father') ?: new MasterKeySetup(keyType: MasterKeyValue.FAMILY_RELATION.value, keyName: 'Father', keyValue: 'Father').save(flush: true)
        MasterKeySetup.findByKeyTypeAndKeyValue(MasterKeyValue.FAMILY_RELATION.value, 'Mother') ?: new MasterKeySetup(keyType: MasterKeyValue.FAMILY_RELATION.value, keyName: 'Mother', keyValue: 'Mother').save(flush: true)
        MasterKeySetup.findByKeyTypeAndKeyValue(MasterKeyValue.FAMILY_RELATION.value, 'Sister') ?: new MasterKeySetup(keyType: MasterKeyValue.FAMILY_RELATION.value, keyName: 'Sister', keyValue: 'Sister').save(flush: true)
        MasterKeySetup.findByKeyTypeAndKeyValue(MasterKeyValue.FAMILY_RELATION.value, 'Brother') ?: new MasterKeySetup(keyType: MasterKeyValue.FAMILY_RELATION.value, keyName: 'Brother', keyValue: 'Brother').save(flush: true)
        MasterKeySetup.findByKeyTypeAndKeyValue(MasterKeyValue.FAMILY_RELATION.value, 'Grandfather (Father side)') ?: new MasterKeySetup(keyType: MasterKeyValue.FAMILY_RELATION.value, keyName: 'Grandfather (Father side)', keyValue: 'Grandfather (Father side)').save(flush: true)
        MasterKeySetup.findByKeyTypeAndKeyValue(MasterKeyValue.FAMILY_RELATION.value, 'Grandfather (Mother side)') ?: new MasterKeySetup(keyType: MasterKeyValue.FAMILY_RELATION.value, keyName: 'Grandfather (Mother side)', keyValue: 'Grandfather (Mother side)').save(flush: true)
        MasterKeySetup.findByKeyTypeAndKeyValue(MasterKeyValue.FAMILY_RELATION.value, 'Grandmother (Father side)') ?: new MasterKeySetup(keyType: MasterKeyValue.FAMILY_RELATION.value, keyName: 'Grandmother (Father side)', keyValue: 'Grandmother (Father side)').save(flush: true)
        MasterKeySetup.findByKeyTypeAndKeyValue(MasterKeyValue.FAMILY_RELATION.value, 'Grandmother (Mother side)') ?: new MasterKeySetup(keyType: MasterKeyValue.FAMILY_RELATION.value, keyName: 'Grandmother (Mother side)', keyValue: 'Grandmother (Mother side)').save(flush: true)
        MasterKeySetup.findByKeyTypeAndKeyValue(MasterKeyValue.FAMILY_RELATION.value, 'Spouse') ?: new MasterKeySetup(keyType: MasterKeyValue.FAMILY_RELATION.value, keyName: 'Spouse', keyValue: 'Spouse').save(flush: true)
        MasterKeySetup.findByKeyTypeAndKeyValue(MasterKeyValue.FAMILY_RELATION.value, 'Maternal uncle') ?: new MasterKeySetup(keyType: MasterKeyValue.FAMILY_RELATION.value, keyName: 'Maternal uncle', keyValue: 'Maternal uncle').save(flush: true)
        MasterKeySetup.findByKeyTypeAndKeyValue(MasterKeyValue.FAMILY_RELATION.value, 'Paternal uncle') ?: new MasterKeySetup(keyType: MasterKeyValue.FAMILY_RELATION.value, keyName: 'Paternal uncle', keyValue: 'Paternal uncle').save(flush: true)

        MasterKeySetup.findByKeyTypeAndKeyValue(MasterKeyValue.MEAL_TYPE.value, 'Breakfast') ?: new MasterKeySetup(keyType: MasterKeyValue.MEAL_TYPE.value, keyName: 'Breakfast', keyValue: 'Breakfast').save(flush: true)
        MasterKeySetup.findByKeyTypeAndKeyValue(MasterKeyValue.MEAL_TYPE.value, 'Snack: before lunch') ?: new MasterKeySetup(keyType: MasterKeyValue.MEAL_TYPE.value, keyName: 'Snack: before lunch', keyValue: 'Snack: before lunch').save(flush: true)
        MasterKeySetup.findByKeyTypeAndKeyValue(MasterKeyValue.MEAL_TYPE.value, 'Lunch') ?: new MasterKeySetup(keyType: MasterKeyValue.MEAL_TYPE.value, keyName: 'Lunch', keyValue: 'Lunch').save(flush: true)
        MasterKeySetup.findByKeyTypeAndKeyValue(MasterKeyValue.MEAL_TYPE.value, 'Snack: after lunch') ?: new MasterKeySetup(keyType: MasterKeyValue.MEAL_TYPE.value, keyName: 'Snack: after lunch', keyValue: 'Snack: after lunch').save(flush: true)
        MasterKeySetup.findByKeyTypeAndKeyValue(MasterKeyValue.MEAL_TYPE.value, 'Other food') ?: new MasterKeySetup(keyType: MasterKeyValue.MEAL_TYPE.value, keyName: 'Other food', keyValue: 'Other food').save(flush: true)

        MasterKeySetup.findByKeyTypeAndKeyValue(MasterKeyValue.PACKAGE_TYPE.value, 'Regular') ?: new MasterKeySetup(keyType: MasterKeyValue.MEAL_TYPE.value, keyName: 'Regular', keyValue: 'Regular').save(flush: true)
        MasterKeySetup.findByKeyTypeAndKeyValue(MasterKeyValue.PACKAGE_TYPE.value, 'Irregular') ?: new MasterKeySetup(keyType: MasterKeyValue.MEAL_TYPE.value, keyName: 'Irregular', keyValue: 'Irregular').save(flush: true)

        District.findByName('Manikganj') ?: new District(name: 'Manikganj').save(flush: true)

        MedCompany.findByCompanyName('Beximco')?: new MedCompany(companyName: 'Beximco').save(flush: true)
        MedCompany.findByCompanyName('Square')?: new MedCompany(companyName: 'Square').save(flush: true)
        MedCompany.findByCompanyName('Aristopharma')?: new MedCompany(companyName: 'Aristopharma').save(flush: true)


        GroupItem.findByItemTypeAndName(GroupItems.DISEASE.value, 'Microvascular') ?: new GroupItem(itemType: GroupItems.DISEASE.value, name: 'Microvascular').save(flush: true)
        GroupItem.findByItemTypeAndName(GroupItems.DISEASE.value, 'Macrovascular') ?: new GroupItem(itemType: GroupItems.DISEASE.value, name: 'Macrovascular').save(flush: true)
        GroupItem.findByItemTypeAndName(GroupItems.DISEASE.value, 'Diabetic Associated') ?: new GroupItem(itemType: GroupItems.DISEASE.value, name: 'Diabetic Associated').save(flush: true)
        GroupItem.findByItemTypeAndName(GroupItems.DISEASE.value, 'Common') ?: new GroupItem(itemType: GroupItems.DISEASE.value, name: 'Common').save(flush: true)

        MasterKeySetup.findByKeyType(MasterKeyValue.BREAKFAST.value) ?: new MasterKeySetup(keyType: MasterKeyValue.BREAKFAST.value, keyName: MasterKeyValue.BREAKFAST.value, keyValue: '08:00AM').save(flush: true)
        MasterKeySetup.findByKeyType(MasterKeyValue.LUNCH.value) ?: new MasterKeySetup(keyType: MasterKeyValue.LUNCH.value, keyName: MasterKeyValue.LUNCH.value, keyValue: '02:00PM').save(flush: true)
        MasterKeySetup.findByKeyType(MasterKeyValue.DINNER.value) ?: new MasterKeySetup(keyType: MasterKeyValue.DINNER.value, keyName: MasterKeyValue.DINNER.value, keyValue: '09:00PM').save(flush: true)
        MasterKeySetup.findByKeyType(MasterKeyValue.SNACK_BEFORE_LUNCH.value) ?: new MasterKeySetup(keyType: MasterKeyValue.SNACK_BEFORE_LUNCH.value, keyName: MasterKeyValue.SNACK_BEFORE_LUNCH.value, keyValue: '11:00AM').save(flush: true)
        MasterKeySetup.findByKeyType(MasterKeyValue.SNACK_BEFORE_DINNER.value) ?: new MasterKeySetup(keyType: MasterKeyValue.SNACK_BEFORE_DINNER.value, keyName: MasterKeyValue.SNACK_BEFORE_DINNER.value, keyValue: '05:00PM').save(flush: true)
        MasterKeySetup.findByKeyType(MasterKeyValue.SNACK_BEFORE_SLEEP.value) ?: new MasterKeySetup(keyType: MasterKeyValue.SNACK_BEFORE_SLEEP.value, keyName: MasterKeyValue.SNACK_BEFORE_SLEEP.value, keyValue: '11:00PM').save(flush: true)
        MasterKeySetup.findByKeyType(MasterKeyValue.EXERCISE_MORNING.value) ?: new MasterKeySetup(keyType: MasterKeyValue.EXERCISE_MORNING.value, keyName: MasterKeyValue.EXERCISE_MORNING.value, keyValue: '07:00AM').save(flush: true)
        MasterKeySetup.findByKeyType(MasterKeyValue.EXERCISE_AFTERNOON.value) ?: new MasterKeySetup(keyType: MasterKeyValue.EXERCISE_AFTERNOON.value, keyName: MasterKeyValue.EXERCISE_AFTERNOON.value, keyValue: '04:30PM').save(flush: true)
        MasterKeySetup.findByKeyType(MasterKeyValue.MEDICINE_41.value) ?: new MasterKeySetup(keyType: MasterKeyValue.MEDICINE_41.value, keyName: MasterKeyValue.MEDICINE_41.value, keyValue: '06:00AM').save(flush: true)
        MasterKeySetup.findByKeyType(MasterKeyValue.MEDICINE_42.value) ?: new MasterKeySetup(keyType: MasterKeyValue.MEDICINE_42.value, keyName: MasterKeyValue.MEDICINE_42.value, keyValue: '12:00PM').save(flush: true)
        MasterKeySetup.findByKeyType(MasterKeyValue.MEDICINE_43.value) ?: new MasterKeySetup(keyType: MasterKeyValue.MEDICINE_43.value, keyName: MasterKeyValue.MEDICINE_43.value, keyValue: '06:00PM').save(flush: true)
        MasterKeySetup.findByKeyType(MasterKeyValue.MEDICINE_44.value) ?: new MasterKeySetup(keyType: MasterKeyValue.MEDICINE_44.value, keyName: MasterKeyValue.MEDICINE_44.value, keyValue: '11:30PM').save(flush: true)
        MasterKeySetup.findByKeyType(MasterKeyValue.MEDICINE_51.value) ?: new MasterKeySetup(keyType: MasterKeyValue.MEDICINE_51.value, keyName: MasterKeyValue.MEDICINE_51.value, keyValue: '05:00AM').save(flush: true)
        MasterKeySetup.findByKeyType(MasterKeyValue.MEDICINE_52.value) ?: new MasterKeySetup(keyType: MasterKeyValue.MEDICINE_52.value, keyName: MasterKeyValue.MEDICINE_52.value, keyValue: '10:00AM').save(flush: true)
        MasterKeySetup.findByKeyType(MasterKeyValue.MEDICINE_53.value) ?: new MasterKeySetup(keyType: MasterKeyValue.MEDICINE_53.value, keyName: MasterKeyValue.MEDICINE_53.value, keyValue: '03:00PM').save(flush: true)
        MasterKeySetup.findByKeyType(MasterKeyValue.MEDICINE_54.value) ?: new MasterKeySetup(keyType: MasterKeyValue.MEDICINE_54.value, keyName: MasterKeyValue.MEDICINE_54.value, keyValue: '08:00PM').save(flush: true)
        MasterKeySetup.findByKeyType(MasterKeyValue.MEDICINE_55.value) ?: new MasterKeySetup(keyType: MasterKeyValue.MEDICINE_55.value, keyName: MasterKeyValue.MEDICINE_55.value, keyValue: '11:30PM').save(flush: true)
    }
    def destroy = {
    }
}
