package com.lifex.loginapp.data

import com.lifex.loginapp.data.model.LoggedInUser

/**
 * Class for the use if we want to store data in some local storage of something,
 * else in code there is no such use of this class
 */

class LoginRepository {

    private var user: LoggedInUser? = null

    init {
        user = null
    }

    fun login(username: String, password: String) {
        val user = LoggedInUser(java.util.UUID.randomUUID().toString(), username,password)
        setLoggedInUser(user)
    }

    private fun setLoggedInUser(loggedInUser: LoggedInUser) {
        this.user = loggedInUser
    }

/**    fun getLoggedInUser(): LoggedInUser? {
        return user
    }*/
}