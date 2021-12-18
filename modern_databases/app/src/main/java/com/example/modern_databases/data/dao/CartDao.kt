package com.example.modern_databases.data.dao

import androidx.lifecycle.LiveData
import androidx.room.*
import com.example.modern_databases.data.data_class.Cart
import com.example.modern_databases.data.data_class.Order


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

}