package com.example.tabletopgames

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.view.menu.ActionMenuItemView
import androidx.recyclerview.widget.RecyclerView

class ReservationsRecyclerAdapter : RecyclerView.Adapter<ReservationsRecyclerAdapter.ViewHolder>() {
    private val titles = arrayOf("Dungeons and Dragons", "Magic the Gathering", "Monopoly",
                            "Dungeons and Dragons", "Dungeons and Dragons", "Magic the Gathering",
                            "Monopoly", "Monopoly")

    private val details = arrayOf("Tuesday 15 June 2021 7pm",
        "Tuesday 22 June 2021 7pm", "Wednesday 23 June 2021 7pm",
        "Friday 25 June 2021 7pm", "Saturday 26 June 2021 10am",
        "Saturday 26 June 2021 6pm", "Sunday 27 June 2021 5pm",
        "Monday 28 June 2021 7pm")

    private val images = intArrayOf(R.drawable.dungeonsndragons,
        R.drawable.magicthegathering,R.drawable.monopoly,
        R.drawable.dungeonsndragons,R.drawable.dungeonsndragons,
        R.drawable.magicthegathering,R.drawable.monopoly,R.drawable.monopoly)

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView){
        var itemImage: ImageView = itemView.findViewById(R.id.reservation_item_image)
        var itemTitle: TextView = itemView.findViewById(R.id.reservation_item_title)
        var itemDetail: TextView = itemView.findViewById(R.id.reservation_item_detail)
    }

    override fun onCreateViewHolder(viewGroup: ViewGroup, i: Int): ViewHolder {
        val v = LayoutInflater.from(viewGroup.context).inflate(R.layout.reservations_card_layout,
            viewGroup, false)
        return ViewHolder(v)
    }

    override fun onBindViewHolder(viewHolder: ViewHolder, i: Int) {
        viewHolder.itemTitle.text = titles[i]
        viewHolder.itemDetail.text = details[i]
        viewHolder.itemImage.setImageResource(images[i])
    }

    override fun getItemCount(): Int {
        return titles.size
    }
}