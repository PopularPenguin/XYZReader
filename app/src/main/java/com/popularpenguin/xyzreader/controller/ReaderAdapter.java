package com.popularpenguin.xyzreader.controller;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.TextView;

import com.popularpenguin.xyzreader.R;
import com.popularpenguin.xyzreader.data.Article;
import com.squareup.picasso.Picasso;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/** RecyclerView to display a list of articles */
public class ReaderAdapter extends RecyclerView.Adapter<ReaderAdapter.ReaderViewHolder> {

    public interface ReaderAdapterOnClickHandler {
        void onClick(int position);
    }

    private final Context ctx;
    private final List<Article> mArticleList;
    private final ReaderAdapterOnClickHandler mClickHandler;

    private int lastPosition = -1; // save the last view position for animations

    public ReaderAdapter(Context ctx,
                         List<Article> articleList,
                         ReaderAdapterOnClickHandler clickHandler) {

        this.ctx = ctx;
        mArticleList = articleList;
        mClickHandler = clickHandler;
    }

    public ReaderAdapter.ReaderViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        int layout = R.layout.list_item;
        LayoutInflater inflater = LayoutInflater.from(ctx);
        View view = inflater.inflate(layout, parent, false);

        return new ReaderViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ReaderViewHolder holder, int position) {
        Article article = mArticleList.get(position);

        holder.bind(article);

        setAnimation(holder.itemView, position);
    }

    @Override
    public int getItemCount() {
        return mArticleList.size();
    }

    @Override
    public void onViewDetachedFromWindow(@NonNull ReaderViewHolder holder) {
        holder.clearAnimation();
    }

    /**
     * Set entry animation for views in the RecyclerView
     * https://stackoverflow.com/questions/26724964/how-to-animate-recyclerview-items-when-they-appear
     * @param view The view to animate
     * @param position View's position in the RecyclerView
     */
    private void setAnimation(View view, int position) {
        // Animate view if it wasn't previously displayed on screen
        if (position > lastPosition) {
            Animation animation = AnimationUtils
                    .loadAnimation(ctx, R.anim.slide_up_bottom);
            view.startAnimation(animation);
            lastPosition = position;
        }
    }

    /** The view holder to hold the article */
    class ReaderViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        @BindView(R.id.iv_image_item) ImageView imageView;
        @BindView(R.id.tv_title_item) TextView titleView;
        @BindView(R.id.tv_date_item) TextView dateView;
        @BindView(R.id.tv_author_item) TextView authorView;

        ReaderViewHolder(View itemView) {
            super(itemView);

            ButterKnife.bind(this, itemView);

            itemView.setOnClickListener(this);
        }

        void bind(@NonNull Article article) {
            Picasso.with(ctx)
                    .load(article.getThumbUrl())
                    .error(R.drawable.error)
                    .into(imageView);

            titleView.setText(article.getTitle());
            dateView.setText(article.getDate());
            authorView.setText(article.getAuthor());
        }

        void clearAnimation() {
            itemView.clearAnimation();
        }

        @Override
        public void onClick(View v) {
            mClickHandler.onClick(getAdapterPosition());
        }
    }
}
