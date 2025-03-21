package com.example.assignment2_yang.view;

import android.os.Bundle;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.example.assignment2_yang.adapter.MovieAdapter;
import com.example.assignment2_yang.databinding.ActivityMainBinding;
import com.example.assignment2_yang.viewmodel.MovieViewModel;

public class MainActivity extends AppCompatActivity {

    MovieViewModel viewModel;// ViewModel to handle movie data
    ActivityMainBinding binding;// View binding for accessing UI elements
    private MovieAdapter movieAdapter;// Adapter for displaying movie data
    private final String API_KEY = "3ddf33ee";// OMDb API Key

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // Initialize View Binding
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        // Initialize ViewModel
        viewModel = new ViewModelProvider(this).get(MovieViewModel.class);

        // Set up RecyclerView with Adapter
        movieAdapter = new MovieAdapter();
        binding.recyclerView.setLayoutManager(new LinearLayoutManager(this));
        binding.recyclerView.setAdapter(movieAdapter);

        // Set click listener to open MovieDetailActivity on item click
        movieAdapter.setOnMovieClickListener(movie -> {
            // Get detailed movie information using imdbID
            viewModel.getMovieDetail(movie.getImdbID(), API_KEY);

            // Observe the detailed movie data
            viewModel.getMovieDetailData().observe(this, detailedMovie -> {
                if (detailedMovie != null) {
                    // Launch detail activity with complete movie information
                    MovieDetailActivity.enter(this, detailedMovie);
                }
            });
        });

        // Set up the search button click listener
        binding.searchButton.setOnClickListener(view -> {
            String query = binding.searchEditText.getText().toString().trim();
            if (!query.isEmpty()) {
                // Trigger movie search with the entered query
                viewModel.searchMovies(query, API_KEY);
            } else {
                // Show toast message if the input is empty
                Toast.makeText(this, "Please enter a movie title.", Toast.LENGTH_SHORT).show();
            }
        });

        // Observe movie data from ViewModel
        viewModel.getMovieDataList().observe(this, movies -> {
            if (movies != null && !movies.isEmpty()) {

                // Update RecyclerView with movie data
                movieAdapter.setMovies(movies);

            } else {
                // Show toast message if no movies are found
                Toast.makeText(this, "Can not find movie", Toast.LENGTH_SHORT).show();
            }
        });
    }
}
