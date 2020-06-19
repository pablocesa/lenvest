package com.example.lendvest.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.annotation.LayoutRes
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.recyclerview.widget.RecyclerView
import com.example.lendvest.models.ui.ItemViewModel
import com.example.lendvest.models.ui.OnItemViewButtonClickListener
import com.example.lendvest.models.ui.OnItemViewClickListener


class RecyclerBindableAdapter<T : ItemViewModel>(@LayoutRes val layoutId: Int, var variableId: Int) :
    RecyclerView.Adapter<RecyclerBindableAdapter.GenericRecyclerViewHolder<T>>() {

    private var onMainButtonClickListener: OnItemViewButtonClickListener? = null
    private val items = mutableListOf<T>()
    private var inflater: LayoutInflater? = null
    private var onListItemViewClickListener: OnItemViewClickListener? = null

//    abstract fun getLayoutResId(): Int

//    abstract fun onBindData(model: T, position: Int, dataBinding: ListItemViewModel)

//    abstract fun onItemClick(model: T, position: Int)

    fun addItems(items: List<T>) {
        this.items.clear()
        this.items.addAll(items)
        notifyDataSetChanged()
    }

    fun setOnListItemViewClickListener(onItemViewClickListener: OnItemViewClickListener?){
        this.onListItemViewClickListener = onItemViewClickListener
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): GenericRecyclerViewHolder<T> {
        val layoutInflater = inflater ?: LayoutInflater.from(parent.context)
        val binding = DataBindingUtil.inflate<ViewDataBinding>(layoutInflater, layoutId, parent, false)
        return GenericRecyclerViewHolder(binding)
    }

    override fun getItemCount(): Int = items.size

    override fun onBindViewHolder(holder: GenericRecyclerViewHolder<T>, position: Int) {
        val itemViewModel = items[position]
        itemViewModel.adapterPosition = position
        onListItemViewClickListener?.let { itemViewModel.onItemViewClickListener = it }
        onMainButtonClickListener?.let { itemViewModel.onItemViewButtonClickListener = it }
        holder.bind(variableId, itemViewModel)
//        holder.bind(itemViewModel)
    }


    class GenericRecyclerViewHolder<T : ItemViewModel>(private val binding: ViewDataBinding) :
        RecyclerView.ViewHolder(binding.root) {

//        fun bind(itemViewModel: T) {
//            binding.setVariable(BR.itemViewModel, itemViewModel)
//            binding.executePendingBindings()
//        }

        fun bind(variableId: Int, itemViewModel: T) {
            binding.setVariable(variableId, itemViewModel)
            binding.executePendingBindings()
        }

    }

    fun setOnItemViewClickListener(clickListener: OnItemViewButtonClickListener?){
        this.onMainButtonClickListener = clickListener
    }
    fun setOnItemViewButtonClickListener(clickListener: OnItemViewButtonClickListener?){
        this.onMainButtonClickListener = clickListener
    }
}