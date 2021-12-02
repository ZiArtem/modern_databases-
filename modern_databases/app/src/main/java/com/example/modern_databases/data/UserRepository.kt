package com.example.modern_databases.data

import androidx.lifecycle.LiveData

class UserRepository(private val userDao: UserDao, private val adDao: AdDao,private val orderDao: OrderDao, private val imageDao: ImageDao) {
    val readAllAd: LiveData<List<Ad>> = adDao.readAllAd()

    suspend fun addUser (user: User) {
        userDao.addUser(user)
    }

    fun getUser (login:String,password:String):List<User> {
        return userDao.getUser(login,password)
    }

    fun checkUniqLogin (login:String):List<User> {
        return userDao.checkUniqLogin(login)
    }

    suspend fun addAd (ad: Ad) {
        adDao.addAd(ad)
    }

    fun getAdById(id:Int): List<Ad> {
        return adDao.getAdById(id)
    }

    fun getAdByIdUser(id:Int): List<Ad> {
        return adDao.getAdByIdUser(id)
    }

    suspend fun addImage (image: Image) {
        imageDao.addImage(image)
    }

    fun getImageById(id_image:Int): List<Image> {
        return imageDao.getImageById(id_image)
    }


}