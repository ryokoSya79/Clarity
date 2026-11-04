    package com.example.clarity

    import android.app.Application
    import android.content.Context
    import android.util.Log
    import androidx.compose.runtime.State
    import androidx.compose.runtime.remember
    import androidx.compose.runtime.rememberCoroutineScope
    import androidx.datastore.preferences.core.edit
    import androidx.lifecycle.AndroidViewModel
    import androidx.lifecycle.ViewModel
    import androidx.lifecycle.application
    import androidx.lifecycle.viewModelScope
    import com.google.firebase.auth.FirebaseAuth
    import kotlinx.coroutines.flow.Flow
    import kotlinx.coroutines.flow.MutableStateFlow
    import kotlinx.coroutines.flow.SharingStarted
    import kotlinx.coroutines.flow.StateFlow
    import kotlinx.coroutines.flow.asStateFlow
    import kotlinx.coroutines.flow.first
    import kotlinx.coroutines.flow.map
    import kotlinx.coroutines.flow.onEach
    import kotlinx.coroutines.flow.stateIn
    import kotlinx.coroutines.launch

    class AuthViewModel(application: Application): AndroidViewModel(application) {
        private val _isLoading = MutableStateFlow(false)
        val isLoading: StateFlow<Boolean> = _isLoading.asStateFlow()

        private val _isAuthSucces = MutableStateFlow(false)
        val isAuthSucces: StateFlow<Boolean> = _isAuthSucces.asStateFlow()

        private val _continueAuth = MutableStateFlow(false)
        val continueAuth: StateFlow<Boolean> = _continueAuth.asStateFlow()

        private val repository = AuthRepository(application)



        private val _newUserData = mutableMapOf<String, Any?>()

        private val _isAuthEmail = MutableStateFlow(false)
        val isAuthEmail: StateFlow<Boolean> = _isAuthEmail.asStateFlow()

        private val _isLogin = MutableStateFlow(false)
        val isLogin: StateFlow<Boolean> = _isLogin.asStateFlow()

        private val _enterName = MutableStateFlow<String?>(null)
        val enterName: StateFlow<String?> = _enterName.asStateFlow()

        private val _enterPassword = MutableStateFlow<String?>(null)
        val enterPassword: StateFlow<String?> = _enterPassword.asStateFlow()

        private val _isEmailExamination = MutableStateFlow<Boolean>(false)
        val isEmailExamination = _isEmailExamination.asStateFlow()

        private val _enterEmail = MutableStateFlow<String?>(null)
        val enterEmail: StateFlow<String?> = _enterEmail.asStateFlow()

        private val _enterCode = MutableStateFlow<String?>(null)
        val enterCode: StateFlow<String?> = _enterCode.asStateFlow()

        private val _enterNewPassword = MutableStateFlow<String?>(null)
        val enterNewPassword: StateFlow<String?> = _enterNewPassword.asStateFlow()

        private val _isEditPassword = MutableStateFlow(false)
        val isEditPassword: StateFlow<Boolean> = _isEditPassword.asStateFlow()

        private val _emailUserVM = MutableStateFlow("")
        val emailUserVM: StateFlow<String?> = _emailUserVM.asStateFlow()

        private val _nameUserVM = MutableStateFlow("")
        val nameUserVM: StateFlow<String?> = _nameUserVM.asStateFlow()

//        private val _nameUser = MutableStateFlow("")
//        val nameUser: StateFlow<String> = _nameUser.asStateFlow()
//
//        private val _emailUser = MutableStateFlow("")
//        val emailUser: StateFlow<String> = _emailUser.asStateFlow()


        fun signInWithGoogle(repository: AuthRepository){
            viewModelScope.launch {
                _isLoading.value = true

                val succes = repository.googleSignIn()

                _isAuthSucces.value = succes
                _isLoading.value = false

                _continueAuth.value = repository.isNewUser.value
            }
        }

        fun loadCurrentUser(){
            val user = FirebaseAuth.getInstance().currentUser

            _emailUserVM.value = user?.email ?: ""
            _nameUserVM.value = user?.displayName ?: ""
        }


        private val _startComposoble = MutableStateFlow<String?>(null)
        val startComposable: StateFlow<String?> = _startComposoble.asStateFlow()
        private val _isLoading_email = MutableStateFlow(false)
        val isLoading_email = _isLoading_email.asStateFlow()

        private val _isLoading_login = MutableStateFlow(false)
        val isLoading_login = _isLoading_login.asStateFlow()

        private val _isLoading_current = MutableStateFlow(false)
        val isLoading_current = _isLoading_current.asStateFlow()

        private val _isLoading_chekedEmail = MutableStateFlow(false)
        val isLoading_chekedEmail = _isLoading_chekedEmail.asStateFlow()

        private val _isLoading_editPassword = MutableStateFlow(false)
        val isLoading_editPassword = _isLoading_editPassword.asStateFlow()

//        val uid: Flow<String> = application.dataStore.data.map { prefs ->
//            prefs[UID_KEY] ?: "0"
//        }




        fun signInWithEmail(repository: AuthRepository){
            viewModelScope.launch {
                _isLoading_email.value = true
                repository.AuthEmail(_newUserData["name"], _newUserData["password"], _newUserData["email"])
                _isAuthEmail.value = true
                _isLoading_email.value = false
            }
        }

        fun updateUserData(key: String, value: Any?){
            _newUserData[key] = value
        }

        fun updateUserName(name: String){
            _enterName.value = name
        }

        fun updateUserPassword(password: String){
            _enterPassword.value = password
        }

        fun updateUserEmail(email: String){
            _enterEmail.value = email
        }

        fun updateUserCode(code: String){
            _enterCode.value = code
        }

        fun updateUserNewPassword(newPassword: String){
            _enterNewPassword.value = newPassword
        }


        init{
            chekedAuthState()
        }

        fun chekedAuthState(){
            viewModelScope.launch {

                _isLoading_current.value = true

                val uid = repository.uid.first()
                val user = FirebaseAuth.getInstance().currentUser


                if (user != null || uid != null){
                    _startComposoble.value = "home"
                }else{
                    _startComposoble.value = "one_promo"
                }
                _isLoading_current.value = false

            }




        }

        fun chekedLoginUser(repository: AuthRepository){

            viewModelScope.launch {

                _isLoading_login.value = true
                val success = repository.loginExamination(enterName.value, enterPassword.value)

                _isLogin.value = success

                _isLoading_login.value = false
            }
        }

        fun chekedEmailUser(repository: AuthRepository){
            viewModelScope.launch {
                _isLoading_chekedEmail.value = true

                val succes = repository.EmailExamination(enterEmail.value)
                _isEmailExamination.value = succes

                _isLoading_chekedEmail.value = false
            }
        }

        private val _code_generation = MutableStateFlow<String>((1000..9999).random().toString())
        val code_generation: StateFlow<String> = _code_generation.asStateFlow()

        fun comparisonCode(repository: AuthRepository){
            viewModelScope.launch {
                _isLoading_editPassword.value = true


                if(enterCode.value == code_generation.value){


                    repository.editPassword(enterEmail.value, enterNewPassword.value)
                    _isEditPassword.value = true

                }
                _isLoading_editPassword.value = false

            }
        }

//        private val repository = AuthRepository(application)
        val weight: StateFlow<Int> = repository.weight
            .stateIn(
                scope = viewModelScope,
                started = SharingStarted.WhileSubscribed(5_000),
                initialValue = 70
            )
        val height: StateFlow<Int> = repository.height
            .stateIn(
                scope = viewModelScope,
                started = SharingStarted.WhileSubscribed(5_000),
                initialValue = 175
            )

        val gender: StateFlow<Boolean> = repository.gender
            .stateIn(
                scope = viewModelScope,
                started = SharingStarted.WhileSubscribed(5_000),
                initialValue = false
            )

        val birthday: StateFlow<String> = repository.birthday
            .stateIn(
                scope = viewModelScope,
                started = SharingStarted.WhileSubscribed(5_000),
                initialValue = "24.04.2006"
            )

        fun saveWeight(value: Int){
            viewModelScope.launch {
                repository.saveWeight(value)
            }
        }

        fun saveHeight(value: Int){
            viewModelScope.launch {
                repository.saveHeight(value)
            }
        }

        fun saveGender(value: Boolean){
            viewModelScope.launch {
                repository.saveGender(value)
            }
        }

        fun saveBirthday(value: String){
            viewModelScope.launch {
                repository.saveBirthday(value)
            }
        }

        fun saveNameAndEmail(repository: AuthRepository){
            val user = FirebaseAuth.getInstance().currentUser
            if (user == null){
                viewModelScope.launch {
                    repository.getNameEmailUser()
                    _emailUserVM.value = repository.emailUser.value
                    _nameUserVM.value = repository.nameUser.value
                }
            }
        }

        fun logout(){
            FirebaseAuth.getInstance().signOut()
            viewModelScope.launch {
                repository.clearBD()
            }
        }

    }