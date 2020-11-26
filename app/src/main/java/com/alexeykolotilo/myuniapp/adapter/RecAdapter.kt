package com.alexeykolotilo.myuniapp.adapter

import android.graphics.Typeface
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.alexeykolotilo.myuniapp.R
import kotlinx.android.synthetic.main.item_edit_text.view.*

class RecAdapter :
    ListAdapter<String, RecAdapter.ViewHolder>(diffUtilItemCallback) {

    override fun onBindViewHolder(holder: ViewHolder, position: Int, payloads: MutableList<Any>) {
        if (payloads.isNotEmpty()) {
            holder.handlePayloads(payloads)
        } else {
            super.onBindViewHolder(holder, position, payloads)
        }
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(getItem(position))
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view =
            LayoutInflater.from(parent.context).inflate(R.layout.item_edit_text, parent, false)

        return ViewHolder(view)
    }

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        fun handlePayloads(payloads: List<Any>) {
            payloads.forEach {
                when (it) {
                    FieldAction.FOCUS -> {
                        itemView.textInput.requestFocus()
                    }
                    FieldAction.TYPEFACE_NORMAL -> {
                        itemView.textInput.typeface = Typeface.SANS_SERIF
                    }
                    FieldAction.TYPEFACE_LIGHT -> {
                        itemView.textInput.typeface =
                            Typeface.create("sans-serif-light", Typeface.NORMAL)
                    }
                    FieldAction.HIDE_KEYBOARD -> {
                    }
                    FieldAction.SHOW_KEYBOARD -> {
                        itemView.textInput.requestFocus()
                    }
                }

            }
        }

        fun bind(string: String) {
            itemView.textInput.setText(string)
        }
    }

    companion object {
        private val diffUtilItemCallback = object : DiffUtil.ItemCallback<String>() {
            override fun areContentsTheSame(oldItem: String, newItem: String): Boolean =
                oldItem == newItem

            override fun areItemsTheSame(oldItem: String, newItem: String): Boolean =
                oldItem == newItem
        }
    }

    enum class FieldAction {
        FOCUS,
        SHOW_KEYBOARD,
        HIDE_KEYBOARD,
        TYPEFACE_LIGHT,
        TYPEFACE_NORMAL,
    }
}