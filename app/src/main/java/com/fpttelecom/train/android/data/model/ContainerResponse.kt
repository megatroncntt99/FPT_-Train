package com.fpttelecom.train.android.data.model

/**
 * Created by Nguyễn Văn Vân on 12/15/2021.
 */
data class ContainerResponse(
    val idContainer: String,
    val typeContainer: String,
    val yearMakeCase: String,
    val yearMakeMachine: String,
    val machineSeriesBrand: String,
    val machineSeriesModel: String,
    val typeService: String,
    val classify: String,
    val status: String,
)