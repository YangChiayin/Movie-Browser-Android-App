package com.example.assignment2_yang.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.assignment2_yang.R;
import com.example.assignment2_yang.model.MovieModel;

import java.util.ArrayList;
import java.util.List;

// Adapter class for managing and displaying movie items in a RecyclerView
public class MovieAdapter extends RecyclerView.Adapter<MovieAdapter.MovieViewHolder> {

    private List<MovieModel> movies = new ArrayList<>();
    private OnMovieClickListener onMovieClickListener;

    // Sets the movie list and refreshes the RecyclerView
    public void setMovies(List<MovieModel> movies) {
        this.movies = movies;
        notifyDataSetChanged();
    }

    // Sets the listener for handling item clicks (once go back button click)
    public void setOnMovieClickListener(OnMovieClickListener listener) {
        this.onMovieClickListener = listener;
    }

    @NonNull
    @Override
    public MovieViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.layout_movie_item, parent, false);
        return new MovieViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MovieViewHolder holder, int position) {
        MovieModel movie = movies.get(position);

        // Bind movie data to the views
        holder.title.setText(movie.getTitle());
        holder.info.setText(movie.getYear());

        holder.rating.setVisibility(View.GONE);
        holder.description.setVisibility(View.GONE);

        // Load the movie poster image using Glide
        Glide.with(holder.itemView.getContext())
                .load(movie.getPoster())
                .placeholder(R.mipmap.ic_launcher)
                .into(holder.poster);

        // Handle item click event
        holder.itemView.setOnClickListener(view -> {
            if (onMovieClickListener != null) {
                // Pass the imdbID to get detailed information
                onMovieClickListener.onGoBackClicked(movie);
            }
        });
    }

    @Override
    public int getItemCount() {
        return movies.size();
    }

    // ViewHolder class to hold references to the views
    static class MovieViewHolder extends RecyclerView.ViewHolder {
        TextView title, info, rating, description;
        ImageView poster;

        MovieViewHolder(View itemView) {
            super(itemView);
            title = itemView.findViewById(R.id.movie_title);
            info = itemView.findViewById(R.id.movie_info);
            rating = itemView.findViewById(R.id.movie_rating);
            description = itemView.findViewById(R.id.movie_description);
            poster = itemView.findViewById(R.id.movie_poster);
        }
    }

    // Interface for handling the "Go Back" button click event
    public interface OnMovieClickListener {
        void onGoBackClicked(MovieModel model);
    }
}
