package com.example.tabletopgames

import android.content.Intent
import android.os.Bundle
import com.google.android.material.floatingactionbutton.FloatingActionButton
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.tabletopgames.models.Reservation
import com.example.tabletopgames.models.User
import io.realm.Realm

class ReservationActivity : AppCompatActivity() {
    private var layoutManager: RecyclerView.LayoutManager? = null
    private var adapter: RecyclerView.Adapter<ReservationsRecyclerAdapter.ViewHolder>? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        var text = "\t\t\t\t\t\t\t\t\t\tComing Soon," +
                "\n\t\t\t\t\t\t\t\t\tAdd a Reservation!"

        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_reservation)
        setSupportActionBar(findViewById(R.id.toolbar))

        layoutManager = LinearLayoutManager(this)
        val recyclerView = findViewById<RecyclerView>(R.id.reservations_recyclerView)
        recyclerView.layoutManager = layoutManager

        adapter = ReservationsRecyclerAdapter()
        recyclerView.adapter = adapter

        findViewById<FloatingActionButton>(R.id.fab).setOnClickListener { view ->
            val startNewReservations = Intent(this, NewReservationActivity::class.java)
            startActivity(startNewReservations)
        }

    }
}