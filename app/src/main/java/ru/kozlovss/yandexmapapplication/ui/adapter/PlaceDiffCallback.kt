package ru.kozlovss.yandexmapapplication.ui.adapter

import androidx.recyclerview.widget.DiffUtil
import ru.kozlovss.yandexmapapplication.dto.Place

class PlaceDiffCallback : DiffUtil.ItemCallback<Place>() {
    override fun areItemsTheSame(oldItem: Place, newItem: Place) = oldItem.id == newItem.id

    override fun areContentsTheSame(oldItem: Place, newItem: Place) = oldItem == newItem
}