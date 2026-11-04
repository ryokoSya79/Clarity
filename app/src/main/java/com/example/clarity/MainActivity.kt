package com.example.clarity

import android.graphics.Paint
import android.graphics.drawable.Drawable
import android.media.audiofx.AudioEffect
import android.os.Build
import android.os.Bundle
import android.transition.CircularPropagation
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.result.contract.ActivityResultContracts
import androidx.activity.viewModels
import androidx.collection.CircularArray
import androidx.compose.animation.AnimatedContentTransitionScope
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.draganddrop.dragAndDropSource
import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.CheckCircle
import androidx.compose.material.icons.filled.KeyboardArrowDown
import androidx.compose.material.icons.filled.KeyboardArrowUp
import androidx.compose.material.icons.outlined.RadioButtonUnchecked
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonColors
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardColors
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Checkbox
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FilterChip
import androidx.compose.material3.FilterChipDefaults
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconToggleButton
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.NavigationBarItemDefaults
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Slider
import androidx.compose.material3.SliderDefaults
import androidx.compose.material3.Switch
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.material3.TriStateCheckbox
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableFloatStateOf
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.composed
import androidx.compose.ui.draw.blur
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.clipToBounds
import androidx.compose.ui.draw.dropShadow
import androidx.compose.ui.draw.paint
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.draw.scale
import androidx.compose.ui.focus.focusModifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.input.pointer.motionEventSpy
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.modifier.modifierLocalConsumer
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.semantics.testTagsAsResourceId
import androidx.compose.ui.state.ToggleableState
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import androidx.datastore.preferences.core.edit
import androidx.lifecycle.ViewModel
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.media3.datasource.RawResourceDataSource
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navigation
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.example.clarity.ui.theme.BackgroundColor
import com.example.clarity.ui.theme.Background_TextField
import com.example.clarity.ui.theme.Black
import com.example.clarity.ui.theme.BorderStrokeButton
import com.example.clarity.ui.theme.ButtonColorDouble
import com.example.clarity.ui.theme.ButtonColorGender
import com.example.clarity.ui.theme.ButtonColorMain
import com.example.clarity.ui.theme.ClarityTheme
import com.example.clarity.ui.theme.ColorIndicaitonNavBar
import com.example.clarity.ui.theme.ColorNonPressNavBar
import com.example.clarity.ui.theme.ColorNoneSelectButton
import com.example.clarity.ui.theme.ColorPressNavBar
import com.example.clarity.ui.theme.ColorSelectButton
import com.example.clarity.ui.theme.Color_Title_Card_Track
import com.example.clarity.ui.theme.My_FieldText_color
import com.example.clarity.ui.theme.SelectButton_Q
import com.example.clarity.ui.theme.TextColor_Body
import com.example.clarity.ui.theme.White
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.launch
import kotlinx.coroutines.selects.select
import java.util.jar.Manifest
import kotlin.Unit
import kotlin.collections.set

class MainActivity : ComponentActivity() {

    private val mainViewModel: AuthViewModel by viewModels()


    override fun onCreate(savedInstanceState: Bundle?) {
        var splashScreen = installSplashScreen()
        super.onCreate(savedInstanceState)

        splashScreen.setKeepOnScreenCondition {
            mainViewModel.startComposable.value == null
        }

        enableEdgeToEdge()
        setContent {
            data class BottomItem(
                val route: String,
                val label: String,
                val icon: Int,
                val iconPress: Int
            )

            val startComposable by mainViewModel.startComposable.collectAsState()
            val isLoading_current by mainViewModel.isLoading_current.collectAsState()


            val bottomItems = listOf(
                BottomItem("zaebok", "Home", R.drawable.icon_home, R.drawable.icon_homle_press),
                BottomItem("discover", "Discover", R.drawable.icon_search_navigbar, R.drawable.icon_search_navigbar_press),
                BottomItem("settings", "Settings", R.drawable.icon_settings, R.drawable.icon_settings_press)
            )

            ClarityTheme {
                val navController = rememberNavController()
                val backStackEntry by navController.currentBackStackEntryAsState()
                val currentRoute = backStackEntry?.destination?.route

                Scaffold(
                    modifier = Modifier.semantics { testTagsAsResourceId = true },
                    bottomBar = {
                        if(currentRoute in bottomItems.map{it.route}){
                            NavigationBar(
                                containerColor = White,
                                contentColor = White,
                                tonalElevation = 0.dp
                            ) {
                                bottomItems.forEach { item ->
                                    val isSelected = currentRoute == item.route

                                    NavigationBarItem(
                                        selected = isSelected,
                                        modifier = Modifier.testTag("${item.route}_click"),
                                        onClick = {
                                            Log.i("TEST_TAGS", "${item.route}_click")
                                            navController.navigate(item.route){
                                                popUpTo(navController.graph.findStartDestination().id){
                                                    saveState = true
                                                }
                                                launchSingleTop = true
                                                restoreState = true
                                            }
                                        },
                                        icon = {
                                            Image(
                                                painter = painterResource(
                                                    id = if(isSelected) item.iconPress else item.icon,
                                                ),
                                                contentDescription = item.label,
                                                modifier = Modifier.size(24.dp)
                                            )
                                        },
                                        label = {
                                            if(isSelected){
                                                Text(
                                                    item.label,
                                                    color = ColorPressNavBar,
                                                    fontWeight = FontWeight.Bold
                                                )
                                            } else {
                                                Text(
                                                    item.label,
                                                    color = ColorNonPressNavBar,
                                                    fontWeight = FontWeight.Normal)
                                            }
                                        },
                                        colors = NavigationBarItemDefaults.colors(
                                            indicatorColor = ColorIndicaitonNavBar
                                        )
                                    )
                                }
                            }
                        }
                    },

                    content = { innerPadding: PaddingValues ->

                        if (startComposable == null){
                            Box(Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                                CircularProgressIndicator()
                            }
                        }else{
                            NavHost(
                                navController = navController,
                                startDestination = startComposable!!,
                                modifier = Modifier
                                    .padding(innerPadding)
                                    .semantics { testTagsAsResourceId = true },
                            ){

                                composable("one_promo") {
                                    One_Promo_Scene(
                                        onNavigateToTwoPromo = {navController.navigate("two_promo")},
                                        onNavigateToSignUp = {navController.navigate("signup_or_login")}
                                    )
                                }

                                composable("two_promo"){
                                    Two_Promo_Scene(
                                        onNavigateToThreePromo = {navController.navigate("three_promo")},
                                        onNavigateToSignUp = {navController.navigate("signup_or_login")}
                                    )
                                }

                                composable("three_promo"){
                                    Three_Promo_Scene(
                                        onNavigateToFourPromo = {navController.navigate("four_promo")},
                                        onNavigateToSignUp = {navController.navigate("signup_or_login")}
                                    )
                                }

                                composable("four_promo"){
                                    Four_Promo_Scene(
                                        onNavigateToTermConditions = {navController.navigate("signup_or_login")},
                                        onNavigateToSignUp = {navController.navigate("signup_or_login")}
                                    )
                                }

                                composable(
                                    "signup_or_login",
                                    enterTransition = { fadeIn(animationSpec = tween(400))+ slideIntoContainer(
                                        towards = AnimatedContentTransitionScope.SlideDirection.Left,
                                        animationSpec = tween(400)
                                    ) }
                                ){
                                    SignUp_or_LogIn(
                                        onNavigateToQ_1 = {navController.navigate("onboarding_q1")},
                                        onNavigateToZaebok = {navController.navigate("zaebok")},
                                        onNavigateToSignUpScreen = {navController.navigate("signup")},
                                        onNavigateToSignIn = {navController.navigate("signin")}
                                    )
                                }

                                composable("signin"){
                                    SignIn(
                                        onNavigateToBackSorL = {navController.popBackStack()},
                                        onNavigateToQ_1 = {navController.navigate("onboarding_q1")},
                                        onNavigateToZaebok = {navController.navigate("zaebok")},
                                        onNavigateToForgotPassword = {navController.navigate("forgot_password")},
                                        onNavigateToSignUp = {navController.navigate("signup")}
                                    )
                                }



                                navigation(startDestination = "forgot_password", route = "forgot_flow"){
                                    composable("forgot_password"){ backStateEntry ->

                                        val parentEntry = remember(backStateEntry){
                                            navController.getBackStackEntry("forgot_flow")
                                        }

                                        val sharedViewModel: AuthViewModel = viewModel(parentEntry)

                                        ForgotPassword(
                                            modelView = sharedViewModel,
                                            onNavigateToBack = {navController.navigate("signin")},
                                            onNavigateToEnterNewPassword = { navController.navigate("enter_new_password") }
                                        )
                                    }

                                    composable("enter_new_password"){ backStateEntry ->

                                        val parentEntry = remember(backStateEntry){
                                            navController.getBackStackEntry("forgot_flow")
                                        }

                                        val sharedViewModel: AuthViewModel = viewModel(parentEntry)

                                        EnterNewPassword(
                                            modelView = sharedViewModel,
                                            onNavigateToBackSorL = {navController.navigate("signin")},
                                            onNavigateToSuccesMessage = {navController.navigate("succes_message")}
                                        )
                                    }
                                }

                                composable(
                                    "succes_message",
                                    enterTransition = { fadeIn(animationSpec = tween(400))+ slideIntoContainer(
                                        towards = AnimatedContentTransitionScope.SlideDirection.Left,
                                        animationSpec = tween(400)
                                    ) }){
                                    SuccesMessage(
                                        onNavigateToZaebok = {navController.navigate("zaebok")}
                                    )
                                }

                                composable("signup"){
                                    SignUp(
                                        onNavigateToBackSorL = {navController.popBackStack()},
                                        onNavigateToCreateAcc = {navController.navigate("onboarding_q1")},
                                        onNavigateToQ_1 = {navController.navigate("onboarding_q1")},
                                        onNavigateToSignIn = {navController.navigate("signin")})

                                }

                                composable(
                                    "zaebok",
                                ){
                                    Zaebok(
                                        onNavigateToOpenGenre = { genreID ->
                                            navController.navigate("card_genre_open/$genreID")
                                        }
                                    )
                                }

                                var mainImageTrack: Int = R.drawable.track_lofi_22
                                var titleTrack: String = "Title"
                                var mainTrack: Int = R.raw.track_lofi_22

                                composable(
                                    "card_genre_open/1",
                                    enterTransition = { fadeIn(animationSpec = tween(400))+ slideIntoContainer(
                                        towards = AnimatedContentTransitionScope.SlideDirection.Left,
                                        animationSpec = tween(400)
                                    ) },

                                    popExitTransition = {
                                        fadeOut(tween(400))+ slideOutOfContainer(
                                            AnimatedContentTransitionScope.SlideDirection.Right,
                                            tween(400)
                                        )
                                    }

//                                enterTransition = {
//                                    slideIntoContainer(
//                                        AnimatedContentTransitionScope.SlideDirection.Left, tween(400)
//                                    )
//                                },
//                                popExitTransition = {
//                                    slideOutOfContainer(
//                                        AnimatedContentTransitionScope.SlideDirection.Right, tween(400)
//                                    )
//                                }
                                    // Это пока не нужно, но анимация пригодиться для нижнего Navbar'а
                                ){
                                    OpenGenreDeepSleep(
                                        onNavigateToBack = {navController.popBackStack()},
                                        onNavigateToOpenTrack = {current ->
                                            mainImageTrack = current.image
                                            titleTrack = current.title
                                            mainTrack = current.track

                                            navController.navigate("open_track")
                                        }
                                    )
                                }

                                composable(
                                    "card_genre_open/2",
                                    enterTransition = { fadeIn(animationSpec = tween(400))+ slideIntoContainer(
                                        towards = AnimatedContentTransitionScope.SlideDirection.Left,
                                        animationSpec = tween(400)
                                    ) },

                                    popExitTransition = {
                                        fadeOut(tween(400))+ slideOutOfContainer(
                                            AnimatedContentTransitionScope.SlideDirection.Right,
                                            tween(400)
                                        )
                                    }
                                ){
                                    OpenGenreJazz(
                                        onNavigateToBack = {navController.popBackStack()},
                                        onNavigateToOpenTrack = { current ->
                                            mainImageTrack = current.image
                                            titleTrack = current.title
                                            mainTrack = current.track
                                            navController.navigate("open_track")
                                        }
                                    )
                                }

                                composable(
                                    "card_genre_open/3",
                                    enterTransition = { fadeIn(animationSpec = tween(400))+ slideIntoContainer(
                                        towards = AnimatedContentTransitionScope.SlideDirection.Left,
                                        animationSpec = tween(400)
                                    ) },

                                    popExitTransition = {
                                        fadeOut(tween(400))+ slideOutOfContainer(
                                            AnimatedContentTransitionScope.SlideDirection.Right,
                                            tween(400)
                                        )
                                    }
                                ){
                                    OpenGenreLoFi(
                                        onNavigateToBack = {navController.popBackStack()},
                                        onNavigateToOpenTrack = {current ->
                                            mainImageTrack = current.image
                                            titleTrack = current.title
                                            mainTrack = current.track
                                            navController.navigate("open_track")
                                        }
                                    )
                                }

                                composable(
                                    "card_genre_open/4",
                                    enterTransition = { fadeIn(animationSpec = tween(400))+ slideIntoContainer(
                                        towards = AnimatedContentTransitionScope.SlideDirection.Left,
                                        animationSpec = tween(400)
                                    ) },

                                    popExitTransition = {
                                        fadeOut(tween(400))+ slideOutOfContainer(
                                            AnimatedContentTransitionScope.SlideDirection.Right,
                                            tween(400)
                                        )
                                    }
                                ){
                                    OpenGenreNature(
                                        onNavigateToBack = {navController.popBackStack()},
                                        onNavigateToOpenTrack = {current ->
                                            mainImageTrack = current.image
                                            titleTrack = current.title
                                            mainTrack = current.track
                                            navController.navigate("open_track")
                                        }
                                    )
                                }

                                composable("onboarding_q1"){
                                    OnBoardingQ_1(
                                        onNavigateToQ_2 = {navController.navigate("onboarding_q2")}
                                    )
                                }

                                composable("onboarding_q2"){
                                    OnBoardingQ_2(
                                        onNavigateToBack = {navController.navigate("onboarding_q1")},
                                        onNavigateToQ_3 = {navController.navigate("onboarding_q3")}
                                    )
                                }

                                composable("onboarding_q3"){
                                    OnBoardingQ_3(
                                        onNavigateToBack = {navController.navigate("onboarding_q2")},
                                        onNavigateToQ_4 = {navController.navigate("onboarding_q4")}
                                    )
                                }

                                composable("onboarding_q4"){
                                    OnBoardingQ_4(
                                        onNavigateToBack = {navController.navigate("onboarding_q3")},
                                        onNavigateToZaebok = {navController.navigate("zaebok")}
                                    )
                                }

                                composable("discover"){
                                    Discover(
                                        onNavigateToOpenFullTrack = { current ->
                                            mainImageTrack = current.image
                                            titleTrack = current.title
                                            mainTrack = current.track
                                            navController.navigate("open_track")
                                        }
                                    )
                                }

                                composable("settings"){
                                    Settings(
                                        onNavigateToMyAccount = {navController.navigate("my_account")},
                                        onNavigateToSignUpOrLogin = {navController.navigate("signup_or_login"){
                                            popUpTo(navController.graph.id){inclusive = true}
                                        } },
                                        onNavigateToTermsAndConditions = {navController.navigate("term_and_conditions")},
                                        onNavigateToPrivacyPolicy = {navController.navigate("privacy_policy")}
                                    )
                                }

                                composable("my_account"){
                                    MyAccount(
                                        onNavigateToBack = {navController.popBackStack()}
                                    )
                                }

                                composable("term_and_conditions"){
                                    TermAndConditions(
                                        onNavigateToBack = {navController.popBackStack()}
                                    )
                                }

                                composable("privacy_policy"){
                                    PrivacyPolicy(
                                        onNavigateToBack = {navController.popBackStack()}
                                    )
                                }

                                composable("open_track"){
                                    OpenTrack(
                                        mainImageTrack = mainImageTrack,
                                        mainTitleTrack = titleTrack,
                                        mainTrack = mainTrack,
                                        onNavigateToBack = {navController.popBackStack()})
                                }
                            }
                        }


                })
            }
        }
    }
}

