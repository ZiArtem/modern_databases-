package com.example.modern_databases.data

import androidx.lifecycle.LiveData
import androidx.room.*

class UserRepository(
    private val userDao: UserDao,
    private val adDao: AdDao,
    private val orderDao: OrderDao,
    private val imageDao: ImageDao,
    private val favoriteDao: FavoriteDao,
    private val userInformationDao: UserInformationDao,
) {

    //user function
    suspend fun addUser(user: User) {
        userDao.addUser(user)
    }

    fun getUser(login: String, password: String): List<User> {
        return userDao.getUser(login, password)
    }

    fun checkUniqueLogin(login: String): List<User> {
        return userDao.checkUniqueLogin(login)
    }

    suspend fun deleteUser(user: User) {
        userDao.deleteUser(user)
    }

    suspend fun updateUser(user: User) {
        userDao.updateUser(user)
    }

    fun getUserInfo(id: Int): List<UserDao.UserInfo> {
        return userDao.getUserInfo(id)
    }

    //ad function

    val readAllAd: LiveData<List<Ad>> = adDao.readAllAd()

    suspend fun addAd(ad: Ad) {
        adDao.addAd(ad)
    }

    suspend fun deleteAd(ad: Ad) {
        adDao.deleteAd(ad)
    }

    suspend fun updateAd(ad: Ad) {
        adDao.updateAd(ad)
    }

    fun getAdById(id: Int): List<Ad> {
        return adDao.getAdById(id)
    }

    fun getAdByIdUser(id: Int): List<Ad> {
        return adDao.getAdByIdUser(id)
    }

    fun getByKeyword(keyword: String): LiveData<List<Ad>> {
        return adDao.getByKeyword(keyword)
    }

    fun getAllAd(): List<Ad> {
        return adDao.getAllAd()
    }

    fun getAdByListIdAd(id_ad_: List<Int>): LiveData<List<Ad>> {
        return adDao.getAdByListIdAd(id_ad_)
    }

    fun readAllAdId(): List<Int> {
        return adDao.readAllAdId()
    }

    fun TestALlAd(): LiveData<List<AdDao.FullAd>> {
        return adDao.TestALlAd()
    }

    //Image function

    suspend fun addImage(image: Image) {
        imageDao.addImage(image)
    }

    suspend fun deleteImage(image: Image) {
        imageDao.deleteImage(image)
    }

    suspend fun updateImage(image: Image) {
        imageDao.updateImage(image)
    }

    fun getImageById(id_image: Int): List<Image> {
        return imageDao.getImageById(id_image)
    }

    fun getImageByIdAd(id_ad: Int): List<Image> {
        return imageDao.getImagesByIdAd(id_ad)
    }

    fun getAllImage(): LiveData<List<Image>> {
        return imageDao.getAllImage()
    }

    fun getAllPreviewImage(id_ad_:List<Int>): LiveData<List<Image>> {
        return imageDao.getAllPreviewImage(id_ad_)
    }

    //order function

    suspend fun addOrder(order: Order) {
        orderDao.addOrder(order)
    }

    suspend fun deleteOrder(order: Order) {
        orderDao.deleteOrder(order)
    }

    suspend fun updateOrder(order: Order) {
        orderDao.updateOrder(order)
    }

    fun readAllOrders(id_user: Int): List<Order> {
        return orderDao.readAllOrders(id_user)
    }

    fun getOrderById(id_order: Int): List<Order> {
        return orderDao.getOrderById(id_order)
    }


    // favorite function

    suspend fun addFavorite(favorite: Favorite) {
        favoriteDao.addFavorite(favorite)
    }

    suspend fun deleteFavorite(favorite: Favorite) {
        favoriteDao.deleteFavorite(favorite)
    }

    suspend fun updateFavorite(favorite: Favorite) {
        favoriteDao.updateFavorite(favorite)
    }

    fun getAllFavoriteByUser(id_user: Int): LiveData<List<Favorite>> {
        return favoriteDao.getAllFavoriteByUser(id_user)
    }

    fun getAllFavoriteAd(id_user: Int): List<Int> {
        return favoriteDao.getAllFavoriteAd(id_user)
    }

    fun getFavoriteById(id_fav:Int): List<Favorite> {
        return favoriteDao.getFavoriteById(id_fav)
    }

    // User Information function

    suspend fun addUserInfo(userInformation: UserInformation) {
        return userInformationDao.addUserInfo(userInformation)
    }

    suspend fun updateUserInfo(userInformation: UserInformation) {
        return userInformationDao.updateUserInfo(userInformation)
    }

    suspend fun deleteUserInfo(userInformation: UserInformation) {
        return userInformationDao.deleteUserInfo(userInformation)
    }

    fun getUserInformation(id: Int): List<UserInformation> {
        return userInformationDao.getUserInfo(id)
    }

}