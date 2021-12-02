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
        val orderDao_ = UserDatabase.getDatabase(application).orderDao()
        val picturesDao_ = UserDatabase.getDatabase(application).imageDao()
        repository = UserRepository(userDao_,adDao_,orderDao_,picturesDao_)
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

    fun getAdById(id:Int): List<Ad> {
        return repository.getAdById(id)
    }

    fun getAdByIdUser(id:Int): List<Ad> {
        return repository.getAdByIdUser(id)
    }

    fun addImage (image: Image) {
        viewModelScope.launch (Dispatchers.IO){
            repository.addImage(image)
        }
    }

    fun getImageById(id_image:Int):List<Image> {
        return repository.getImageById(id_image)
    }
}

