package com.example.chowdown

import android.content.Intent
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.RecyclerView
import com.example.chowdown.adapters.CategoryAdapter
import com.example.chowdown.adapters.ProductAdapter
import com.example.chowdown.models.Category
import com.example.chowdown.models.Modifier
import com.example.chowdown.models.Product
import com.google.android.material.badge.BadgeDrawable
import com.google.android.material.badge.BadgeUtils

class HomeFragment : Fragment() {

    private val allProducts = listOf(
        Product("1", "Classic Smash Burger", "Delicious handmade double beef patty with melted cheese", 45.0, 4.8, "20-30 min", R.drawable.img_1001777626, "burgers", listOf(
            Modifier("m1", "Extra Cheddar Cheese", 5.0),
            Modifier("m2", "No Onions", 0.0),
            Modifier("m3", "Add Bacon strips", 12.0)
        )),
        Product("2", "Double Smash Burger", "Two juicy hand-smashed beef patties, double melted cheese", 65.0, 4.8, "20-30 min", R.drawable.img_1001777627, "burgers", listOf(
            Modifier("m1", "Extra Cheddar Cheese", 5.0),
            Modifier("m2", "No Onions", 0.0),
            Modifier("m3", "Add Bacon strips", 12.0)
        )),
        Product("3", "Margherita Classic", "Fresh mozzarella, basil, and tomato sauce on thin crust", 75.0, 4.7, "30-40 min", R.drawable.img_1001777630, "pizza", listOf(
            Modifier("m4", "Extra Cheese", 10.0),
            Modifier("m5", "Thick Crust", 8.0)
        )),
        Product("4", "Pepperoni Feast", "Loaded with mozzarella and spicy pepperoni slices", 85.0, 4.9, "30-40 min", R.drawable.img_1001777631, "pizza", listOf(
            Modifier("m4", "Extra Cheese", 10.0),
            Modifier("m5", "Thick Crust", 8.0),
            Modifier("m6", "Extra Pepperoni", 12.0)
        )),
        Product("5", "Salmon Nigiri Set", "Fresh Atlantic salmon on hand-pressed sushi rice", 120.0, 4.9, "25-35 min", R.drawable.img_1001777632, "sushi", listOf(
            Modifier("m7", "Extra Wasabi", 3.0),
            Modifier("m8", "Soy Sauce (Extra)", 2.0)
        )),
        Product("6", "California Roll Platter", "Crab, avocado, cucumber wrapped in seaweed and rice", 95.0, 4.7, "25-35 min", R.drawable.img_1001777633, "sushi", listOf(
            Modifier("m7", "Extra Wasabi", 3.0),
            Modifier("m8", "Soy Sauce (Extra)", 2.0)
        )),
        Product("7", "Chocolate Lava Cake", "Warm dark chocolate center served with vanilla bean ice cream", 55.0, 4.7, "15-20 min", R.drawable.img_1001777634, "desserts", listOf(
            Modifier("m9", "Extra Ice Cream", 8.0),
            Modifier("m10", "Chocolate Sauce", 5.0)
        )),
        Product("8", "Chocolate Cake slice", "Classic rich chocolate cake with cocoa frosting", 48.0, 4.8, "15-20 min", R.drawable.img_1001777635, "desserts", listOf(
            Modifier("m9", "Extra Ice Cream", 8.0),
            Modifier("m10", "Chocolate Sauce", 5.0)
        )),
        Product("9", "Vanilla Shake", "Large, whipped cream", 35.0, 4.8, "10-15 min", R.drawable.img_1001777636, "drinks", listOf(
            Modifier("m11", "Extra Whipped Cream", 5.0),
            Modifier("m12", "Add Oreo", 8.0)
        )),
        Product("10", "Strawberry Smoothie", "Fresh strawberries blended with yogurt and honey", 38.0, 4.7, "10-15 min", R.drawable.img_1001777637, "drinks", listOf(
            Modifier("m11", "Extra Whipped Cream", 5.0),
            Modifier("m12", "Add Oreo", 8.0)
        ))
    )

    private lateinit var productAdapter: ProductAdapter
    private var currentCategory = "burgers"
    private lateinit var tvNotificationBadge: TextView

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view = inflater.inflate(R.layout.fragment_home, container, false)

        val name = UserPrefs.getUserName(requireContext())
        val initials = UserPrefs.getInitials(name)

        view.findViewById<TextView>(R.id.tvAvatarInitials)?.text = initials
        view.findViewById<TextView>(R.id.tvGreeting)?.text = "Hello, $name!"

        // Notification Bell mapping
        val btnNotifications = view.findViewById<ImageView>(R.id.btnNotifications)
        tvNotificationBadge = view.findViewById(R.id.tvNotificationBadge)
        
        btnNotifications.setOnClickListener {
            startActivity(Intent(requireContext(), MessagesActivity::class.java))
            // Reset badge on click
            updateNotificationBadge(0)
        }
        
        // Simulating 2 unread messages
        updateNotificationBadge(2)

        view.findViewById<Button>(R.id.btnPromoOrder)?.setOnClickListener {
            Toast.makeText(requireContext(), "Special Ordered", Toast.LENGTH_SHORT).show()
        }

        setupCategories(view)
        setupPopularItems(view)
        setupSearch(view)
        
        return view
    }
    
    fun updateNotificationBadge(unreadCount: Int) {
        if (unreadCount > 0) {
            tvNotificationBadge.visibility = View.VISIBLE
            tvNotificationBadge.text = unreadCount.toString()
        } else {
            tvNotificationBadge.visibility = View.GONE
        }
    }

    private fun setupCategories(view: View) {
        val categories = listOf(
            Category("burgers", "Burgers", R.drawable.ic_category_burger),
            Category("pizza", "Pizza", R.drawable.ic_category_pizza),
            Category("sushi", "Sushi", R.drawable.ic_category_sushi),
            Category("desserts", "Desserts", R.drawable.ic_category_dessert),
            Category("drinks", "Drinks", R.drawable.ic_category_drink)
        )

        val rvCategories = view.findViewById<RecyclerView>(R.id.rvCategories)
        rvCategories?.adapter = CategoryAdapter(categories) { selectedCategory ->
            currentCategory = selectedCategory.id
            val etSearch = view.findViewById<EditText>(R.id.etSearch)
            etSearch?.setText("")
            productAdapter.setCurrentCategory(currentCategory)
            productAdapter.filter.filter("")
        }
    }

    private fun setupPopularItems(view: View) {
        val rvPopular = view.findViewById<RecyclerView>(R.id.rvPopular)
        
        productAdapter = ProductAdapter(
            allProducts.filter { it.categoryId == currentCategory }.toMutableList(), 
            allProducts
        ) { product ->
            val intent = Intent(requireContext(), ProductDetailActivity::class.java).apply {
                putExtra("PRODUCT", product)
            }
            startActivity(intent)
        }
        
        rvPopular?.adapter = productAdapter
    }

    private fun setupSearch(view: View) {
        val etSearch = view.findViewById<EditText>(R.id.etSearch)
        etSearch?.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}
            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                productAdapter.setCurrentCategory(currentCategory)
                productAdapter.filter.filter(s?.toString() ?: "")
            }
            override fun afterTextChanged(s: Editable?) {}
        })
    }
}