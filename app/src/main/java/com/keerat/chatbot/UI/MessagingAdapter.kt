package com.keerat.chatbot.UI

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.keerat.chatbot.R
import com.keerat.chatbot.data.Message
import com.keerat.chatbot.utils.Constants.RECEIVE_ID
import com.keerat.chatbot.utils.Constants.SEND_ID
import kotlinx.android.synthetic.main.message_item.view.*

class MessagingAdapter:RecyclerView.Adapter<MessagingAdapter.MessageViewHolder>() {


    var messagingList= mutableListOf<Message>()
    inner class MessageViewHolder(itemView: View):RecyclerView.ViewHolder(itemView){
        init {
            itemView.setOnClickListener {
            messagingList.removeAt(adapterPosition)
                notifyItemRemoved(adapterPosition)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MessageViewHolder {
        return MessageViewHolder(
            LayoutInflater.from(parent.context).inflate(R.layout.message_item, parent, false)
        )
    }

    override fun getItemCount(): Int {
        return messagingList.size
    }

    @SuppressLint("SetTextI18n")
    override fun onBindViewHolder(holder: MessageViewHolder, position: Int) {
        val currentMessage = messagingList[position]

        when (currentMessage.id) {
            SEND_ID -> {
                holder.itemView.tv_message.apply {
                    text = currentMessage.message
                    visibility = View.VISIBLE
                }
                holder.itemView.tv_bot_message.visibility = View.GONE
            }
            RECEIVE_ID -> {
                holder.itemView.tv_bot_message.apply {
                    text = currentMessage.message
                    visibility = View.VISIBLE
                }
                holder.itemView.tv_message.visibility = View.GONE
            }
        }
    }

    fun insertMessage(message: Message) {
        this.messagingList.add(message)
        notifyItemInserted(messagingList.size)
    }

}