package com.gms.go.articles.ui;

import static androidx.recyclerview.widget.RecyclerView.*;

import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintSet;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.gms.go.articles.R;
import com.gms.go.articles.data.model.Article;
import com.gms.go.articles.databinding.ActivityMainBinding;
import com.gms.go.articles.ui.adapter.ArticleDetailsFragment;
import com.gms.go.articles.ui.adapter.NewsAdapter;
import com.gms.go.articles.ui.adapter.OnReadMoreClickListener;
import com.gms.go.articles.utils.AppUtils;
import com.gms.go.articles.utils.NetworkUtils;

import java.util.ArrayList;

import javax.inject.Inject;

import dagger.hilt.android.AndroidEntryPoint;

@AndroidEntryPoint
public class MainActivity extends AppCompatActivity implements OnReadMoreClickListener {

    @Inject
    ViewModelProvider.Factory viewModelFactory;

    private NewsViewModel viewModel;
    private NewsAdapter adapter;
    private ActivityMainBinding binding;
    private boolean isGrid = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);

        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        binding.progressBar.setVisibility(VISIBLE);
        setupRecyclerView();
        setupViewModel();

        isGrid = AppUtils.getSavedViewMode(this);
        binding.rvArticles.setLayoutManager(getLayoutManager());
        adapter.updateState(isGrid);
        adapter.notifyDataSetChanged();

        setImages();

        binding.ivViewMode.setOnClickListener(v -> {
            isGrid = !isGrid;
            binding.rvArticles.setLayoutManager(getLayoutManager());
            adapter.updateState(isGrid);
            adapter.notifyDataSetChanged();
            setImages();
            AppUtils.saveViewMode(this, isGrid);
        });

        binding.ivSearch.setOnClickListener(v -> {
            binding.titleLayout.setVisibility(View.GONE);
            binding.searchLayout.setVisibility(View.VISIBLE);
            binding.clearIcon.setVisibility(View.VISIBLE);
            ConstraintSet constraintSet = new ConstraintSet();
            constraintSet.clone(binding.getRoot());
            constraintSet.connect(R.id.rvArticles, ConstraintSet.TOP, R.id.searchLayout, ConstraintSet.BOTTOM);
            constraintSet.connect(R.id.noInternetLayout, ConstraintSet.TOP, R.id.searchLayout, ConstraintSet.BOTTOM);
            constraintSet.applyTo(binding.getRoot());
        });

        binding.clearIcon.setOnClickListener(v -> {
            binding.searchInput.setText("");
            binding.searchLayout.setVisibility(View.GONE);
            binding.titleLayout.setVisibility(View.VISIBLE);
            ConstraintSet constraintSet = new ConstraintSet();
            constraintSet.clone(binding.getRoot());
            constraintSet.connect(R.id.rvArticles, ConstraintSet.TOP, R.id.titleLayout, ConstraintSet.BOTTOM);
            constraintSet.connect(R.id.noInternetLayout, ConstraintSet.TOP, R.id.titleLayout, ConstraintSet.BOTTOM);
            constraintSet.applyTo(binding.getRoot());
        });

        binding.searchInput.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                adapter.filter(s.toString());
            }

            @Override
            public void afterTextChanged(Editable s) {
            }
        });

        checkNetwork();
    }

    private void checkNetwork() {
        if (!NetworkUtils.isInternetAvailable(this)) {
            binding.progressBar.setVisibility(GONE);
            binding.noInternetLayout.setOnClickListener(v -> {
                binding.progressBar.setVisibility(VISIBLE);
                binding.noInternetLayout.setVisibility(GONE);
                new Handler(Looper.getMainLooper()).postDelayed(this::checkNetwork, 300);
            });
            binding.noInternetLayout.setVisibility(VISIBLE);
            binding.rvArticles.setVisibility(GONE);
        } else {
            viewModel.fetchArticles();
            binding.noInternetLayout.setVisibility(GONE);
            binding.rvArticles.setVisibility(VISIBLE);
        }
    }

    private void setImages() {
        if (isGrid) {
            binding.ivViewMode.setImageResource(R.drawable.list);
            binding.ivViewMode.setPadding(4, 4, 4, 4);
        } else {
            binding.ivViewMode.setImageResource(R.drawable.grid);
            binding.ivViewMode.setPadding(0, 0, 0, 0);
        }
    }

    private LayoutManager getLayoutManager() {
        return isGrid ? new GridLayoutManager(this, 2) : new LinearLayoutManager(this);
    }

    private void setupRecyclerView() {
        adapter = new NewsAdapter(this);
        binding.rvArticles.setLayoutManager(new LinearLayoutManager(this));
        binding.rvArticles.setAdapter(adapter);
    }

    private void setupViewModel() {
        viewModel = new ViewModelProvider(this, viewModelFactory).get(NewsViewModel.class);
        viewModel.articlesLiveData.observe(this, articles -> {
            adapter.updateArticles(articles);
        });
    }

    @Override
    public void onReadMoreClick(Article article) {
        binding.container.setVisibility(View.VISIBLE);
        viewModel.setSelectedArticle(article);
        getSupportFragmentManager()
                .beginTransaction()
                .replace(binding.container.getId(), new ArticleDetailsFragment())
                .addToBackStack(null)
                .commit();
    }
}