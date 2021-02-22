package com.mqa.jobwishlist.core.ui

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.mqa.core.R
import com.mqa.core.databinding.ItemListJobBinding
import com.mqa.jobwishlist.core.domain.model.Job

class JobAdapter : RecyclerView.Adapter<JobAdapter.ListViewHolder>() {

    private var listData = ArrayList<Job>()
    var onItemClick: ((Job) -> Unit)? = null

    fun setData(newListData: List<Job>?) {
        if (newListData == null) return
        listData.clear()
        listData.addAll(newListData)
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) =
        ListViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.item_list_job, parent, false))

    override fun getItemCount() = listData.size

    override fun onBindViewHolder(holder: ListViewHolder, position: Int) {
        val data = listData[position]
        holder.bind(data)
    }

    inner class ListViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val binding = ItemListJobBinding.bind(itemView)
        fun bind(data: Job) {
            with(binding) {
                tvItemTitle.text = data.jobTitle
                tvItemSubtitle.text = data.employerName
            }
        }

        init {
            binding.root.setOnClickListener {
                onItemClick?.invoke(listData[adapterPosition])
            }
        }
    }
}