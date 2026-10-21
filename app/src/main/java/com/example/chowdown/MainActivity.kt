package com.example.chowdown

import android.animation.AnimatorSet
import android.animation.ObjectAnimator
import android.animation.ValueAnimator
import android.os.Bundle
import androidx.activity.OnBackPressedCallback
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import com.google.android.material.bottomnavigation.BottomNavigationView

import android.view.View
import android.widget.ImageView
import android.widget.TextView

class MainActivity : AppCompatActivity() {

    private lateinit var navHome: ImageView
    private lateinit var navMap: ImageView
    private lateinit var navOrderStatus: TextView
    private lateinit var navCartIcon: ImageView
    private lateinit var navProfile: ImageView
    private lateinit var tvCartBadge: TextView
    
    private var pulseAnimator: AnimatorSet? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        navHome = findViewById(R.id.navHome)
        navMap = findViewById(R.id.navMap)
        navOrderStatus = findViewById(R.id.navOrderStatus)
        navCartIcon = findViewById(R.id.ivCartIcon)
        navProfile = findViewById(R.id.navProfile)
        tvCartBadge = findViewById(R.id.tvCartBadge)
        
        supportFragmentManager.addOnBackStackChangedListener {
            val currentFragment = supportFragmentManager.findFragmentById(R.id.nav_host_fragment)
            when (currentFragment) {
                is HomeFragment -> updateNavColors(R.id.navHome)
                is LocationFragment -> updateNavColors(R.id.navMap)
                is OrderStatusFragment -> updateNavColors(R.id.navOrderStatus)
                is CartFragment -> updateNavColors(R.id.navCart)
                is ProfileFragment -> updateNavColors(R.id.navProfile)
            }
        }

        navHome.setOnClickListener { 
            loadFragment(HomeFragment()) 
        }
        findViewById<View>(R.id.navMap).setOnClickListener { 
            loadFragment(LocationFragment()) 
        }
        findViewById<View>(R.id.navOrderStatus).setOnClickListener { 
            loadFragment(OrderStatusFragment()) 
        }
        findViewById<View>(R.id.navCart).setOnClickListener { 
            loadFragment(CartFragment()) 
        }
        findViewById<View>(R.id.navProfile).setOnClickListener { 
            loadFragment(ProfileFragment()) 
        }

        // Handle back button presses
        onBackPressedDispatcher.addCallback(this, object : OnBackPressedCallback(true) {
            override fun handleOnBackPressed() {
                if (supportFragmentManager.backStackEntryCount > 1) {
                    supportFragmentManager.popBackStack()
                } else {
                    val dialogView = layoutInflater.inflate(R.layout.dialog_exit, null)
                    val dialog = AlertDialog.Builder(this@MainActivity)
                        .setView(dialogView)
                        .create()
                    
                    dialog.window?.setBackgroundDrawable(android.graphics.drawable.ColorDrawable(android.graphics.Color.TRANSPARENT))
                    
                    dialogView.findViewById<android.widget.Button>(R.id.btnYes).setOnClickListener { 
                        dialog.dismiss()
                        finish() 
                    }
                    dialogView.findViewById<android.widget.Button>(R.id.btnNo).setOnClickListener { 
                        dialog.dismiss() 
                    }
                    dialog.show()
                }
            }
        })

        // Load default tab
        if (savedInstanceState == null) {
            val startTab = intent.getStringExtra("TAB")
            if (startTab == "CART") {
                updateNavColors(R.id.navCart)
                loadFragment(CartFragment())
            } else {
                updateNavColors(R.id.navHome)
                loadFragment(HomeFragment())
            }
        }
    }

    private fun updateNavColors(selectedId: Int) {
        val primaryColor = android.graphics.Color.parseColor("#FF1A1A")
        val grayColor = android.graphics.Color.parseColor("#888888")

        navHome.setColorFilter(if (selectedId == R.id.navHome) primaryColor else grayColor)
        navMap.setColorFilter(if (selectedId == R.id.navMap) primaryColor else grayColor)
        navCartIcon.setColorFilter(if (selectedId == R.id.navCart) primaryColor else grayColor)
        navProfile.setColorFilter(if (selectedId == R.id.navProfile) primaryColor else grayColor)
    }

    override fun onResume() {
        super.onResume()
        updateOrderStatus()
        updateCartBadge()
    }

    fun updateOrderStatus() {
        if (OrderManager.orders.isNotEmpty()) {
            val activeOrder = OrderManager.orders.first()
            navOrderStatus.text = activeOrder.status
            if (activeOrder.status.equals("PREPPING", ignoreCase = true)) {
                startPulseAnimation()
            } else {
                stopPulseAnimation()
            }
        } else {
            navOrderStatus.text = "Orders"
            stopPulseAnimation()
        }
    }

    private fun startPulseAnimation() {
        if (pulseAnimator?.isRunning == true) return
        val scaleX = ObjectAnimator.ofFloat(navOrderStatus, "scaleX", 1f, 1.08f, 1f)
        val scaleY = ObjectAnimator.ofFloat(navOrderStatus, "scaleY", 1f, 1.08f, 1f)
        scaleX.repeatCount = ValueAnimator.INFINITE
        scaleY.repeatCount = ValueAnimator.INFINITE
        scaleX.duration = 1000
        scaleY.duration = 1000
        pulseAnimator = AnimatorSet().apply {
            playTogether(scaleX, scaleY)
            start()
        }
    }

    private fun stopPulseAnimation() {
        pulseAnimator?.cancel()
        pulseAnimator = null
        navOrderStatus.scaleX = 1f
        navOrderStatus.scaleY = 1f
    }

    fun updateCartBadge() {
        val count = CartManager.items.size
        if (count > 0) {
            tvCartBadge.visibility = View.VISIBLE
            tvCartBadge.text = count.toString()
        } else {
            tvCartBadge.visibility = View.GONE
        }
    }

    fun loadFragment(fragment: Fragment) {
        val currentFragment = supportFragmentManager.findFragmentById(R.id.nav_host_fragment)
        if (currentFragment != null && currentFragment::class.java == fragment::class.java) {
            return // Don't reload the same fragment
        }
        
        supportFragmentManager.beginTransaction()
            .replace(R.id.nav_host_fragment, fragment)
            .addToBackStack(null)
            .commit()
    }
}