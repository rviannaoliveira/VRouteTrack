package com.rviannaoliveira.vroutetrack

import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView

/**
 * Criado por rodrigo on 17/05/17.
 */

class RegisterAdapter(private val registers: ArrayList<RegisterTrack>?) : RecyclerView.Adapter<RegisterAdapter.RegisterViewHolder>() {


    override fun onBindViewHolder(holder: RegisterViewHolder, position: Int) {
        if(registers?.isNotEmpty() as Boolean){
            val register = registers[position]
            holder.address.text = register.address
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RegisterViewHolder {
        return RegisterViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.item,parent,false))
    }

    override fun getItemCount(): Int = registers?.size as Int

    inner class RegisterViewHolder(itemView : View) : RecyclerView.ViewHolder(itemView){
        var address = itemView.findViewById(R.id.address) as TextView
    }
}
