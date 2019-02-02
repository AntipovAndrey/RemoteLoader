package ru.andrey.remoteloader.ui.view

import android.support.v7.recyclerview.extensions.ListAdapter
import android.support.v7.util.DiffUtil
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import ru.andrey.domain.model.Command
import ru.andrey.remoteloader.R

class MainAdapter : ListAdapter<Command, Holder>(DiffCallback()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): Holder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_command, parent, false)
        return Holder(view)
    }

    override fun onBindViewHolder(holder: Holder, position: Int) {
        val item = getItem(position)
        holder.action.text = item.action.toString()
        holder.payload.text = item.payload ?: ""
    }
}

class Holder(itemView: View) : RecyclerView.ViewHolder(itemView) {
    var action: TextView = itemView.findViewById(R.id.action)
    var payload: TextView = itemView.findViewById(R.id.payload)
}

internal class DiffCallback : DiffUtil.ItemCallback<Command>() {

    override fun areItemsTheSame(oldItem: Command, newItem: Command) = oldItem == newItem

    override fun areContentsTheSame(oldItem: Command, newItem: Command) = oldItem === newItem
}
