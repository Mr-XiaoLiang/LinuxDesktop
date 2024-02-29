package com.lollipoppp.desktop.utils

import java.util.prefs.Preferences
import kotlin.reflect.KProperty

class PreferencesHelper(
    private val preferences: Preferences
) {

    companion object {
        private val userRoot by lazy {
            Preferences.userNodeForPackage(PreferencesHelper::class.java)
        }

        fun create(name: String): PreferencesHelper {
            return PreferencesHelper(userRoot.node(name))
        }

        inline fun <reified T : Any> T.localPreferences(): PreferencesHelper {
            return create(this::class.java.simpleName)
        }

    }

    fun node(name: String): PreferencesHelper {
        return PreferencesHelper(preferences.node(name))
    }

    operator fun get(key: String, def: Long = 0L): Long {
        return preferences.getLong(key, def)
    }

    operator fun get(key: String, def: String = ""): String {
        return preferences.get(key, def)
    }

    operator fun get(key: String, def: Boolean = false): Boolean {
        return preferences.getBoolean(key, def)
    }

    operator fun get(key: String, def: Float = 0F): Float {
        return preferences.getFloat(key, def)
    }

    operator fun get(key: String, def: Int = 0): Int {
        return preferences.getInt(key, def)
    }

    operator fun get(key: String, def: Double = 0.0): Double {
        return preferences.getDouble(key, def)
    }

    operator fun get(key: String, def: ByteArray = ByteArray(0)): ByteArray {
        return preferences.getByteArray(key, def)
    }


    fun put(key: String, def: Long = 0L) {
        preferences.putLong(key, def)
    }

    fun put(key: String, def: String = "") {
        preferences.put(key, def)
    }

    fun put(key: String, def: Boolean = false) {
        preferences.putBoolean(key, def)
    }

    fun put(key: String, def: Float = 0F) {
        preferences.putFloat(key, def)
    }

    fun put(key: String, def: Int = 0) {
        preferences.putInt(key, def)
    }

    fun put(key: String, def: Double = 0.0) {
        preferences.putDouble(key, def)
    }

    fun put(key: String, def: ByteArray = ByteArray(0)) {
        preferences.putByteArray(key, def)
    }

    fun with(def: String = ""): StringDelegate {
        return StringDelegate(this, def)
    }

    fun with(def: Long = 0L): LongDelegate {
        return LongDelegate(this, def)
    }

    fun with(def: Int = 0): IntDelegate {
        return IntDelegate(this, def)
    }

    fun with(def: Boolean = false): BooleanDelegate {
        return BooleanDelegate(this, def)
    }

    fun with(def: Float = 0F): FloatDelegate {
        return FloatDelegate(this, def)
    }

    fun with(def: Double = 0.0): DoubleDelegate {
        return DoubleDelegate(this, def)
    }

    fun with(def: ByteArray = ByteArray(0)): ByteArrayDelegate {
        return ByteArrayDelegate(this, def)
    }

    class StringDelegate(
        private val pref: PreferencesHelper,
        private val defValue: String
    ) {
        operator fun getValue(target: Any, property: KProperty<*>): String {
            return pref[property.name, defValue]
        }

        operator fun setValue(target: Any, property: KProperty<*>, s: String) {
            pref.put(property.name, s)
        }
    }

    class IntDelegate(
        private val pref: PreferencesHelper,
        private val defValue: Int
    ) {
        operator fun getValue(target: Any, property: KProperty<*>): Int {
            return pref[property.name, defValue]
        }

        operator fun setValue(target: Any, property: KProperty<*>, s: Int) {
            pref.put(property.name, s)
        }
    }

    class LongDelegate(
        private val pref: PreferencesHelper,
        private val defValue: Long
    ) {
        operator fun getValue(target: Any, property: KProperty<*>): Long {
            return pref[property.name, defValue]
        }

        operator fun setValue(target: Any, property: KProperty<*>, s: Long) {
            pref.put(property.name, s)
        }
    }

    class FloatDelegate(
        private val pref: PreferencesHelper,
        private val defValue: Float
    ) {
        operator fun getValue(target: Any, property: KProperty<*>): Float {
            return pref[property.name, defValue]
        }

        operator fun setValue(target: Any, property: KProperty<*>, s: Float) {
            pref.put(property.name, s)
        }
    }

    class BooleanDelegate(
        private val pref: PreferencesHelper,
        private val defValue: Boolean
    ) {
        operator fun getValue(target: Any, property: KProperty<*>): Boolean {
            return pref[property.name, defValue]
        }

        operator fun setValue(target: Any, property: KProperty<*>, s: Boolean) {
            pref.put(property.name, s)
        }
    }

    class DoubleDelegate(
        private val pref: PreferencesHelper,
        private val defValue: Double
    ) {
        operator fun getValue(target: Any, property: KProperty<*>): Double {
            return pref[property.name, defValue]
        }

        operator fun setValue(target: Any, property: KProperty<*>, s: Double) {
            pref.put(property.name, s)
        }
    }

    class ByteArrayDelegate(
        private val pref: PreferencesHelper,
        private val defValue: ByteArray
    ) {
        operator fun getValue(target: Any, property: KProperty<*>): ByteArray {
            return pref[property.name, defValue]
        }

        operator fun setValue(target: Any, property: KProperty<*>, s: ByteArray) {
            pref.put(property.name, s)
        }
    }

}