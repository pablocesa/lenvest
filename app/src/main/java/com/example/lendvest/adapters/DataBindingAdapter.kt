package com.example.lendvest.adapters

import android.graphics.Bitmap
import android.graphics.drawable.Drawable
import android.net.Uri
import android.widget.ImageView
import android.widget.TextView
import androidx.databinding.BindingAdapter
import com.example.lendvest.models.contracts.NotificationType
import java.time.LocalDate


object DataBindingAdapter {
    @JvmStatic // add this line !!
    @BindingAdapter("android:src")
    fun setImageUri(view: ImageView, imageUri: String?) {
        if (imageUri == null) {
            view.setImageURI(null)
        } else {
            view.setImageURI(Uri.parse(imageUri))
        }
    }
    @JvmStatic // add this line !!
    @BindingAdapter("android:src")
    fun setImageViewResource(view: ImageView, resId : Int) {
        view.setImageResource(resId)
    }
    @JvmStatic // add this line !!
    @BindingAdapter("android:src")
    fun setImageUri(view: ImageView, imageUri: Uri?) {
        view.setImageURI(imageUri)
    }
    @JvmStatic // add this line !!
    @BindingAdapter("android:src")
    fun setImageDrawable(view: ImageView, drawable: Drawable?) {
        view.setImageDrawable(drawable)
    }
    @JvmStatic // add this line !!
    @BindingAdapter("android:src")
    fun setImageResource(imageView: ImageView, resource: Int) {
        imageView.setImageResource(resource)
    }

    @JvmStatic // add this line !!
    @BindingAdapter("bind:imageBitmap")
    fun setImageBitmap(imageView: ImageView, bitmap: Bitmap) {
        imageView.setImageBitmap(bitmap)
    }

    @JvmStatic // add this line !!
    @BindingAdapter("android:text")
    fun setTextViewNotificationTypeEnum(textView: TextView, notificationType: NotificationType) {
        when(notificationType){
            NotificationType.LOAN         -> textView.text = "Loan"
            NotificationType.INVESTMENT   -> textView.text = "Investment"
            NotificationType.CONTRIBUTION -> textView.text = "Contribution"
        }

    }
    @JvmStatic // add this line !!
    @BindingAdapter("android:text")
    fun setTextViewLocalDate(textView: TextView, date: LocalDate) {
       textView.text = date.toString()
    }
}