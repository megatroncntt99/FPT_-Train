package com.fpttelecom.train.android.utils

import android.annotation.TargetApi
import android.app.Activity
import android.content.Context
import android.content.ContextWrapper
import android.os.Build
import java.util.*

/**
 * Creator: Nguyen Van Van
 * Date: 02,April,2022
 * Time: 6:40 PM
 */

object LanguageUtils {
    fun getLanguageName(activity: Activity): String {
        return activity.baseContext.resources.configuration.locale.displayLanguage
    }

    @TargetApi(Build.VERSION_CODES.N)
    fun getLanguageCode(context: Context): String {
        return context.resources.configuration.locales.get(0).language
    }

    fun setLocale(context: Context, language: String): Context {
        return updateResourcesLegacy(context, language)
    }

    private fun updateResourcesLegacy(context: Context, language: String): ContextWrapper {
        val locale = Locale(language)
        Locale.setDefault(locale)
        val resources = context.resources
        val configuration = resources.configuration
        configuration.setLocale(locale)
        configuration.setLayoutDirection(locale)

        return ContextWrapper(context.createConfigurationContext(configuration))
    }
}