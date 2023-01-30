package ru.kozlovss.yandexmapapplication.ui

import ru.kozlovss.yandexmapapplication.dto.Place

interface OnInteractionListener {
    fun onRemove(place: Place)

    fun onToPlace(place: Place)
}