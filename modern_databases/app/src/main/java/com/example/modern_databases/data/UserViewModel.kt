package com.example.modern_databases.data

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class UserViewModel(application: Application) : AndroidViewModel(application) {
    private val repository: UserRepository
    val readAllAd: LiveData<List<Ad>>
    val getAllImage: LiveData<List<Image>>

    init {
        val userDao_ = UserDatabase.getDatabase(application).userDao()
        val adDao_ = UserDatabase.getDatabase(application).adDao()
        val orderDao_ = UserDatabase.getDatabase(application).orderDao()
        val picturesDao_ = UserDatabase.getDatabase(application).imageDao()
        val favoriteDao_ = UserDatabase.getDatabase(application).favoriteDao()
        val userInformationDao_ = UserDatabase.getDatabase(application).userInformationDao()
        repository = UserRepository(
            userDao_,
            adDao_,
            orderDao_,
            picturesDao_,
            favoriteDao_,
            userInformationDao_
        )
        readAllAd = repository.readAllAd
        getAllImage = repository.getAllImage()
    }

    //user function

    fun addUser(user: User) {
        viewModelScope.launch(Dispatchers.IO) {
            repository.addUser(user)
        }
    }

    fun deleteUser(user: User) {
        viewModelScope.launch(Dispatchers.IO) {
            repository.deleteUser(user)
        }
    }

    fun updateUser(user: User) {
        viewModelScope.launch(Dispatchers.IO) {
            repository.updateUser(user)
        }
    }

    fun getUser(login: String, password: String): List<User> {
        return repository.getUser(login, password)
    }

    fun checkUniqueLogin(login: String): List<User> {
        return repository.checkUniqueLogin(login)
    }

    fun getUserInfo(id: Int): List<UserDao.UserInfo> {
        return repository.getUserInfo(id)
    }

    // ad function

    fun addAd(ad: Ad) {
        viewModelScope.launch(Dispatchers.IO) {
            repository.addAd(ad)
        }
    }

    fun deleteAd(ad: Ad) {
        viewModelScope.launch(Dispatchers.IO) {
            repository.deleteAd(ad)
        }
    }

    fun updateAd(ad: Ad) {
        viewModelScope.launch(Dispatchers.IO) {
            repository.updateAd(ad)
        }
    }

    fun getAdById(id: Int): List<Ad> {
        return repository.getAdById(id)
    }

    fun getAdByIdUser(id: Int): List<Ad> {
        return repository.getAdByIdUser(id)
    }

    fun getByKeyword(keyword: String): LiveData<List<Ad>> {
        return repository.getByKeyword(keyword)
    }

    fun getAllAd(): List<Ad> {
        return repository.getAllAd()
    }

    fun getAdByListIdAd(id_ad_: List<Int>): LiveData<List<Ad>> {
        return repository.getAdByListIdAd(id_ad_)
    }

    fun readAllAdId(): List<Int> {
        return repository.readAllAdId()
    }

    //image function

    fun addImage(image: Image) {
        viewModelScope.launch(Dispatchers.IO) {
            repository.addImage(image)
        }
    }

    fun deleteImage(image: Image) {
        viewModelScope.launch(Dispatchers.IO) {
            repository.deleteImage(image)
        }
    }

    fun updateImage(image: Image) {
        viewModelScope.launch(Dispatchers.IO) {
            repository.updateImage(image)
        }
    }

    fun getImageById(id_image: Int): List<Image> {
        return repository.getImageById(id_image)
    }

    fun getImageByIdAd(id_ad: Int): List<Image> {
        return repository.getImageByIdAd(id_ad)
    }

//    fun getAllImage(): LiveData<List<Image>> {
//        return  repository.getAllImage()
//    }

    fun getAllPreviewImage(id_ad_:List<Int>): LiveData<List<Image>> {
        return repository.getAllPreviewImage(id_ad_)
    }

    // order function

    fun addOrder(order: Order) {
        viewModelScope.launch(Dispatchers.IO) {
            repository.addOrder(order)
        }
    }

    fun deleteOrder(order: Order) {
        viewModelScope.launch(Dispatchers.IO) {
            repository.deleteOrder(order)
        }
    }

    fun updateOrder(order: Order) {
        viewModelScope.launch(Dispatchers.IO) {
            repository.updateOrder(order)
        }
    }

    fun readAllOrders(id_user: Int): List<Order> {
        return repository.readAllOrders(id_user)
    }

    fun getOrderById(id_order: Int): List<Order> {
        return repository.getOrderById(id_order)
    }

    // favorite function

    fun addFavorite(favorite: Favorite) {
        viewModelScope.launch(Dispatchers.IO) {
            repository.addFavorite(favorite)
        }
    }

    fun deleteFavorite(favorite: Favorite) {
        viewModelScope.launch(Dispatchers.IO) {
            repository.deleteFavorite(favorite)
        }
    }

    fun updateFavorite(favorite: Favorite) {
        viewModelScope.launch(Dispatchers.IO) {
            repository.updateFavorite(favorite)
        }
    }

    fun getAllFavoriteByUser(id_user: Int): LiveData<List<Favorite>> {
        return repository.getAllFavoriteByUser(id_user)
    }

    fun getAllFavoriteAd(id_user: Int): List<Int> {
        return repository.getAllFavoriteAd(id_user)
    }

    fun getFavoriteById(id_fav:Int): List<Favorite> {
        return repository.getFavoriteById(id_fav)
    }

    // User Information function

    fun addUserInfo(userInformation: UserInformation) {
        viewModelScope.launch(Dispatchers.IO) {
            repository.addUserInfo(userInformation)
        }
    }

    fun updateUserInfo(userInformation: UserInformation) {
        viewModelScope.launch(Dispatchers.IO) {
            repository.updateUserInfo(userInformation)
        }
    }

    fun deleteUserInfo(userInformation: UserInformation) {
        viewModelScope.launch(Dispatchers.IO) {
            repository.deleteUserInfo(userInformation)
        }
    }

    fun getUserInformation(id: Int): List<UserInformation> {
        return repository.getUserInformation(id)
    }



}