@Composable
fun One_Promo_Scene(
    onNavigateToTwoPromo: () -> Unit,
    onNavigateToSignUp: () -> Unit) {
    Column(
        modifier = Modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Spacer(modifier = Modifier.height(24.dp))

        Image(
            painter = painterResource(R.drawable.one_promo_screen_image),
            contentDescription = "one_promo_screen_image",
            modifier = Modifier.size(width = 334.dp, height = 458.dp),

            )

        Spacer(modifier = Modifier.height(28.dp))

        Text("STRESS LESS.", fontWeight = FontWeight.Bold, fontSize = 18.sp)

        Spacer(modifier = Modifier.height(21.dp))

        Text(
            "Make mindfulness a daily habit\n and be kind to your mind.",
            fontWeight = FontWeight.Normal,
            fontSize = 14.sp,
            textAlign = TextAlign.Center
        )

        Spacer(modifier = Modifier.height(16.dp))

        Image(
            painter = painterResource(R.drawable.one_promo_shape),
            contentDescription = "four_promo_shape"
        )

        Spacer(modifier = Modifier.height(28.dp))

        Button(
            onClick = onNavigateToTwoPromo,
            shape = RoundedCornerShape(12.dp),
            modifier = Modifier.size(width = 334.dp, height = 60.dp),
            colors = ButtonDefaults.buttonColors(containerColor = ButtonColorMain)
            ) {
                Text("Next", fontSize = 16.sp, fontWeight = FontWeight.Bold)
        }

        Spacer(modifier = Modifier.height(15.dp))

        Text(
            "Sign in",
            modifier = Modifier.clickable(
                indication = null,
                interactionSource = remember{ MutableInteractionSource()}
            ){
                onNavigateToSignUp()
            },
            textDecoration = TextDecoration.Underline,
            fontWeight = FontWeight.Bold,
            fontSize = 15.sp
        )
    }

}

@Composable
fun Two_Promo_Scene(onNavigateToThreePromo: () -> Unit, onNavigateToSignUp: () -> Unit) {
    Column(
        modifier = Modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Spacer(modifier = Modifier.height(24.dp))

        Image(
            painter = painterResource(R.drawable.two_promo_screen_image),
            contentDescription = "two_promo_screen_image",
            modifier = Modifier.size(width = 334.dp, height = 458.dp),

            )

        Spacer(modifier = Modifier.height(28.dp))

        Text("RELAX MORE.", fontWeight = FontWeight.Bold, fontSize = 18.sp)

        Spacer(modifier = Modifier.height(21.dp))

        Text(
            "Unwind and find serenity in a \n guided meditation sessions",
            fontWeight = FontWeight.Normal,
            fontSize = 14.sp,
            textAlign = TextAlign.Center
        )

        Spacer(modifier = Modifier.height(16.dp))

        Image(
            painter = painterResource(R.drawable.two_promo_shape),
            contentDescription = "four_promo_shape"
        )

        Spacer(modifier = Modifier.height(28.dp))

        Button(
            onClick = onNavigateToThreePromo,
            shape = RoundedCornerShape(12.dp),
            modifier = Modifier.size(width = 334.dp, height = 60.dp),
            colors = ButtonDefaults.buttonColors(containerColor = ButtonColorMain)
        ) {
            Text("Next", fontSize = 16.sp, fontWeight = FontWeight.Bold)
        }

        Spacer(modifier = Modifier.height(15.dp))

        Text(
            "Sign in",
            modifier = Modifier.clickable(
                indication = null,
                interactionSource = remember{ MutableInteractionSource()}
            ){
                onNavigateToSignUp()
            },
            textDecoration = TextDecoration.Underline,
            fontWeight = FontWeight.Bold,
            fontSize = 15.sp
        )
    }

}

@Composable
fun Three_Promo_Scene(
    onNavigateToFourPromo: () -> Unit,
    onNavigateToSignUp: () -> Unit) {
    Column(
        modifier = Modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Spacer(modifier = Modifier.height(24.dp))

        Image(
            painter = painterResource(R.drawable.three_promo_screen_image),
            contentDescription = "three_promo_screen_image",
            modifier = Modifier.size(width = 334.dp, height = 458.dp),

            )

        Spacer(modifier = Modifier.height(28.dp))

        Text("SLEEP LONGER.", fontWeight = FontWeight.Bold, fontSize = 18.sp)

        Spacer(modifier = Modifier.height(21.dp))

        Text(
            "Calm racing mind and prepare \n your body for deep sleep.",
            fontWeight = FontWeight.Normal,
            fontSize = 14.sp,
            textAlign = TextAlign.Center
        )

        Spacer(modifier = Modifier.height(16.dp))

        Image(
            painter = painterResource(R.drawable.three_promo_shape),
            contentDescription = "four_promo_shape"
        )

        Spacer(modifier = Modifier.height(28.dp))

        Button(
            onClick = onNavigateToFourPromo,
            shape = RoundedCornerShape(12.dp),
            modifier = Modifier.size(width = 334.dp, height = 60.dp),
            colors = ButtonDefaults.buttonColors(containerColor = ButtonColorMain)
        ) {
            Text("Next", fontSize = 16.sp, fontWeight = FontWeight.Bold)
        }

        Spacer(modifier = Modifier.height(15.dp))

        Text(
            "Sign in",
            modifier = Modifier.clickable(
                indication = null,
                interactionSource = remember{ MutableInteractionSource()}
            ){
                onNavigateToSignUp()
            },
            textDecoration = TextDecoration.Underline,
            fontWeight = FontWeight.Bold,
            fontSize = 15.sp
        )
    }

}

@Composable
fun Four_Promo_Scene(
    onNavigateToTermConditions: () -> Unit,
    onNavigateToSignUp: () -> Unit) {
    Column(
        modifier = Modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Spacer(modifier = Modifier.height(24.dp))

        Image(
            painter = painterResource(R.drawable.four_promo_screen_image),
            contentDescription = "four_promo_screen_image",
            modifier = Modifier.size(width = 334.dp, height = 458.dp),

            )

        Spacer(modifier = Modifier.height(28.dp))

        Text("LIVE BETTER.", fontWeight = FontWeight.Bold, fontSize = 18.sp)

        Spacer(modifier = Modifier.height(21.dp))

        Text(
            "Invest in personal sense of inner \n peace and balance.",
            fontWeight = FontWeight.Normal,
            fontSize = 14.sp,
            textAlign = TextAlign.Center
        )
        Spacer(modifier = Modifier.height(16.dp))

        Image(
            painter = painterResource(R.drawable.four_promo_shape),
            contentDescription = "four_promo_shape"
        )

        Spacer(modifier = Modifier.height(28.dp))

        Button(
            onClick = onNavigateToTermConditions,
            shape = RoundedCornerShape(12.dp),
            modifier = Modifier.size(width = 334.dp, height = 60.dp),
            colors = ButtonDefaults.buttonColors(containerColor = ButtonColorMain)
        ) {
            Text("Let's begin", fontSize = 16.sp, fontWeight = FontWeight.Bold)
        }

        Spacer(modifier = Modifier.height(15.dp))

        Text(
            "Sign in",
            modifier = Modifier.clickable(
                indication = null,
                interactionSource = remember{ MutableInteractionSource()}
            ){
                onNavigateToSignUp()
            },
            textDecoration = TextDecoration.Underline,
            fontWeight = FontWeight.Bold,
            fontSize = 15.sp
        )
    }

}

@Composable
fun SignUp_or_LogIn(
    onNavigateToSignUpScreen: () -> Unit,
    onNavigateToZaebok: () -> Unit,
    onNavigateToQ_1: () -> Unit,
    onNavigateToSignIn: () -> Unit,
    modelView: AuthViewModel = viewModel()
){
    val context = LocalContext.current
    val repository = remember { AuthRepository(context) }

    val startComposable = modelView.startComposable.collectAsState()
    val isLoading by modelView.isLoading.collectAsState()
    val continueAuth by modelView.continueAuth.collectAsState()
    val isAuthSucces by modelView.isAuthSucces.collectAsState()



    LaunchedEffect(continueAuth, isAuthSucces) {
        Log.i("LOGOPOCO", "${continueAuth} Launched")

        if (continueAuth && isAuthSucces){
            onNavigateToQ_1()
        }else if(!continueAuth && isAuthSucces){
            onNavigateToZaebok()
        }

    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(start = 20.5.dp, end = 20.5.dp, top = 12.dp),

        horizontalAlignment = Alignment.CenterHorizontally
    ){
        Image(
            painter = painterResource(R.drawable.log_in_image),
            contentDescription = "Image clarity",
            modifier = Modifier.size(width = 334.dp, height = 458.dp)
        )

        Spacer(modifier = Modifier.height(20.dp))

        Button(
            onClick = {},
            colors = ButtonDefaults.buttonColors(ButtonColorDouble),
            modifier = Modifier.size(width = 334.dp, height = 56.dp),
            shape = RoundedCornerShape(12.dp),
            border = BorderStroke(1.dp, BorderStrokeButton)
        ){
            Row(verticalAlignment = Alignment.CenterVertically) {
                Image(
                    painter = painterResource(R.drawable.icon_apple),
                    contentDescription = "Continue with Apple"
                )

                Spacer(modifier = Modifier.width(19.25.dp))

                Text(
                    "Continue with Apple",
                    color = Color.Black
                )
            }

        }

        Spacer(modifier = Modifier.height(12.dp))

        if(isLoading){
            CircularProgressIndicator()
        }else{
            Button(
                onClick = {
                    modelView.signInWithGoogle(repository)
//                    modelView.getNameAndEmailUser(AuthRepository(context))
                },
                colors = ButtonDefaults.buttonColors(ButtonColorDouble),
                modifier = Modifier.size(width = 334.dp, height = 56.dp),
                shape = RoundedCornerShape(12.dp),
                border = BorderStroke(1.dp, BorderStrokeButton)
            ){
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Image(
                        painter = painterResource(R.drawable.icon_google),
                        contentDescription = "Continue with Google"
                    )

                    Spacer(modifier = Modifier.width(19.25.dp))

                    Text(
                        "Continue with Google",
                        color = Color.Black
                    )
                }


            }
        }


        Spacer(modifier = Modifier.height(12.dp))

        Button(
            onClick = {
                onNavigateToSignIn()
            },
            colors = ButtonDefaults.buttonColors(ButtonColorMain),
            modifier = Modifier.size(width = 334.dp, height = 56.dp),
            shape = RoundedCornerShape(12.dp)
        ){
            Row(verticalAlignment = Alignment.CenterVertically) {
                Image(
                    painter = painterResource(R.drawable.icon_email),
                    contentDescription = "Continue with Email"
                )

                Spacer(modifier = Modifier.width(19.25.dp))

                Text(
                    "Continue with Email",
                    color = Color.White
                )
            }

        }

        Spacer(modifier = Modifier.height(26.dp))

        Row{
            Text(
                "Not a member? ",
                color = Color.Black
            )
            Text(
                "Sign Up",
                modifier = Modifier.clickable(
                    indication = null,
                    interactionSource = remember{ MutableInteractionSource()}
                ){
                    onNavigateToSignUpScreen()
                },
                color = ButtonColorMain,
                textDecoration = TextDecoration.Underline,
                fontWeight = FontWeight.Bold
            )
        }
    }

}

