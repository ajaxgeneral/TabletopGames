package com.example.tabletopgames

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Toast

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
    }

    fun alert(view: View) {
        Toast.makeText(this,"Coming Soon", Toast.LENGTH_SHORT).show()
    }

    fun reservations(view: View){
        val startReservations = Intent(this, ReservationActivity::class.java)
        startActivity(startReservations)
    }


}