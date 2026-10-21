package com.example.chowdown

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.cardview.widget.CardView
import androidx.fragment.app.Fragment

class ProfileFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_profile, container, false)
        
        view.findViewById<View>(R.id.toolbar)?.visibility = View.GONE
        
        val name = UserPrefs.getUserName(requireContext())
        val email = UserPrefs.getUserEmail(requireContext())
        val initials = UserPrefs.getInitials(name)

        view.findViewById<TextView>(R.id.tvProfileInitials)?.text = initials
        view.findViewById<TextView>(R.id.tvProfileName)?.text = name
        view.findViewById<TextView>(R.id.tvProfileEmail)?.text = email

        view.findViewById<CardView>(R.id.btnOrderHistory)?.setOnClickListener {
            (activity as? MainActivity)?.loadFragment(OrderStatusFragment())
        }
        
        view.findViewById<CardView>(R.id.btnPrivacyPolicy)?.setOnClickListener {
            val dialogView = layoutInflater.inflate(R.layout.dialog_privacy, null)
            val dialog = androidx.appcompat.app.AlertDialog.Builder(requireContext())
                .setView(dialogView)
                .create()
            
            dialog.window?.setBackgroundDrawable(android.graphics.drawable.ColorDrawable(android.graphics.Color.TRANSPARENT))
            
            dialogView.findViewById<Button>(R.id.btnClose).setOnClickListener { 
                dialog.dismiss() 
            }
            dialog.show()
        }
        
        view.findViewById<View>(R.id.btnHelpSupportEmail)?.setOnClickListener {
            val i = Intent(Intent.ACTION_SENDTO, Uri.parse("mailto:support@chowdown.co.za"))
            startActivity(i)
        }

        view.findViewById<View>(R.id.btnHelpSupportPhone)?.setOnClickListener {
            val i = Intent(Intent.ACTION_DIAL, Uri.parse("tel:0657330077"))
            startActivity(i)
        }

        view.findViewById<Button>(R.id.btnLogOut)?.setOnClickListener {
            startActivity(Intent(requireContext(), LoginActivity::class.java))
            activity?.finishAffinity()
        }
        
        return view
    }
}