@Composable
fun SignUp(
    viewModel: AuthViewModel = viewModel(),
    onNavigateToBackSorL: () -> Unit,
    onNavigateToCreateAcc: () -> Unit,
    onNavigateToQ_1: () -> Unit,
    onNavigateToSignIn: () -> Unit
){
    val context = LocalContext.current
    val repository = remember { AuthRepository(context) }

    val isLoading_email by viewModel.isLoading_email.collectAsState()
    val isLoading by viewModel.isLoading.collectAsState()
    val isAuthSucces by viewModel.isAuthSucces.collectAsState()
    val isAuthEmail by viewModel.isAuthEmail.collectAsState()

    LaunchedEffect(isAuthSucces) {
        if (isAuthSucces){
            onNavigateToQ_1()
        }
    }

    LaunchedEffect(isAuthEmail) {
        if (isAuthEmail){
            onNavigateToQ_1()
        }
    }

    Column(
        modifier = Modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally
    ){
        Row(
            modifier = Modifier.padding(start = 18.dp, top = 16.dp)
        ){
            Image(
                painter = painterResource(R.drawable.back_button),
                contentDescription = "Button back",
                modifier = Modifier
                    .size(width = 24.dp, height = 24.dp)
                    .clickable(
                        indication = null,
                        interactionSource = remember { MutableInteractionSource() })
                    {
                        onNavigateToBackSorL()
                    }
            )
            
            Text(
                "Sign Up",
                textAlign = TextAlign.Center,
                modifier = Modifier.fillMaxWidth(),
                fontWeight = FontWeight.Bold,
                fontSize = 18.sp
            )
        }

        Spacer(modifier = Modifier.height(45.dp))

        Button(
            onClick = {},
            colors = ButtonDefaults.buttonColors(ButtonColorDouble),
            modifier = Modifier.size(width = 334.dp, height = 56.dp),
            shape = RoundedCornerShape(12.dp),
            border = BorderStroke(1.dp, BorderStrokeButton)
        ){
            Row(verticalAlignment = Alignment.CenterVertically) {
                Image(
                    painter = painterResource(R.drawable.icon_apple),
                    contentDescription = "Continue with Apple"
                )

                Spacer(modifier = Modifier.width(19.25.dp))

                Text(
                    "Continue with Apple",
                    color = Color.Black
                )
            }

        }

        Spacer(modifier = Modifier.height(12.dp))

        if (isLoading){
            CircularProgressIndicator()
        }else{
            Button(
                onClick = {
                    viewModel.signInWithGoogle(repository)
                },
                colors = ButtonDefaults.buttonColors(ButtonColorDouble),
                modifier = Modifier.size(width = 334.dp, height = 56.dp),
                shape = RoundedCornerShape(12.dp),
                border = BorderStroke(1.dp, BorderStrokeButton)
            ){
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Image(
                        painter = painterResource(R.drawable.icon_google),
                        contentDescription = "Continue with Google"
                    )

                    Spacer(modifier = Modifier.width(19.25.dp))

                    Text(
                        "Continue with Google",
                        color = Color.Black
                    )
                }

            }
        }



        Spacer(modifier = Modifier.height(53.dp))

        Row(verticalAlignment = Alignment.CenterVertically){
            Image(
                painter = painterResource(R.drawable.line_signup),
                contentDescription = ""
            )
            Text(
                "Or sign up with email",
                color = My_FieldText_color
            )
            Image(
                painter = painterResource(R.drawable.line_signup_2),
                contentDescription = ""
            )
        }

        Spacer(modifier = Modifier.height(54.dp))

        var textState_Name by remember {mutableStateOf("")}

        TextField(
            value = textState_Name,
            onValueChange = {newValue ->
                textState_Name = newValue
                viewModel.updateUserData("name", newValue) },
            label = {Text("Name")},
            shape = RoundedCornerShape(12.dp),
            singleLine = true,
            colors = TextFieldDefaults.colors(
                focusedContainerColor = Background_TextField,
                unfocusedContainerColor = Background_TextField,
                unfocusedTextColor = Color.Black,
                focusedTextColor = Color.Black,
                focusedLabelColor = My_FieldText_color,
                unfocusedLabelColor = My_FieldText_color,
                focusedIndicatorColor = Color.Transparent,
                unfocusedIndicatorColor = Color.Transparent),
            modifier = Modifier.size(width = 334.dp, height = 56.dp)
        )

        Spacer(modifier = Modifier.height(12.dp))

        var textState_Email by remember {mutableStateOf("")}

        TextField(
            value = textState_Email,
            onValueChange = {newValue ->
                textState_Email = newValue
                viewModel.updateUserData("email", newValue) },
            label = {Text("Email")},
            shape = RoundedCornerShape(12.dp),
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Email),
            singleLine = true,
            colors = TextFieldDefaults.colors(
                focusedContainerColor = Background_TextField,
                unfocusedContainerColor = Background_TextField,
                unfocusedTextColor = Color.Black,
                focusedTextColor = Color.Black,
                focusedLabelColor = My_FieldText_color,
                unfocusedLabelColor = My_FieldText_color,
                focusedIndicatorColor = Color.Transparent,
                unfocusedIndicatorColor = Color.Transparent),
            modifier = Modifier.size(width = 334.dp, height = 56.dp)

        )

        Spacer(modifier = Modifier.height(12.dp))

        var textState_Password by remember {mutableStateOf("")}

        TextField(
            value = textState_Password,
            onValueChange = {newValue ->
                textState_Password = newValue
                viewModel.updateUserData("password", newValue) },
            label = {Text("Password")},
            shape = RoundedCornerShape(12.dp),
            visualTransformation = PasswordVisualTransformation(),
            singleLine = true,
            colors = TextFieldDefaults.colors(
                focusedContainerColor = Background_TextField,
                unfocusedContainerColor = Background_TextField,
                unfocusedTextColor = Color.Black,
                focusedTextColor = Color.Black,
                focusedLabelColor = My_FieldText_color,
                unfocusedLabelColor = My_FieldText_color,
                focusedIndicatorColor = Color.Transparent,
                unfocusedIndicatorColor = Color.Transparent),
            modifier = Modifier.size(width = 334.dp, height = 56.dp)
        )

        Spacer(modifier = Modifier.height(12.dp))

        if(isLoading_email){
            CircularProgressIndicator()
        }else{
            Button(
                onClick = {
//                onNavigateToCreateAcc()
                    viewModel.signInWithEmail(repository)
                },
                colors = ButtonDefaults.buttonColors(ButtonColorMain),
                modifier = Modifier.size(width = 334.dp, height = 56.dp),
                shape = RoundedCornerShape(12.dp)
            ){
                Text(
                    "Create account",
                    color = Color.White,
                    fontWeight = FontWeight.Bold
                )
            }
        }



        Spacer(modifier = Modifier.height(52.dp))

        Row{
            Text(
                "Already have an account? ",
                color = Color.Black
            )
            Text(
                "Sign In",
                modifier = Modifier.clickable(
                    indication = null,
                    interactionSource = remember{ MutableInteractionSource()}
                ){
//                    onNavigateToSignUpScreen()
                    onNavigateToSignIn()
                },
                color = ButtonColorMain,
                textDecoration = TextDecoration.Underline,
                fontWeight = FontWeight.Bold
            )
        }
    }

}

@Composable()
fun SignIn(
    onNavigateToBackSorL: () -> Unit,
    modelView: AuthViewModel = viewModel(),
    onNavigateToQ_1: () -> Unit,
    onNavigateToZaebok: () -> Unit,
    onNavigateToForgotPassword: () -> Unit,
    onNavigateToSignUp: () -> Unit
){

    val scrollState = rememberScrollState()

    val context = LocalContext.current
    val repository = remember { AuthRepository(context) }

    val isLoading by modelView.isLoading.collectAsState()
    val continueAuth by modelView.continueAuth.collectAsState()
    val isAuthSucces by modelView.isAuthSucces.collectAsState()
    val isLogin by modelView.isLogin.collectAsState()
    val isLoading_login by modelView.isLoading_login.collectAsState()

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(start = 20.dp, top = 16.dp, end = 20.dp)
            .verticalScroll(scrollState),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {

        LaunchedEffect(continueAuth, isAuthSucces) {
            Log.i("LOGOPOCO", "${continueAuth} Launched")

            if (continueAuth && isAuthSucces){
                onNavigateToQ_1()
            }else if(!continueAuth && isAuthSucces){
                onNavigateToZaebok()
            }

        }

        LaunchedEffect(isLogin) {
            if(isLogin){
                onNavigateToZaebok()
            }
        }

        Image(
            painter = painterResource(R.drawable.back_button),
            contentDescription = "Button Back",
            modifier = Modifier
                .size(width = 24.dp, height = 24.dp)
                .align(Alignment.Start)
                .clickable(
                    indication = null,
                    interactionSource = remember { MutableInteractionSource() })
                {
                    onNavigateToBackSorL()
                }
        )


        Spacer(modifier = Modifier.height(45.dp))
        Text(
            "Sign In",
            fontSize = 24.sp,
            fontWeight = FontWeight.Bold,
            modifier = Modifier
                .fillMaxWidth()
                .padding(start = 10.dp),
            textAlign = TextAlign.Start
        )

        Spacer(modifier = Modifier.height(20.dp))

        Text(
            "Use the same method that you created your\naccount with.",
            fontSize = 14.sp,
            fontWeight = FontWeight.Normal,
            color = TextColor_Body,
            modifier = Modifier
                .fillMaxWidth()
                .padding(start = 10.dp),
            textAlign = TextAlign.Start
        )

        Spacer(modifier = Modifier.height(30.dp))

        Button(
            onClick = {},
            colors = ButtonDefaults.buttonColors(ButtonColorDouble),
            modifier = Modifier.size(width = 334.dp, height = 56.dp),
            shape = RoundedCornerShape(12.dp),
            border = BorderStroke(1.dp, BorderStrokeButton)
        ){
            Row(verticalAlignment = Alignment.CenterVertically) {
                Image(
                    painter = painterResource(R.drawable.icon_apple),
                    contentDescription = "Continue with Apple"
                )

                Spacer(modifier = Modifier.width(19.25.dp))

                Text(
                    "Continue with Apple",
                    color = Color.Black
                )
            }

        }

        Spacer(modifier = Modifier.height(12.dp))
        if(isLoading){
            CircularProgressIndicator()
        }else{
            Button(
                    onClick = {
                        modelView.signInWithGoogle(repository)
                    },
                colors = ButtonDefaults.buttonColors(ButtonColorDouble),
                modifier = Modifier.size(width = 334.dp, height = 56.dp),
                shape = RoundedCornerShape(12.dp),
                border = BorderStroke(1.dp, BorderStrokeButton)
            ){
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Image(
                        painter = painterResource(R.drawable.icon_google),
                        contentDescription = "Continue with Google"
                    )

                    Spacer(modifier = Modifier.width(19.25.dp))

                    Text(
                        "Continue with Google",
                        color = Color.Black
                    )
                }

            }
        }


        Spacer(modifier = Modifier.height(53.dp))

        Row(verticalAlignment = Alignment.CenterVertically){
            Image(
                painter = painterResource(R.drawable.line_signup),
                contentDescription = ""
            )
            Text(
                "Or sign up with email",
                color = My_FieldText_color
            )
            Image(
                painter = painterResource(R.drawable.line_signup_2),
                contentDescription = ""
            )
        }

        Spacer(modifier = with(Modifier) { height(54.dp) })


        var textState_Email by remember {mutableStateOf("")}

        TextField(
            value = textState_Email,
            onValueChange = {newValue ->
                textState_Email = newValue

                modelView.updateUserName(newValue)},
            label = {Text("Email")},
            shape = RoundedCornerShape(12.dp),
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Email),
            singleLine = true,
            colors = TextFieldDefaults.colors(
                focusedContainerColor = Background_TextField,
                unfocusedContainerColor = Background_TextField,
                unfocusedTextColor = Color.Black,
                focusedTextColor = Color.Black,
                focusedLabelColor = My_FieldText_color,
                unfocusedLabelColor = My_FieldText_color,
                focusedIndicatorColor = Color.Transparent,
                unfocusedIndicatorColor = Color.Transparent),
            modifier = Modifier.size(width = 334.dp, height = 56.dp)

        )

        Spacer(modifier = Modifier.height(12.dp))

        var textState_Password by remember {mutableStateOf("")}

        TextField(
            value = textState_Password,
            onValueChange = {newValue ->
                textState_Password = newValue
                modelView.updateUserPassword(newValue)},
            label = {Text("Password")},
            shape = RoundedCornerShape(12.dp),
            visualTransformation = PasswordVisualTransformation(),
            singleLine = true,
            colors = TextFieldDefaults.colors(
                focusedContainerColor = Background_TextField,
                unfocusedContainerColor = Background_TextField,
                unfocusedTextColor = Color.Black,
                focusedTextColor = Color.Black,
                focusedLabelColor = My_FieldText_color,
                unfocusedLabelColor = My_FieldText_color,
                focusedIndicatorColor = Color.Transparent,
                unfocusedIndicatorColor = Color.Transparent),
            modifier = Modifier.size(width = 334.dp, height = 56.dp)
        )

        Spacer(modifier = Modifier.height(12.dp))

//        if(isLoading_login){
//            CircularProgressIndicator()
//        }else{
            Button(
                onClick = {
                    Log.e("LOGOPOCO", "АЛЛО! КНОПКА НАЖАЛАСЬ В COMPOSE!")
                    modelView.chekedLoginUser(repository)
                },
                colors = ButtonDefaults.buttonColors(ButtonColorMain),
                modifier = Modifier.size(width = 334.dp, height = 56.dp),
                shape = RoundedCornerShape(12.dp)
            ){
                Text(
                    "Sign In",
                    color = Color.White,
                    fontWeight = FontWeight.Bold
                )
            }
//        }

        Spacer(modifier = Modifier.height(17.dp))

        Text(
            "Forgot password?",
            fontSize = 14.sp,
            fontWeight = FontWeight.Bold,
            color = ButtonColorMain,
            modifier = Modifier.clickable (
                indication = null,
                interactionSource = remember{ MutableInteractionSource()}
            ){
                onNavigateToForgotPassword()
            }
        )

        Spacer(modifier = Modifier.height(20.dp))

        Row{
            Text(
                "Don’t have an account? ",
                fontSize = 14.sp
            )

            Text(
                "Sign Up",
                fontSize = 14.sp,
                color = ButtonColorMain,
                textDecoration = TextDecoration.Underline,
                fontWeight = FontWeight.Bold,
                modifier = Modifier.clickable(
                    indication = null,
                    interactionSource = remember{ MutableInteractionSource()}
                ){
                    onNavigateToSignUp()
                }
            )
        }

    }
}

@Composable
fun ForgotPassword(
    modelView: AuthViewModel = viewModel(),
    onNavigateToBack: () -> Unit,
    onNavigateToEnterNewPassword: () -> Unit
){
    val context = LocalContext.current
    val isLoading by modelView.isLoading_chekedEmail.collectAsState()
    val emailExamination by modelView.isEmailExamination.collectAsState()
    val repository = remember { AuthRepository(context) }


    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(start = 20.dp, top = 16.dp, end = 20.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {

        LaunchedEffect(emailExamination) {
            if(emailExamination){
                onNavigateToEnterNewPassword()
            }
        }

        Image(
            painter = painterResource(R.drawable.back_button),
            contentDescription = "Button Back",
            modifier = Modifier
                .size(width = 24.dp, height = 24.dp)
                .align(Alignment.Start)
                .clickable(
                    indication = null,
                    interactionSource = remember { MutableInteractionSource() })
                {
                    onNavigateToBack()
                }
        )


        Spacer(modifier = Modifier.height(45.dp))
        Text(
            "Forgotten Password",
            fontSize = 24.sp,
            fontWeight = FontWeight.Bold,
            modifier = Modifier
                .fillMaxWidth()
                .padding(start = 10.dp),
            textAlign = TextAlign.Start
        )

        Spacer(modifier = Modifier.height(20.dp))

        Text(
            "Please enter an email address that you used to\ncreate account with so we can send you an email to\nreset your password.",
            fontSize = 14.sp,
            fontWeight = FontWeight.Normal,
            color = TextColor_Body,
            modifier = Modifier
                .fillMaxWidth()
                .padding(start = 10.dp),
            textAlign = TextAlign.Start
        )

        Spacer(modifier = with(Modifier) { height(45.dp) })


        var textState_Email by remember {mutableStateOf("")}

        TextField(
            value = textState_Email,
            onValueChange = {newValue ->
                textState_Email = newValue
                modelView.updateUserEmail(newValue)},
            label = {Text("Email")},
            shape = RoundedCornerShape(12.dp),
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Email),
            singleLine = true,
            colors = TextFieldDefaults.colors(
                focusedContainerColor = Background_TextField,
                unfocusedContainerColor = Background_TextField,
                unfocusedTextColor = Color.Black,
                focusedTextColor = Color.Black,
                focusedLabelColor = My_FieldText_color,
                unfocusedLabelColor = My_FieldText_color,
                focusedIndicatorColor = Color.Transparent,
                unfocusedIndicatorColor = Color.Transparent),
            modifier = Modifier.size(width = 334.dp, height = 56.dp)

        )

        Spacer(modifier = Modifier.height(120.dp))

        if(isLoading){
            CircularProgressIndicator()
        }else{
            Button(
                onClick = {
                    modelView.chekedEmailUser(repository)
                },
                colors = ButtonDefaults.buttonColors(ButtonColorMain),
                modifier = Modifier.size(width = 334.dp, height = 56.dp),
                shape = RoundedCornerShape(12.dp)
            ){
                Text(
                    "Send email",
                    color = Color.White,
                    fontWeight = FontWeight.Bold
                )
            }
        }
    }
}

