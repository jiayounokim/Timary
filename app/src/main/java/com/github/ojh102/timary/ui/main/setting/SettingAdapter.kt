package com.github.ojh102.timary.ui.main.setting

import com.github.ojh102.timary.R
import com.github.ojh102.timary.base.BaseListAdapter
import com.github.ojh102.timary.base.BaseViewModel

internal class SettingAdapter(viewModel: BaseViewModel) : BaseListAdapter<SettingItems>(viewModel) {
    override fun layoutIdByViewType(viewType: Int): Int {
        return R.layout.view_setting_title
    }
}
