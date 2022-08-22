package com.fpttelecom.train.android.view.tabChangeScroll

import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.fpttelecom.train.android.base.BaseFragment
import com.fpttelecom.train.android.databinding.FragmentTabChangeScrollBinding

/**
 * Creator: Nguyen Van Van
 * Date: 21,April,2022
 * Time: 4:04 PM
 */

class TabChangeScrollFragment : BaseFragment<FragmentTabChangeScrollBinding>() {
    private var linearLayoutManager: LinearLayoutManager? = null
    private var subCatAdapter: SubCategoryAdapter? = null
    private var selectedSubCatPosition: Int = 0

    override fun getViewBinding() = FragmentTabChangeScrollBinding.inflate(layoutInflater)

    override fun initViewModel() {

    }

    override fun initView() {
        setStatusBarContentBlack()
        val subCatModel = ArrayList<SubCategoryModel>()
        val catModel = ArrayList<CatModel>()

        catModel.addAll(getCatModel(oneToFive, 0))
        subCatModel.add(SubCategoryModel(oneToFive, "one to five", true, 0))

        subCatModel.add(SubCategoryModel(sixToten, "six to ten", false, catModel.size + 1))
        catModel.addAll(getCatModel(sixToten, 1))

        subCatModel.add(
            SubCategoryModel(
                eleventTofifteen,
                "eleven to fifteen",
                false,
                catModel.size + 1
            )
        )
        catModel.addAll(getCatModel(eleventTofifteen, 2))

        subCatModel.add(
            SubCategoryModel(
                sixteenToTwenty,
                "sixteen to twenty",
                false,
                catModel.size + 1
            )
        )
        catModel.addAll(getCatModel(sixteenToTwenty, 3))

        subCatModel.add(
            SubCategoryModel(
                twentyoneToTwentyfive,
                "twenty one to twenty five",
                false,
                catModel.size + 1
            )
        )
        catModel.addAll(getCatModel(twentyoneToTwentyfive, 4))

        subCatModel.add(
            SubCategoryModel(
                twentysixToThirty,
                "twenty six to thrity",
                false,
                catModel.size + 1
            )
        )
        catModel.addAll(getCatModel(twentysixToThirty, 5))

        getVB().rvSubCatAct.apply {
            layoutManager = LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false)
            subCatAdapter = SubCategoryAdapter(subCatModel)
            adapter = subCatAdapter
        }

        linearLayoutManager = LinearLayoutManager(requireContext())
        getVB().rvServiceList.apply {
            layoutManager = linearLayoutManager
            adapter = CategoryAdapter(catModel)
            addOnScrollListener(object : RecyclerView.OnScrollListener() {
                override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                    linearLayoutManager?.let { lm ->
                        val index = lm.findFirstVisibleItemPosition() + 2
                        if ((catModel.size) >= index) {
                            catModel[index].let { sd ->
                                if (selectedSubCatPosition != sd.index) {
                                    selectedSubCatPosition = sd.index
                                    subCatAdapter?.updateList(selectedSubCatPosition)
                                    getVB().rvSubCatAct.layoutManager?.scrollToPosition(
                                        selectedSubCatPosition
                                    )
                                }
                            }
                        }
                    }
//                    }
                }
            })
        }
    }

    override fun clickView() {

    }

    override fun flowOnce() {

    }

    override fun onComeBack() {

    }

    private fun getCatModel(model: ArrayList<String>, index: Int): ArrayList<CatModel> {
        val catModelList = ArrayList<CatModel>()
        model.forEach { str ->
            catModelList.add(CatModel(str, index))
        }
        return catModelList
    }

    private var oneToFive = arrayListOf(
        "1 for first tab",
        "2 for first tab",
        "3 for first tab",
        "4 for first tab",
        "5 for first tab"
    )
    private var sixToten = arrayListOf(
        "6 for second tab",
        "7 for second tab",
        "8 for second tab",
        "9 for second tab",
        "10 for second tab"
    )
    private var eleventTofifteen = arrayListOf(
        "11 for third tab",
        "12 for third tab",
        "13 for third tab",
        "14 for third tab",
        "15 for third tab"
    )
    private var sixteenToTwenty = arrayListOf(
        "16 for fourth tab",
        "17 for fourth tab",
        "18 for fourth tab",
        "19 for fourth tab",
        "20 for fourth tab"
    )
    private var twentyoneToTwentyfive = arrayListOf(
        "21 for fifth tab",
        "22 for fifth tab",
        "23 for fifth tab",
        "24 for fifth tab",
        "25 for fifth tab"
    )
    private var twentysixToThirty = arrayListOf(
        "26 for sixth tab",
        "27 for sixth tab",
        "28 for sixth tab",
        "29 for sixth tab",
        "30 for sixth tab"
    )
}