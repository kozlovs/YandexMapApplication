package ru.kozlovss.yandexmapapplication.ui.places

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import ru.kozlovss.yandexmapapplication.databinding.FragmentPlacesBinding
import ru.kozlovss.yandexmapapplication.dto.Place
import ru.kozlovss.yandexmapapplication.ui.OnInteractionListener
import ru.kozlovss.yandexmapapplication.ui.adapter.PlacesAdapter

class PlacesFragment : Fragment() {

    private val viewModel: PlacesViewModel by viewModels(
        ownerProducer = ::requireParentFragment
    )
    private var _binding: FragmentPlacesBinding? = null
    private val binding get() = _binding!!
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentPlacesBinding.inflate(inflater, container, false)
        val adapter = PlacesAdapter(object : OnInteractionListener {
            override fun onRemove(place: Place) {
                viewModel.removeById(place.id)
            }

            override fun onToPlace(place: Place) {
                // TODO: переход на экран с меткой
            }

            override fun onEdit(place: Place) {
                viewModel.edit(place)
            }
        })

        binding.list.adapter = adapter

//        observe()

        return binding.root
    }

//    private fun observe() {
//        TODO("Not yet implemented")
//    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}