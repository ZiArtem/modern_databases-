package com.example.modern_databases.data.dao

import androidx.lifecycle.LiveData
import androidx.room.*
import com.example.modern_databases.data.entities.Order
import java.util.*

@Dao
interface OrderDao {
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun addOrder(order: Order)

    @Delete
    suspend fun deleteOrder(order: Order)

    @Update
    suspend fun updateOrder(order: Order)

    @Query("SELECT*FROM order_table WHERE id_user_=(:id_user) ORDER BY date")
    fun readAllOrders(id_user: Int): LiveData<List<Order>>

    @Query("SELECT*FROM order_table WHERE id_order=(:id_order)")
    fun getOrderById(id_order: Int): List<Order>

    @Query("SELECT id_order FROM order_table WHERE id_user_=(:id_user) AND date =(:date)")
    fun getOrdeIdrByUserIdAndDate(id_user: Int,date: Date): List<Int>

}