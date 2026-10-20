package com.example.chowdown.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.CheckBox
import android.widget.TextView
import androidx.cardview.widget.CardView
import androidx.recyclerview.widget.RecyclerView
import com.example.chowdown.R
import com.example.chowdown.models.Modifier

class ModifierAdapter(
    private val modifiers: List<Modifier>,
    private val onModifierChange: () -> Unit
) : RecyclerView.Adapter<ModifierAdapter.ModifierViewHolder>() {

    inner class ModifierViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val cvContainer: CardView = view.findViewById(R.id.cvModifierContainer)
        val cbModifier: CheckBox = view.findViewById(R.id.cbModifier)
        val tvName: TextView = view.findViewById(R.id.tvModifierName)
        val tvPrice: TextView = view.findViewById(R.id.tvModifierPrice)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ModifierViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_modifier, parent, false)
        return ModifierViewHolder(view)
    }

    override fun onBindViewHolder(holder: ModifierViewHolder, position: Int) {
        val modifier = modifiers[position]
        holder.tvName.text = modifier.name
        holder.tvPrice.text = String.format("+R%.2f", modifier.price)
        holder.cbModifier.isChecked = modifier.isSelected

        val toggleSelection = {
            modifier.isSelected = !modifier.isSelected
            holder.cbModifier.isChecked = modifier.isSelected
            if (modifier.isSelected) {
                holder.cvContainer.setCardBackgroundColor(0xFF330000.toInt()) // subtle red tint
            } else {
                holder.cvContainer.setCardBackgroundColor(0xFF1E1E1E.toInt()) // surface color
            }
            onModifierChange()
        }

        holder.itemView.setOnClickListener { toggleSelection() }
        holder.cbModifier.setOnClickListener { toggleSelection() }
    }

    override fun getItemCount(): Int = modifiers.size
}