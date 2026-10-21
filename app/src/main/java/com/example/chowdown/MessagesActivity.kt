package com.example.chowdown

import android.os.Bundle
import android.widget.ImageView
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.RecyclerView
import com.example.chowdown.adapters.MessageAdapter
import com.example.chowdown.models.Message
import com.google.android.material.chip.ChipGroup

class MessagesActivity : AppCompatActivity() {

    private val allMessages = listOf(
        Message("1", "Order Ready!", "Your order #72603109 is ready for collection.", "2 mins ago", "Orders"),
        Message("2", "50% Off Sushi", "Use code SUSHI50 this weekend for half price.", "1 hr ago", "Vouchers"),
        Message("3", "System Update", "Our app will be under maintenance tonight at 2 AM.", "Yesterday", "Updates"),
        Message("4", "Order Received", "We have received your order #7FBF39CF.", "Yesterday", "Orders"),
        Message("5", "Free Delivery", "Get free delivery on orders over R200.", "2 days ago", "Vouchers")
    )

    private lateinit var messageAdapter: MessageAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_messages)

        findViewById<ImageView>(R.id.btnBack)?.setOnClickListener { finish() }

        val rvMessages = findViewById<RecyclerView>(R.id.rvMessages)
        messageAdapter = MessageAdapter(allMessages)
        rvMessages?.adapter = messageAdapter

        val chipGroup = findViewById<ChipGroup>(R.id.chipGroupFilters)
        chipGroup?.setOnCheckedStateChangeListener { group, checkedIds ->
            if (checkedIds.isEmpty()) return@setOnCheckedStateChangeListener
            
            val filtered = when (checkedIds.first()) {
                R.id.chipOrders -> allMessages.filter { it.type == "Orders" }
                R.id.chipUpdates -> allMessages.filter { it.type == "Updates" }
                R.id.chipVouchers -> allMessages.filter { it.type == "Vouchers" }
                else -> allMessages
            }
            messageAdapter.updateData(filtered)
        }
    }
}