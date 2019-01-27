package com.cybersuccess.demo.kotlinappliccation
import android.content.Context
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.cybersuccess.demo.kotlinappliccation.sp.CustomRadioButton
import kotlinx.android.synthetic.main.item_view_documents.view.*

class AnimalAdapter(val items : ArrayList<Documents>, val context: Context?) : RecyclerView.Adapter<ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(LayoutInflater.from(context).inflate(R.layout.item_view_documents, parent, false))

    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder?.tvAnimalType?.text = items.get(position).name
        holder?.date?.text = items.get(position).date
     }


    // Gets the number of animals in the list
    override fun getItemCount(): Int {
        return items.size
    }

   }

class ViewHolder (view: View) : RecyclerView.ViewHolder(view) {
    // Holds the TextView that will add each animal to
    val tvAnimalType = view.name
    val date = view.date
}





