package com.example.lendvest.models.contracts

import androidx.recyclerview.widget.RecyclerView
import com.example.lendvest.models.ui.ItemViewModel

interface NestedRecyclerViewOnBindListener{
    fun onBind(holder: RecyclerView.ViewHolder, T: ItemViewModel)
}