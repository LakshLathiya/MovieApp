package com.example.lakshmovieapp.utils

import android.content.Context
import android.graphics.Color
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import android.os.Build
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

        fun isNetworkAvailable(context: Context): Boolean {
            val connectivityManager =
                context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager

            // For 29 api or above
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
                val capabilities =
                    connectivityManager.getNetworkCapabilities(connectivityManager.activeNetwork)
                        ?: return false
                return when {
                    capabilities.hasTransport(NetworkCapabilities.TRANSPORT_WIFI) -> true
                    capabilities.hasTransport(NetworkCapabilities.TRANSPORT_ETHERNET) -> true
                    capabilities.hasTransport(NetworkCapabilities.TRANSPORT_CELLULAR) -> true
                    else -> false
                }
            }
            // For below 29 api
            else {
                if (connectivityManager.activeNetworkInfo != null && connectivityManager.activeNetworkInfo!!.isConnectedOrConnecting) {
                    return true
                }
            }
            return false
        }
    }

}