package com.catalin.mvvmanimalslist.view

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import com.catalin.mvvmanimalslist.R
import com.catalin.mvvmanimalslist.api.AnimalService
import com.catalin.mvvmanimalslist.model.Animal
import com.catalin.mvvmanimalslist.ui.theme.Purple700

@Composable
fun AnimalDetailFragmentComposable(animal: Animal) {
    val url = AnimalService.BASE_URL + animal.image
    Column(horizontalAlignment = Alignment.CenterHorizontally, modifier = Modifier.fillMaxSize()) {
        AsyncImage(model = url, contentDescription = null, modifier = Modifier.fillMaxWidth())
        AnimalDetails(animal = animal)
    }
}

@Composable
fun AnimalDetails(animal: Animal) {
    val titleSize = dimensionResource(id = R.dimen.std_title).value.sp
    val textSize = dimensionResource(id = R.dimen.std_text).value.sp

    Column(horizontalAlignment = Alignment.CenterHorizontally) {
        Text(
            text = animal.name ?: "",
            fontSize = titleSize,
            fontWeight = FontWeight.Bold,
            color = Purple700
        )
        Text(
            text = stringResource(id = R.string.detail_location, animal.location ?: ""),
            fontSize = textSize
        )
        Text(
            text = stringResource(id = R.string.detail_lifespan, animal.lifespan ?: ""),
            fontSize = textSize
        )
        Text(
            text = stringResource(id = R.string.detail_diet, animal.diet ?: ""),
            fontSize = textSize
        )
    }
}