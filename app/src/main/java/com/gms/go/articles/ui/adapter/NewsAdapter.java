package com.gms.go.articles.ui.adapter;

import android.content.Context;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.target.Target;
import com.gms.go.articles.R;
import com.gms.go.articles.data.model.Article;
import com.gms.go.articles.databinding.ItemArticleBinding;
import com.gms.go.articles.utils.AppUtils;

import java.util.ArrayList;
import java.util.List;

public class NewsAdapter extends RecyclerView.Adapter<NewsAdapter.NewsViewHolder> {

    private List<Article> articles = new ArrayList<>();
    private List<Article> fullList = new ArrayList<>();
    private final OnReadMoreClickListener listener;
    private boolean isGridEnabled = false;

    public NewsAdapter(OnReadMoreClickListener listener) {
        this.listener = listener;
    }

    public void updateArticles(List<Article> newArticles) {
        articles = new ArrayList<>(newArticles);
        fullList = new ArrayList<>(newArticles);
        notifyDataSetChanged();
    }

    public void updateState(boolean isGrid) {
        isGridEnabled = isGrid;
    }

    public void filter(String text) {
        articles.clear();
        if (text.isEmpty()) {
            articles.addAll(fullList);
        } else {
            for (Article article : fullList) {
                if (article.getTitle() != null &&
                        article.getTitle().toLowerCase().contains(text.toLowerCase())) {
                    articles.add(article);
                }
            }
        }
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public NewsViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        ItemArticleBinding binding = ItemArticleBinding.inflate(LayoutInflater.from(parent.getContext()), parent, false);
        return new NewsViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull NewsViewHolder holder, int position) {
        Article article = articles.get(position);
        holder.binding.setArticle(article);

        Glide.with(holder.binding.getRoot().getContext())
                .load(article.getImageUrl())
                .placeholder(R.drawable.article)
                .error(R.drawable.article)
                .override(Target.SIZE_ORIGINAL, Target.SIZE_ORIGINAL)
                .into(holder.binding.ivArticle);

        holder.binding.tvDate.setText(AppUtils.formatDate(article.getPublishedAt()));

        if (isGridEnabled) {
            holder.binding.buttonsView.setVisibility(View.GONE);
            holder.binding.getRoot().setOnClickListener(v -> listener.onReadMoreClick(article));
            setImageHeight(holder.binding.ivArticle, dpToPx(100, holder.binding.getRoot().getContext()));
            ViewGroup.MarginLayoutParams params = (ViewGroup.MarginLayoutParams) holder.binding.getRoot().getLayoutParams();
            if (position % 2 != 0) {
                params.setMarginStart(dpToPx(10, holder.binding.getRoot().getContext()));
            } else {
                params.setMarginStart(0);
            }
            holder.binding.getRoot().setLayoutParams(params);
        } else {
            holder.binding.buttonsView.setVisibility(View.VISIBLE);
            holder.binding.readMoreButton.setOnClickListener(v -> listener.onReadMoreClick(article));
            setImageHeight(holder.binding.ivArticle, dpToPx(200, holder.binding.getRoot().getContext()));
            ViewGroup.MarginLayoutParams params = (ViewGroup.MarginLayoutParams) holder.binding.getRoot().getLayoutParams();
            params.setMarginStart(0);
            holder.binding.getRoot().setLayoutParams(params);
        }
    }

    private void setImageHeight(View imageView, int height) {
        ViewGroup.LayoutParams params = imageView.getLayoutParams();
        params.height = height;
        imageView.setLayoutParams(params);
    }


    private int dpToPx(int dp, Context context) {
        return (int) TypedValue.applyDimension(
                TypedValue.COMPLEX_UNIT_DIP, dp, context.getResources().getDisplayMetrics());
    }


    @Override
    public int getItemCount() {
        return articles.size();
    }

    static class NewsViewHolder extends RecyclerView.ViewHolder {
        private final ItemArticleBinding binding;

        public NewsViewHolder(@NonNull ItemArticleBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }
    }
}

