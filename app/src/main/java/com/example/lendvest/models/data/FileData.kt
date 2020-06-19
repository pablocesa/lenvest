package com.example.lendvest.models.data

import android.graphics.Bitmap
import android.graphics.drawable.Drawable
import com.example.lendvest.models.ui.ItemViewModel

class FileData (val id: Int,
                var name: String = "",
                val resourceId: Int,
                var drawable: Drawable? = null,
                var bitmap: Bitmap? = null
): ItemViewModel()