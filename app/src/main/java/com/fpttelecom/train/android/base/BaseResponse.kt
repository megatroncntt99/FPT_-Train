package com.fpttelecom.train.android.base

import com.google.gson.JsonElement
import java.util.*


class BaseResponse(
    val data: JsonElement? = null,
    val message: String = "",
    val code: String = "",
    val status: Int = 0,
){
    fun data(): String {
        return Objects.toString(data)
    }
}