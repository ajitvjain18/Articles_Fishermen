package com.gms.go.articles.ui.adapter;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.bumptech.glide.Glide;
import com.gms.go.articles.R;
import com.gms.go.articles.databinding.FragmentArticleDetailsBinding;
import com.gms.go.articles.ui.NewsViewModel;
import com.gms.go.articles.utils.AppUtils;

public class ArticleDetailsFragment extends Fragment {

    private FragmentArticleDetailsBinding binding;
    private NewsViewModel viewModel;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding = FragmentArticleDetailsBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        viewModel = new ViewModelProvider(requireActivity()).get(NewsViewModel.class);

        viewModel.selectedArticleLiveData.observe(getViewLifecycleOwner(), article -> {
            if (article != null) {
                binding.tvTitle.setText(article.getTitle());
                binding.tvDescription.setText(article.getDescription());

                Glide.with(requireContext())
                        .load(article.getImageUrl())
                        .placeholder(R.drawable.article)
                        .error(R.drawable.article)
                        .into(binding.ivLogo);

                binding.tvTime.setText(AppUtils.formatDate(article.getPublishedAt()));
            }
        });

        binding.back.setOnClickListener(v -> closeFragment());
    }

    private void closeFragment() {
        requireActivity().getSupportFragmentManager().popBackStack();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
        if (getActivity() != null) {
            View container = getActivity().findViewById(R.id.container);
            if (container != null) {
                container.setVisibility(View.GONE);
            }
        }
    }
}
