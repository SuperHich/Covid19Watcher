package tn.superhich.covid19watcher.data.model

import androidx.annotation.DrawableRes
import androidx.annotation.StringRes

data class PracticalItem(
    @DrawableRes val iconResId: Int,
    @StringRes val titleResId: Int
)