@Composable
fun EnterNewPassword(
    onNavigateToBackSorL: () -> Unit,
    onNavigateToSuccesMessage: () -> Unit,
    modelView: AuthViewModel = viewModel()
){
    val code_generation by modelView.code_generation.collectAsState()
    val context = LocalContext.current
    val repository = remember { AuthRepository(context) }
    val isEditPassword by modelView.isEditPassword.collectAsState()
    val isLoading_editPassword by modelView.isLoading_editPassword.collectAsState()

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(start = 20.dp, top = 16.dp, end = 20.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {

        val context = LocalContext.current
        val permissionLauncher = rememberLauncherForActivityResult(
            contract = ActivityResultContracts.RequestPermission(),
            onResult = { isGranted ->
                if(isGranted){
                    showPushNotification(context, code_generation)
                }
            }
        )

        LaunchedEffect(isEditPassword) {
            if(isEditPassword){
                onNavigateToSuccesMessage()
            }
        }

        Image(
            painter = painterResource(R.drawable.back_button),
            contentDescription = "Button Back",
            modifier = Modifier
                .size(width = 24.dp, height = 24.dp)
                .align(Alignment.Start)
                .clickable(
                    indication = null,
                    interactionSource = remember { MutableInteractionSource() })
                {
                    onNavigateToBackSorL()
                }
        )


        Spacer(modifier = Modifier.height(45.dp))
        Text(
            "Create New Password",
            fontSize = 24.sp,
            fontWeight = FontWeight.Bold,
            modifier = Modifier
                .fillMaxWidth()
                .padding(start = 10.dp),
            textAlign = TextAlign.Start
        )

        Spacer(modifier = Modifier.height(20.dp))

        Text(
            "Your new password must be different from your\npreviously used password.",
            fontSize = 14.sp,
            fontWeight = FontWeight.Normal,
            color = TextColor_Body,
            modifier = Modifier
                .fillMaxWidth()
                .padding(start = 10.dp),
            textAlign = TextAlign.Start
        )

        Spacer(modifier = Modifier.height(45.dp))

        var textState_NewPassword by remember {mutableStateOf("")}

        TextField(
            value = textState_NewPassword,
            onValueChange = {newValue ->
                textState_NewPassword = newValue
                modelView.updateUserCode(newValue)},
            label = {Text("Code")},
            shape = RoundedCornerShape(12.dp),
            visualTransformation = PasswordVisualTransformation(),
            singleLine = true,
            colors = TextFieldDefaults.colors(
                focusedContainerColor = Background_TextField,
                unfocusedContainerColor = Background_TextField,
                unfocusedTextColor = Color.Black,
                focusedTextColor = Color.Black,
                focusedLabelColor = My_FieldText_color,
                unfocusedLabelColor = My_FieldText_color,
                focusedIndicatorColor = Color.Transparent,
                unfocusedIndicatorColor = Color.Transparent),
            modifier = Modifier.size(width = 334.dp, height = 56.dp)
        )

        Spacer(modifier = Modifier.height(12.dp))

        var textState_NewPasswordReplace by remember {mutableStateOf("")}

        TextField(
            value = textState_NewPasswordReplace,
            onValueChange = {newValue ->
                textState_NewPasswordReplace = newValue
                modelView.updateUserNewPassword(newValue)},
            label = {Text("New Password")},
            shape = RoundedCornerShape(12.dp),
            visualTransformation = PasswordVisualTransformation(),
            singleLine = true,
            colors = TextFieldDefaults.colors(
                focusedContainerColor = Background_TextField,
                unfocusedContainerColor = Background_TextField,
                unfocusedTextColor = Color.Black,
                focusedTextColor = Color.Black,
                focusedLabelColor = My_FieldText_color,
                unfocusedLabelColor = My_FieldText_color,
                focusedIndicatorColor = Color.Transparent,
                unfocusedIndicatorColor = Color.Transparent),
            modifier = Modifier.size(width = 334.dp, height = 56.dp)
        )

        Spacer(modifier = Modifier.height(18.dp))

        Text(
            "Sent Code",
            fontSize = 14.sp,
            fontWeight = FontWeight.Bold,
            color = ButtonColorMain,
            textAlign = TextAlign.Start,
            modifier = Modifier
                .padding(start = 10.dp)
                .fillMaxWidth()
                .clickable(
                    indication = null,
                    interactionSource = remember { MutableInteractionSource() }
                ) {
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
                        permissionLauncher.launch(android.Manifest.permission.POST_NOTIFICATIONS)
                    } else {
                        showPushNotification(context, code_generation)
                    }
                }
        )

        if(isLoading_editPassword){
            CircularProgressIndicator()
        }else{
            Row(
                modifier = Modifier
                    .fillMaxHeight()
                    .padding(bottom = 48.dp)
            ){
                Button(
                    onClick = {
                        modelView.comparisonCode(repository)
                    },
                    colors = ButtonDefaults.buttonColors(ButtonColorMain),
                    modifier = Modifier
                        .size(width = 334.dp, height = 56.dp)
                        .align(Alignment.Bottom),
                    shape = RoundedCornerShape(12.dp)
                ){
                    Text(
                        "Reset Password",
                        color = Color.White,
                        fontWeight = FontWeight.Bold
                    )
                }
            }
        }



    }
}

@Composable
fun SuccesMessage(
    onNavigateToZaebok: () -> Unit
){
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier
            .fillMaxSize()
            .padding(top = 80.dp)
    ){
        Image(
            painter = painterResource(R.drawable.succes_message_image),
            contentDescription = ""
        )

        Spacer(modifier = Modifier.height(45.dp))

        Text(
            "Success!",
            fontWeight = FontWeight.Bold,
            fontSize = 24.sp
        )

        Spacer(modifier = Modifier.height(18.dp))

        Text(
            "Your password has been changed.\nFrom now on use your new password to log in.",
            fontWeight = FontWeight.Normal,
            fontSize = 14.sp,
            color = TextColor_Body,
            textAlign = TextAlign.Center
        )

        Row(
            modifier = Modifier
                .fillMaxHeight()
                .padding(bottom = 48.dp)
        ){
            Button(
                onClick = {onNavigateToZaebok()},
                colors = ButtonDefaults.buttonColors(ButtonColorMain),
                modifier = Modifier
                    .size(width = 334.dp, height = 56.dp)
                    .align(Alignment.Bottom),
                shape = RoundedCornerShape(12.dp)
            ){
                Text(
                    "OK",
                    color = Color.White,
                    fontWeight = FontWeight.Bold
                )
            }
        }
    }
}

@Composable
fun OnBoardingQ_1(
    onNavigateToQ_2: () -> Unit
){
    val options = listOf("Relax more", "Sleep Better", "Learn to meditate")
    var optionSelect by remember {mutableStateOf<String?>(null)}
    var enableButton = false


    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(start = 24.dp, end = 24.dp, top = 16.dp),
        verticalArrangement = Arrangement.spacedBy(12.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            "My goals",
            modifier = Modifier.fillMaxWidth(),
            textAlign = TextAlign.Center,
            fontWeight = FontWeight.Bold,
            fontSize = 18.sp
        )
        

        Spacer(modifier = Modifier.height(100.dp))


        Text(
            "What would you\nlike to achieve?",
            fontSize = 24.sp,
            fontWeight = FontWeight.Bold,
            color = SelectButton_Q
        )

        Spacer(modifier = Modifier.height(62.dp))


        options.forEach{ option ->

            val isSelect = (option == optionSelect)

            val backgroundColor = if (isSelect) SelectButton_Q else ButtonColorDouble
            val textColor = if (isSelect) BackgroundColor else SelectButton_Q

            if(optionSelect.toString() in options){
                enableButton = true
            }else{
                enableButton = false
            }

            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(62.dp)
                    .clip(RoundedCornerShape(12.dp))
                    .background(backgroundColor)
                    .border(
                        width = 2.dp,
                        color = BorderStrokeButton,
                        shape = RoundedCornerShape(12.dp)
                    )
                    .padding(start = 20.dp)
                    .clickable {
                        optionSelect = option
                    },
                contentAlignment = Alignment.CenterStart

            ){
                Text(
                    text = option,
                    color = textColor,
                    textAlign = TextAlign.Start,
                    fontWeight = FontWeight.Bold,
                    fontSize = 16.sp
                )
            }

        }
        

        Row(
            modifier = Modifier
                .fillMaxHeight()
                .padding(bottom = 48.dp)
        ){
            Button(
                onClick = {
                    onNavigateToQ_2()
                },
                enabled = enableButton,
                colors = ButtonDefaults.buttonColors(
                    ButtonColorMain,
                    disabledContainerColor = BorderStrokeButton,
                    disabledContentColor = BackgroundColor),
                modifier = Modifier
                    .size(width = 334.dp, height = 56.dp)
                    .align(Alignment.Bottom),
                shape = RoundedCornerShape(12.dp)
            ){
                Text(
                    "Continue",
                    color = Color.White,
                    fontWeight = FontWeight.Bold
                )
            }
        }
    }


}

@Composable
fun OnBoardingQ_2(
    onNavigateToBack: () -> Unit,
    onNavigateToQ_3: () -> Unit
){
    val options = listOf("Restless", "Anxiety", "Difficulty to concentrate", "Difficulty falling asleep")
    var optionSelect by remember {mutableStateOf<String?>(null)}
    var enableButton = false


    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(start = 24.dp, end = 24.dp),
        verticalArrangement = Arrangement.spacedBy(12.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Row(
            modifier = Modifier.padding(top = 16.dp)
        ){
            Image(
                painter = painterResource(R.drawable.back_button),
                contentDescription = "Button back",
                modifier = Modifier
                    .size(width = 24.dp, height = 24.dp)
                    .clickable(
                        indication = null,
                        interactionSource = remember { MutableInteractionSource() })
                    {
                        onNavigateToBack()
                    }
            )
            Text(
                "My goals",
                modifier = Modifier.fillMaxWidth(),
                textAlign = TextAlign.Center,
                fontWeight = FontWeight.Bold,
                fontSize = 18.sp
            )
        }


        Spacer(modifier = Modifier.height(100.dp))


        Text(
            "Do you experience\nany of following? ",
            fontSize = 24.sp,
            fontWeight = FontWeight.Bold,
            textAlign = TextAlign.Center,
            color = SelectButton_Q
        )

        Spacer(modifier = Modifier.height(62.dp))


        options.forEach{ option ->

            val isSelect = (option == optionSelect)

            val backgroundColor = if (isSelect) SelectButton_Q else ButtonColorDouble
            val textColor = if (isSelect) BackgroundColor else SelectButton_Q

            if(optionSelect.toString() in options){
                enableButton = true
            }else{
                enableButton = false
            }


            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(62.dp)
                    .clip(RoundedCornerShape(12.dp))
                    .background(backgroundColor)
                    .border(
                        width = 2.dp,
                        color = BorderStrokeButton,
                        shape = RoundedCornerShape(12.dp)
                    )
                    .padding(start = 20.dp)
                    .clickable {
                        optionSelect = option
                    },
                contentAlignment = Alignment.CenterStart

            ){
                Text(
                    text = option,
                    color = textColor,
                    textAlign = TextAlign.Start,
                    fontWeight = FontWeight.Bold,
                    fontSize = 16.sp
                )
            }

        }


        Row(
            modifier = Modifier
                .fillMaxHeight()
                .padding(bottom = 48.dp)
        ){
            Button(
                onClick = {
                    onNavigateToQ_3()
                },
                enabled = enableButton,
                colors = ButtonDefaults.buttonColors(
                    ButtonColorMain,
                    disabledContainerColor = BorderStrokeButton,
                    disabledContentColor = BackgroundColor),
                modifier = Modifier
                    .size(width = 334.dp, height = 56.dp)
                    .align(Alignment.Bottom),
                shape = RoundedCornerShape(12.dp)
            ){
                Text(
                    "Continue",
                    color = Color.White,
                    fontWeight = FontWeight.Bold
                )
            }
        }
    }


}

@Composable
fun OnBoardingQ_3(
    onNavigateToBack: () -> Unit,
    onNavigateToQ_4: () -> Unit
){
    val options = listOf("Yes", "No", "A bit")
    var optionSelect by remember {mutableStateOf<String?>(null)}
    var enableButton = false


    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(start = 24.dp, end = 24.dp),
        verticalArrangement = Arrangement.spacedBy(12.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Row(
            modifier = Modifier.padding(top = 16.dp)
        ){
            Image(
                painter = painterResource(R.drawable.back_button),
                contentDescription = "Button back",
                modifier = Modifier
                    .size(width = 24.dp, height = 24.dp)
                    .clickable(
                        indication = null,
                        interactionSource = remember { MutableInteractionSource() })
                    {
                        onNavigateToBack()
                    }
            )
            Text(
                "My goals",
                modifier = Modifier.fillMaxWidth(),
                textAlign = TextAlign.Center,
                fontWeight = FontWeight.Bold,
                fontSize = 18.sp
            )
        }


        Spacer(modifier = Modifier.height(100.dp))


        Text(
            "Do you have experience\nwith meditation? ",
            fontSize = 24.sp,
            fontWeight = FontWeight.Bold,
            textAlign = TextAlign.Center,
            color = SelectButton_Q
        )

        Spacer(modifier = Modifier.height(62.dp))


        options.forEach{ option ->

            val isSelect = (option == optionSelect)

            val backgroundColor = if (isSelect) SelectButton_Q else ButtonColorDouble
            val textColor = if (isSelect) BackgroundColor else SelectButton_Q

            if(optionSelect.toString() in options){
                enableButton = true
            }else{
                enableButton = false
            }


            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(62.dp)
                    .clip(RoundedCornerShape(12.dp))
                    .background(backgroundColor)
                    .border(
                        width = 2.dp,
                        color = BorderStrokeButton,
                        shape = RoundedCornerShape(12.dp)
                    )
                    .padding(start = 20.dp)
                    .clickable {
                        optionSelect = option
                    },
                contentAlignment = Alignment.CenterStart

            ){
                Text(
                    text = option,
                    color = textColor,
                    textAlign = TextAlign.Start,
                    fontWeight = FontWeight.Bold,
                    fontSize = 16.sp
                )
            }

        }


        Row(
            modifier = Modifier
                .fillMaxHeight()
                .padding(bottom = 48.dp)
        ){
            Button(
                onClick = {
                    onNavigateToQ_4()
                },
                enabled = enableButton,
                colors = ButtonDefaults.buttonColors(
                    ButtonColorMain,
                    disabledContainerColor = BorderStrokeButton,
                    disabledContentColor = BackgroundColor),
                modifier = Modifier
                    .size(width = 334.dp, height = 56.dp)
                    .align(Alignment.Bottom),
                shape = RoundedCornerShape(12.dp)
            ){
                Text(
                    "Continue",
                    color = Color.White,
                    fontWeight = FontWeight.Bold
                )
            }
        }
    }


}

