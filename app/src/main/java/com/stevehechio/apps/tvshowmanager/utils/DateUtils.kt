package com.stevehechio.apps.tvshowmanager.utils

import java.text.DateFormat
import java.text.ParseException
import java.text.SimpleDateFormat
import java.util.*

/**
 * Created by stevehechio on 9/3/21
 */
object DateUtils {
    //"2014-11-07T00:00:00.000Z"
    private const val DATE_FORMAT = "yyyy-MM-dd'T'HH:mm:ss.SSS'Z'"

    fun formatGMTDateStr(strDate: String): String {
        val inputFormatter: DateFormat =
            SimpleDateFormat(DATE_FORMAT, Locale.getDefault())
        inputFormatter.timeZone = TimeZone.getTimeZone(TimeZone.getDefault().id)
        val outputFormatter: DateFormat =
            SimpleDateFormat("dd MMMM yyyy", Locale.ENGLISH)
        return try {
            outputFormatter.format(Objects.requireNonNull(inputFormatter.parse(strDate))) ?: ""
        }catch (e: ParseException){
            strDate
        }
    }

    fun formatStrDateToGMTString(calendar: Date): String{
        val sdf = SimpleDateFormat(DATE_FORMAT, Locale.ENGLISH)
        return try {
            sdf.format(calendar.time)
        }catch (e: ParseException){
            ""
        }
    }

    fun formatStrDateToGMTStringForUser(calendar: Date): String{
        val sdf = SimpleDateFormat("dd MMMM yyyy", Locale.ENGLISH)
        return try {
            sdf.format(calendar.time)
        }catch (e: ParseException){
            ""
        }
    }


}