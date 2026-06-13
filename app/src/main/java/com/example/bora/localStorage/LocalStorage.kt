package com.example.bora.localStorage

object LocalStorage {

    private val storage: MutableMap<String, String?> = mutableMapOf()

    fun getItem(key: String) : String? {
        return storage[key]
    }

    fun setItem(key: String, value: String) {
        storage[key] = value
    }

    fun removeItem(key: String) {
        storage.remove(key)
    }
}