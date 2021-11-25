package com.example.modern_databases.data

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class UserViewModel(application:Application):AndroidViewModel(application) {
    private val repository: UserRepository
    val readAllData: LiveData<List<User>>

    init {
        val userDao_ = UserDatabase.getDatabase(application).userDao()
        repository = UserRepository(userDao_)
        readAllData =repository.readAllData
    }

    fun addUser (user: User) {
        viewModelScope.launch (Dispatchers.IO){
            repository.addUser(user)
        }
    }
}

