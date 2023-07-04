package com.catalin.mvvmanimalslist.view

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.compose.foundation.layout.Column
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp
import androidx.fragment.app.Fragment
import com.bumptech.glide.Glide
import com.catalin.mvvmanimalslist.R
import com.catalin.mvvmanimalslist.api.AnimalService
import com.catalin.mvvmanimalslist.databinding.FragmentAnimalDetailBinding
import com.catalin.mvvmanimalslist.model.Animal
import com.catalin.mvvmanimalslist.ui.theme.Purple700


class AnimalDetailFragment : Fragment() {
    private var animal: Animal? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            animal = it.getParcelable(currentAnimal)
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val binding = FragmentAnimalDetailBinding.inflate(inflater, container, false)
            .apply {
                animal?.let {
                    val url = AnimalService.BASE_URL + it.image
                    Glide.with(detailImage.context)
                        .load(url)
                        .into(detailImage)
                    composeView.setContent {
                        AnimalDetails(animal = it)
                    }
//                    detailTitle.text = it.name
//                    detailLocation.text = getString(R.string.detail_location, it.location)
//                    detailLifespan.text = getString(R.string.detail_lifespan, it.lifespan)
//                    detailDiet.text = getString(R.string.detail_diet, it.diet)
                }
            }

        return binding.root
    }

    companion object {
        const val currentAnimal = "currentAnimal"
    }
}

@Composable
fun AnimalDetails(animal: Animal) {
    val titleSize = dimensionResource(id = R.dimen.std_title).value.sp
    val textSize = dimensionResource(id = R.dimen.std_text).value.sp

    Column(horizontalAlignment = Alignment.CenterHorizontally) {
        Text(text = animal.name ?: "", fontSize = titleSize, fontWeight = FontWeight.Bold, color = Purple700)
        Text(text = stringResource(id = R.string.detail_location, animal.location ?: ""), fontSize = textSize)
        Text(text = stringResource(id = R.string.detail_lifespan, animal.lifespan ?: ""), fontSize = textSize)
        Text(text = stringResource(id = R.string.detail_diet, animal.diet ?: ""), fontSize = textSize)
    }
}






