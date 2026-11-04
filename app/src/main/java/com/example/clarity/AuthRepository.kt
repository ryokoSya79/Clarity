package com.example.clarity

import android.content.Context
import android.util.Log
import androidx.compose.runtime.key
import androidx.credentials.CredentialManager
import androidx.credentials.CustomCredential
import androidx.credentials.GetCredentialRequest
import androidx.credentials.GetCredentialResponse
import androidx.datastore.preferences.core.edit
import androidx.lifecycle.compose.dropUnlessResumed
import com.google.android.libraries.identity.googleid.GetGoogleIdOption
import com.google.android.libraries.identity.googleid.GoogleIdTokenCredential
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.GoogleAuthProvider
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.values
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.tasks.await

class AuthRepository(private val context: Context) {
    private val auth = FirebaseAuth.getInstance()
    private val credentialManager = CredentialManager.create(context)
    private val webClient = "32718238621-90d7c1agnlfv635np66c35sisltpdaba.apps.googleusercontent.com"

    private val _isNewUser = MutableStateFlow(true)
    val isNewUser: StateFlow<Boolean> = _isNewUser.asStateFlow()



    private val database = FirebaseDatabase.getInstance().getReference("users")

    val weight: Flow<Int> = context.dataStore.data.map{ prefs ->
        prefs[WEIGHT_KEY] ?: 70
    }

    val height: Flow<Int> = context.dataStore.data.map { prefs ->
        prefs[HEIGHT_KEY] ?: 175
    }

    val gender: Flow<Boolean> = context.dataStore.data.map { prefs ->
        prefs[GENDER_KEY] ?: false
    }

    val birthday: Flow<String> = context.dataStore.data.map { prefs ->
        prefs[BIRTHDAY_KEY] ?: "24.04.2006"
    }

    val uid: Flow<String?> = context.dataStore.data.map { prefs ->
        prefs[UID_KEY] ?: null
    }

    private val _nameUser = MutableStateFlow("")
    val nameUser: StateFlow<String> = _nameUser.asStateFlow()

    private val _emailUser = MutableStateFlow("")
    val emailUser: StateFlow<String> = _emailUser.asStateFlow()


    suspend fun googleSignIn(): Boolean{
        val googleIdOption = GetGoogleIdOption.Builder()
            .setFilterByAuthorizedAccounts(false) //если стоит false значит покажуться все акк, если true о только те через которые нзаходил
            .setServerClientId(webClient)//ключик передаем
            .setAutoSelectEnabled(false)//если будет true и у пользователя один акк, то он сразу же зайдет в него без варианта выбора
            .build()

        val request = GetCredentialRequest.Builder()
            .addCredentialOption(googleIdOption)
            .build()

        try{
            val result = credentialManager.getCredential(
                context = context,
                request = request
            )
            val credential = result.credential

            if (credential is CustomCredential && credential.type == GoogleIdTokenCredential.TYPE_GOOGLE_ID_TOKEN_CREDENTIAL){
                val googleIdTokenCredential = GoogleIdTokenCredential.createFrom(credential.data)
                val idToken = googleIdTokenCredential.idToken

                val firebaseCredential = GoogleAuthProvider.getCredential(idToken, null)

                val authResult = auth.signInWithCredential(firebaseCredential).await()

                val isNewUserAcc = authResult.additionalUserInfo?.isNewUser ?: false
//                val emailgogolu = authResult.user?.email


                _isNewUser.value = isNewUserAcc
//                проверка был ли польщовтель уже зарегн у нас или нет

                return true
            }else{
                return false
            }

        }catch (e: Exception){
            e.printStackTrace()
            return false
        }
    }

    suspend fun AuthEmail(userName: Any?, userPassword: Any?, userEmail: Any?): Boolean{

        val database = FirebaseDatabase.getInstance()
        val userData = mapOf(
            "email" to userEmail,
            "name" to userName,
            "password" to userPassword
        )

        val countUser = database.getReference("users").get().await().childrenCount

        database.getReference("users").child("user_${countUser+1}").setValue(userData)

        context.dataStore.edit {prefs ->
            prefs[UID_KEY] = "user_${countUser+1}"
        }
        return true
    }

    suspend fun loginExamination(enterName: String?, enterPassword: String?): Boolean{
            try{

                val query = database.orderByChild("email").equalTo(enterName)

                Log.i("LOGOPOCO", "${query.get()}, query email")
                Log.i("LOGOPOCO", "${enterName}, enterName")
                Log.i("LOGOPOCO", "${enterPassword}, enterPassword")

                val snapshot = query.get().await()

                if(snapshot.exists()){
                    var isLoginSuccesful = false

                    for(userSnapshot in snapshot.children){
                        val dbPassword = userSnapshot.child("password").getValue(String::class.java)

                        Log.i("LOGOPOCO", "${dbPassword}, query Password")

                        if(dbPassword == enterPassword){
                            isLoginSuccesful = true

                            context.dataStore.edit {prefs ->
                                prefs[UID_KEY] = "${userSnapshot.key}"
                            }
//                            Log.i("TEST_TAGS", "${userSnapshot}, snapshotUser")
                            break
                        }
                    }
                    return isLoginSuccesful
                }else{
                    return false
                }

            }catch (e: Exception){
                Log.e("LOGOPOCO", "КРАШ ЗАПРОСА К FIREBASE: ${e.message}", e)
                return false
            }
    }

    suspend fun EmailExamination(enterEmail: String?): Boolean{
        try{
            val query = database.orderByChild("email").equalTo(enterEmail)
            val shapshot = query.get().await()

            return shapshot.exists()
        }catch (e: Exception){
            Log.e("LOGOPOCO", "КРАШ ЗАПРОСА К FIREBASE: ${e.message}", e)
            return false
        }
    }

    suspend fun editPassword(enterEmail: String?, enterNewPassword: String?): Boolean{
        try{
            val query = database.orderByChild("email").equalTo(enterEmail)
            val snapshot = query.get().await()

            if(snapshot.exists()){
                for(userSnapshot in snapshot.children){
                    database.child(userSnapshot.key.toString()).child("password").setValue(enterNewPassword)
                    Log.e("TEST_TAGS", "Всё гуд всё нашлось ${userSnapshot.key}")

                    context.dataStore.edit {prefs ->
                        prefs[UID_KEY] = "${userSnapshot.key}"
                    }

                }
            }

            return snapshot.exists()
        }catch (e: Exception){
            Log.e("LOGOPOCO", "КРАШ ЗАПРОСА К FIREBASE: ${e.message}", e)
            return false
        }
    }

    suspend fun saveWeight(value: Int){
        context.dataStore.edit{ prefs ->
            prefs[WEIGHT_KEY] = value
        }
    }

    suspend fun saveHeight(value: Int){
        context.dataStore.edit { prefs ->
            prefs[HEIGHT_KEY] = value
        }
    }

    suspend fun saveGender(value: Boolean){
        context.dataStore.edit {prefs ->
            prefs[GENDER_KEY] = value
        }
    }

    suspend fun saveBirthday(value: String){
        context.dataStore.edit { prefs ->
            prefs[BIRTHDAY_KEY] = value
        }
    }

    suspend fun getNameEmailUser(){

        val snapshot = FirebaseDatabase.getInstance().reference
            .child("users")
            .child(uid.first()!!)
            .get()
            .await()

        _nameUser.value = snapshot.child("name").getValue(String::class.java) ?: ""
        _emailUser.value = snapshot.child("email").getValue(String::class.java) ?: ""
    }

    suspend fun clearBD(){
        context.dataStore.edit {prefs -> prefs.clear()}
    }
}