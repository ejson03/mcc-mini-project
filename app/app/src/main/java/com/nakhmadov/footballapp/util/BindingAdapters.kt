package com.nakhmadov.football_data.util

import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.core.content.res.ResourcesCompat
import androidx.databinding.BindingAdapter
import com.bumptech.glide.Glide
import android.graphics.drawable.PictureDrawable
import android.util.Log
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions.withCrossFade
import com.nakhmadov.footballapp.R
import com.nakhmadov.footballapp.util.SvgSoftwareLayerSetter


@BindingAdapter("imageWithUrl")
fun ImageView.imageWithUrl(imageUrl: String?) {

    if (imageUrl != null) {
        if (imageUrl.endsWith(".svg")) {

            val requestBuilder = Glide.with(this)
                .`as`(PictureDrawable::class.java)
                .error(R.drawable.ic_competition)
                .transition(withCrossFade())
                .centerCrop()
                .listener(SvgSoftwareLayerSetter())
            requestBuilder.load(imageUrl).into(this)

        } else
            Glide
                .with(this)
                .load(imageUrl)
                .centerInside()
                .placeholder(R.drawable.ic_competition)
                .error(R.drawable.ic_competition)
                .into(this)
    } else
        this.setImageResource(R.drawable.ic_competition)
}

@BindingAdapter("goneIfNotNull")
fun goneIfNotNull(view: View, it: Any?) {
    view.visibility = if (it != null) View.GONE else View.VISIBLE
}

@BindingAdapter("visibleIfEmpty")
fun visibleIfEmpty(view: View, it: List<Any?>?) {
    view.visibility = if (it != null && it.isNotEmpty()) View.GONE else View.VISIBLE
}


@BindingAdapter("setTextOrEmpty")
fun TextView.setTextOrEmpty(text: String?) {
    if (text == null) {
        this.text = ""
    } else {
        this.text = text
    }
}

@BindingAdapter("setTextOrDefis")
fun TextView.setTextOrDefis(text: String?) {
    if (text == null) {
        this.text = "-"
    } else {
        this.text = text
    }
}

@BindingAdapter("setDateOrEmpty")
fun TextView.setDateOrEmpty(text: String?) {
    if (text == null) {
        this.text = ""
    } else {
        this.text = convertDate(text, context)
    }
}

// For CircularProgressBar
@BindingAdapter("goneIfCount")
fun View.goneIfCount(count: Int) {
    if (count == 2) {
        this.visibility = View.GONE
    } else {
        this.visibility = View.VISIBLE
    }
}

// For Views
@BindingAdapter("visibleIfCount")
fun View.visibleIfCount(count: Int) {
    if (count == 2) {
        this.visibility = View.VISIBLE
    } else {
        this.visibility = View.GONE
    }
}

@BindingAdapter("visibleIfNullOrEmpty")
fun View.visibleIfNullOrEmpty(list: List<Any?>?) {
    this.visibility = if (list == null || list.isEmpty()) View.VISIBLE else View.GONE
}
