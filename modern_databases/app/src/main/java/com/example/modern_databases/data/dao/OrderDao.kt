package com.example.modern_databases.data.dao

import androidx.room.*
import com.example.modern_databases.data.data_class.Order

@Dao
interface OrderDao {
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun addOrder(order: Order)

    @Delete
    suspend fun deleteOrder(order: Order)

    @Update
    suspend fun updateOrder(order: Order)

    @Query("SELECT*FROM parchasehistory_table WHERE id_user_=(:id_user) ORDER BY date")
    fun readAllOrders(id_user: Int): List<Order>

    @Query("SELECT*FROM parchasehistory_table WHERE id_order=(:id_order)")
    fun getOrderById(id_order: Int): List<Order>

}