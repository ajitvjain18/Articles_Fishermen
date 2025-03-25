package com.gms.go.articles.di;

import androidx.annotation.NonNull;
import androidx.lifecycle.ViewModelProvider;

import com.gms.go.articles.data.api.NewsRepository;
import com.gms.go.articles.ui.NewsViewModel;

import dagger.Module;
import dagger.Provides;
import dagger.hilt.InstallIn;
import dagger.hilt.components.SingletonComponent;
import javax.inject.Singleton;

@Module
@InstallIn(SingletonComponent.class)
public class AppModule {

    @Provides
    @Singleton
    public static ViewModelProvider.Factory provideViewModelFactory(NewsRepository repository) {
        return new ViewModelProvider.Factory() {
            @NonNull
            @Override
            public <T extends androidx.lifecycle.ViewModel> T create(@NonNull Class<T> modelClass) {
                if (modelClass.isAssignableFrom(NewsViewModel.class)) {
                    return (T) new NewsViewModel(repository);
                }
                throw new IllegalArgumentException("Unknown ViewModel class");
            }
        };
    }
}
