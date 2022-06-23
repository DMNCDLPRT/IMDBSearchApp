package com.example.imdbsearchapp.Listeners;

import com.example.imdbsearchapp.Models.DetailApiResponse;

public interface OnDetailsApiListener {
    void onResponse(DetailApiResponse response);
    void onError(String message);
}
