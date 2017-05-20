package com.rviannaoliveira.vroutetrack.route

import com.rviannaoliveira.vroutetrack.model.RegisterTrack

/**
 * Criado por rodrigo on 17/05/17.
 */

class RegisterAdapter(private val registers: ArrayList<RegisterTrack>, private val routePresenter: RoutePresenter) : android.support.v7.widget.RecyclerView.Adapter<RegisterAdapter.RegisterViewHolder>() {


    override fun onBindViewHolder(holder: com.rviannaoliveira.vroutetrack.route.RegisterAdapter.RegisterViewHolder, position: Int) {

        if(registers.isNotEmpty()){
            val register = registers[position]
            holder.address.text = register.address
            holder.id.text = register.id.toString()
            holder.time.text = register.getTimeFormat()
        }
    }

    override fun onCreateViewHolder(parent: android.view.ViewGroup, viewType: Int): com.rviannaoliveira.vroutetrack.route.RegisterAdapter.RegisterViewHolder {
        return RegisterViewHolder(android.view.LayoutInflater.from(parent.context).inflate(com.rviannaoliveira.vroutetrack.R.layout.item,parent,false))
    }

    override fun getItemCount(): Int = registers.size

    inner class RegisterViewHolder(itemView : android.view.View) : android.support.v7.widget.RecyclerView.ViewHolder(itemView){
        var id = itemView.findViewById(com.rviannaoliveira.vroutetrack.R.id.id) as android.widget.TextView
        var address = itemView.findViewById(com.rviannaoliveira.vroutetrack.R.id.address) as android.widget.TextView
        var time = itemView.findViewById(com.rviannaoliveira.vroutetrack.R.id.time_register) as android.widget.TextView
    }

    fun refresh(registers: List<RegisterTrack>) {
        this.registers.clear()
        this.registers.addAll(registers)
        this.notifyDataSetChanged()
    }
}
