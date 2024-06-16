package com.dev_enzo.domain.utils

import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale
import java.util.TimeZone

class DateUtil {
    companion object {
        /**
         * This function formats the current Date string received
         * from a data source
         * @param inDate receive the date value
         * @param returns "MM/dd/yyyy" format
         */
        fun formatDate(inDate: String): String {
            val inputDateFormat = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'", Locale.getDefault())
            val outputDateFormat = SimpleDateFormat("MM/dd/yyyy", Locale.getDefault())
            inputDateFormat.timeZone = TimeZone.getTimeZone("UTC")
            val date: Date = inputDateFormat.parse(inDate)!!
            return outputDateFormat.format(date)
        }

        /**
         * This function format the current Date string received
         * from a data source
         * @param inDate receive the date value
         * @param returns "yyyy" format
         */
        fun getYear(inDate: String): String {
            val inputDateFormat = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'", Locale.getDefault())
            val outputDateFormat = SimpleDateFormat("yyyy", Locale.getDefault())
            inputDateFormat.timeZone = TimeZone.getTimeZone("UTC")
            val date: Date = inputDateFormat.parse(inDate)!!
            return outputDateFormat.format(date)
        }
    }
}