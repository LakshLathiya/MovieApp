package com.example.lakshmovieapp.utils

import android.graphics.Color
import android.text.SpannableString
import android.text.SpannableStringBuilder
import android.text.style.ForegroundColorSpan
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

        fun twoColorTextView(preText: String , postText : String): SpannableStringBuilder {
            val builder = SpannableStringBuilder()
            val str1 = SpannableString(preText)
            str1.setSpan(ForegroundColorSpan(Color.BLUE), 0, str1.length, 0)
            builder.append(str1)
            val str2 = SpannableString(postText)
            str2.setSpan(ForegroundColorSpan(Color.BLACK), 0, str2.length, 0)
            builder.append(str2)

            return builder
        }
    }

}