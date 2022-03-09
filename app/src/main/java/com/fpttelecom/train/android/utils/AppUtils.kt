package com.fpttelecom.train.android.utils

import android.content.ActivityNotFoundException
import android.content.Context
import android.content.Intent
import android.net.Uri
import com.fpttelecom.train.android.R
import java.lang.Exception

/**
 * Created by Nguyễn Văn Vân on 12/15/2021.
 */
object AppUtils {
    fun openPlayStoreForApp(context: Context) {
        try {
            val intent = Intent(
                Intent.ACTION_VIEW,
                Uri.parse(
                    context
                        .resources
                        .getString(R.string.app_market_link) + context.packageName
                )
            )
            intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK
            context.startActivity(intent)
        } catch (e: ActivityNotFoundException) {
            try {
                val intent = Intent(
                    Intent.ACTION_VIEW,
                    Uri.parse(
                        context
                            .resources
                            .getString(R.string.app_google_play_store_link) + context.packageName
                    )
                )
                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
                context.startActivity(intent)
            } catch (ignored: Exception) {
            }
        }
    }

    fun openOtherApp(context: Context, appPackageName: String) {
        try {
            val intent = context.packageManager.getLaunchIntentForPackage(appPackageName)
            if (intent != null) {
                context.startActivity(intent)
                return
            }
            context.startActivity(
                Intent(
                    Intent.ACTION_VIEW,
                    Uri.parse(
                        context
                            .resources
                            .getString(R.string.app_market_link) + appPackageName
                    )
                )
            )
        } catch (e: ActivityNotFoundException) {
            context.startActivity(
                Intent(
                    Intent.ACTION_VIEW,
                    Uri.parse(
                        context
                            .resources
                            .getString(R.string.app_google_play_store_link) + appPackageName
                    )
                )
            )
        }
    }
}