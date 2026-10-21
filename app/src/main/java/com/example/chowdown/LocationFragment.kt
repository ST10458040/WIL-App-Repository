package com.example.chowdown

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions

class LocationFragment : Fragment(), OnMapReadyCallback {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_location, container, false)
        
        val mapFragment = childFragmentManager
            .findFragmentById(R.id.map) as SupportMapFragment
        mapFragment.getMapAsync(this)
        
        view.findViewById<View>(R.id.bottomInfoContainer)?.setOnClickListener {
            val intent = android.content.Intent(
                android.content.Intent.ACTION_VIEW, 
                android.net.Uri.parse("geo:0,0?q=320+Anton+Lembede+St,+CBD,+Durban,+4001")
            )
            startActivity(intent)
        }
        
        return view
    }

    override fun onMapReady(googleMap: GoogleMap) {
        // 320 Anton Lembede St, CBD, Durban, 4001
        val storeLocation = LatLng(-29.858680, 31.021840)
        googleMap.addMarker(
            MarkerOptions()
                .position(storeLocation)
                .title("Chow Down CAFÉ")
        )
        googleMap.animateCamera(CameraUpdateFactory.newLatLngZoom(storeLocation, 16f))
    }
}