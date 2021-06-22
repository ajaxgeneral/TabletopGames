package com.example.tabletopgames

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import java.util.*
import android.app.DatePickerDialog
import android.app.TimePickerDialog
import android.content.Intent
import android.text.format.DateFormat
import android.util.Log
import android.widget.*
import com.example.tabletopgames.models.Reservation
import com.example.tabletopgames.models.User
import java.text.Format
import java.text.SimpleDateFormat
import io.realm.Realm
import org.jetbrains.anko.find
import java.lang.Exception

class NewReservationActivity() : AppCompatActivity(), DatePickerDialog.OnDateSetListener,
TimePickerDialog.OnTimeSetListener{
    lateinit var reservationDetailsTextView: TextView
    lateinit var chooseReservationButton: Button
    private var tableSelected: String? = "1"
    private var seatSelected: String? = "1"
    private var durationSelected: String? = "1"
    private var gameTypeSelected: String? = "Dungeons and Dragons"
    private var day = 0
    private var month: Int = 0
    private var year: Int = 0
    private var hour: Int = 0
    private var minute: Int = 0
    private var myDay = 0
    private var myMonth: Int = 0
    private var myYear: Int = 0
    private var myHour: Int = 0
    private var myMinute: Int = 0
    private var dayOfWeek: Int = 0
    private val daysOfTheWeek = arrayOf("Sunday","Monday","Tuesday","Wednesday",
        "Thursday","Friday")
    private val nameOfTheMonth = arrayOf("January","February","March","April","May,",
        "June","July","August","September","October","November","December")

    private var dayName: String? = daysOfTheWeek[dayOfWeek]
    private var monthName: String? = nameOfTheMonth[myMonth]
    private var f: Format = SimpleDateFormat("a")
    private var dateF: String? = ""
    private var timeF: String? = ""
    private var realm: Realm? = null
    private val reservation = Reservation()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_new_reservation)

        realm = Realm.getDefaultInstance()
        val submitNewReservationButton = find<Button>(R.id.submit_new_reservation_button)
        submitNewReservationButton.setOnClickListener(){
            addReservation()
            Toast.makeText(this,"You have submitted a Reservation.", Toast.LENGTH_LONG).show()
            val startReservations = Intent(this, ReservationActivity::class.java)
            startActivity(startReservations)
        }


        reservationDetailsTextView = findViewById(R.id.details_of_reservation_textView)

        val gameTypeSpinner = findViewById<Spinner>(R.id.game_type_selection_drop_down_list) as Spinner
            //this should be a global variable accessible by all activities*/
           val gameTypes =  arrayOf("Dungeons and Dragons", "Magic the Gathering", "Other")
            val gameTypeSpinnerAdapter = ArrayAdapter(this,android.R.layout.simple_spinner_dropdown_item, gameTypes)
            gameTypeSpinner.adapter = gameTypeSpinnerAdapter

            gameTypeSpinner.onItemSelectedListener = object :AdapterView.OnItemSelectedListener{
                override fun onItemSelected(
                    parent: AdapterView<*>?,
                    view: View?,
                    position: Int,
                    id: Long
                ) {
                    gameTypeSelected = gameTypes[position]
                }

                    override fun onNothingSelected(parent: AdapterView<*>?) {
                        TODO("Not yet implemented")
                    }

                }

            val tableSelectionSpinner = findViewById<Spinner>(R.id.table_selection_drop_down_list) as Spinner
            val tables = arrayOf("1","2","3")
            val tableSelectionSpinnerAdapter = ArrayAdapter(this@NewReservationActivity,android.R.layout.simple_spinner_dropdown_item, tables)
            tableSelectionSpinner.adapter = tableSelectionSpinnerAdapter

            tableSelectionSpinner.onItemSelectedListener = object :AdapterView.OnItemSelectedListener{
                override fun onItemSelected(
                    parent: AdapterView<*>?,
                    view: View?,
                    position: Int,
                    id: Long
                ) {
                    tableSelected = tables[position]
                }

                override fun onNothingSelected(parent: AdapterView<*>?) {
                    TODO("Not yet implemented")
                }

            }

            val seatSelectionSpinner = findViewById<Spinner>(R.id.seat_selection_drop_down_list) as Spinner
            val seats = arrayOf("1","2","3","4","5","6","7","8","9","10")
            val seatSelectionSpinnerAdapter = ArrayAdapter(this@NewReservationActivity,android.R.layout.simple_spinner_dropdown_item,seats)
            seatSelectionSpinner.adapter = seatSelectionSpinnerAdapter

            seatSelectionSpinner.onItemSelectedListener = object :AdapterView.OnItemSelectedListener {
                override fun onItemSelected(
                    parent: AdapterView<*>?,
                    view: View?,
                    position: Int,
                    id: Long
                ) {
                    seatSelected = seats[position]
                }

                override fun onNothingSelected(parent: AdapterView<*>?) {
                    TODO("Not yet implemented")
                }

            }

            val durationSelectionSpinner = findViewById<Spinner>(R.id.duration_selection_drop_down_list) as Spinner
            val duration = arrayOf("1","2","3","4")
            val durationSelectionSpinnerAdapter = ArrayAdapter(this@NewReservationActivity,android.R.layout.simple_spinner_dropdown_item, duration)
            durationSelectionSpinner.adapter = durationSelectionSpinnerAdapter

            durationSelectionSpinner.onItemSelectedListener = object :AdapterView.OnItemSelectedListener{
                override fun onItemSelected(
                    parent: AdapterView<*>?,
                    view: View?,
                    position: Int,
                    id: Long
                ) {
                    durationSelected = duration[position]
                }

                override fun onNothingSelected(parent: AdapterView<*>?) {
                    TODO("Not yet implemented")
                }

            }

            chooseReservationButton = findViewById(R.id.choose_reservation_button)
            chooseReservationButton.setOnClickListener{
                val calendar: Calendar = Calendar.getInstance()
                dayOfWeek = calendar.get(Calendar.DAY_OF_WEEK)
                day = calendar.get(Calendar.DAY_OF_MONTH)
                month = calendar.get(Calendar.MONTH)
                year = calendar.get(Calendar.YEAR)
                val datePickerDialog =
                    DatePickerDialog(this@NewReservationActivity, this@NewReservationActivity,year,month,day)
                datePickerDialog.show()
            }

        }

    private fun addReservation( ) {
        try{
            reservation.duration = durationSelected
            reservation.game_type = gameTypeSelected
            reservation.table = tableSelected
            reservation.seat = seatSelected
            reservation.date = dateF
            reservation.time = timeF
            reservation.user_id = 1.toString()
            realm?.executeTransactionAsync{ realm -> realm.insert(reservation)}
        }catch (e:Exception){
            Log.d("Status","Something went Wrong!")
        }
    }

    override fun onDateSet(view: DatePicker?, year: Int, month: Int, dayOfMonth: Int) {
        myDay = dayOfMonth
        myYear = year
        myMonth = month+1
        val calendar: Calendar = Calendar.getInstance()
        hour = calendar.get(Calendar.HOUR)
        minute = calendar.get(Calendar.MINUTE)
        val timePickerDialog = TimePickerDialog(this@NewReservationActivity,this@NewReservationActivity,
                hour,minute, DateFormat.is24HourFormat(this))
        timePickerDialog.show()
        dayOfWeek = calendar.get(Calendar.DAY_OF_WEEK)
    }

    override fun onTimeSet(view: TimePicker?, hourOfDay: Int, minute: Int) {
        myHour = hourOfDay
        myMinute = minute
        setDetailsOfTextView()
    }

    private fun setDetailsOfTextView(){
        dateF = myDay.toString()+" "+monthName+", "+myYear
        timeF = myHour.toString()+":"+String.format("%02d",myMinute)+" "+f.format(myMinute)+
                " for "+durationSelected
        val msg = "Game: "+gameTypeSelected+"\n"+
                "Table: "+tableSelected+" Seat: "+seatSelected+"\n"+
                dayName+", "+dateF+"\n"+"at "+timeF+" for "+durationSelected+" hrs."
        reservationDetailsTextView.text = msg

    }

}
