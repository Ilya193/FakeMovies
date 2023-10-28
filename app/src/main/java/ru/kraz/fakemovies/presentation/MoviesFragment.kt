package ru.kraz.fakemovies.presentation

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.setFragmentResult
import dagger.hilt.android.AndroidEntryPoint
import ru.kraz.fakemovies.R
import ru.kraz.fakemovies.databinding.FragmentMoviesBinding
import ru.kraz.fakemovies.presentation.MovieUiState.Loading.isSuccess

@AndroidEntryPoint
class MoviesFragment : Fragment() {
    private var _binding: FragmentMoviesBinding? = null
    private val binding: FragmentMoviesBinding
        get() = _binding!!

    private val mainViewModel by activityViewModels<MainViewModel>()

    private val adapter = MoviesAdapter { movie ->
        mainViewModel.setMovie(movie)
        parentFragmentManager.beginTransaction()
            .setCustomAnimations(
                R.anim.slide_in,
                R.anim.fade_out,
                R.anim.fade_in,
                R.anim.slide_out
            )
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

        binding.chipGroup.setOnCheckedStateChangeListener { group, checkedIds ->
            val genres = mutableListOf<String>()
            checkedIds.forEach { chipId ->
                if (chipId == binding.horrorChip.id) genres.add("ужасы")
                if (chipId == binding.comedyChip.id) genres.add("комедия")
                if (chipId == binding.adventuresChip.id) genres.add("приключения")
                if (chipId == binding.fantasticChip.id) genres.add("фантастика")
                if (chipId == binding.thrillerChip.id) genres.add("триллер")
                if (chipId == binding.dramaChip.id) genres.add("драма")
                if (chipId == binding.crimeChip.id) genres.add("криминал")
                if (chipId == binding.actionChip.id) genres.add("боевик")
                if (chipId == binding.musicalChip.id) genres.add("мюзикл")
                if (chipId == binding.detectiveChip.id) genres.add("детектив")
                if (chipId == binding.biographyChip.id) genres.add("биография")
                if (chipId == binding.melodramaChip.id) genres.add("мелодрама")
            }
            mainViewModel.moviesFilter(genres)
        }
    }

    private fun settingRecyclerView() {
        binding.rvMovies.adapter = adapter
    }

    private fun settingViewModel() {
        mainViewModel.fetchMovies()
        mainViewModel.uiState.observe(viewLifecycleOwner) { state ->
            binding.loading.visibility =
                if (state is MovieUiState.Loading) View.VISIBLE else View.GONE
            binding.containerError.visibility =
                if (state is MovieUiState.Error) View.VISIBLE else View.GONE
            binding.tvError.text = if (state is MovieUiState.Error) state.msg else ""
            binding.containerGenres.visibility =
                if (state.isSuccess()) View.VISIBLE else View.GONE
            binding.rvMovies.visibility =
                if (state.isSuccess()) View.VISIBLE else View.GONE
            if (state is MovieUiState.Success) adapter.submitList(state.list)
            if (state is MovieUiState.SuccessFilter) adapter.submitList(state.list)
            if (state.isSuccess())
                binding.rvMovies.postDelayed({
                    binding.rvMovies.scrollToPosition(0)
                }, 10)
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}