package com.example.neontest.data.source.local

import android.annotation.SuppressLint
import android.content.SharedPreferences
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class SharedPreferencesManager @Inject constructor(private val sharedPref: SharedPreferences) {

    fun getString(key: String, defValue: String? = null): String? =
        sharedPref.getString(key, defValue)

    fun getBoolean(key: String, defValue: Boolean = false) = sharedPref.getBoolean(key, defValue)

    fun getInt(key: String, defValue: Int = 0) = sharedPref.getInt(key, defValue)

    fun getLong(key: String, defValue: Long = 0) = sharedPref.getLong(key, defValue)

    fun getFloat(key: String, defValue: Float = 0f) = sharedPref.getFloat(key, defValue)

    fun <T : Enum<*>> getEnum(
        key: String,
        enumValues: Array<T>,
        defValue: T = enumValues.first()
    ): T {
        val enumOrdinal = sharedPref.getInt(key, INVALID_ENUM_ORDINAL)

        return if (enumOrdinal != INVALID_ENUM_ORDINAL) enumValues[enumOrdinal] else defValue
    }

    fun contains(key: String): Boolean = sharedPref.contains(key)

    fun save(vararg pairs: Pair<String, Any?>, commit: Boolean = false) {
        sharedPref.edit(commit) {
            for ((key, value) in pairs) {
                when (value) {
                    null -> putString(key, null)

                    is String -> putString(key, value)
                    is Boolean -> putBoolean(key, value)
                    is Int -> putInt(key, value)
                    is Long -> putLong(key, value)
                    is Float -> putFloat(key, value)

                    is Enum<*> -> putInt(key, value.ordinal)

                    else -> throw IllegalArgumentException("Illegal value type ${value::class.java.simpleName} for key $key")
                }
            }
        }
    }

    fun remove(vararg keys: String, commit: Boolean = false) {
        sharedPref.edit(commit) {
            keys.forEach { remove(it) }
        }
    }

    fun clear(commit: Boolean = false) {
        sharedPref.edit(commit) {
            clear()
        }
    }

    @SuppressLint("ApplySharedPref")
    fun SharedPreferences.edit(
        commit: Boolean = false,
        action: SharedPreferences.Editor.() -> Unit
    ) {
        val editor = edit().apply { action() }
        if (commit) editor.commit() else editor.apply()
    }

    companion object {

        private const val INVALID_ENUM_ORDINAL = -1
    }
}