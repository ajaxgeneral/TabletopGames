package com.example.tabletopgames

import android.os.Bundle
import android.view.View
import android.widget.Toast
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.google.android.material.snackbar.Snackbar
import androidx.appcompat.app.AppCompatActivity
import android.view.Menu
import android.view.MenuItem
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

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
            Snackbar.make(view, text,Snackbar.LENGTH_LONG)
                .setAction("Action", null).show()
        }

    }
}