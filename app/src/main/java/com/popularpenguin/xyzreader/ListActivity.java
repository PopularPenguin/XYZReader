package com.popularpenguin.xyzreader;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;

/** Activity that displays the article list */
public class ListActivity extends ReaderActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list);

        setTransition();

        // TODO: Add RecyclerView (separate class)
        // TODO: Add Loader (separate class)
        // TODO: Delete button
        Button detailButton = findViewById(R.id.btn_list_test);
        detailButton.setOnClickListener(view -> {
            Bundle animation = getTransitionAnimation();
            Intent intent = new Intent(this, DetailActivity.class);
            intent.putExtra("bundle", new Bundle());

            startActivity(intent, animation);
        });
    }
}
