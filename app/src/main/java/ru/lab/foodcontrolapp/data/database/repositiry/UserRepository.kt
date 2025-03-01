package ru.lab.foodcontrolapp.data.database.repositiry

import ru.lab.foodcontrolapp.data.database.dao.UserDao
import ru.lab.foodcontrolapp.data.database.entity.User

class UserRepository(private val userDao: UserDao) {

    suspend fun getUser(): User? {
        return userDao.getUser()
    }

    suspend fun updateUser(user: User){
        return userDao.updateUser(user)
    }

}