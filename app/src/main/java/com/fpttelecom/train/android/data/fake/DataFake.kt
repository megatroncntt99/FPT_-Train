package com.fpttelecom.train.android.data.fake

import com.fpttelecom.train.android.data.model.BrandContainerResponse
import com.fpttelecom.train.android.data.model.ContainerResponse
import com.fpttelecom.train.android.data.model.TypeContainerResponse
import com.fpttelecom.train.android.data.model.WorkResponse

/**
 * Created by Nguyễn Văn Vân on 12/7/2021.
 */
object DataFake {
    fun getDataListWork(): ArrayList<WorkResponse> {
        val listWork: ArrayList<WorkResponse> = ArrayList()
        listWork.add(
            WorkResponse(
                "http://113.161.84.22:8063/Images/Product/211207044802_Oval.png",
                "CÔNG TY  TNHH PEPSI",
                "114/23 đường số 8 , phường Bình Hưng Hòa ,Q.Bình Tan, TP.HCM",
                "0966662918"
            )
        )
        listWork.add(
            WorkResponse(
                "http://113.161.84.22:8063/Images/Product/211207044802_Oval@2x.png",
                "CÔNG TY TNHH OLAM",
                "114/23 đường số 8 , phường Bình Hưng Hòa ,Q.Bình Tan, TP.HCM",
                "0966662918"
            )
        )
        listWork.add(
            WorkResponse(
                "http://113.161.84.22:8063/Images/Product/211207044802_Oval.png",
                "CJ FOODS VIETNAM CO. LTD",
                "114/23 đường số 8 , phường Bình Hưng Hòa ,Q.Bình Tan, TP.HCM",
                "0966662918"
            )
        )
        listWork.add(
            WorkResponse(
                "http://113.161.84.22:8063/Images/Product/211207044802_Oval@2x.png",
                "CÔNG TY TNHH THÁI BÌNH GIA",
                "114/23 đường số 8 , phường Bình Hưng Hòa ,Q.Bình Tan, TP.HCM",
                "0966662918"
            )
        )
        listWork.add(
            WorkResponse(
                "http://113.161.84.22:8063/Images/Product/211207044802_Oval.png",
                "CÔNG TY  TNHH PEPSI",
                "114/23 đường số 8 , phường Bình Hưng Hòa ,Q.Bình Tan, TP.HCM",
                "0966662918"
            )
        )
        listWork.add(
            WorkResponse(
                "http://113.161.84.22:8063/Images/Product/211207044802_Oval@2x.png",
                "CÔNG TY TNHH OLAM",
                "114/23 đường số 8 , phường Bình Hưng Hòa ,Q.Bình Tan, TP.HCM",
                "0966662918"
            )
        )
        listWork.add(
            WorkResponse(
                "http://113.161.84.22:8063/Images/Product/211207044802_Oval.png",
                "CJ FOODS VIETNAM CO. LTD",
                "114/23 đường số 8 , phường Bình Hưng Hòa ,Q.Bình Tan, TP.HCM",
                "0966662918"
            )
        )
        listWork.add(
            WorkResponse(
                "http://113.161.84.22:8063/Images/Product/211207044802_Oval@2x.png",
                "CÔNG TY TNHH THÁI BÌNH GIA",
                "114/23 đường số 8 , phường Bình Hưng Hòa ,Q.Bình Tan, TP.HCM",
                "0966662918"
            )
        )
        return listWork;
    }

