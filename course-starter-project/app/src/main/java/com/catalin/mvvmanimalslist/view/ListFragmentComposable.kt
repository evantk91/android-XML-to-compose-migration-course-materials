package com.catalin.mvvmanimalslist.view

import android.widget.Toast
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.Button
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import com.catalin.mvvmanimalslist.R
import com.catalin.mvvmanimalslist.api.AnimalService
import com.catalin.mvvmanimalslist.api.NetworkResult
import com.catalin.mvvmanimalslist.model.Animal

@Composable
fun ListFragmentComposable(
    vm: MainViewModel,
    buttonClick: () -> Unit,
    animalOnClick: (animal: Animal) -> Unit
) {
    val context = LocalContext.current
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        when(vm.result.value) {
            is NetworkResult.Initial -> {
                val btnText = stringResource(id = R.string.btn_fetch_animals)
                Button(onClick = buttonClick) {
                    Text(text = btnText)
                }
            }
            is NetworkResult.Loading -> {
                CircularProgressIndicator()
            }
            is NetworkResult.Error -> {
                Toast.makeText(context, vm.result.value.message, Toast.LENGTH_SHORT).show()
            }
            is NetworkResult.Success -> {
                val animals = vm.result.value.data ?: listOf()
                LazyColumn {
                    items(animals) { animal ->
                        Row(
                            modifier = Modifier
                                .fillMaxWidth()
                                .height(100.dp)
                                .clickable { animalOnClick.invoke(animal) }) {
                            val url = AnimalService.BASE_URL + animal.image
                            ComposeAnimalImage(url = url)
                            Column(modifier = Modifier.padding(start = 8.dp, top = 4.dp)) {
                                Text(text = animal.name ?: "", fontWeight = FontWeight.Bold)
                                Text(text = animal.location ?: "")
                            }
                        }
                    }
                }
            }
        }
    }
}

@Composable
fun ComposeAnimalImage(url: String) {
    AsyncImage(
        model = url,
        contentDescription = null,
        modifier = Modifier
            .padding(4.dp)
            .width(100.dp),
        contentScale = ContentScale.Crop
    )
}