package ru.kraz.fakemovies.presentation

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.setFragmentResult
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.GridLayoutManager
import dagger.hilt.android.AndroidEntryPoint
import ru.kraz.fakemovies.R
import ru.kraz.fakemovies.databinding.FragmentMoviesBinding

@AndroidEntryPoint
class MoviesFragment : Fragment() {
    private var _binding: FragmentMoviesBinding? = null
    private val binding: FragmentMoviesBinding
        get() = _binding!!

    private val mainViewModel by activityViewModels<MainViewModel>()

    private val adapter = MoviesAdapter { movie ->
        mainViewModel.setMovie(movie)
        parentFragmentManager.beginTransaction()
            .replace(R.id.fragmentContainer, DetailsFragment.newInstance())
            .addToBackStack(null)
            .commit()
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View? {
        _binding = FragmentMoviesBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setFragmentResult("actionBack", bundleOf("button" to false))
        settingRecyclerView()
        settingViewModel()
        settingClickListener()
    }

    private fun settingClickListener() {
        binding.btnRetry.setOnClickListener {
            mainViewModel.fetchMovies()
        }
    }

    private fun settingRecyclerView() {
        binding.rvMovies.adapter = adapter
    }

    private fun settingViewModel() {
        mainViewModel.fetchMovies()
        mainViewModel.uiState.observe(viewLifecycleOwner) {
            binding.loading.visibility = if (it is MovieUiState.Loading) View.VISIBLE else View.GONE
            binding.containerError.visibility = if (it is MovieUiState.Error) View.VISIBLE else View.GONE
            binding.tvError.text = if (it is MovieUiState.Error) it.msg else ""
            binding.rvMovies.visibility = if (it is MovieUiState.Success) View.VISIBLE else View.GONE
            if (it is MovieUiState.Success) adapter.submitList(it.list)
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    companion object {
        fun newInstance() =
            MoviesFragment()
    }
}