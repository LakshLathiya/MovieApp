package com.example.lakshmovieapp.utils

import java.text.ParseException
import java.text.SimpleDateFormat
import java.util.*

class Helper {
    companion object {

        fun parseDateyyyy(input: String?): String? {
            val inputPattern = "yyyy-MM-dd"
            val outputPattern = "yyyy"
            val inputFormat = SimpleDateFormat(inputPattern)
            val outputFormat = SimpleDateFormat(outputPattern)
            var date: Date? = null
            var str: String? = null
            try {
                date = inputFormat.parse(input)
                str = outputFormat.format(date)
            } catch (e: ParseException) {
                e.printStackTrace()
            }
            return str
        }
    }

}