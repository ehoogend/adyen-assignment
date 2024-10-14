package com.adyen.android.assignment.data.places.model

import androidx.annotation.StringRes
import com.adyen.android.assignment.R

enum class ClosedBucket(@StringRes val stringRes: Int) {
    VeryLikelyOpen(R.string.very_likely_open),
    LikelyOpen(R.string.likely_open),
    LikelyClosed(R.string.likely_closed),
    VeryLikelyClosed(R.string.very_likely_closed),
    Unsure(R.string.unsure)
}
