package com.example.modern_databases.data

import androidx.lifecycle.LiveData

class UserRepository(private val userDao: UserDao) {
    val readAllData: LiveData<List<User>> = userDao.readAllData()
    suspend fun addUser (user: User) {
        userDao.addUser(user)
    }

    fun getUser (login:String,password:String):List<User> {
        return userDao.getUser(login,password)
    }

}