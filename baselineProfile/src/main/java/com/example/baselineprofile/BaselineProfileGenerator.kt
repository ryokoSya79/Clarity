package com.example.baselineprofile

import androidx.benchmark.macro.junit4.BaselineProfileRule
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.filters.LargeTest
import androidx.test.platform.app.InstrumentationRegistry
import androidx.test.uiautomator.By
import androidx.test.uiautomator.Direction
import androidx.test.uiautomator.Until
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

/**
 * This test class generates a basic startup baseline profile for the target package.
 *
 * We recommend you start with this but add important user flows to the profile to improve their performance.
 * Refer to the [baseline profile documentation](https://d.android.com/topic/performance/baselineprofiles)
 * for more information.
 *
 * You can run the generator with the "Generate Baseline Profile" run configuration in Android Studio or
 * the equivalent `generateBaselineProfile` gradle task:
 * ```
 * ./gradlew :app:generateReleaseBaselineProfile
 * ```
 * The run configuration runs the Gradle task and applies filtering to run only the generators.
 *
 * Check [documentation](https://d.android.com/topic/performance/benchmarking/macrobenchmark-instrumentation-args)
 * for more information about available instrumentation arguments.
 *
 * After you run the generator, you can verify the improvements running the [StartupBenchmarks] benchmark.
 *
 * When using this class to generate a baseline profile, only API 33+ or rooted API 28+ are supported.
 *
 * The minimum required version of androidx.benchmark to generate a baseline profile is 1.2.0.
 **/
@RunWith(AndroidJUnit4::class)
@LargeTest
class BaselineProfileGenerator {

    @get:Rule
    val rule = BaselineProfileRule()

    @Test
    fun generate() {
        // The application id for the running build variant is read from the instrumentation arguments.
        rule.collect(
//            packageName = InstrumentationRegistry.getArguments().getString("targetAppId")
//                ?: throw Exception("targetAppId not passed as instrumentation runner arg"),
            packageName = "com.example.clarity",

            // See: https://d.android.com/topic/performance/baselineprofiles/dex-layout-optimizations
            includeInStartupProfile = true
        ) {
            // This block defines the app's critical user journey. Here we are interested in
            // optimizing for app startup. But you can also navigate and scroll through your most important UI.

            // Start default activity for your app
            pressHome()
            startActivityAndWait()

            repeat(3) {
                device.findObject(By.res("list_genre")).apply {
                    setGestureMargin(device.displayWidth / 5)
                    fling(Direction.DOWN)
                }
                device.waitForIdle()
            }

            device.findObject(By.res("list_genre")).apply {
                setGestureMargin(device.displayWidth / 5)
                fling(Direction.UP)
            }
            device.waitForIdle()

            device.findObject(By.res("genre_card_2")).click()

            device.wait(Until.hasObject(By.res("genre_open_screen")), 5_000)
            device.waitForIdle()

            device.findObject(By.res("list_track")).apply {
                setGestureMargin(device.displayWidth / 5)
                fling(Direction.DOWN)
            }

            device.findObject(By.res("list_track")).apply {
                setGestureMargin(device.displayWidth / 5)
                fling(Direction.UP)
            }

            device.pressBack()
            device.wait(Until.hasObject(By.res("list_genre")), 2_000)

            device.findObject(By.res("discover_click")).click()
            device.wait(Until.hasObject(By.res("discover_screen")), 2_000)

            repeat(2){
                device.findObject(By.res("list_track_discover")).apply {
                    setGestureMargin(device.displayWidth / 5)
                    fling(Direction.DOWN)
                }
            }

            repeat(2){
                device.findObject(By.res("list_track_discover")).apply {
                    setGestureMargin(device.displayWidth / 5)
                    fling(Direction.UP)
                }
            }

            device.findObject(By.res("settings_click")).click()
            device.wait(Until.hasObject(By.res("settings_screen")), 2_000)

            device.findObject(By.res("my_account_click")).click()
            device.wait(Until.hasObject(By.res("my_account_screen")), 5_000)

            device.pressBack()
            device.wait(Until.hasObject(By.res("settings_screen")), 1_000)

            device.findObject(By.res("terms_click")).click()
            device.wait(Until.hasObject(By.res("terms_screen")), 3_000)

            device.pressBack()
            device.wait(Until.hasObject(By.res("settings_screen")), 1_000)

            device.findObject(By.res("privacy_click")).click()
            device.wait(Until.hasObject(By.res("privacy_screen")), 3_000)

            device.pressBack()
            device.wait(Until.hasObject(By.res("settings_screen")), 1_000)

            device.pressBack()
            device.wait(Until.hasObject(By.res("list_genre")), 5_000)


        }
    }
}