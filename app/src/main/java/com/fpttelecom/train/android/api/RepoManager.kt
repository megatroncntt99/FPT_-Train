package com.fpttelecom.train.android.api

import com.fpttelecom.train.android.di.module.ApiClientModule.CMC_API_KEY
import com.fpttelecom.train.android.di.module.ApiClientModule.HEADER_API_KEY
import com.fpttelecom.train.android.di.module.ApiClientModule.HEADER_LANG
import com.fpttelecom.train.android.di.module.ApiClientModule.HEADER_UUID
import com.fpttelecom.train.android.view.sample_view.SampleRepo
import java.util.*
import kotlin.collections.HashMap

class RepoManager : SampleRepo
//    ,SampleRepo2,
//    ...

interface BaseRepo {
    fun getHeaders(withToken: Boolean = true): HashMap<String, String> {
        val headers = HashMap<String, String>()
        /*if (ProfileService.authen?.access_token?.isNotEmpty() == true && withToken)
            headers[HEADER_AUTHORIZATION] = ProfileService.authen?.access_token.toString()*/
        headers[HEADER_LANG] = "vi"
        headers[HEADER_API_KEY] = CMC_API_KEY
        headers[HEADER_UUID] = UUID.randomUUID().toString()
        return headers
    }
}