@Composable
fun OnBoardingQ_4(
    onNavigateToBack: () -> Unit,
    onNavigateToZaebok: () -> Unit
){
    val context = LocalContext.current
    var name_image = "q4_image"
    val options = listOf(
        "${name_image}_1",
        "${name_image}_2",
        "${name_image}_3",
        "${name_image}_4",
        "${name_image}_5",
        "${name_image}_6",
        "${name_image}_7",
        "${name_image}_8")
    var selectOptions = remember {mutableStateListOf<String?>(null)}
    var enableButton = selectOptions.isNotEmpty()


    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(start = 24.dp, end = 24.dp),
        verticalArrangement = Arrangement.spacedBy(12.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Row(
            modifier = Modifier.padding(top = 16.dp)
        ){
            Image(
                painter = painterResource(R.drawable.back_button),
                contentDescription = "Button back",
                modifier = Modifier
                    .size(width = 24.dp, height = 24.dp)
                    .clickable(
                        indication = null,
                        interactionSource = remember { MutableInteractionSource() })
                    {
                        onNavigateToBack()
                    }
            )
            Text(
                "My goals",
                modifier = Modifier.fillMaxWidth(),
                textAlign = TextAlign.Center,
                fontWeight = FontWeight.Bold,
                fontSize = 18.sp
            )
        }


        Spacer(modifier = Modifier.height(45.dp))


        Text(
            "Choose a few of the sounds\nyou find relaxing",
            fontSize = 24.sp,
            fontWeight = FontWeight.Bold,
            textAlign = TextAlign.Center,
            color = SelectButton_Q
        )

        Spacer(modifier = Modifier.height(42.dp))

        LazyVerticalGrid(
            columns = GridCells.Fixed(2), // 2 фиксированные колонки
            horizontalArrangement = Arrangement.spacedBy(8.dp),
            verticalArrangement = Arrangement.spacedBy(8.dp),
            modifier = Modifier.fillMaxWidth()
        ){
            items(options){ option ->
                val isSelected = selectOptions.contains(option)

                val finalImageName = if (isSelected) {
                    "${option}_press"
                } else {
                    option
                }

                val image_to_id = context.resources.getIdentifier(finalImageName, "drawable", context.packageName)

                Box(
                    modifier = Modifier
                        .clip(RoundedCornerShape(12.dp))
                        .size(width = 160.dp, height = 101.dp)
                        .clickable {
                            if (isSelected) {
                                selectOptions.remove(option)
                            } else {
                                selectOptions.add(option)
                            }
                        },
                    contentAlignment = Alignment.Center

                ){
                    Image(
                        painter = painterResource(image_to_id),
                        contentDescription = "",
                        modifier = Modifier.size(width = 160.dp, height = 101.dp)
                    )
                }

            }
        }

        Row(
            modifier = Modifier
                .fillMaxHeight()
                .padding(bottom = 48.dp)
        ){
            Button(
                onClick = {
                    onNavigateToZaebok()
                },
                enabled = enableButton,
                colors = ButtonDefaults.buttonColors(
                    ButtonColorMain,
                    disabledContainerColor = BorderStrokeButton,
                    disabledContentColor = BackgroundColor),
                modifier = Modifier
                    .size(width = 334.dp, height = 56.dp)
                    .align(Alignment.Bottom),
                shape = RoundedCornerShape(12.dp)
            ){
                Text(
                    "Let's begin",
                    color = Color.White,
                    fontWeight = FontWeight.Bold
                )
            }
        }

    }



}
@Composable
fun MiniCardTrack(
    track: MeditationTrack,
    onClickTrack: () -> Unit,
    modifier: Modifier = Modifier
){
    Card(
        modifier = modifier
            .size(width = 140.dp, height = 170.dp)
            .clickable{
                onClickTrack()
            }
            .testTag("mini_track_card"),
        shape = RoundedCornerShape(12.dp),
        onClick = {
            onClickTrack()
        }
    ){
        Box{

            AsyncImage(
                model = ImageRequest.Builder(LocalContext.current)
                    .data(track.image)
                    .crossfade(300)
                    .build(),
                contentDescription = "Image Track",
                modifier = modifier.fillMaxSize(),
                contentScale = ContentScale.Crop
            )

            Image(
                painter = painterResource(R.drawable.front_rectable_image),
                contentDescription = "",
                modifier = modifier.fillMaxSize(),
                contentScale = ContentScale.Crop
            )

            Image(
                painter = painterResource(R.drawable.front_panel_track_card),
                contentDescription = "",
                modifier = modifier
                    .fillMaxWidth()
                    .align(Alignment.BottomCenter),
                contentScale = ContentScale.FillWidth
            )

            Text(
                text = track.title,
                fontWeight = FontWeight.Bold,
                color = Color.White,
                modifier = modifier
                    .align(Alignment.BottomStart)
                    .padding(bottom = 25.dp, start = 8.dp, top = 8.dp),
                fontSize = 15.sp,
                textAlign = TextAlign.Start
            )

            Image(
                painter = painterResource(R.drawable.clock),
                contentDescription = "",
                modifier = modifier
                    .align(Alignment.BottomStart)
                    .padding(bottom = 14.dp, start = 8.dp)
            )

            Text(
                text = track.time,
                modifier = modifier
                    .align(Alignment.BottomStart)
                    .padding(bottom = 8.dp, start = 24.dp),
                color = Color_Title_Card_Track,
                fontSize = 10.sp,
                fontWeight = FontWeight.Bold
            )
        }
    }
}

@Composable
fun Card_Genre(
    data_genre: DataGenre,
    onClickGenre: () -> Unit,
    modifier: Modifier = Modifier
){
    Card(
        modifier = modifier
            .size(width = 334.dp, height = 192.dp)
            .clickable {
                onClickGenre()
            },
        shape = RoundedCornerShape(12.dp),
        onClick = {
            onClickGenre()
        }
    ){
        Box{
            AsyncImage(
                model = ImageRequest.Builder(LocalContext.current)
                    .data(data_genre.image_genre)
                    .crossfade(true)
                    .build(),
                contentDescription = "",
                modifier = Modifier.fillMaxSize(),
                contentScale = ContentScale.Crop
            )

            Image(
                painter = painterResource(R.drawable.front_panel_genre),
                contentDescription = "",
                modifier = Modifier
                    .fillMaxWidth()
                    .align(Alignment.BottomCenter),
                contentScale = ContentScale.FillWidth
            )

            Text(
                text = data_genre.title_genre,
                fontWeight = FontWeight.Bold,
                color = Color.White,
                modifier = Modifier
                    .align(Alignment.BottomStart)
                    .padding(bottom = 36.dp, start = 22.dp, top = 8.dp),
                fontSize = 18.sp,
                textAlign = TextAlign.Start
            )

            Image(
                painter = painterResource(R.drawable.clock),
                contentDescription = "",
                modifier = Modifier
                    .align(Alignment.BottomStart)
                    .padding(bottom = 16.dp, start = 111.dp)
            )

            Text(
                text = data_genre.average_time,
                modifier = Modifier
                    .align(Alignment.BottomStart)
                    .padding(bottom = 10.dp, start = 127.dp),
                color = Color_Title_Card_Track,
                fontSize = 10.sp,
                fontWeight = FontWeight.Bold
            )

            Image(
                painter = painterResource(R.drawable.micro_non_guided),
                contentDescription = "",
                modifier = Modifier
                    .align(Alignment.BottomStart)
                    .padding(bottom = 14.dp, start = 22.dp)
            )

            Text(
                text = "Un-guided",
                modifier = Modifier
                    .align(Alignment.BottomStart)
                    .padding(bottom = 10.dp, start = 42.dp),
                color = Color_Title_Card_Track,
                fontSize = 10.sp,
                fontWeight = FontWeight.Bold
            )
        }
    }
}

@Composable()
fun Card_Track(
    track: MeditationTrack,
    onClickMiniTrack: () -> Unit,
    modifier: Modifier = Modifier
){
    Card(
        modifier = modifier
            .size(width = 327.dp, height = 88.dp)
            .clickable{ onClickMiniTrack() },
        onClick = { onClickMiniTrack() },
        colors = CardDefaults.cardColors(
            containerColor = Color.White          // любой ваш цвет
        )
    ){
        Box(
            modifier = modifier.fillMaxSize()
        ){
            Image(
                painter = painterResource(track.image),
                contentDescription = "",
                modifier = modifier
                    .size(72.dp)
                    .clip(RoundedCornerShape(8))
                    .align(Alignment.CenterStart),
                contentScale = ContentScale.Crop,

                )

            Text(
                track.title,
                fontWeight = FontWeight.SemiBold,
                fontSize = 16.sp,
                modifier = modifier.padding(top = 20.dp, start = 84.dp)
            )

            Image(
                painter = painterResource(R.drawable.micro_non_guided_green),
                contentDescription = "",
                modifier = modifier.padding(top = 50.dp, start = 84.dp)
            )

            Text(
                "Un-guided",
                fontWeight = FontWeight.Medium,
                fontSize = 12.sp,
                modifier = modifier.padding(top = 46.dp, start = 104.dp)
            )

            Image(
                painter = painterResource(R.drawable.clock_green),
                contentDescription = "",
                modifier = modifier
                    .padding(top = 51.dp, start = 180.dp)
                    .size(14.dp)
            )

            Text(
                track.time,
                fontWeight = FontWeight.Medium,
                fontSize = 12.sp,
                modifier = modifier.padding(top = 46.dp, start = 202.dp)
            )
        }
    }

}

@Composable
fun Zaebok(
    viewModel: AudioViewModel = viewModel(),
    onNavigateToOpenGenre: (String) -> Unit
){

    val tracks by viewModel.tracks.collectAsState()
    val genrs by viewModel.genre.collectAsState()


    Column(
        modifier = Modifier
            .fillMaxSize()
    ){

        Text(
            "Suggested for you...",
            fontSize = 18.sp,
            fontWeight = FontWeight.Bold,
            color = Color.Black,
            modifier = Modifier
                .padding(top = 21.dp, bottom = 24.dp)
                .align(Alignment.CenterHorizontally)
        )

        LazyColumn(
            modifier = Modifier
                .fillMaxWidth()
                .padding(start = 12.dp, end = 12.dp)
                .testTag("list_genre"),
            verticalArrangement = Arrangement.spacedBy(11.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ){
            items(items = genrs){ currentGenre ->
                Card_Genre(
                    data_genre = currentGenre,
                    onClickGenre = {
                        onNavigateToOpenGenre(currentGenre.id.toString())
                    },
                    modifier = Modifier.testTag("genre_card_${currentGenre.id}")
                )
            }
        }
// Этот кусок кода рабочий, просто надо будет засунуть в другой Composable
//        LazyVerticalGrid(
//            columns = GridCells.Fixed(3),
//            modifier = Modifier.fillMaxSize().padding(start = 12.dp, end = 12.dp),
//            verticalArrangement = Arrangement.spacedBy(11.dp),
//            horizontalArrangement = Arrangement.spacedBy(11.dp)
//        ) {
//            items(items = tracks) { currentTrack ->
//                MiniCardTrack(
//                    track = currentTrack
//                )
//            }
//        }

    }
}

@Composable()
fun OpenGenreJazz(
    viewModel: AudioViewModel = viewModel(),
    onNavigateToBack: () -> Unit,
    onNavigateToOpenTrack: (MeditationTrack) -> Unit
){
//    val tracks by viewModel.filter_tracks_all_jazz.collectAsState()
    val filter_search_tracks_jazz by viewModel.filter_search_tracks_jazz.collectAsState()

    Column{
        Box(
            modifier = Modifier
                .size(height = 355.dp, width = 400.dp)
                .testTag("genre_open_screen")
        ){

            AsyncImage(
                model = ImageRequest.Builder(LocalContext.current)
                    .data(R.drawable.image_genre_jazz)
                    .crossfade(400)
                    .build(),
                contentDescription = "genre jazz",
                modifier = Modifier.fillMaxSize(),
                contentScale = ContentScale.Crop
            )

            Image(
                painter = painterResource(R.drawable.shadow_main_image_genre),
                contentDescription = "",
                modifier = Modifier.fillMaxSize(),
                contentScale = ContentScale.Crop
            )

            Text(
                text = "Jazz",
                fontSize = 32.sp,
                fontWeight = FontWeight.Bold,
                color = Color.White,
                modifier = Modifier.padding(top = 158.dp, start = 24.dp)
            )

            Text(
                text = "10 practices",
                color = Color.White,
                fontSize = 14.sp,
                fontWeight = FontWeight.Medium,
                modifier = Modifier.padding(top = 199.dp, start = 24.dp)
            )

            Text(
                text = "Jazz music improves brain function,\nreduces stress levels, and fosters\nmental flexibility.",
                color = Color.White,
                fontSize = 14.sp,
                fontWeight = FontWeight.Medium,
                modifier = Modifier.padding(top = 231.dp, start = 24.dp)
            )

            Image(
                painter = painterResource(R.drawable.button_back_open_genre),
                contentDescription = "Back Button",
                modifier = Modifier
                    .padding(start = 24.dp, top = 24.dp)
                    .clickable(
                        indication = null,
                        interactionSource = remember { MutableInteractionSource() })
                    {
                        onNavigateToBack()
                    }
            )

            Image(
                painter = painterResource(R.drawable.corner_radius_plashka_open_genre),
                contentDescription = "",
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 331.dp),
                contentScale = ContentScale.Crop
            )
        }

            var textState_Search by remember {mutableStateOf("")}

            OutlinedTextField(
                value = textState_Search,
                onValueChange = {newValue ->
                    textState_Search = newValue
                    viewModel.onSearchTracks(newValue)},

                placeholder = {Text("What do you want to listen?", fontSize = 15.sp, color = My_FieldText_color)},
                shape = RoundedCornerShape(12.dp),
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Email),
                singleLine = true,
                leadingIcon = {
                    Image(
                        painter = painterResource(R.drawable.search_logo),
                        contentDescription = "search logo"
                    )
                },
                colors = OutlinedTextFieldDefaults.colors(
                    focusedContainerColor = Background_TextField,
                    unfocusedContainerColor = Background_TextField,
                    unfocusedTextColor = Color.Black,
                    focusedTextColor = Color.Black,
                    focusedLabelColor = My_FieldText_color,
                    unfocusedLabelColor = My_FieldText_color,
                    focusedBorderColor = ButtonColorMain,
                    unfocusedBorderColor = ButtonColorMain),
                modifier = Modifier
                    .width(width = 327.dp)
                    .align(Alignment.CenterHorizontally)
            )

            Spacer(modifier = Modifier.height(28.dp))

            Text(
                text = "All jazz music",
                fontSize = 18.sp,
                color = Color.Black,
                fontWeight = FontWeight.Bold,
                modifier = Modifier.padding(start = 12.dp)
            )

            Spacer(modifier = Modifier.height(10.dp))

            LazyVerticalGrid(
                columns = GridCells.Fixed(3),
                modifier = Modifier
                    .fillMaxSize()
                    .padding(start = 12.dp, end = 12.dp)
                    .testTag("list_track"),
                verticalArrangement = Arrangement.spacedBy(11.dp),
                horizontalArrangement = Arrangement.spacedBy(11.dp)
            ) {
                items(
                    items = filter_search_tracks_jazz,
                    key = {track -> track.id}
                ) { currentTrack ->
                    MiniCardTrack(
                        track = currentTrack,
                        onClickTrack = {
                            Log.i("TEST_TAGS", "${currentTrack}")
                            onNavigateToOpenTrack(currentTrack)
                        },
                        modifier = Modifier.testTag("track_${currentTrack.id}_click")
                    )
                }
            }
    }

}

