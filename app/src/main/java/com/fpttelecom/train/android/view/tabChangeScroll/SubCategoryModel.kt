package com.fpttelecom.train.android.view.tabChangeScroll

/**
 * Creator: Nguyen Van Van
 * Date: 21,April,2022
 * Time: 4:19 PM
 */

data class SubCategoryModel(
    val services: List<String?>? = null,
    val name: String? = "",
    var isSelected: Boolean = false,
    var scrollPosition: Int = 0
)