package com.example.modern_databases.data.dao

import androidx.lifecycle.LiveData
import androidx.room.*
import com.example.modern_databases.data.entities.*


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

    @Query("SELECT id_ad_ FROM cart_table WHERE id_user_=(:id_user)")
    fun getAllIdElementOnCart(id_user: Int): List<Int>


    @Query("SELECT*FROM cart_table WHERE id_user_=(:id_user) ORDER BY date")
    fun getAllElementOnCartTest1(id_user: Int): LiveData<List<CartAndAdList>>

    @Query("SELECT*FROM cart_table WHERE id_ad_=(:id_ad) ORDER BY date")
    fun getCartByIdAd(id_ad: Int): List<Cart>
}

data class CartAndAdList (
    @Embedded val cart: Cart,
    @Relation (
        parentColumn = "id_ad_",
        entity = Ad::class,
        entityColumn = "id_ad"
    )
  val f:List<AdDao.AdAndImageAndFavorite>)