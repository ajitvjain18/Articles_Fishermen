package com.gms.go.articles.data.api;

import com.gms.go.articles.data.model.NewsResponse;

import io.reactivex.Single;
import retrofit2.http.GET;

public interface NewsApiService {

    @GET("v1/9b040bf5-62aa-4ba6-b3f2-f7a1e146097a")
    Single<NewsResponse> getArticles();
}
