package com.example.chowdown.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.chowdown.R
import com.example.chowdown.models.Message

class MessageAdapter(
    private var messages: List<Message>
) : RecyclerView.Adapter<MessageAdapter.MessageViewHolder>() {

    inner class MessageViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val tvTitle: TextView = view.findViewById(R.id.tvMessageTitle)
        val tvBody: TextView = view.findViewById(R.id.tvMessageBody)
        val tvTime: TextView = view.findViewById(R.id.tvMessageTime)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MessageViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_message, parent, false)
        return MessageViewHolder(view)
    }

    override fun onBindViewHolder(holder: MessageViewHolder, position: Int) {
        val message = messages[position]
        holder.tvTitle.text = message.title
        holder.tvBody.text = message.body
        holder.tvTime.text = message.time
    }

    override fun getItemCount(): Int = messages.size

    fun updateData(newMessages: List<Message>) {
        messages = newMessages
        notifyDataSetChanged()
    }
}