package com.teknolojipiri.contactsgenerator

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.teknolojipiri.contactsgenerator.generate.ContactGeneratorScreen
import com.teknolojipiri.contactsgenerator.home.HomeScreen
import com.teknolojipiri.contactsgenerator.remove.ContactRemoverScreen
import com.teknolojipiri.contactsgenerator.ui.theme.ContactsGeneratorFreeTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            ContactsGeneratorFreeTheme {

                // A surface container using the 'background' color from the theme
                Surface(modifier = Modifier.fillMaxSize(), color = MaterialTheme.colorScheme.background) {
                    CGNavHost()
                }
            }
        }
    }
}

@Composable
fun CGNavHost(
    modifier: Modifier = Modifier,
    navController: NavHostController = rememberNavController(),
    startDestination: String = "home"
) {
    NavHost(
        modifier = modifier,
        navController = navController,
        startDestination = startDestination
    ) {
        composable("home") {
            HomeScreen(onGeneratorTapped = { navController.navigate("contact-generator") },
                onDeleterTapped = { navController.navigate("contact-remover") })
        }

        composable("contact-generator") {
            ContactGeneratorScreen()
        }

        composable("contact-remover") {
            ContactRemoverScreen()
        }
    }
}