@Composable()
fun OpenGenreDeepSleep(
    viewModel: AudioViewModel = viewModel(),
    onNavigateToBack: () -> Unit,
    onNavigateToOpenTrack: (MeditationTrack) -> Unit
){
//    val tracks by viewModel.filter_tracks_all_deep_sleep.collectAsState()

//    val searchTrack by viewModel.searchTrack.collectAsState()
    val search_filter_tracks by viewModel.filter_search_tracks_deep_sleep.collectAsState()

    Column{

        Box(
            modifier = Modifier
                .size(height = 355.dp, width = 400.dp)
                .testTag("genre_open_screen_2")
        ){

            AsyncImage(
                model = ImageRequest.Builder(LocalContext.current)
                    .data(R.drawable.image_genre_deep_sleep)
                    .crossfade(400)
                    .build(),
                contentDescription = "genre deep sleep",
                modifier = Modifier.fillMaxSize(),
                contentScale = ContentScale.Crop
            )

            Image(
                painter = painterResource(R.drawable.shadow_main_image_genre),
                contentDescription = "",
                modifier = Modifier.fillMaxSize(),
                contentScale = ContentScale.Crop
            )

            Text(
                text = "Deep Sleep",
                fontSize = 32.sp,
                fontWeight = FontWeight.Bold,
                color = Color.White,
                modifier = Modifier.padding(top = 158.dp, start = 24.dp)
            )

            Text(
                text = "10 practices",
                color = Color.White,
                fontSize = 14.sp,
                fontWeight = FontWeight.Medium,
                modifier = Modifier.padding(top = 199.dp, start = 24.dp)
            )

            Text(
                text = "Deep sleep helps your brain recover,\nlowers tension, and supports\nclear thinking and calm.",
                color = Color.White,
                fontSize = 14.sp,
                fontWeight = FontWeight.Medium,
                modifier = Modifier.padding(top = 231.dp, start = 24.dp)
            )

            Image(
                painter = painterResource(R.drawable.button_back_open_genre),
                contentDescription = "Back Button",
                modifier = Modifier
                    .padding(start = 24.dp, top = 24.dp)
                    .clickable(
                        indication = null,
                        interactionSource = remember { MutableInteractionSource() })
                    {
                        onNavigateToBack()
                    }
            )

            Image(
                painter = painterResource(R.drawable.corner_radius_plashka_open_genre),
                contentDescription = "",
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 331.dp),
                contentScale = ContentScale.Crop
            )
        }

        var textState_Search by remember {mutableStateOf("")}

        OutlinedTextField(
            value = textState_Search,
            onValueChange = {newValue ->
                textState_Search = newValue
                viewModel.onSearchTracks(newValue)},

            placeholder = {Text("What do you want to listen?", fontSize = 15.sp, color = My_FieldText_color)},
            shape = RoundedCornerShape(12.dp),
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Email),
            singleLine = true,
            leadingIcon = {
                Image(
                    painter = painterResource(R.drawable.search_logo),
                    contentDescription = "search logo"
                )
            },
            trailingIcon = {
                Image(
                    painter = painterResource(R.drawable.filter_logo),
                    contentDescription = "filter logo"
                )
            },
            colors = OutlinedTextFieldDefaults.colors(
                focusedContainerColor = Background_TextField,
                unfocusedContainerColor = Background_TextField,
                unfocusedTextColor = Color.Black,
                focusedTextColor = Color.Black,
                focusedLabelColor = My_FieldText_color,
                unfocusedLabelColor = My_FieldText_color,
                focusedBorderColor = ButtonColorMain,
                unfocusedBorderColor = ButtonColorMain),
            modifier = Modifier
                .width(width = 327.dp)
                .align(Alignment.CenterHorizontally)
        )

        Spacer(modifier = Modifier.height(28.dp))

        Text(
            text = "All deep sleep music",
            fontSize = 18.sp,
            color = Color.Black,
            fontWeight = FontWeight.Bold,
            modifier = Modifier.padding(start = 12.dp)
        )

        Spacer(modifier = Modifier.height(10.dp))

        LazyVerticalGrid(
            columns = GridCells.Fixed(3),
            modifier = Modifier
                .fillMaxSize()
                .padding(start = 12.dp, end = 12.dp),
            verticalArrangement = Arrangement.spacedBy(11.dp),
            horizontalArrangement = Arrangement.spacedBy(11.dp)
        ) {
            items(
                items = search_filter_tracks,
                key = {track -> track.id}
            ) { currentTrack ->
                MiniCardTrack(
                    track = currentTrack,
                    onClickTrack = {
                        onNavigateToOpenTrack(currentTrack)
                    }
                )
            }
        }

    }
}

@Composable()
fun OpenGenreLoFi(
    viewModel: AudioViewModel = viewModel(),
    onNavigateToBack: () -> Unit,
    onNavigateToOpenTrack: (MeditationTrack) -> Unit
){
//    val tracks by viewModel.filter_tracks_all_lofi.collectAsState()

    val filter_search_tracks_lofi by viewModel.filter_search_tracks_lofi.collectAsState()

    Column{

        Box(
            modifier = Modifier
                .size(height = 355.dp, width = 400.dp)
                .testTag("genre_open_screen_3")
        ){

            AsyncImage(
                model = ImageRequest.Builder(LocalContext.current)
                    .data(R.drawable.image_genre_lofi)
                    .crossfade(400)
                    .build(),
                contentDescription = "genre Lo-Fi",
                modifier = Modifier.fillMaxSize(),
                contentScale = ContentScale.Crop
            )

            Image(
                painter = painterResource(R.drawable.shadow_main_image_genre),
                contentDescription = "",
                modifier = Modifier.fillMaxSize(),
                contentScale = ContentScale.Crop
            )

            Text(
                text = "Lo-Fi",
                fontSize = 32.sp,
                fontWeight = FontWeight.Bold,
                color = Color.White,
                modifier = Modifier.padding(top = 158.dp, start = 24.dp)
            )

            Text(
                text = "10 practices",
                color = Color.White,
                fontSize = 14.sp,
                fontWeight = FontWeight.Medium,
                modifier = Modifier.padding(top = 199.dp, start = 24.dp)
            )

            Text(
                text = "Lo-fi music sets a gentle rhythm,\nsoothes tension, and makes it\neasier to study and unwind.",
                color = Color.White,
                fontSize = 14.sp,
                fontWeight = FontWeight.Medium,
                modifier = Modifier.padding(top = 231.dp, start = 24.dp)
            )

            Image(
                painter = painterResource(R.drawable.button_back_open_genre),
                contentDescription = "Back Button",
                modifier = Modifier
                    .padding(start = 24.dp, top = 24.dp)
                    .clickable(
                        indication = null,
                        interactionSource = remember { MutableInteractionSource() })
                    {
                        onNavigateToBack()
                    }
            )

            Image(
                painter = painterResource(R.drawable.corner_radius_plashka_open_genre),
                contentDescription = "",
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 331.dp),
                contentScale = ContentScale.Crop
            )
        }

        var textState_Search by remember {mutableStateOf("")}

        OutlinedTextField(
            value = textState_Search,
            onValueChange = {newValue ->
                textState_Search = newValue
                viewModel.onSearchTracks(newValue)},

            placeholder = {Text("What do you want to listen?", fontSize = 15.sp, color = My_FieldText_color)},
            shape = RoundedCornerShape(12.dp),
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Email),
            singleLine = true,
            leadingIcon = {
                Image(
                    painter = painterResource(R.drawable.search_logo),
                    contentDescription = "search logo"
                )
            },
            trailingIcon = {
                Image(
                    painter = painterResource(R.drawable.filter_logo),
                    contentDescription = "filter logo"
                )
            },
            colors = OutlinedTextFieldDefaults.colors(
                focusedContainerColor = Background_TextField,
                unfocusedContainerColor = Background_TextField,
                unfocusedTextColor = Color.Black,
                focusedTextColor = Color.Black,
                focusedLabelColor = My_FieldText_color,
                unfocusedLabelColor = My_FieldText_color,
                focusedBorderColor = ButtonColorMain,
                unfocusedBorderColor = ButtonColorMain),
            modifier = Modifier
                .width(width = 327.dp)
                .align(Alignment.CenterHorizontally)
        )

        Spacer(modifier = Modifier.height(28.dp))

        Text(
            text = "All deep sleep music",
            fontSize = 18.sp,
            color = Color.Black,
            fontWeight = FontWeight.Bold,
            modifier = Modifier.padding(start = 12.dp)
        )

        Spacer(modifier = Modifier.height(10.dp))

        LazyVerticalGrid(
            columns = GridCells.Fixed(3),
            modifier = Modifier
                .fillMaxSize()
                .padding(start = 12.dp, end = 12.dp),
            verticalArrangement = Arrangement.spacedBy(11.dp),
            horizontalArrangement = Arrangement.spacedBy(11.dp)
        ) {
            items(
                items = filter_search_tracks_lofi,
                key = {track -> track.id}
            ) { currentTrack ->
                MiniCardTrack(
                    track = currentTrack,
                    onClickTrack = {
                        onNavigateToOpenTrack(currentTrack)
                    }
                )
            }
        }
    }
}

