package com.example.assignment2_yang.view;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.bumptech.glide.Glide;
import com.example.assignment2_yang.R;
import com.example.assignment2_yang.databinding.ActivityMovieDetailBinding;
import com.example.assignment2_yang.model.MovieModel;

public class MovieDetailActivity extends AppCompatActivity {
    private static String KEY_MOVIE_DATA = "KeyMovieData";// Key for passing movie data
    ActivityMovieDetailBinding binding;// View binding for UI elements
    private MovieModel model;// Movie model instance

    /**
     * Static method to launch MovieDetailActivity with movie data.
     * @param activity The current activity
     * @param model The movie data to display
     */

    static public void enter(Activity activity, MovieModel model) {
        Intent intent = new Intent(activity, MovieDetailActivity.class);
        intent.putExtra(KEY_MOVIE_DATA, model);
        activity.startActivity(intent);
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // Initialize View Binding
        binding = ActivityMovieDetailBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        // Retrieve movie data from the intent
        model = (MovieModel) getIntent().getSerializableExtra(KEY_MOVIE_DATA);

        // Set up the "Go Back" button click listener
        binding.goBackButton.setOnClickListener(view -> finish());

        // Populate the UI with movie details
        binding.movieTitle.setText(model.getTitle());
        binding.movieRating.setText(model.getImdbRating());
        binding.movieInfo.setText(model.getYear() + " " + model.getRuntime() + " " + model.getGenre());
        binding.movieDescription.setText(model.getPlot());

        // Load the movie poster using Glide with a placeholder
        Glide.with(getApplicationContext())
                .load(model.getPoster())
                .placeholder(R.mipmap.ic_launcher)  // Placeholder image while loading
                .into(binding.moviePoster);
    }
}
