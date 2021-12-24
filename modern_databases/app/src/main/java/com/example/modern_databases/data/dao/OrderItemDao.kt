package com.example.modern_databases.data.dao

import androidx.lifecycle.LiveData
import androidx.room.*
import com.example.modern_databases.data.entities.*

@Dao
interface OrderItemDao {
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun addOrderItem(orderItem: OrderItem)

    @Delete
    suspend fun deleteOrderItem(orderItem: OrderItem)

    @Update
    suspend fun updateOrderItem(orderItem: OrderItem)


    @Query("SELECT*FROM order_item_table WHERE id_order_=(:id_order)")
    fun getOrderItemByIdOrder(id_order: Int): LiveData<List<OrderItemAndAdAndImage>>

    data class OrderItemAndAdAndImage(
        @Embedded val orderItem: OrderItem,
        @Relation(
            parentColumn = "id_ad_",
            entity = Ad::class,
            entityColumn = "id_ad"
        )
        val adList: List<FavoriteDao.AdAndImage>,
    )
}