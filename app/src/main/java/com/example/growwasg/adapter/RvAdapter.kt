package com.example.growwasg.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.growwasg.R
import kotlinx.android.synthetic.main.item_todo.view.*

class RvAdapter(val list:ArrayList<String>,val click: OnClick) :RecyclerView.Adapter<RvAdapter.viewholder>() {



    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): viewholder {
       return viewholder(LayoutInflater.from(parent.context).inflate(R.layout.item_todo,parent,false))
    }

    override fun onBindViewHolder(holder: viewholder, position: Int) {
        holder.bind(list[position],position)
    }

    override fun getItemCount(): Int {
        return list.size
    }

    inner class viewholder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        fun bind(todo: String, position: Int) {

            itemView.tv_todo.text=todo
            itemView.btn_delete.setOnClickListener {
                click.itemClicked(position)
            }

        }

    }
    interface OnClick{
        fun itemClicked(position:Int);
    }



}