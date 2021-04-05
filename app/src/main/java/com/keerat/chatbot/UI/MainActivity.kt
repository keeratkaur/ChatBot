package com.keerat.chatbot.UI

import android.content.Intent
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.recyclerview.widget.LinearLayoutManager
import com.keerat.chatbot.R
import com.keerat.chatbot.data.Message
import com.keerat.chatbot.utils.Constants.OPEN_GOOGLE
import com.keerat.chatbot.utils.Constants.OPEN_SEARCH
import com.keerat.chatbot.utils.Constants.RECEIVE_ID
import com.keerat.chatbot.utils.Constants.SEND_ID
import com.keerat.chatbot.utils.Response
import com.keerat.chatbot.utils.Time
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.coroutines.*

class MainActivity : AppCompatActivity() {

    private lateinit var adapter:MessagingAdapter
    private val botList= listOf("Devi","TeluRam","Purnima","Shizuka")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        recyclerView()
        clickEvents()
        val random=(0..3).random()
        customMessage("Hey there !! I am your friend ${botList[random]}")
    }

    private fun clickEvents(){
        btn_send.setOnClickListener{
            sendMessage()
        }
        et_message.setOnClickListener {
            GlobalScope.launch {
                delay(100)
                withContext(Dispatchers.Main){
                    rv_messages.scrollToPosition(adapter.itemCount-1)

                }

            }
        }
    }
    private fun customMessage(message:String){
        GlobalScope.launch{
            delay(1000)
            withContext(Dispatchers.Main){
                val timeStamp=Time.timeStamp()
                adapter.insertMessage(Message(message,RECEIVE_ID,timeStamp))

                rv_messages.scrollToPosition(adapter.itemCount-1)
            }
        }
    }
    private fun recyclerView(){
        adapter= MessagingAdapter()
        rv_messages.adapter=adapter
        rv_messages.layoutManager=LinearLayoutManager(applicationContext)
    }
    private fun sendMessage(){
        val message=et_message.text.toString()
        val timeStamp=Time.timeStamp()

        if (message.isNotEmpty()){
            et_message.setText("")
            adapter.insertMessage(Message(message,SEND_ID,timeStamp))
            rv_messages.scrollToPosition(adapter.itemCount-1)

            Response(message)
        }
    }
    private fun Response(message:String){
        val timeStamp=Time.timeStamp()

        GlobalScope.launch{
            delay(1000)

            withContext(Dispatchers.Main){
                val response= Response.basicResponses(message)

                adapter.insertMessage(Message(message, RECEIVE_ID,timeStamp))
                rv_messages.scrollToPosition(adapter.itemCount-1)


                when(response){
                    OPEN_GOOGLE->{
                        val site= Intent(Intent.ACTION_VIEW)

                        site.data= Uri.parse("https://www.google.com/")
                        startActivity(site)
                    }
                    OPEN_SEARCH->{
                        val site= Intent(Intent.ACTION_VIEW)
                        val searchTermL:String?=message.substringAfter("search")

                        site.data= Uri.parse("https://www.google.com/search?&q=$searchTermL")
                        startActivity(site)

                    }
                }
            }


        }

    }
    override fun onStart(){
        super.onStart()

        GlobalScope.launch {
            delay(1000)
            withContext(Dispatchers.Main){
                rv_messages.scrollToPosition(adapter.itemCount-1)

            }

        }
    }
}