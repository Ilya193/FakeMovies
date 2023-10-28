package ru.kraz.fakemovies.presentation

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import coil.load
import com.example.studying.presentation.DiffUtilCallback
import ru.kraz.fakemovies.R
import ru.kraz.fakemovies.databinding.MovieItemBinding

class MoviesAdapter(
    private val onClick: (MovieUi.Success) -> Unit,
) : ListAdapter<MovieUi, MoviesAdapter.ViewHolder>(DiffUtilCallback<MovieUi>()) {
    inner class ViewHolder(private val view: MovieItemBinding) : RecyclerView.ViewHolder(view.root) {
        fun bind(item: MovieUi) {
            item as MovieUi.Success
            view.image.load(item.imageUrl) {
                error(R.drawable.ic_error)
            }
            view.tvName.text = item.name
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view =
            MovieItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        val holder = ViewHolder(view).apply {
            view.root.setOnClickListener { onClick(getItem(adapterPosition) as MovieUi.Success) }
        }
        return holder
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(getItem(position))
    }
}