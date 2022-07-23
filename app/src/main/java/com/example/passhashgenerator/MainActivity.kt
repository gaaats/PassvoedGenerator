package com.example.passhashgenerator

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.activity.viewModels
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.NavController
import androidx.navigation.findNavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.setupActionBarWithNavController
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import javax.inject.Singleton

@AndroidEntryPoint
@Singleton
class MainActivity : AppCompatActivity() {

    private val mainVievModel by viewModels<MainVievModel>()

    private val navController by lazy {
        val navHostFrag =
            supportFragmentManager.findFragmentById(R.id.fragmentContainerView) as NavHostFragment
        navHostFrag.navController
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        installSplashScreen().apply {
            this.setKeepOnScreenCondition{
                mainVievModel.isSplashScreenActive.value
            }
        }
        Log.d("MY_TAG", "vievmodel is${mainVievModel}")
        setContentView(R.layout.activity_main)
        setupActionBarWithNavController(navController)
    }


    override fun onSupportNavigateUp(): Boolean {
        return navController.navigateUp() || super.onSupportNavigateUp()
    }
}