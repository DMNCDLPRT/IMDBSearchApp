package com.example.imdbsearchapp.Listeners;

import com.example.imdbsearchapp.Models.SearchApiResponse;

public interface OnSearchApiListeners {
    void onResponse(SearchApiResponse response);
    void onError(String message);
}
