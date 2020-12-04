package com.example.application.todolist

import android.content.Context
import android.opengl.Visibility
import android.view.LayoutInflater
import android.view.View
import android.view.View.INVISIBLE
import android.view.View.VISIBLE
import android.view.ViewGroup
import android.widget.*
import androidx.appcompat.app.AlertDialog
import androidx.core.view.isVisible
import androidx.recyclerview.widget.RecyclerView

private val myarray = arrayOf("Update", "Delete", "red")

class recycleView_adapter(val context: Context, val userList: ArrayList<User>) :
    RecyclerView.Adapter<recycleView_adapter.ViewHolder>() {

    //this method is returning the view for each item in the list
    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): recycleView_adapter.ViewHolder {
        val v =
            LayoutInflater.from(parent.context).inflate(R.layout.item_recycleview, parent, false)
        return ViewHolder(v)
    }

    //this method is binding the data on the list
    override fun onBindViewHolder(holder: recycleView_adapter.ViewHolder, position: Int) {

        holder.textViewName.text = userList[position].value

        if(userList[position].tick_value.equals("0")){
            holder.img_tick.visibility = INVISIBLE
        }else{
            holder.img_tick.visibility = VISIBLE
        }

        holder.parent_layout.setOnClickListener(View.OnClickListener {
            val builder = AlertDialog.Builder(context)
            builder.setTitle("Title")
            builder.setItems(myarray) { dialog, which ->

                if (myarray[which].equals("Delete")) {
                    var databaseHandler: DatabaseHandler = DatabaseHandler(context)
                    databaseHandler.deleteValues(userList[position])
                    userList.removeAt(position)
                    notifyItemRemoved(position)
                    notifyDataSetChanged()
                }
                Toast.makeText(context, myarray[which], Toast.LENGTH_LONG).show()
            }

            val dialog = builder.create()
            dialog.show()
        })


        holder.parent_layout.setOnLongClickListener {

            if(holder.img_tick.visibility == View.INVISIBLE){
                holder.img_tick.visibility = VISIBLE
            }else{
                holder.img_tick.visibility = INVISIBLE
            }

        //    Toast.makeText(context, "wrr ja", Toast.LENGTH_LONG).show()
            return@setOnLongClickListener true
        }


    }

    //this method is giving the size of the list
    override fun getItemCount(): Int {
        return userList.size
    }

    //the class is hodling the list view
    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val textViewName = itemView.findViewById<TextView>(R.id.list_item_text)
        val parent_layout = itemView.findViewById<LinearLayout>(R.id.parent_layout)
        val img_tick = itemView.findViewById<ImageView>(R.id.img_tick)
    }
}