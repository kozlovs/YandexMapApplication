package ru.kozlovss.yandexmapapplication.ui.places

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import ru.kozlovss.yandexmapapplication.databinding.FragmentPlacesBinding
import ru.kozlovss.yandexmapapplication.dto.Place
import ru.kozlovss.yandexmapapplication.ui.OnInteractionListener
import ru.kozlovss.yandexmapapplication.adapter.PlacesAdapter

class PlacesFragment : Fragment() {

    private val viewModel by viewModels<PlacesViewModel>()
    private var _binding: FragmentPlacesBinding? = null
    private val binding get() = _binding!!
    private var _adapter: PlacesAdapter? = null
    private val adapter get() = _adapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentPlacesBinding.inflate(inflater, container, false)
        _adapter = PlacesAdapter(object : OnInteractionListener {
            override fun onRemove(place: Place) {
                viewModel.removeById(place.id)
            }

            override fun onToPlace(place: Place) {
                // TODO: переход на экран с меткой
            }
        })

        binding.list.adapter = adapter

        observe()

        return binding.root
    }

    private fun observe() {
        lifecycleScope.launchWhenCreated {
            viewModel.places.collect {
                adapter?.submitList(it)
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
        _adapter = null
    }
}