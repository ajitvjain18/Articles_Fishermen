package com.gms.go.articles.data.api;

import com.gms.go.articles.data.model.Article;
import com.gms.go.articles.data.model.NewsResponse;

import java.util.List;

import javax.inject.Inject;

import io.reactivex.Single;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

public class NewsRepository {
    private final NewsApiService apiService;

    @Inject
    public NewsRepository(NewsApiService newsApiService) {
        apiService = newsApiService;
    }

    public Single<List<Article>> fetchArticles() {
        return apiService.getArticles()
                .map(NewsResponse::getArticles)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());
    }
}
