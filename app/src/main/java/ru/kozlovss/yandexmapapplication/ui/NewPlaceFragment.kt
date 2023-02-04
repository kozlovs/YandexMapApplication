package ru.kozlovss.yandexmapapplication.ui

import android.app.Dialog
import android.os.Bundle
import android.widget.Toast
import androidx.core.os.bundleOf
import androidx.fragment.app.DialogFragment
import androidx.fragment.app.viewModels
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import ru.kozlovss.yandexmapapplication.R
import ru.kozlovss.yandexmapapplication.databinding.FragmentNewPlaceBinding
import ru.kozlovss.yandexmapapplication.dto.Place
import ru.kozlovss.yandexmapapplication.viewmodel.PlacesViewModel

class NewPlaceFragment : DialogFragment() {


    companion object {
        private const val ID_KEY = "ID_KEY"
        private const val LAT_KEY = "LAT_KEY"
        private const val LONG_KEY = "LONG_KEY"
        fun newInstance(lat: Double, long: Double, id: Long? = null) = NewPlaceFragment().apply {
            arguments = bundleOf(LAT_KEY to lat, LONG_KEY to long, ID_KEY to id)
        }
    }

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        val viewModel by viewModels<PlacesViewModel>()
        val view = FragmentNewPlaceBinding.inflate(layoutInflater).inputView
        return MaterialAlertDialogBuilder(requireContext())
            .setView(view)
            .setTitle(getString(R.string.enter_name))
            .setPositiveButton(android.R.string.ok) { _, _ ->
                val text = view.editText?.text?.toString()?.takeIf { it.isNotBlank() } ?: run {
                    Toast.makeText(requireContext(), "Name is empty", Toast.LENGTH_SHORT).show()
                    return@setPositiveButton
                }
                viewModel.insert(
                    Place(
                        id = requireArguments().getLong(ID_KEY),
                        latitude = requireArguments().getDouble(LAT_KEY),
                        longitude = requireArguments().getDouble(LONG_KEY),
                        name = text,
                    )
                )
            }
            .create()
    }
}