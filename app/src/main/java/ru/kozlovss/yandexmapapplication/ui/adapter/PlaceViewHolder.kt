package ru.kozlovss.yandexmapapplication.ui.adapter

import androidx.recyclerview.widget.RecyclerView
import ru.kozlovss.yandexmapapplication.databinding.CardPlaceBinding
import ru.kozlovss.yandexmapapplication.dto.Place
import ru.kozlovss.yandexmapapplication.ui.OnInteractionListener

class PlaceViewHolder(
    private val binding: CardPlaceBinding,
    private val onInteractionListener: OnInteractionListener
): RecyclerView.ViewHolder(binding.root) {

    fun bind(place: Place) {
        binding.apply {
            name.text = place.name
            latitude.text = place.latitude.toString()
            longitude.text = place.longitude.toString()
        }
        setListeners(binding, place)
    }

    private fun setListeners(binding: CardPlaceBinding, place: Place) = with(binding) {
        root.setOnClickListener { onInteractionListener.onToPlace(place) }
        deleteButton.setOnClickListener { onInteractionListener.onRemove(place) }
    }
}