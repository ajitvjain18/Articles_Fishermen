package com.gms.go.articles.ui;

import androidx.lifecycle.ViewModel;
import javax.inject.Inject;

import dagger.hilt.android.lifecycle.HiltViewModel;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.schedulers.Schedulers;
import io.reactivex.android.schedulers.AndroidSchedulers;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.gms.go.articles.data.api.NewsRepository;
import com.gms.go.articles.data.model.Article;

import java.util.List;

@HiltViewModel
public class NewsViewModel extends ViewModel {
    private final NewsRepository repository;
    private final CompositeDisposable disposable = new CompositeDisposable();
    private final MutableLiveData<List<Article>> _articlesLiveData = new MutableLiveData<>();
    public LiveData<List<Article>> articlesLiveData = _articlesLiveData;

    private final MutableLiveData<Article> _selectedArticleLiveData = new MutableLiveData<>();
    public LiveData<Article> selectedArticleLiveData = _selectedArticleLiveData;

    @Inject
    public NewsViewModel(NewsRepository repository) {
        this.repository = repository;
    }

    public void fetchArticles() {
        disposable.add(repository.fetchArticles()
                .subscribe(_articlesLiveData::postValue, Throwable::printStackTrace));
    }

    @Override
    protected void onCleared() {
        super.onCleared();
        disposable.clear();
    }

    public void setSelectedArticle(Article article) {
        _selectedArticleLiveData.postValue(article);
    }
}