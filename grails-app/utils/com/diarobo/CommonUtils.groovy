package com.diarobo

import grails.web.servlet.mvc.GrailsParameterMap
import org.grails.web.json.JSONObject
import org.joda.time.DateTime
import org.joda.time.format.DateTimeFormat
import org.joda.time.format.DateTimeFormatter

/**
 * Created by Aminul on 12/1/2016.
 */
class CommonUtils {
    public static final String ROLE_PATIENT = "ROLE_PATIENT"
    public static final String ROLE_CAREGIVER = "ROLE_CAREGIVER"
    public static final String ROLE_DOCTOR = "ROLE_DOCTOR"
    public static final String ROLE_ADMIN = "ROLE_ADMIN"

    public static final String IS_ERROR ="isError"
    public static final String MESSAGE ="message"
    public static final String OBJ ="obj"

    public static final String COMMON_ERROR_MESSAGE ="Something wrong. Please try later or contact admin"
    public static final String COMMON_NOT_FOUND_KEY ="common.item.not.found"
    public static final String DELETE_SUCCESS_KEY ="common.delete.success"
    public static final String COMMON_DELETE_FAILED_MESSAGE ="Data delete failed."
    public static final String NOT_APPLICABLE ="N/A"


    //dataTable related items
    public static final int DEFAULT_PAGINATION_START =0
    public static final int DEFAULT_PAGINATION_LENGTH =10
    public static final int MAX_PAGINATION_LENGTH =100
    public static final String DEFAULT_PAGINATION_SORT_ORDER ='desc'
    public static final String DEFAULT_PAGINATION_SORT_COLUMN ='id'
    public static final int DEFAULT_PAGINATION_SORT_IDX =0
    public static final String PERCENTAGE_SIGN ='%'
    public static final String SORT_ORDER_ASC ='asc'
    public static final String SORT_ORDER_DESC ='desc'
    public static final String UI_DATE_FORMAT ='dd/MM/YYYY'
    public static final String PRESCRIPTION_TIME_FORMAT ='hh:mm a'
    public static final String PRESCRIPTION_TIME_FORMAT_NO_SPACE ='hh:mma'
    public static final Integer DAYS_NEED_TO_BE_SYNC = 2
    public static final String EXERCISE_LIBRARY = 'exercise_library'
    public static final String MEDICINE_LIBRARY = 'medicine_library'
    public static final String FOOD_LIBRARY = 'food_library'
    public static final String LIBRARY = 'library'
    public static final String DATE_FORMAT_FULL = 'dd/MM/YYYY hh:mm a'

    public static Map getPaginationParams(GrailsParameterMap params, String[] sortColumns) {
        String iDisplayLength = params.iDisplayLength ? params.iDisplayLength : DEFAULT_PAGINATION_LENGTH
        String iDisplayStart = params.iDisplayStart ? params.iDisplayStart : DEFAULT_PAGINATION_START

        String sSortDir = params.sSortDir_0 ? params.sSortDir_0 : DEFAULT_PAGINATION_SORT_ORDER
        int iSortingCol = params.iSortCol_0 ? params.getInt('iSortCol_0') : DEFAULT_PAGINATION_SORT_IDX
        String sSearch = params.sSearch ? params.sSearch : null
        if (sSearch) {
            sSearch = PERCENTAGE_SIGN + sSearch + PERCENTAGE_SIGN
        }
        String sortColumn = getSortColumn(sortColumns, iSortingCol)
        return [iDisplayStart:iDisplayStart, iDisplayLength:iDisplayLength, sSearch:sSearch, sortColumn: sortColumn, sSortDir: sSortDir]
    }
    public static String getSortColumn(String [] sortColumns, int idx){
        if(!sortColumns || sortColumns.length<1)
            return DEFAULT_PAGINATION_SORT_COLUMN
        int columnCounts = sortColumns.length
        if(idx>0 && idx<columnCounts){
            return sortColumns[idx]
        }
        return sortColumns[DEFAULT_PAGINATION_SORT_IDX]
    }

    public static DateTime getDateFromString(String dateStr) {
        DateTimeFormatter formatter = DateTimeFormat.forPattern(UI_DATE_FORMAT)
        DateTime dateTime = formatter.parseDateTime(dateStr)
        dateTime
    }

    public static Double getBmi(Double weight, Double height) {
        if(weight && height) {
            Double bmi = weight/(Math.pow(height, 2))
           return bmi
        }
        return null
    }

    public static DateTime addTimeToDate(String time, DateTime dateTime) {
        DateTimeFormatter formatter
        DateTime dateOnlyTime
        try {
            formatter = DateTimeFormat.forPattern(PRESCRIPTION_TIME_FORMAT)
            dateOnlyTime = formatter.parseDateTime(time)
        } catch (Exception ex) {
            formatter = DateTimeFormat.forPattern(PRESCRIPTION_TIME_FORMAT_NO_SPACE)
            dateOnlyTime = formatter.parseDateTime(time)
        }
        DateTime dateTimeReturn = new DateTime(dateTime.year, dateTime.monthOfYear, dateTime.dayOfMonth, dateOnlyTime.hourOfDay, dateOnlyTime.minuteOfHour)
        dateTimeReturn
    }

    static def getJsonValue(JSONObject json, String key) {
        if(json.has(key)) {
            return json[key]
        } else {
            return null
        }
    }
}
