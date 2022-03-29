package com.fpttelecom.train.android.view.demoCallApi

import com.fpttelecom.train.android.api.BaseRepo
import com.fpttelecom.train.android.api.Repo
import com.fpttelecom.train.android.api.TypeRepo

/**
 * Copyright by Intelin.
 * Creator: Nguyen Van Van
 * Date: 29,March,2022
 * Time: 3:30 PM
 */

interface GitRepo : BaseRepo {
    fun repoGetListUser(): Repo {
        return Repo(
            HashMap<String, String>(),
            "users",
            typeRepo = TypeRepo.GET
        )
    }
}