    fun getDataListContainer(): ArrayList<ContainerResponse> {
        val listContainer: ArrayList<ContainerResponse> = ArrayList()
        listContainer.add(
            ContainerResponse(
                "HDMU5467659",
                "40HR",
                "2001",
                "2006",
                "Thermoking",
                "2015",
                "KH đang thuê",
                "Chưa phân loại",
                "OK"
            )
        )
        listContainer.add(
            ContainerResponse(
                "EMCU5217510",
                "40HR",
                "2002",
                "2006",
                "Daikin",
                "2015",
                "KH đang thuê",
                "Chưa phân loại",
                "OK"
            )
        )
        listContainer.add(
            ContainerResponse(
                "HDMU5467659",
                "40HR",
                "2001",
                "2006",
                "Thermoking",
                "2015",
                "KH đang thuê",
                "Chưa phân loại",
                "OK"
            )
        )
        listContainer.add(
            ContainerResponse(
                "EMCU5217510",
                "40HR",
                "2002",
                "2006",
                "Daikin",
                "2015",
                "KH đang thuê",
                "Chưa phân loại",
                "OK"
            )
        )
        listContainer.add(
            ContainerResponse(
                "HDMU5467659",
                "40HR",
                "2001",
                "2006",
                "Thermoking",
                "2015",
                "KH đang thuê",
                "Chưa phân loại",
                "OK"
            )
        )
        listContainer.add(
            ContainerResponse(
                "EMCU5217510",
                "40HR",
                "2002",
                "2006",
                "Daikin",
                "2015",
                "KH đang thuê",
                "Chưa phân loại",
                "OK"
            )
        )
        return listContainer
    }

    fun getDataListBrandContainer(): ArrayList<BrandContainerResponse> {
        val listBrandContainer: ArrayList<BrandContainerResponse> = ArrayList()
        listBrandContainer.add(BrandContainerResponse("Carrier", 10, 0))
        listBrandContainer.add(BrandContainerResponse("Daikin", 20, 1))
        listBrandContainer.add(BrandContainerResponse("Thermokin", 30, 2))
        listBrandContainer.add(BrandContainerResponse("Starcool", 30, 0))
        listBrandContainer.add(BrandContainerResponse("Mitsubishi", 40, 1))
        listBrandContainer.add(BrandContainerResponse("Thermokin", 30, 2))

        return listBrandContainer
    }

    fun getDataListTypeContainer(): ArrayList<TypeContainerResponse> {
        val listBrandContainer: ArrayList<TypeContainerResponse> = ArrayList()
        listBrandContainer.add(TypeContainerResponse("10DC", "10"))
        listBrandContainer.add(TypeContainerResponse("20DC", "20"))
        listBrandContainer.add(TypeContainerResponse("30HC", "20"))
        listBrandContainer.add(TypeContainerResponse("40YC", "10"))
        listBrandContainer.add(TypeContainerResponse("50DC", "20"))
        listBrandContainer.add(TypeContainerResponse("60DC", "20"))
        listBrandContainer.add(TypeContainerResponse("70DC", "10"))
        listBrandContainer.add(TypeContainerResponse("80HC", "20"))
        listBrandContainer.add(TypeContainerResponse("90YC", "20"))
        listBrandContainer.add(TypeContainerResponse("100DC", "10"))
        return listBrandContainer
    }

    fun getDataListTypeContainerCold(): ArrayList<TypeContainerResponse> {
        val listBrandContainer: ArrayList<TypeContainerResponse> = ArrayList()
        listBrandContainer.add(TypeContainerResponse("10DC", "10"))
        listBrandContainer.add(TypeContainerResponse("20DC", "20"))
        listBrandContainer.add(TypeContainerResponse("30HC", "20"))
        listBrandContainer.add(TypeContainerResponse("40YC", "10"))
        listBrandContainer.add(TypeContainerResponse("50DC", "20"))
        return listBrandContainer
    }

    fun getDataListTypeContainerDried(): ArrayList<TypeContainerResponse> {
        val listBrandContainer: ArrayList<TypeContainerResponse> = ArrayList()
        listBrandContainer.add(TypeContainerResponse("60DC", "20"))
        listBrandContainer.add(TypeContainerResponse("70DC", "10"))
        listBrandContainer.add(TypeContainerResponse("80HC", "20"))
        listBrandContainer.add(TypeContainerResponse("90YC", "20"))
        listBrandContainer.add(TypeContainerResponse("100DC", "10"))
        return listBrandContainer
    }
}