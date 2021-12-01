package com.example.modern_databases.data

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class UserViewModel(application:Application):AndroidViewModel(application) {
    private val repository: UserRepository
    val readAllData: LiveData<List<Ad>>

    init {
        val userDao_ = UserDatabase.getDatabase(application).userDao()
        val adDao_ = UserDatabase.getDatabase(application).adDao()
        repository = UserRepository(userDao_,adDao_)
        readAllData =repository.readAllAd
    }

    fun addUser (user: User) {
        viewModelScope.launch (Dispatchers.IO){
            repository.addUser(user)
        }
    }

    fun getUser(login:String,password:String):List<User> {
        return repository.getUser(login,password)
    }

    fun checkUniqLogin(login:String):List<User> {
        return repository.checkUniqLogin(login)
    }

    fun addAd (ad: Ad) {
        viewModelScope.launch (Dispatchers.IO){
            repository.addAd(ad)
        }
    }
}

