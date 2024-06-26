package com.muratdayan.epasaj.presentation.base

import android.content.Context
import android.content.SharedPreferences

class SharedPrefManager(context: Context, prefFileName: String) {

    private val sharedPreferences: SharedPreferences =
        context.getSharedPreferences(prefFileName, Context.MODE_PRIVATE)

    private val editor = sharedPreferences.edit()

    // veri ne tür ise o türe göre set yapar
    fun <T> setValue(key: String, value: T) {
        when (value) {
            is String -> editor.putString(key, value)
            is Int -> editor.putInt(key, value)
            is Boolean -> editor.putBoolean(key, value)
            is Float -> editor.putFloat(key, value)
            is Long -> editor.putLong(key, value)
            else -> throw IllegalArgumentException("Unsupported data type")
        }
        editor.apply()
    }

    // veri ne tür ise o türe göre return yapar
    fun <T> getValue(key: String, defaultValue: T): T {
        return when (defaultValue) {
            is String -> sharedPreferences.getString(key, defaultValue) as T
            is Int -> sharedPreferences.getInt(key, defaultValue) as T
            is Boolean -> sharedPreferences.getBoolean(key, defaultValue) as T
            is Float -> sharedPreferences.getFloat(key, defaultValue) as T
            is Long -> sharedPreferences.getLong(key, defaultValue) as T
            else -> throw IllegalArgumentException("Unsupported data type")
        }
    }


    //veriyi siler
    fun deleteValue(key: String) {
        editor.remove(key)
        editor.apply()
    }
}