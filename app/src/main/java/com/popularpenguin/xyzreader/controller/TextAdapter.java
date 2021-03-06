package com.popularpenguin.xyzreader.controller;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.text.Html;
import android.text.method.LinkMovementMethod;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.popularpenguin.xyzreader.R;

import java.util.List;
import java.util.regex.Pattern;

import butterknife.BindView;
import butterknife.ButterKnife;

/** Adapter to efficiently display the article body, 200k+ characters don't work well with TextViews */
public class TextAdapter extends RecyclerView.Adapter<TextAdapter.TextHolder> {

    private Context ctx;
    private List<String> body;
    private Pattern mPattern;

    public TextAdapter(Context ctx, List<String> body) {
        this.ctx = ctx;
        this.body = body;

        // compile the regex pattern once only for efficiency
        mPattern = Pattern.compile("\r\n");
    }

    @NonNull
    @Override
    public TextAdapter.TextHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        int layout = R.layout.list_body_text;
        LayoutInflater inflater = LayoutInflater.from(ctx);
        View view = inflater.inflate(layout, parent, false);

        return new TextHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull TextAdapter.TextHolder holder, int position) {
        String paragraph = body.get(position);

        holder.bind(paragraph);
    }

    @Override
    public int getItemCount() {
        return body.size();
    }

    class TextHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.tv_paragraph) TextView paragraphView;

        TextHolder(View itemView) {
            super(itemView);

            ButterKnife.bind(this, itemView);
        }

        void bind(String paragraph) {
            // replace carriage returns with a space
            String text = mPattern.matcher(paragraph).replaceAll(" ");

            paragraphView.setText(Html.fromHtml(text));
            paragraphView.setMovementMethod(LinkMovementMethod.getInstance());
        }
    }
}
