package com.example.modern_databases.data

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class UserViewModel(application:Application):AndroidViewModel(application) {
    private val repository: UserRepository
    val readAllAd: LiveData<List<Ad>>

    init {
        val userDao_ = UserDatabase.getDatabase(application).userDao()
        val adDao_ = UserDatabase.getDatabase(application).adDao()
        val orderDao_ = UserDatabase.getDatabase(application).orderDao()
        val picturesDao_ = UserDatabase.getDatabase(application).imageDao()
        repository = UserRepository(userDao_,adDao_,orderDao_,picturesDao_)
        readAllAd =repository.readAllAd
    }

    //user function

    fun addUser (user: User) {
        viewModelScope.launch (Dispatchers.IO){
            repository.addUser(user)
        }
    }

    fun deleteUser (user: User) {
        viewModelScope.launch (Dispatchers.IO){
            repository.deleteUser(user)
        }
    }

    fun updateUser (user: User) {
        viewModelScope.launch (Dispatchers.IO){
            repository.updateUser(user)
        }
    }

    fun getUser(login:String,password:String):List<User> {
        return repository.getUser(login,password)
    }

    fun checkUniqueLogin(login:String):List<User> {
        return repository.checkUniqueLogin(login)
    }

    // ad function

    fun addAd (ad: Ad) {
        viewModelScope.launch (Dispatchers.IO){
            repository.addAd(ad)
        }
    }

    fun deleteAd (ad: Ad) {
        viewModelScope.launch (Dispatchers.IO){
            repository.deleteAd(ad)
        }
    }

    fun updateAd (ad: Ad) {
        viewModelScope.launch (Dispatchers.IO){
            repository.updateAd(ad)
        }
    }

    fun getAdById(id:Int): List<Ad> {
        return repository.getAdById(id)
    }

    fun getAdByIdUser(id:Int): List<Ad> {
        return repository.getAdByIdUser(id)
    }
    fun getByKeyword(keyword:String): List<Ad> {
        return repository.getByKeyword(keyword)
    }

    //image function

    fun addImage (image: Image) {
        viewModelScope.launch (Dispatchers.IO){
            repository.addImage(image)
        }
    }

    fun deleteImage (image: Image) {
        viewModelScope.launch (Dispatchers.IO){
            repository.deleteImage(image)
        }
    }

    fun updateImage (image: Image) {
        viewModelScope.launch (Dispatchers.IO){
            repository.updateImage(image)
        }
    }

    fun getImageById(id_image:Int):List<Image> {
        return repository.getImageById(id_image)
    }

    fun getImageByIdAd(id_ad:Int): List<Image> {
        return repository.getImageByIdAd(id_ad)
    }

    // order function

    fun addOrder (order: Order) {
        viewModelScope.launch (Dispatchers.IO){
            repository.addOrder(order)
        }
    }

    fun deleteOrder (order: Order) {
        viewModelScope.launch (Dispatchers.IO){
            repository.deleteOrder(order)
        }
    }

    fun updateOrder (order: Order) {
        viewModelScope.launch (Dispatchers.IO){
            repository.updateOrder(order)
        }
    }

    fun readAllOrders (id_user:Int): List<Order> {
        return repository.readAllOrders(id_user)
    }

    fun getOrderById(id_order:Int): List<Order> {
        return repository.getOrderById(id_order)
    }
}

