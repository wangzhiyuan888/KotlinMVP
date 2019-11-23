package com.wzy.arms.network.model

class UserInfo{
    val id: Int? = null
    val login: String? = null
    val avatar_url: String? = null

    fun getAvatarUrl(): String {
        return if (avatar_url!!.isEmpty()) avatar_url else avatar_url.split("\\?".toRegex()).dropLastWhile { it.isEmpty() }.toTypedArray()[0]
    }

}