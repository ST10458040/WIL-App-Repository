package com.example.chowdown

import android.os.Bundle
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.RecyclerView
import com.example.chowdown.adapters.ModifierAdapter
import com.example.chowdown.models.CartItem
import com.example.chowdown.models.Modifier
import com.example.chowdown.models.Product

class ProductDetailActivity : AppCompatActivity() {

    private lateinit var product: Product
    private val modifiers = listOf(
        Modifier("1", "Extra Cheddar Cheese", 5.0),
        Modifier("2", "No Onions", 0.0),
        Modifier("3", "Add Bacon strips", 12.0),
        Modifier("4", "Extra Patty", 15.0)
    )

    private lateinit var tvTotalPrice: TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_product_detail)

        product = intent.getSerializableExtra("PRODUCT") as? Product
            ?: Product("1", "Classic Smash Burger", "Delicious handmade double beef patty with melted cheese", 45.0, 4.8, "20-30 min", R.drawable.img_1001777626, "burgers")

        findViewById<ImageView>(R.id.btnBack)?.setOnClickListener { finish() }

        val ivHero = findViewById<ImageView>(R.id.ivProductHero)
        ivHero?.setImageResource(product.imageResId)

        findViewById<TextView>(R.id.tvProductTitle)?.text = product.name
        findViewById<TextView>(R.id.tvRating)?.text = "⭐ ${product.rating} (1200+ reviews)"
        findViewById<TextView>(R.id.tvDeliveryTime)?.text = "🕒 ${product.deliveryTime}"
        findViewById<TextView>(R.id.tvPrice)?.text = String.format("R%.2f", product.price)
        findViewById<TextView>(R.id.tvDescription)?.text = product.description

        tvTotalPrice = findViewById(R.id.tvTotalPrice)
        updateTotalPrice()

        val rvModifiers = findViewById<RecyclerView>(R.id.rvModifiers)
        rvModifiers?.adapter = ModifierAdapter(modifiers) {
            updateTotalPrice()
        }

        findViewById<Button>(R.id.btnAddToCart)?.setOnClickListener {
            val selectedModifiers = modifiers.filter { it.isSelected }
            CartManager.addItem(CartItem(product = product, modifiers = selectedModifiers, quantity = 1))
            Toast.makeText(this, "Added to cart!", Toast.LENGTH_SHORT).show()
            finish()
        }
    }

    private fun updateTotalPrice() {
        val modifierTotal = modifiers.filter { it.isSelected }.sumOf { it.price }
        val total = product.price + modifierTotal
        tvTotalPrice.text = String.format("R%.2f", total)
    }
}