@Composable()
fun OpenGenreNature(
    viewModel: AudioViewModel = viewModel(),
    onNavigateToBack: () -> Unit,
    onNavigateToOpenTrack: (MeditationTrack) -> Unit
){
//    val tracks by viewModel.filter_tracks_all_nature.collectAsState()

    val filter_search_tracks_nature by viewModel.filter_search_tracks_nature.collectAsState()

    Column{

        Box(
            modifier = Modifier
                .size(height = 355.dp, width = 400.dp)
                .testTag("genre_open_screen_3")
        ){

            AsyncImage(
                model = ImageRequest.Builder(LocalContext.current)
                    .data(R.drawable.image_genre_lofi)
                    .crossfade(400)
                    .build(),
                contentDescription = "genre Lo-Fi",
                modifier = Modifier.fillMaxSize(),
                contentScale = ContentScale.Crop
            )

            Image(
                painter = painterResource(R.drawable.shadow_main_image_genre),
                contentDescription = "",
                modifier = Modifier.fillMaxSize(),
                contentScale = ContentScale.Crop
            )

            Text(
                text = "Sound of Nature",
                fontSize = 32.sp,
                fontWeight = FontWeight.Bold,
                color = Color.White,
                modifier = Modifier.padding(top = 158.dp, start = 24.dp)
            )

            Text(
                text = "5 practices",
                color = Color.White,
                fontSize = 14.sp,
                fontWeight = FontWeight.Medium,
                modifier = Modifier.padding(top = 199.dp, start = 24.dp)
            )

            Text(
                text = "Nature sounds quiet the noise,\nease anxiety, and help your mind\nreset and breathe.",
                color = Color.White,
                fontSize = 14.sp,
                fontWeight = FontWeight.Medium,
                modifier = Modifier.padding(top = 231.dp, start = 24.dp)
            )

            Image(
                painter = painterResource(R.drawable.button_back_open_genre),
                contentDescription = "Back Button",
                modifier = Modifier
                    .padding(start = 24.dp, top = 24.dp)
                    .clickable(
                        indication = null,
                        interactionSource = remember { MutableInteractionSource() })
                    {
                        onNavigateToBack()
                    }
            )

            Image(
                painter = painterResource(R.drawable.corner_radius_plashka_open_genre),
                contentDescription = "",
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 331.dp),
                contentScale = ContentScale.Crop
            )
        }

        var textState_Search by remember {mutableStateOf("")}

        OutlinedTextField(
            value = textState_Search,
            onValueChange = {newValue ->
                textState_Search = newValue
                viewModel.onSearchTracks(newValue)},

            placeholder = {Text("What do you want to listen?", fontSize = 15.sp, color = My_FieldText_color)},
            shape = RoundedCornerShape(12.dp),
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Email),
            singleLine = true,
            leadingIcon = {
                Image(
                    painter = painterResource(R.drawable.search_logo),
                    contentDescription = "search logo"
                )
            },
            trailingIcon = {
                Image(
                    painter = painterResource(R.drawable.filter_logo),
                    contentDescription = "filter logo"
                )
            },
            colors = OutlinedTextFieldDefaults.colors(
                focusedContainerColor = Background_TextField,
                unfocusedContainerColor = Background_TextField,
                unfocusedTextColor = Color.Black,
                focusedTextColor = Color.Black,
                focusedLabelColor = My_FieldText_color,
                unfocusedLabelColor = My_FieldText_color,
                focusedBorderColor = ButtonColorMain,
                unfocusedBorderColor = ButtonColorMain),
            modifier = Modifier
                .width(width = 327.dp)
                .align(Alignment.CenterHorizontally)
        )

        Spacer(modifier = Modifier.height(28.dp))

        Text(
            text = "All sound of nature",
            fontSize = 18.sp,
            color = Color.Black,
            fontWeight = FontWeight.Bold,
            modifier = Modifier.padding(start = 12.dp)
        )

        Spacer(modifier = Modifier.height(10.dp))

        LazyVerticalGrid(
            columns = GridCells.Fixed(3),
            modifier = Modifier
                .fillMaxSize()
                .padding(start = 12.dp, end = 12.dp),
            verticalArrangement = Arrangement.spacedBy(11.dp),
            horizontalArrangement = Arrangement.spacedBy(11.dp)
        ) {
            items(
                items = filter_search_tracks_nature,
                key = {track -> track.id}
            ) { currentTrack ->
                MiniCardTrack(
                    track = currentTrack,
                    onClickTrack = {
                        onNavigateToOpenTrack(currentTrack)
                    }
                )
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable()
fun Discover(
    viewModel: AudioViewModel = viewModel(),
    onNavigateToOpenFullTrack: (MeditationTrack) -> Unit
){

    Column(modifier = Modifier
        .fillMaxSize()
        .padding(top = 16.dp)
        .semantics { testTagsAsResourceId = true }
        .testTag("discover_screen")
    ){

        var textState_Search by remember {mutableStateOf("")}
        val tracks by viewModel.search_all_tracks.collectAsState()
        val genres by viewModel.genre.collectAsState()

        var showSheet by remember { mutableStateOf(false) }
        val sheetState = rememberModalBottomSheetState(skipPartiallyExpanded = true)
        val scope = rememberCoroutineScope()

        val tags = listOf("All", "Relaxation", "Breathing", "Focus", "Nature",
                        "Healing", "Energy", "Peace", "Sleep",
                        "Love", "Relief", "Creativity")
        var selected by remember{mutableStateOf(setOf<String>("All"))}

        OutlinedTextField(
            value = textState_Search,
            onValueChange = {newValue ->
                textState_Search = newValue
                viewModel.onSearchTracks(newValue)},

            placeholder = {Text("What do you want to listen?", fontSize = 15.sp, color = My_FieldText_color)},
            shape = RoundedCornerShape(12.dp),
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Email),
            singleLine = true,
            leadingIcon = {
                Image(
                    painter = painterResource(R.drawable.search_logo),
                    contentDescription = "search logo"
                )
            },
            trailingIcon = {
                Image(
                    painter = painterResource(R.drawable.filter_logo),
                    contentDescription = "filter logo",
                    modifier = Modifier.clickable(
                        indication = null,
                        interactionSource = remember{ MutableInteractionSource()}
                    ){
                        showSheet = true
                    }
                )
            },
            colors = OutlinedTextFieldDefaults.colors(
                focusedContainerColor = Background_TextField,
                unfocusedContainerColor = Background_TextField,
                unfocusedTextColor = Color.Black,
                focusedTextColor = Color.Black,
                focusedLabelColor = My_FieldText_color,
                unfocusedLabelColor = My_FieldText_color,
                focusedBorderColor = ButtonColorMain,
                unfocusedBorderColor = ButtonColorMain),
            modifier = Modifier
                .width(width = 327.dp)
                .align(Alignment.CenterHorizontally)
        )

        LazyColumn(
            modifier = Modifier
                .fillMaxWidth()
                .padding(start = 12.dp, end = 12.dp, top = 16.dp)
                .testTag("list_track_discover"),
            verticalArrangement = Arrangement.spacedBy(11.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ){
//            items(items = genres){currentGenre ->
//                Card_Genre(
//                    data_genre = currentGenre,
//                    onClickGenre = {
//                        onNavigateToOpenGenre(currentGenre.id.toString())
//                    },
//                    modifier = Modifier.testTag("genre_card_${currentGenre.id}")
//                )
//            }

            items(items = tracks){ currentTrack ->
                Card_Track(
                    track = currentTrack,
                    onClickMiniTrack = {
                        onNavigateToOpenFullTrack(currentTrack)
                    },
                    modifier = Modifier.testTag("open_track_discover_click")
                )
            }
        }

        if(showSheet){
            ModalBottomSheet(
                onDismissRequest = {
                    showSheet = false },
                sheetState = sheetState,
                containerColor = White,
                tonalElevation = 0.dp
            ){
                Column(modifier = Modifier.padding(top = 33.dp, start = 25.dp, end = 25.dp)){
                    Text(
                        "Best for",
                        fontWeight = FontWeight.Medium,
                        fontSize = 14.sp
                    )

                    FlowRow(
                        horizontalArrangement = Arrangement.spacedBy(4.dp),
                        verticalArrangement = Arrangement.spacedBy(4.dp)
                    ) {
                        tags.forEach { tag ->
                            val isSelected = tag in selected

                            FilterChip(
                                selected = isSelected,
                                onClick = {
                                    selected = when{
                                        tag == "All" -> setOf("All")
                                        isSelected -> (selected - tag).ifEmpty { setOf("All") }
                                        else -> (selected - "All") + tag
                                    }
                                },
                                label = {
                                    Text(
                                        tag,
                                        fontWeight = FontWeight.Medium,
//                                        fontSize = 14.sp
                                    )
                                },
                                colors = FilterChipDefaults.filterChipColors(
                                    containerColor = ColorNoneSelectButton,
                                    labelColor = Black,
                                    selectedContainerColor = ColorSelectButton,
                                    selectedLabelColor = White
                                ),
                                border = null,
                                shape = RoundedCornerShape(6.dp),
                                modifier = Modifier.height(48.dp)
                            )


                        }
                    }

                    Spacer(modifier = Modifier.height(40.dp))

                    Button(
                        onClick = {
//                            onNavigateToSignIn()
                            viewModel.updateSelectTags(selected)
                            viewModel.loadDataTracksTags()
                            scope.launch { sheetState.hide() }.invokeOnCompletion {
                                if (!sheetState.isVisible) showSheet = false
                            }
                        },
                        colors = ButtonDefaults.buttonColors(ButtonColorMain),
                        modifier = Modifier
                            .size(width = 334.dp, height = 56.dp)
                            .align(Alignment.CenterHorizontally),
                        shape = RoundedCornerShape(12.dp)
                    ){
                        Text(
                            "Apply Filter",
                            color = White,
                            fontWeight = FontWeight.Bold,
                            fontSize = 16.sp
                        )

                    }

                    Spacer(modifier = Modifier.height(12.dp))


                    Text(
                        "Clear filter",
                        modifier = Modifier
                            .clickable(
                                indication = null,
                                interactionSource = remember { MutableInteractionSource() }
                            ) {
                                selected = setOf("All")
//                            onNavigateToSignUpScreen()
                            }
                            .align(Alignment.CenterHorizontally),
                        color = ColorSelectButton,
                        textDecoration = TextDecoration.Underline,
                        fontWeight = FontWeight.Bold,
                    )

                    Spacer(modifier = Modifier.height(24.dp))



                }

            }
        }
    }
}

@Composable()
fun Settings(
    onNavigateToMyAccount: () -> Unit,
    onNavigateToSignUpOrLogin: () -> Unit,
    onNavigateToTermsAndConditions: () -> Unit,
    onNavigateToPrivacyPolicy: () -> Unit,
    viewModel: AuthViewModel = viewModel()
){

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(White)
            .padding(top = 16.dp, start = 24.dp, end = 24.dp)
            .semantics { testTagsAsResourceId = true }
            .testTag("settings_screen")
    ){
        Text(
            "Settings",
            fontWeight = FontWeight.Bold,
            fontSize = 20.sp
        )

        Spacer(modifier = Modifier.height(26.dp))

        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(97.dp)
        ){
            Image(
                painter = painterResource(R.drawable.big_rec_settings),
                contentDescription = ""
            )

            Text(
                text = "Fine-tune your life with Clarity",
                fontWeight = FontWeight.SemiBold,
                fontSize = 24.sp,
                modifier = Modifier.padding(top = 24.dp, start = 24.dp, end = 24.dp),
                color = White
            )
        }

        Spacer(modifier = Modifier.height(33.dp))


        Button(
            onClick = { onNavigateToMyAccount() },
            colors = ButtonDefaults.buttonColors(White),
            modifier = Modifier
                .height(40.dp)
                .fillMaxWidth()
                .testTag("my_account_click"),
            shape = RoundedCornerShape(12.dp),
            contentPadding = PaddingValues(0.dp)
        ){
            Box{
                Text(
                    "My Account",
                    color = Color.Black,
                    textAlign = TextAlign.Start,
                    modifier = Modifier.fillMaxWidth(),
                    fontWeight = FontWeight.Medium,
                    fontSize = 16.sp
//                    modifier = Modifier.align(Alignment.Start)
                )

                Spacer(modifier = Modifier.width(20.dp))

                Image(
                    painter = painterResource(R.drawable.strelka_button_settings),
                    contentDescription = "My Account",
                    modifier = Modifier
                        .size(24.dp)
                        .align(Alignment.CenterEnd)
                )
            }
        }

//        Spacer(modifier = Modifier.height(12.dp))
//
//        Button(
//            onClick = {},
//            colors = ButtonDefaults.buttonColors(White),
//            modifier = Modifier
//                .height(40.dp)
//                .fillMaxWidth(),
//            shape = RoundedCornerShape(12.dp),
//            contentPadding = PaddingValues(0.dp)
//        ){
//            Box{
//                Text(
//                    "FAQ",
//                    color = Color.Black,
//                    textAlign = TextAlign.Start,
//                    modifier = Modifier.fillMaxWidth(),
//                    fontWeight = FontWeight.Medium,
//                    fontSize = 16.sp
////                    modifier = Modifier.align(Alignment.Start)
//                )
//
//                Spacer(modifier = Modifier.width(20.dp))
//
//                Image(
//                    painter = painterResource(R.drawable.strelka_button_settings),
//                    contentDescription = "FAQ",
//                    modifier = Modifier
//                        .size(24.dp)
//                        .align(Alignment.CenterEnd)
//                )
//            }
//        }

        Spacer(modifier = Modifier.height(12.dp))

        Button(
            onClick = {onNavigateToPrivacyPolicy()},
            colors = ButtonDefaults.buttonColors(White),
            modifier = Modifier
                .height(40.dp)
                .fillMaxWidth(),
            shape = RoundedCornerShape(12.dp),
            contentPadding = PaddingValues(0.dp)
        ){
            Box{
                Text(
                    "Privacy Policy",
                    color = Color.Black,
                    textAlign = TextAlign.Start,
                    modifier = Modifier
                        .fillMaxWidth()
                        .testTag("privacy_click"),
                    fontWeight = FontWeight.Medium,
                    fontSize = 16.sp
//                    modifier = Modifier.align(Alignment.Start)
                )

                Spacer(modifier = Modifier.width(20.dp))

                Image(
                    painter = painterResource(R.drawable.strelka_button_settings),
                    contentDescription = "Privacy Policy",
                    modifier = Modifier
                        .size(24.dp)
                        .align(Alignment.CenterEnd)
                )
            }
        }

        Spacer(modifier = Modifier.height(12.dp))

        Button(
            onClick = {onNavigateToTermsAndConditions()},
            colors = ButtonDefaults.buttonColors(White),
            modifier = Modifier
                .height(40.dp)
                .fillMaxWidth()
                .testTag("terms_click"),
            shape = RoundedCornerShape(12.dp),
            contentPadding = PaddingValues(0.dp)
        ){
            Box{
                Text(
                    "Terms & Conditions",
                    color = Color.Black,
                    textAlign = TextAlign.Start,
                    modifier = Modifier.fillMaxWidth(),
                    fontWeight = FontWeight.Medium,
                    fontSize = 16.sp
//                    modifier = Modifier.align(Alignment.Start)
                )

                Spacer(modifier = Modifier.width(20.dp))

                Image(
                    painter = painterResource(R.drawable.strelka_button_settings),
                    contentDescription = "Terms & Conditions",
                    modifier = Modifier
                        .size(24.dp)
                        .align(Alignment.CenterEnd)
                )
            }
        }

        Spacer(modifier = Modifier.height(12.dp))

        var showLogoutDialog by remember{mutableStateOf(false)}

        Text(
            "Log out",
            modifier = Modifier.clickable(
                indication = null,
                interactionSource = remember{ MutableInteractionSource()}
            ){
//                onNavigateToSignUpScreen()
                showLogoutDialog = true
            },
            color = ButtonColorMain,
            textDecoration = TextDecoration.Underline,
            fontWeight = FontWeight.Bold,
            fontSize = 15.sp
        )

        if (showLogoutDialog){
            AlertDialog(
                containerColor = White,
                onDismissRequest = {showLogoutDialog = false},
                title = {
                    Text(
                        "Log out",
                        fontWeight = FontWeight.Bold,
                        fontSize = 24.sp,
                        modifier = Modifier.fillMaxWidth(),
                        textAlign = TextAlign.Center

                    )
                },
                text = {
                    Text(
                        "Are you sure you want to log out?",
                        fontWeight = FontWeight.Normal,
                        fontSize = 18.sp,
                        modifier = Modifier.fillMaxWidth(),
                        textAlign = TextAlign.Center
                    )
                       },
                confirmButton = {
                    Button(
                        onClick = {
                            viewModel.logout()
                            onNavigateToSignUpOrLogin()
                            showLogoutDialog = false
                        },
                        modifier = Modifier.size(width = 105.dp, height = 42.dp),
                        colors = ButtonDefaults.buttonColors(ButtonColorMain),
                        shape = RoundedCornerShape(12.dp)
                    ) {
                        Text(
                            "Logout",
                            fontWeight = FontWeight.Bold,
                            fontSize = 16.sp,
                            color = White
                        )
                    }
                },
                dismissButton = {
                    Button(
                        onClick = {
                            showLogoutDialog = false
                        },
                        modifier = Modifier.size(width = 105.dp, height = 42.dp),
                        border = BorderStroke(1.dp, ColorSelectButton),
                        colors = ButtonDefaults.buttonColors(White),
                        shape = RoundedCornerShape(12.dp)
                    ) {
                        Text(
                            "Cancel",
                            fontWeight = FontWeight.Bold,
                            fontSize = 16.sp,
                            color = Black
                        )
                    }
                }
            )
        }
    }
}

