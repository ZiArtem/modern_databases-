package com.example.modern_databases.data.dao

import androidx.lifecycle.LiveData
import androidx.room.*
import com.example.modern_databases.data.data_class.*


@Dao
interface CartDao {
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun addCartElement(cart: Cart)

    @Delete
    suspend fun deleteCartElement(cart: Cart)

    @Update
    suspend fun updateCartElement(cart: Cart)

    @Query("SELECT*FROM cart_table WHERE id_user_=(:id_user) ORDER BY date")
    fun getAllElementOnCart(id_user: Int): LiveData<List<Cart>>

    @Query("SELECT*FROM cart_table WHERE id_user_=(:id_user)")
    fun getAllElementOnCartTest(id_user: Int): List<Cart>
//
//    @Query("SELECT*FROM parchasehistory_table WHERE id_order=(:id_order)")
//    fun getOrderById(id_order: Int): List<Order>

    @Query("SELECT*FROM cart_table WHERE id_user_=(:id_user) ORDER BY date")
    fun getAllElementOnCartTest1(id_user: Int): LiveData<List<FullAd1>>

    @Query("SELECT*FROM cart_table WHERE id_ad_=(:id_ad) ORDER BY date")
    fun getCartByIdAd(id_ad: Int): List<Cart>

}

data class FullAd1 (
    @Embedded val cart: Cart,
    @Relation (
        parentColumn = "id_ad_",
        entity = Ad::class,
        entityColumn = "id_ad"
    )
  val f:List<AdDao.FullAd>

//    @Relation (
//        parentColumn = "id_ad",
//        entity = Favorite::class,
//        entityColumn = "id_ad_"
//    )
//    val fav: List<Favorite>
)