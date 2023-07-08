package com.catalin.mvvmanimalslist.view

import android.os.Bundle
import android.widget.Toast
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.catalin.mvvmanimalslist.model.Animal
import com.catalin.mvvmanimalslist.ui.theme.AnimalListTheme
import dagger.hilt.android.AndroidEntryPoint

sealed class Destination (val route: String) {
    object List: Destination("list")
    object Detail: Destination("detail/{animalName}") {
        fun createRoute(animalName: String) = "detail/$animalName"
    }
}

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    private val mainViewModel by viewModels<MainViewModel>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            AnimalListTheme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colors.background
                ) {
                    val navController = rememberNavController()
                    NavigationAppHost(navController = navController, vm = mainViewModel)
                }
            }
        }
    }
}

@Composable
fun NavigationAppHost(navController: NavHostController, vm: MainViewModel) {
    val ctx = LocalContext.current

    val onButtonClick: () -> Unit = {
        vm.getAnimals()
    }

    val onClickCallback: (animal: Animal) -> Unit = { animal ->
        animal.name?.let { name -> navController.navigate(Destination.Detail.createRoute(name)) }
    }

    NavHost(navController = navController, startDestination = "list") {
        composable(Destination.List.route) {
            ListFragmentComposable(
                vm = vm,
                buttonClick = onButtonClick,
                animalOnClick = onClickCallback
            )
        }
        composable(Destination.Detail.route) { navBackStackEntry ->
            val name = navBackStackEntry.arguments?.getString("animalName")
            if(name == null)
                Toast.makeText(ctx, "Animal name is required", Toast.LENGTH_SHORT).show()
            else {
                val animal = vm.getAnimal(name)
                animal?.let {
                    AnimalDetailFragmentComposable(animal = it)
                }
            }
        }
    }
}