@Composable()
fun MyAccount(
    viewModel: AuthViewModel = viewModel(),
    onNavigateToBack: () -> Unit
){
    val context = LocalContext.current

    val weight by viewModel.weight.collectAsState()
    val height by viewModel.height.collectAsState()
    val gender by viewModel.gender.collectAsState()
    val birthday by viewModel.birthday.collectAsState()

    LaunchedEffect(Unit) {
        viewModel.loadCurrentUser()
    }

    viewModel.saveNameAndEmail(AuthRepository(context))

    val userEmail by viewModel.emailUserVM.collectAsState()
    val userName by viewModel.nameUserVM.collectAsState()


    Column(
        modifier = Modifier
            .padding(top = 16.dp, start = 24.dp, end = 24.dp)
            .fillMaxSize()
            .testTag("")
    ){
        Box{
            Image(
                painter = painterResource(R.drawable.back_button),
                contentDescription = "Back",
                modifier = Modifier
                    .size(24.dp)
                    .clickable(
                        indication = null,
                        interactionSource = remember { MutableInteractionSource() })
                    {
                        onNavigateToBack()
                    }
            )

            Text(
                "My Account",
                fontWeight = FontWeight.Bold,
                fontSize = 18.sp,
                modifier = Modifier.fillMaxWidth(),
                textAlign = TextAlign.Center
            )
        }

        Spacer(modifier = Modifier.height(30.dp))

        var textState_Name by remember("$userName") {mutableStateOf("$userName")}

        TextField(
            value = textState_Name,
            onValueChange = {newValue ->
                textState_Name = newValue
//                viewModel.updateUserData("email", newValue)
                            },
            label = {Text("Name")},
            shape = RoundedCornerShape(12.dp),
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Email),
            singleLine = true,
            colors = TextFieldDefaults.colors(
                focusedContainerColor = Background_TextField,
                unfocusedContainerColor = Background_TextField,
                unfocusedTextColor = Color.Black,
                focusedTextColor = Color.Black,
                focusedLabelColor = My_FieldText_color,
                unfocusedLabelColor = My_FieldText_color,
                focusedIndicatorColor = Color.Transparent,
                unfocusedIndicatorColor = Color.Transparent),
            modifier = Modifier.size(width = 334.dp, height = 56.dp)

        )

        Spacer(modifier = Modifier.height(36.dp))

        var textState_Email by remember("$userEmail") {mutableStateOf("$userEmail")}

        TextField(
            value = textState_Email,
            onValueChange = {newValue ->
                textState_Email = newValue
//                viewModel.updateUserData("email", newValue)
            },
            label = {Text("Email")},
            shape = RoundedCornerShape(12.dp),
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Email),
            singleLine = true,
            colors = TextFieldDefaults.colors(
                focusedContainerColor = Background_TextField,
                unfocusedContainerColor = Background_TextField,
                unfocusedTextColor = Color.Black,
                focusedTextColor = Color.Black,
                focusedLabelColor = My_FieldText_color,
                unfocusedLabelColor = My_FieldText_color,
                focusedIndicatorColor = Color.Transparent,
                unfocusedIndicatorColor = Color.Transparent),
            modifier = Modifier.size(width = 334.dp, height = 56.dp)

        )

        Spacer(modifier = Modifier.height(36.dp))

        var textState_Birthday by remember(birthday) {mutableStateOf(birthday)}

        TextField(
            value = textState_Birthday,
            onValueChange = {newValue ->
                textState_Birthday = newValue
//                viewModel.updateUserData("email", newValue)
            },
            label = {Text("Birthday")},
            shape = RoundedCornerShape(12.dp),
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
            singleLine = true,
            colors = TextFieldDefaults.colors(
                focusedContainerColor = Background_TextField,
                unfocusedContainerColor = Background_TextField,
                unfocusedTextColor = Color.Black,
                focusedTextColor = Color.Black,
                focusedLabelColor = My_FieldText_color,
                unfocusedLabelColor = My_FieldText_color,
                focusedIndicatorColor = Color.Transparent,
                unfocusedIndicatorColor = Color.Transparent),
            modifier = Modifier.size(width = 334.dp, height = 56.dp)
        )

        Spacer(modifier = Modifier.height(36.dp))

        var textState_Height by remember(height) {mutableStateOf("${height}")}
        var textState_Weight by remember(weight) {mutableStateOf("${weight}")}
        
        Box(
            modifier = Modifier.fillMaxWidth()
        ){
//            var onValueChange = {textState_Height}

            TextField(
                value = textState_Height,
                onValueChange = {newValue ->
                    val digits = newValue.filter{ it.isDigit() }.take(3)
                    textState_Height = digits
//                viewModel.updateUserData("email", newValue)
                },
                label = {Text("Height (cm)")},
                shape = RoundedCornerShape(12.dp),
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                singleLine = true,
                colors = TextFieldDefaults.colors(
                    focusedContainerColor = Background_TextField,
                    unfocusedContainerColor = Background_TextField,
                    unfocusedTextColor = Color.Black,
                    focusedTextColor = Color.Black,
                    focusedLabelColor = My_FieldText_color,
                    unfocusedLabelColor = My_FieldText_color,
                    focusedIndicatorColor = Color.Transparent,
                    unfocusedIndicatorColor = Color.Transparent),
                modifier = Modifier.size(width = 160.dp, height = 56.dp),
                trailingIcon = {
                    Column(
                        modifier = Modifier.padding(8.dp),
                        verticalArrangement = Arrangement.Center
                    ) {
                        Icon(
                            painter = painterResource(R.drawable.plus_strelka),
                            contentDescription = "Увеличить",
                            modifier = Modifier
                                .clickable {
                                    val current = textState_Height.toIntOrNull() ?: 0
                                    textState_Height = (current+1).coerceAtMost(250).toString()
                                }
                        )

                        Spacer(modifier = Modifier.height(5.dp))

                        Icon(
                            painter = painterResource(R.drawable.minus_strelka),
                            contentDescription = "Уменьшить",
                            modifier = Modifier
                                .clickable {
                                    val current = textState_Height.toIntOrNull() ?: 0
                                    textState_Height = (current-1).coerceAtLeast(0).toString()
                                }
                        )
                    }
                }

            )


            TextField(
                value = textState_Weight,
                onValueChange = {newValue ->
//                    textState_Weight = newValue
                    val digits = newValue.filter{ it.isDigit() }.take(3)
                    textState_Weight = digits
//                viewModel.updateUserData("email", newValue)
                },
                label = {Text("Weight (kg)")},
                shape = RoundedCornerShape(12.dp),
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                singleLine = true,
                colors = TextFieldDefaults.colors(
                    focusedContainerColor = Background_TextField,
                    unfocusedContainerColor = Background_TextField,
                    unfocusedTextColor = Color.Black,
                    focusedTextColor = Color.Black,
                    focusedLabelColor = My_FieldText_color,
                    unfocusedLabelColor = My_FieldText_color,
                    focusedIndicatorColor = Color.Transparent,
                    unfocusedIndicatorColor = Color.Transparent),
                modifier = Modifier
                    .padding(start = 176.dp)
                    .size(width = 160.dp, height = 56.dp),
                trailingIcon = {
                    Column(
                        modifier = Modifier.padding(8.dp),
                        verticalArrangement = Arrangement.Center
                    ) {
                        Icon(
                            painter = painterResource(R.drawable.plus_strelka),
                            contentDescription = "Увеличить",
                            modifier = Modifier
                                .clickable {
                                    val current = textState_Weight.toIntOrNull() ?: 0
                                    textState_Weight = (current+1).coerceAtMost(300).toString()
                                }
                        )

                        Spacer(modifier = Modifier.height(5.dp))

                        Icon(
                            painter = painterResource(R.drawable.minus_strelka),
                            contentDescription = "Уменьшить",
                            modifier = Modifier
                                .clickable {
                                    val current = textState_Weight.toIntOrNull() ?: 0
                                    textState_Weight = (current-1).coerceAtLeast(0).toString()
                                }
                        )
                    }
                }
            )
        }

        Spacer(modifier = Modifier.height(16.dp))

        Text(
            "Gender",
            color = TextColor_Body,
            fontWeight = FontWeight.Medium,
            fontSize = 14.sp
        )

        Spacer(modifier = Modifier.height(8.dp))

        var isSelectedMale by remember(gender) {mutableStateOf(gender)}


        Box(
            modifier = Modifier.fillMaxWidth()
        ){
            Button(
                onClick = {
                    isSelectedMale = true
                },
                border = if(isSelectedMale) BorderStroke(1.dp, ColorPressNavBar) else BorderStroke(1.dp, White),
                colors = ButtonDefaults.buttonColors(ButtonColorGender),
                modifier = Modifier.size(width = 160.dp, height = 56.dp),
                shape = RoundedCornerShape(12.dp),
            ){
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Image(
                        painter = painterResource(
                            id = if (isSelectedMale) R.drawable.icon_male_press else R.drawable.icon_male),
                        contentDescription = "Gender Male"
                    )

                    Spacer(modifier = Modifier.width(15.dp))

                    Text(
                        "Male",
                        color = if(isSelectedMale) ColorPressNavBar else TextColor_Body
                    )
                }
            }

            Button(
                onClick = {
                    isSelectedMale = false
                },
                border = if(!isSelectedMale) BorderStroke(1.dp, ColorPressNavBar) else BorderStroke(1.dp, White),
                colors = ButtonDefaults.buttonColors(ButtonColorGender),
                modifier = Modifier
                    .padding(start = 176.dp)
                    .size(width = 160.dp, height = 56.dp),
                shape = RoundedCornerShape(12.dp),
            ){
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Image(
                        painter = painterResource(
                            id = if (!isSelectedMale) R.drawable.icon_woman_press else R.drawable.icon_woman),
                        contentDescription = "Gender Male"
                    )

                    Spacer(modifier = Modifier.width(15.dp))

                    Text(
                        "Woman",
                        color = if(!isSelectedMale) ColorPressNavBar else TextColor_Body
                    )
                }
            }
        }

        Spacer(modifier = Modifier.height(16.dp))

        Row{
            Button(
                onClick = {
                    textState_Weight.toIntOrNull()?.let{
                        viewModel.saveWeight(it)
                    }

                    textState_Height.toIntOrNull()?.let{
                        viewModel.saveHeight(it)
                    }

                    viewModel.saveGender(isSelectedMale)
                    viewModel.saveBirthday(textState_Birthday)

                },
                colors = ButtonDefaults.buttonColors(ButtonColorMain),
                modifier = Modifier
                    .height(56.dp)
                    .fillMaxWidth()
//                    .size(width = 334.dp, height = 56.dp)
                    .align(Alignment.Bottom),
                shape = RoundedCornerShape(12.dp)
            ){
                Text(
                    "Save",
                    color = Color.White,
                    fontWeight = FontWeight.Bold
                )
            }
        }
    }
}

@Composable()
fun TermAndConditions(
    onNavigateToBack: () -> Unit
){
    Column(
        modifier = Modifier
            .padding(top = 16.dp, start = 24.dp, end = 24.dp)
            .fillMaxSize()
            .testTag("terms_screen")
    ) {
        Box {
            Image(
                painter = painterResource(R.drawable.back_button),
                contentDescription = "Back",
                modifier = Modifier
                    .size(24.dp)
                    .clickable(
                        indication = null,
                        interactionSource = remember { MutableInteractionSource() })
                    {
                        onNavigateToBack()
                    }
            )

            Text(
                "Term and conditions",
                fontWeight = FontWeight.Bold,
                fontSize = 18.sp,
                modifier = Modifier.fillMaxWidth(),
                textAlign = TextAlign.Center
            )
        }

        Spacer(modifier = Modifier.height(33.dp))

        Text(
            "1. Acceptance of Terms",
            fontWeight = FontWeight.Medium,
            fontSize = 14.sp,
            color = Black
        )

        Spacer(modifier = Modifier.height(12.dp))

        Text(
            "By downloading, installing, accessing, or using Clarity, you agree to comply with and be bound by these terms and conditions, along with our Privacy Policy. If you do not agree with any part of these terms, you may not use our app.",
            fontWeight = FontWeight.Normal,
            fontSize = 12.sp,
            color = Black
        )

        Spacer(modifier = Modifier.height(27.dp))

        Text(
            "2. License",
            fontWeight = FontWeight.Medium,
            fontSize = 14.sp,
            color = Black
        )

        Spacer(modifier = Modifier.height(12.dp))

        Text(
            "We grant you a non-exclusive, non-transferable, limited license to use Clarity solely for your personal, non-commercial purposes, subject to these terms and the applicable app store's terms of service.",
            fontWeight = FontWeight.Normal,
            fontSize = 12.sp,
            color = Black
        )

        Spacer(modifier = Modifier.height(27.dp))

        Text(
            "3. User Account",
            fontWeight = FontWeight.Medium,
            fontSize = 14.sp,
            color = Black
        )

        Spacer(modifier = Modifier.height(12.dp))

        Text(
            "Some features of Clarity may require you to create an account. You are responsible for maintaining the confidentiality of your account information and for all activities that occur under your account. You agree to provide accurate, current, and complete information during the registration process and to update such information to keep it accurate, current, and complete.",
            fontWeight = FontWeight.Normal,
            fontSize = 12.sp,
            color = Black
        )

        Spacer(modifier = Modifier.height(27.dp))

        Text(
            "4. Use of the App",
            fontWeight = FontWeight.Medium,
            fontSize = 14.sp,
            color = Black
        )

        Spacer(modifier = Modifier.height(12.dp))

        Text(
            "You agree to use Clarity only for lawful purposes and in a manner consistent with all applicable local, national, and international laws and regulations.",
            fontWeight = FontWeight.Normal,
            fontSize = 12.sp,
            color = Black
        )
    }
}

@Composable()
fun PrivacyPolicy(
    onNavigateToBack: () -> Unit
){
    Column(
        modifier = Modifier
            .padding(top = 16.dp, start = 24.dp, end = 24.dp)
            .fillMaxSize()
            .testTag("privacy_screen")
    ) {
        Box {
            Image(
                painter = painterResource(R.drawable.back_button),
                contentDescription = "Back",
                modifier = Modifier
                    .size(24.dp)
                    .clickable(
                        indication = null,
                        interactionSource = remember { MutableInteractionSource() })
                    {
                        onNavigateToBack()
                    }
            )

            Text(
                "Privacy Policy",
                fontWeight = FontWeight.Bold,
                fontSize = 18.sp,
                modifier = Modifier.fillMaxWidth(),
                textAlign = TextAlign.Center
            )
        }

        Spacer(modifier = Modifier.height(33.dp))

        Text(
            "This document confirms that the Clarity application, created for demonstration purposes as part of a professional developer portfolio, collects only the minimum user data: name (for interface personalization) and email address (for authentication and account creation). All collected data is used solely to ensure the core functionality of the application, is stored in a secured demo database, will never be sold, traded, or disclosed to third parties, and can be completely deleted at any time upon the user's request.",
            fontWeight = FontWeight.Normal,
            fontSize = 16.sp,
            color = Black
        )
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable()
fun OpenTrack(
    mainImageTrack: Int,
    mainTitleTrack: String,
    mainTrack: Int,
    onNavigateToBack: () -> Unit,
    viewModel: MediaViewModel = viewModel()
){
//    val context = LocalContext.current
    val state by viewModel.state.collectAsState()

    LaunchedEffect(Unit) {
        val uri = RawResourceDataSource.buildRawResourceUri(mainTrack)
        viewModel.loadTrack(uri)
    }

    var isDragging by remember { mutableStateOf(false) }
    var dragPosition by remember { mutableFloatStateOf(0f) }

    Image(
        painter = painterResource(mainImageTrack),
        contentDescription = "Image Track",
        modifier = Modifier.fillMaxSize(),
        contentScale = ContentScale.Crop
    )

    Image(
        painter = painterResource(R.drawable.front_open_track),
        contentDescription = "",
        modifier = Modifier.fillMaxSize(),
        contentScale = ContentScale.Crop
    )

    Box(
        modifier = Modifier
            .fillMaxSize()
            .testTag("track_open_screen")
    ){
        Image(
            painter = painterResource(R.drawable.button_back_open_track),
            contentDescription = "",
            modifier = Modifier
                .padding(start = 24.dp, top = 24.dp)
                .clickable(
                    indication = null,
                    interactionSource = remember { MutableInteractionSource() })
                {
                    onNavigateToBack()
                }

        )

        Image(
            painter = painterResource(R.drawable.bottom_player),
            contentDescription = "",
            modifier = Modifier
                .fillMaxWidth()
                .align(Alignment.BottomCenter),
            contentScale = ContentScale.FillWidth
        )

        Text(
            text = mainTitleTrack,
            color = White,
            fontWeight = FontWeight.Bold,
            fontSize = 24.sp,
            modifier = Modifier
                .fillMaxWidth()
                .align(Alignment.BottomCenter)
                .padding(bottom = 118.dp),
            textAlign = TextAlign.Center
        )

        Slider(
            value = if(isDragging) dragPosition else state.positionMS.toFloat(),
            onValueChange = { newValue ->
                isDragging = true
                dragPosition = newValue
            },
            onValueChangeFinished = {
                viewModel.seekTo(dragPosition.toLong())
                isDragging = false
            },
            valueRange = 0f..state.durationMS.coerceAtLeast(1L).toFloat(),
            modifier = Modifier
                .width(240.dp)
                .align(Alignment.BottomCenter)
                .padding(bottom = 60.dp),
            colors = SliderDefaults.colors(
                thumbColor = ColorPressNavBar,
                activeTrackColor = ColorPressNavBar,
                inactiveTrackColor = ColorNonPressNavBar
            ),
            thumb = {
                Box(
                    modifier = Modifier
                        .size(14.dp)
                        .background(ColorPressNavBar, CircleShape)
                )
            },
            track = { sliderState ->
                SliderDefaults.Track(
                    sliderState = sliderState,
                    modifier = Modifier.height(5.dp),
                    thumbTrackGapSize = 0.dp,
                    drawStopIndicator = null,
                    colors = SliderDefaults.colors(
                        activeTrackColor = ColorPressNavBar,
                        inactiveTrackColor = ColorNonPressNavBar)
                )
            }
        )

        Row(
            modifier = Modifier
                .width(240.dp)
                .align(Alignment.BottomCenter)
                .padding(bottom = 41.dp),
            horizontalArrangement = Arrangement.SpaceBetween
        ){
            Text(formatTime(if (isDragging) dragPosition.toLong() else state.positionMS), color = White)
            Text(formatTime(state.durationMS), color = White)
        }


        Image(
            painter = painterResource(id = if (state.isPlaying) R.drawable.button_pause else R.drawable.button_play),
            contentDescription = "",
            modifier = Modifier
                .align(Alignment.Center)
                .clickable(
                    indication = null,
                    interactionSource = remember { MutableInteractionSource() })
                {
                    viewModel.tooglePlayer()
                }
        )
    }

}

fun formatTime(ms: Long): String {
    val totalSeconds = ms / 1000
    val minutes = totalSeconds / 60
    val seconds = totalSeconds % 60
    return "%d:%02d".format(minutes, seconds)
}

@Composable()
@Preview(showBackground = true)
fun Any_Preview() {
    ClarityTheme {
//        MyAccount()
    }
}