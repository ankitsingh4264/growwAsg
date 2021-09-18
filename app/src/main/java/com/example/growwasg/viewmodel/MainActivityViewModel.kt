package com.example.growwasg.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.growwasg.Repository
import com.example.growwasg.dataclass.User

class MainActivityViewModel :ViewModel() {

    val repo= Repository();

    private var _userData=MutableLiveData<User>()
    val userData:LiveData<User>
      get()=_userData

    private var _update=MutableLiveData<Boolean>();
    val update:MutableLiveData<Boolean>
       get() = _update

    private var _dataListener=MutableLiveData<ArrayList<String>>()
    val dataListener:MutableLiveData<ArrayList<String>>
    get()=_dataListener

    fun getUserData(){
        _userData=repo.getUserData()
    }
    fun updateData(list: ArrayList<String>){

        _update.value=false
        _update=repo.updateData(list)

    }
    fun listenUpdateData(){
        _dataListener=repo.listenTodo()
    }



}