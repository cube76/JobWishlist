package com.mqa.jobwishlist.core.utils

import android.text.SpannableString
import android.text.Spanned
import androidx.core.text.HtmlCompat

class ConvertHtml{
    fun fromHtml(html: String?): Spanned {
        return if (html == null) {
            SpannableString("")
        } else
            HtmlCompat.fromHtml(html, HtmlCompat.FROM_HTML_MODE_LEGACY)
    }
}