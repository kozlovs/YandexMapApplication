package ru.kozlovss.yandexmapapplication.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter
import ru.kozlovss.yandexmapapplication.databinding.CardPlaceBinding
import ru.kozlovss.yandexmapapplication.dto.Place
import ru.kozlovss.yandexmapapplication.ui.OnInteractionListener

class PlacesAdapter(private val onInteractionListener: OnInteractionListener) :
    ListAdapter<Place, PlaceViewHolder>(PlaceDiffCallback()) {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PlaceViewHolder {
        val binding = CardPlaceBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return PlaceViewHolder(binding, onInteractionListener)
    }

    override fun onBindViewHolder(holder: PlaceViewHolder, position: Int) {
        val place = getItem(position)
        holder.bind(place)
    }
}