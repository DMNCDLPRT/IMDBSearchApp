package com.example.imdbsearchapp;

import android.content.Context;
import android.widget.Toast;

import com.example.imdbsearchapp.Listeners.OnDetailsApiListener;
import com.example.imdbsearchapp.Listeners.OnSearchApiListeners;
import com.example.imdbsearchapp.Models.DetailApiResponse;
import com.example.imdbsearchapp.Models.SearchApiResponse;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.Headers;
import retrofit2.http.Path;


public class RequestManager {
    Context context;
    Retrofit retrofit = new Retrofit.Builder()
            .baseUrl("https://imdb-api.com/")
            .addConverterFactory(GsonConverterFactory.create())
            .build();

    public RequestManager(Context context) {
        this.context = context;
    }

    public void  searchMovies(OnSearchApiListeners listener, String movieName) {
        getMovies getMovies = retrofit.create(RequestManager.getMovies.class);
        Call<SearchApiResponse> call = getMovies.callMovie(movieName);
        
        call.enqueue(new Callback<SearchApiResponse>() {
            @Override
            public void onResponse(Call<SearchApiResponse> call, Response<SearchApiResponse> response) {
                if (!response.isSuccessful()) {
                    Toast.makeText(context, "Couldn't fetch data!", Toast.LENGTH_SHORT).show();
                    return;
                }
                listener.onResponse(response.body());
            }
            @Override
            public void onFailure(Call<SearchApiResponse> call, Throwable t) {
                listener.onError(t.getMessage());
            }
        });
    }

    public void  searchMovieDetails(OnDetailsApiListener listener, String movie_id) {
        getMovieDetails getMovieDetails = retrofit.create(RequestManager.getMovieDetails.class);
        Call<DetailApiResponse> call = getMovieDetails.callMovieDetails(movie_id);

        call.enqueue(new Callback<DetailApiResponse>() {
            @Override
            public void onResponse(Call<DetailApiResponse> call, Response<DetailApiResponse> response) {
                if (!response.isSuccessful()) {
                    Toast.makeText(context, "Couldn't fetch data!", Toast.LENGTH_SHORT).show();
                    return;
                }
                listener.onResponse(response.body());
            }
            @Override
            public void onFailure(Call<DetailApiResponse> call, Throwable t) {
                listener.onError(t.getMessage());
            }
        });
    }

    public interface getMovies {
        @Headers({
                "Accept: application/json",
        })
        @GET("https://imdb-api.com/en/API/Search/k_damtjky7/{movieName}")
        Call<SearchApiResponse> callMovie(
                @Path("movieName") String movieName
        );
    }

    public interface getMovieDetails {
        @Headers({
                "Accept: application/json",
        })
        @GET("https://imdb-api.com/en/API/Title/k_damtjky7/{movie_id}/Trailer,Ratings,")
        Call<DetailApiResponse> callMovieDetails(
                @Path("movie_id") String movie_id
        );
    }
}
