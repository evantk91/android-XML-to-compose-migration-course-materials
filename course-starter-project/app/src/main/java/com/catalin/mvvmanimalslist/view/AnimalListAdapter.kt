package com.catalin.mvvmanimalslist.view

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.dp
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import coil.compose.AsyncImage
import com.bumptech.glide.Glide
import com.catalin.mvvmanimalslist.R
import com.catalin.mvvmanimalslist.api.AnimalService
import com.catalin.mvvmanimalslist.databinding.AnimalItemBinding
import com.catalin.mvvmanimalslist.model.Animal

class AnimalListAdapter(
    private val animals: ArrayList<Animal>,
    private val clickCallback: (animal: Animal) -> Unit
) :
    RecyclerView.Adapter<AnimalListAdapter.DataViewHolder>() {

    fun newAnimals(newAnimals: List<Animal>) {
        animals.clear()
        animals.addAll(newAnimals)
        notifyDataSetChanged()
    }

    class DataViewHolder(
        private val binding: AnimalItemBinding,
        private val clickCallback: (animal: Animal) -> Unit
    ) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(animal: Animal) {
            binding.animalName.text = animal.name
            binding.animalLocation.text = animal.location
//            Glide.with(binding.animalImage.context)
//                .load(url)
//                .into(binding.animalImage)
            binding.composeAnimalImage.setContent {
                val url = AnimalService.BASE_URL + animal.image
                ComposeAnimalImage(url = url)
            }
            binding.animalItem.setOnClickListener {
                clickCallback(animal)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): DataViewHolder {
        val binding = DataBindingUtil.inflate<AnimalItemBinding>(
            LayoutInflater.from(parent.context),
            R.layout.animal_item, parent, false
        )
        return DataViewHolder(binding, clickCallback)
    }

    override fun onBindViewHolder(holder: DataViewHolder, position: Int) {
        holder.bind(animals[position])
    }

    override fun getItemCount() = animals.size
}

