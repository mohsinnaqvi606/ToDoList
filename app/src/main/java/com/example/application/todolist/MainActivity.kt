package com.example.application.todolist

import android.annotation.SuppressLint
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.LinearLayout
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

class MainActivity : AppCompatActivity() {

    lateinit var databaseHandler : DatabaseHandler
    lateinit var adapter: recycleView_adapter
    var users = ArrayList<User>()
    lateinit var recyclerView: RecyclerView


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        databaseHandler = DatabaseHandler(this)
        //getting recyclerview from xml
        recyclerView = findViewById(R.id.recyclerView1) as RecyclerView

        //adding a layoutmanager
        recyclerView.layoutManager = LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false)

      //  users.addAll(databaseHandler.getUsers())

        users = databaseHandler.getUsers()
        //adding some dummy data to the list

        //creating our adapter
        adapter = recycleView_adapter(this,users)

        //now adding the adapter to recyclerview
        recyclerView.adapter = adapter
    }

    fun btn_add(view: View) {
        var dialog: AlertDialog? = null
        val builder = AlertDialog.Builder(this)
        // set the custom layout
        val view = layoutInflater.inflate(R.layout.layout_dialog, null)
        // Get Views references from your layout
        val tvTitle: TextView = view.findViewById(R.id.tv_title)
        val et_value: TextView = view.findViewById(R.id.et_addItem)
        val btDone: Button = view.findViewById(R.id.bt_done)

        tvTitle.setText("Add Record")

        btDone.setOnClickListener(View.OnClickListener {
            dialog?.dismiss()
            var a = User("",et_value.text.toString(),"0")
            var b: Long = databaseHandler.addUsers(a)

            users = databaseHandler.getUsers()
            adapter = recycleView_adapter(this,users)
          //  adapter.notifyDataSetChanged()
            recyclerView.adapter = adapter
        })


        builder.setView(view)
        dialog = builder.create()
        dialog.show()
    }
}