package com.example.imdbsearchapp;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.imdbsearchapp.Adapters.CastRecyclerAdapter;
import com.example.imdbsearchapp.Listeners.OnDetailsApiListener;
import com.example.imdbsearchapp.Models.DetailApiResponse;
import com.example.imdbsearchapp.Models.Ratings;
import com.squareup.picasso.Picasso;

public class DetailsActivity extends AppCompatActivity {

    TextView movie_name_tv, movie_released_tv, movie_runtime_tv, movie_rating_tv, movie_votes_tv, movie_plot_tv;
    ImageView movie_poster_iv;
    RecyclerView recycler_view_cast;
    CastRecyclerAdapter adapter;
    RequestManager manager;
    ProgressDialog dialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_details);

        movie_name_tv = findViewById(R.id.movie_name_tv);
        movie_released_tv = findViewById(R.id.movie_released_tv);
        movie_runtime_tv = findViewById(R.id.movie_runtime_tv);
        movie_rating_tv = findViewById(R.id.movie_rating_tv);
        movie_votes_tv = findViewById(R.id.movie_votes_tv);
        movie_plot_tv = findViewById(R.id.movie_plot_tv);
        movie_poster_iv = findViewById(R.id.movie_poster_iv);
        recycler_view_cast = findViewById(R.id.recycler_view_cast);

        manager = new RequestManager(this);

        String movie_id = getIntent().getStringExtra("data");

        dialog = new ProgressDialog(this);
        dialog.setTitle("Please wait...");
        dialog.show();

        manager.searchMovieDetails(listener, movie_id);

    }

    private final OnDetailsApiListener listener = new OnDetailsApiListener() {
        @Override
        public void onResponse(DetailApiResponse response) {
                dialog.dismiss();
            if (response.equals(null)){
                Toast.makeText(DetailsActivity.this, "Error! response is null", Toast.LENGTH_SHORT).show();
                return;
            }
            showResults(response);
        }

        @Override
        public void onError(String message) {
            dialog.dismiss();
            Toast.makeText(DetailsActivity.this, "Error", Toast.LENGTH_SHORT).show();
        }
    };

    private void showResults(DetailApiResponse response) {
        movie_name_tv.setText(response.getTitle());
        movie_released_tv.setText("Year Released: " + response.getYear());
        movie_runtime_tv.setText("Runtime: " + response.getRuntimeStr());
        movie_rating_tv.setText("Rotten Tomatoes: " + response.getRatings());
        movie_votes_tv.setText(response.getAwards());
        movie_plot_tv.setText(response.getPlot());

        try {
            Picasso.get().load(response.getImage()).into(movie_poster_iv);
        } catch (Exception e) {
            e.printStackTrace();
        }

        recycler_view_cast.setHasFixedSize(true);
        recycler_view_cast.setLayoutManager(new GridLayoutManager(this, 2));
        adapter = new CastRecyclerAdapter(this, response.getActorList());
        recycler_view_cast.setAdapter(adapter);
    }
}