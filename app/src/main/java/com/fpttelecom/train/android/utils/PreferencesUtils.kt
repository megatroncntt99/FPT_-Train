package com.fpttelecom.train.android.utils

import android.content.SharedPreferences
import com.google.gson.Gson

/**
 * Creator: Nguyen Van Van
 * Date: 02,April,2022
 * Time: 7:45 PM
 */

object SharedPrefsUtils {
    private const val KEY_CURRENT_LANGUAGE = "KEY_CURRENT_LANGUAGE"
    fun <T> get(
        mSharedPreferences: SharedPreferences,
        key: String,
        anonymousClass: Class<T>,
        defaultValue: T
    ): T {
        return when (anonymousClass) {
            String::class.java -> {
                mSharedPreferences.getString(key, defaultValue as String) as T
            }
            Boolean::class.java -> {
                java.lang.Boolean.valueOf(
                    mSharedPreferences.getBoolean(
                        key,
                        (defaultValue as Boolean)
                    )
                ) as T
            }
            Float::class.java -> {
                java.lang.Float.valueOf(
                    mSharedPreferences.getFloat(
                        key,
                        (defaultValue as Float)
                    )
                ) as T
            }
            Int::class.java -> {
                Integer.valueOf(mSharedPreferences.getInt(key, (defaultValue as Int))) as T
            }
            Long::class.java -> {
                java.lang.Long.valueOf(
                    mSharedPreferences.getLong(
                        key,
                        (defaultValue as Long)
                    )
                ) as T
            }
            else -> {
                Gson().fromJson(mSharedPreferences.getString(key, ""), anonymousClass)
            }
        }
    }
}