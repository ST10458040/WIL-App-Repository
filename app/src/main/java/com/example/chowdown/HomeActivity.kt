package com.example.chowdown

import android.content.Intent
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.widget.EditText
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.RecyclerView
import com.example.chowdown.adapters.CategoryAdapter
import com.example.chowdown.adapters.ProductAdapter
import com.example.chowdown.models.Category
import com.example.chowdown.models.Product

class HomeActivity : AppCompatActivity() {

    private val allProducts = listOf(
        Product("1", "Classic Smash Burger", "Delicious handmade double beef patty with melted cheese", 45.0, 4.8, "20-30 min", R.drawable.img_1001777626, "burgers"),
        Product("2", "Double Smash Burger", "Two juicy hand-smashed beef patties, double melted cheese", 65.0, 4.8, "20-30 min", R.drawable.img_1001777627, "burgers"),
        Product("3", "Margherita Classic", "Fresh mozzarella, basil, and tomato sauce on thin crust", 75.0, 4.7, "30-40 min", R.drawable.img_1001777630, "pizza"),
        Product("4", "Pepperoni Feast", "Loaded with mozzarella and spicy pepperoni slices", 85.0, 4.9, "30-40 min", R.drawable.img_1001777631, "pizza"),
        Product("5", "Salmon Nigiri Set", "Fresh Atlantic salmon on hand-pressed sushi rice", 120.0, 4.9, "25-35 min", R.drawable.img_1001777632, "sushi"),
        Product("6", "California Roll Platter", "Crab, avocado, cucumber wrapped in seaweed and rice", 95.0, 4.7, "25-35 min", R.drawable.img_1001777633, "sushi"),
        Product("7", "Chocolate Lava Cake", "Warm dark chocolate center served with vanilla bean ice cream", 55.0, 4.7, "15-20 min", R.drawable.img_1001777634, "desserts"),
        Product("8", "Chocolate Cake slice", "Classic rich chocolate cake with cocoa frosting", 48.0, 4.8, "15-20 min", R.drawable.img_1001777635, "desserts"),
        Product("9", "Chocolate Shake", "Creamy thick chocolate milkshake topped with whipped cream", 35.0, 4.6, "10-15 min", R.drawable.img_1001777636, "drinks"),
        Product("10", "Oreo Frappe", "Blended iced cookies and cream coffee with Oreo crumble", 42.0, 4.8, "10-15 min", R.drawable.img_1001777637, "drinks")
    )

    private lateinit var productAdapter: ProductAdapter
    private var currentCategory = "burgers"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home)

        val name = UserPrefs.getUserName(this)
        val initials = UserPrefs.getInitials(name)

        findViewById<TextView>(R.id.tvAvatarInitials)?.text = initials
        findViewById<TextView>(R.id.tvGreeting)?.text = "Hello, $name!"

        findViewById<ImageView>(R.id.btnCart)?.setOnClickListener {
            startActivity(Intent(this, CartActivity::class.java))
        }

        findViewById<ImageView>(R.id.btnProfile)?.setOnClickListener {
            startActivity(Intent(this, ProfileActivity::class.java))
        }

        setupCategories()
        setupPopularItems()
        setupSearch()
    }

    private fun setupCategories() {
        val categories = listOf(
            Category("burgers", "Burgers", R.drawable.ic_category_burger),
            Category("pizza", "Pizza", R.drawable.ic_category_pizza),
            Category("sushi", "Sushi", R.drawable.ic_category_sushi),
            Category("desserts", "Desserts", R.drawable.ic_category_dessert),
            Category("drinks", "Drinks", R.drawable.ic_category_drink)
        )

        val rvCategories = findViewById<RecyclerView>(R.id.rvCategories)
        rvCategories?.adapter = CategoryAdapter(categories) { selectedCategory ->
            currentCategory = selectedCategory.id
            filterProducts("")
        }
    }

    private fun setupPopularItems() {
        val rvPopular = findViewById<RecyclerView>(R.id.rvPopular)
        
        productAdapter = ProductAdapter(allProducts.filter { it.categoryId == currentCategory }) { product ->
            val intent = Intent(this, ProductDetailActivity::class.java).apply {
                putExtra("PRODUCT", product)
            }
            startActivity(intent)
        }
        
        rvPopular?.adapter = productAdapter
    }

    private fun setupSearch() {
        val etSearch = findViewById<EditText>(R.id.etSearch)
        etSearch?.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}
            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                filterProducts(s?.toString() ?: "")
            }
            override fun afterTextChanged(s: Editable?) {}
        })
    }

    private fun filterProducts(query: String) {
        val trimmed = query.trim()
        val filtered = if (trimmed.isEmpty()) {
            allProducts.filter { it.categoryId == currentCategory }
        } else {
            allProducts.filter {
                it.name.contains(trimmed, ignoreCase = true) ||
                it.description.contains(trimmed, ignoreCase = true) ||
                it.categoryId.contains(trimmed, ignoreCase = true)
            }
        }

        if (filtered.isEmpty() && trimmed.isNotEmpty()) {
            Toast.makeText(this, "No chow found for \"$trimmed\"", Toast.LENGTH_SHORT).show()
        }

        productAdapter.updateData(filtered)
    }
}