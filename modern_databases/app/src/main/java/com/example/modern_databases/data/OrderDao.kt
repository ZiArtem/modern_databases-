package com.example.modern_databases.data

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query

@Dao
interface OrderDao {
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun addOrder(order: Order)

    @Query("SELECT*FROM parchasehistory_table WHERE id_user_=(:id_user) ORDER BY date")
    fun readAllOrders(id_user:Int): LiveData<List<Order>>

    @Query("SELECT*FROM parchasehistory_table WHERE id_order=(:id_order)")
    fun getOrderById(id_order:Int): List<Order>
}