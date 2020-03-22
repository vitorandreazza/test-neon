package com.example.neontest.ui.history

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.neontest.databinding.ItemGraphTransferHistoryBinding
import com.example.neontest.extensions.updateLayoutParams
import com.example.neontest.ui.transfer.ContactItem

private const val MIN_BAR_HEIGHT = .65f
private const val MAX_BAR_HEIGHT = 0f

class TransfersGraphAdapter :
    ListAdapter<ContactItem, TransfersGraphAdapter.TransferGraphViewHolder>(
        TransferGraphDiffCallback()
    ) {

    private var itemsWeight = listOf<Float>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TransferGraphViewHolder =
        TransferGraphViewHolder(
            ItemGraphTransferHistoryBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )

    override fun onBindViewHolder(holder: TransferGraphViewHolder, position: Int) {
        holder.bind(getItem(position), itemsWeight[position])
    }

    override fun submitList(list: List<ContactItem>?) {
        calculateItemsWeight(list)
        super.submitList(list)
    }

    private fun calculateItemsWeight(list: List<ContactItem>?) {
        if (list == null) return
        val maxValue = list.maxBy { it.transferValue }!!.transferValue
        val minValue = list.minBy { it.transferValue }!!.transferValue
        itemsWeight = list.map { scaleValues(it.transferValue, maxValue, minValue) }
    }

    private fun scaleValues(value: Float, maxValue: Float, minValue: Float): Float {
        val valuesRange = maxValue - minValue
        val percentageRange = MAX_BAR_HEIGHT - MIN_BAR_HEIGHT
        return (((value - minValue) * percentageRange) / valuesRange) + MIN_BAR_HEIGHT
    }

    class TransferGraphViewHolder(
        private val binding: ItemGraphTransferHistoryBinding
    ) : RecyclerView.ViewHolder(binding.root) {

        fun bind(item: ContactItem, weight: Float) {
            binding.apply {
                contact = item
                guidelineLine.updateLayoutParams<ConstraintLayout.LayoutParams> {
                    guidePercent = weight
                }
                executePendingBindings()
            }
        }
    }
}

private class TransferGraphDiffCallback : DiffUtil.ItemCallback<ContactItem>() {

    override fun areItemsTheSame(oldItem: ContactItem, newItem: ContactItem): Boolean {
        return oldItem.id == newItem.id
    }

    override fun areContentsTheSame(oldItem: ContactItem, newItem: ContactItem): Boolean {
        return oldItem == newItem
    }
}