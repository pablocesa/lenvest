package com.example.lendvest.models.ui

import android.view.View

interface OnItemViewClickListener{
    fun onClick(view: View, position: Int)
}

interface OnItemViewButtonClickListener{
    fun onClick(view: View, position: Int)
}

abstract class ItemViewModel{
    var adapterPosition: Int = -1
    var onItemViewClickListener: OnItemViewClickListener? = null
    var onItemViewButtonClickListener: OnItemViewButtonClickListener?=null

}
