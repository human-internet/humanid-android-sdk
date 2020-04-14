package com.nbs.humanidui.util.extensions

fun Map<String, Any>?.getString(key: String) = this?.get(key) as String?

fun Map<String, Any>?.getLong(key: String) = (this?.get(key) as Double?)?.toLong()

fun Map<String, Any>?.getInt(key: String) = try { this?.get(key) as Int? }catch (e:Exception) { (this?.get(key) as Double?)?.toInt() }

fun Map<String, Any>?.getDouble(key: String) = this?.get(key) as Double?

fun Map<String, Any>?.getBoolean(key: String) = this?.get(key) as Boolean?

fun <T>Map<String, Any>?.getList(key: String) = this?.get(key) as List<T>?

fun Map<String, Any>?.getAsLong(key: String) = try { getLong(key) }catch (e:Exception) { getString(key)?.toLong() }

fun Map<String, Any>?.getAsDouble(key: String) = try { getDouble(key) }catch (e:Exception) { getString(key)?.toDouble() }

fun <T>Map<String, Any>?.getAsObject(key: String) : T = this?.get(key) as T