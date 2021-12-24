package com.example.modern_databases.data.repository

import androidx.lifecycle.LiveData
import androidx.room.*
import com.example.modern_databases.data.dao.*
import com.example.modern_databases.data.entities.*
import java.util.*

class Repository(
    private val userDao: UserDao,
    private val adDao: AdDao,
    private val orderDao: OrderDao,
    private val imageDao: ImageDao,
    private val favoriteDao: FavoriteDao,
    private val userInformationDao: UserInformationDao,
    private val cartDao: CartDao,
    private val orderItemDao: OrderItemDao
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

    fun getUserIdByLogin(login: String): List<Int> {
        return userDao.getUserIdByLogin(login)
    }

    fun checkPasswordByUser(password: String,id_user:Int): List<User> {
        return userDao.checkPasswordByUser(password,id_user)
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


    fun getByKeyword(keyword: String): LiveData<List<AdDao.AdAndImageAndFavorite>> {
        return adDao.getByKeyword(keyword)
    }

    fun getAdByListIdAd(id_ad_: List<Int>): LiveData<List<Ad>> {
        return adDao.getAdByListIdAd(id_ad_)
    }

    fun getAdByListIdAdNoLiveData(id_ad_: List<Int>): List<Ad> {
        return adDao.getAdByListIdAdNoLiveData(id_ad_)
    }

    fun getAllAdAndImageAndFavorite(): LiveData<List<AdDao.AdAndImageAndFavorite>> {
        return adDao.getAllAdAndImageAndFavorite()
    }

    fun getAllAdAndImageAndFavoriteByIdAd(favList: List<Int>): LiveData<List<AdDao.AdAndImageAndFavorite>> {
        return adDao.getAllAdAndImageAndFavoriteByIdAd(favList)
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

    fun getImageByIdAd(id_ad: Int):  LiveData<List<Image>> {
        return imageDao.getImagesByIdAd(id_ad)
    }

    fun getAllImage(): LiveData<List<Image>> {
        return imageDao.getAllImage()
    }

    fun getAllPreviewImage(id_ad_: List<Int>): LiveData<List<Image>> {
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

    fun readAllOrders(id_user: Int):  LiveData<List<Order>> {
        return orderDao.readAllOrders(id_user)
    }

    fun getOrderById(id_order: Int): List<Order> {
        return orderDao.getOrderById(id_order)
    }

    fun getOrdeIdrByUserIdAndDate(id_user: Int,date: Date): List<Int> {
        return orderDao.getOrdeIdrByUserIdAndDate(id_user,date)
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

    fun getAllFavoriteByUser(id_user: Int): LiveData<List<FavoriteDao.FavoriteAndAdAndImage>> {
        return favoriteDao.getAllFavoriteByUser(id_user)
    }

    fun getAllFavoriteAd(id_user: Int): List<Int> {
        return favoriteDao.getAllFavoriteAd(id_user)
    }

    fun getFavoriteByIdAd(id_fav: Int): List<Favorite> {
        return favoriteDao.getFavoriteByIdAd(id_fav)
    }

    // User Information function

    suspend fun addUserInfo(userInformation: UserInformation) {
        userInformationDao.addUserInfo(userInformation)
    }

    suspend fun updateUserInfo(userInformation: UserInformation) {
        userInformationDao.updateUserInfo(userInformation)
    }

    suspend fun deleteUserInfo(userInformation: UserInformation) {
        userInformationDao.deleteUserInfo(userInformation)
    }

    fun getUserInformation(id: Int): List<UserInformation> {
        return userInformationDao.getUserInfo(id)
    }

    // cart function

    fun getAllElementOnCart(id_user: Int): LiveData<List<Cart>> {
        return cartDao.getAllElementOnCart(id_user)
    }

    suspend fun addCartElement(cart: Cart) {
        cartDao.addCartElement(cart)
    }

    suspend fun deleteCartElement(cart: Cart) {
        cartDao.deleteCartElement(cart)
    }
    suspend fun updateCartElement(cart: Cart)  {
        cartDao.updateCartElement(cart)
    }

    fun getAllIdElementOnCart(id_user: Int): List<Int> {
        return cartDao.getAllIdElementOnCart(id_user)
    }


    fun getAllElementOnCartTets(id_user: Int): List<Cart> {
        return cartDao.getAllElementOnCartTest(id_user)
    }

    fun getAllElementOnCartTest1(id_user: Int): LiveData<List<CartAndAdList>> {
        return cartDao.getAllElementOnCartTest1(id_user)
    }

    fun getCartByIdAd(id_ad: Int): List<Cart> {
        return cartDao.getCartByIdAd(id_ad)
    }

    // order Item function

    suspend fun addOrderItem(orderItem: OrderItem) {
        orderItemDao.addOrderItem(orderItem)
    }

    suspend fun deleteOrderItem(orderItem: OrderItem){
        orderItemDao.deleteOrderItem(orderItem)
    }

    suspend fun updateOrderItem(orderItem: OrderItem){
        orderItemDao.updateOrderItem(orderItem)
    }

    fun getOrderItemByIdOrder(id_order: Int): LiveData<List<OrderItemDao.OrderItemAndAdAndImage>> {
        return  orderItemDao.getOrderItemByIdOrder(id_order)
    }
}