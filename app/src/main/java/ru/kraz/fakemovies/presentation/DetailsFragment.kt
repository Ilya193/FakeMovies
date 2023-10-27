package ru.kraz.fakemovies.presentation

import android.os.Bundle
import android.text.method.ScrollingMovementMethod
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.os.bundleOf
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.setFragmentResult
import coil.load
import ru.kraz.fakemovies.R
import ru.kraz.fakemovies.databinding.FragmentDetailsBinding
import ru.kraz.fakemovies.databinding.FragmentMoviesBinding

class DetailsFragment : Fragment() {
    private var _binding: FragmentDetailsBinding? = null
    private val binding: FragmentDetailsBinding
        get() = _binding!!

    private val mainViewModel by activityViewModels<MainViewModel>()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View? {
        _binding = FragmentDetailsBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setFragmentResult("actionBack", bundleOf("button" to true))
        binding.tvDescription.movementMethod = ScrollingMovementMethod()
        settingViewModel()
    }

    private fun settingViewModel() {
        mainViewModel.movie.observe(viewLifecycleOwner) {
            it.getContentOrNot { movie ->
                binding.image.load(movie.imageUrl) {
                    error(R.drawable.ic_error)
                }
                binding.tvName.text = movie.localizedName
                binding.tvYear.text = context?.getString(R.string.year) + " " + movie.year.toString()
                binding.tvRating.text = context?.getString(R.string.rating) + " " + movie.rating.toString()
                binding.tvDescription.text = movie.description
            }
        }
    }

    companion object {
        fun newInstance() =
            DetailsFragment()
    }
}