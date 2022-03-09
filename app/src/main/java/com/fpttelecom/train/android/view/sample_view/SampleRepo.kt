package com.fpttelecom.train.android.view.sample_view

import com.fpttelecom.train.android.api.BaseRepo
import com.fpttelecom.train.android.api.Repo

interface SampleRepo: BaseRepo {
    fun repoSample(): Repo {
        return Repo(
            getHeaders(false),
            "sample",
            message = ""
        )
    }
}