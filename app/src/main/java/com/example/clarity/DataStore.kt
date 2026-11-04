package com.example.clarity

import android.content.Context
import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.datastore.preferences.core.intPreferencesKey
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore

val Context.dataStore by preferencesDataStore(name = "user_data")

val WEIGHT_KEY = intPreferencesKey("weight")
val HEIGHT_KEY = intPreferencesKey("height")
val GENDER_KEY = booleanPreferencesKey("gender")
val BIRTHDAY_KEY = stringPreferencesKey("24.04.2006")
val UID_KEY = stringPreferencesKey("uid")