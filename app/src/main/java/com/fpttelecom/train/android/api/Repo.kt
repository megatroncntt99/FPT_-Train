package com.fpttelecom.train.android.api

/**
 * Created by giaan on 03/12/21.
 * Company: TVO
 * Email: antranit95@gmail.com
 */
class Repo(
    val headers: Map<String, String>,
    val url: String,
    val message: Any? = null,
    val codeRequired: Any? = null,
    val typeRepo: TypeRepo = TypeRepo.GET,
)

enum class TypeRepo {
    GET, POST, PUT, DELETE
}