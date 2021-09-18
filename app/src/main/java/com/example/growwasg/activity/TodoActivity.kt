package com.example.growwasg.activity

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.growwasg.R
import com.example.growwasg.adapter.RvAdapter
import com.example.growwasg.viewmodel.MainActivityViewModel
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.ktx.Firebase
import kotlinx.android.synthetic.main.activity_todo.*

class TodoActivity : AppCompatActivity() , RvAdapter.OnClick {
    private lateinit var viewModel: MainActivityViewModel
    private lateinit var adapter: RvAdapter
    private lateinit var list:ArrayList<String>
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_todo)

        viewModel=ViewModelProvider(this).get(MainActivityViewModel::class.java)
        list=ArrayList()
        adapter= RvAdapter(list,this)
        viewModel.getUserData()

        recyler_view.apply {
            adapter=this@TodoActivity.adapter;
            layoutManager= LinearLayoutManager(this@TodoActivity,LinearLayoutManager.VERTICAL,false)
        }

        btn_add.setOnClickListener {
            list.add(tv_add.text.toString())
            updateData()
        }
        viewModel.listenUpdateData()
       observeViewModel()

        btn_logout.setOnClickListener {
            FirebaseAuth.getInstance().signOut()
            val intent= Intent(this, MainActivity::class.java)
            startActivity(intent)
        }



    }
    private fun observeViewModel(){

        viewModel.dataListener.observe(this, Observer {
            val temp=it
            list.clear()

            list.addAll(temp!!)
            adapter.notifyDataSetChanged()
        })
    }
    private fun updateData(){
        viewModel.updateData(list)

    }


    override fun itemClicked(position: Int) {
        list.removeAt(position)
        updateData()


    }

}