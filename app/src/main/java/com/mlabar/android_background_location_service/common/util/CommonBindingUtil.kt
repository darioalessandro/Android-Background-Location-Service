package com.mlabar.android_background_location_service.common.util

import android.databinding.BindingAdapter
import android.view.View

object CommonBindingUtil {

    @JvmStatic
    @BindingAdapter("isVisible")
    fun isVisible(view: View, visible: Boolean) {
        view.visibility = if(visible) View.VISIBLE else View.INVISIBLE
    }

}