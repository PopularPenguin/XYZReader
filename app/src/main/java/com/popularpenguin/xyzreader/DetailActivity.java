package com.popularpenguin.xyzreader;

import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

/** Activity that displays content */
public class DetailActivity extends ReaderActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);

        setTransition();
        addFragment(R.id.detail_container, new DetailFragment());
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_detail, menu);

        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int itemId = item.getItemId();

        switch (itemId) {

            case R.id.action_share:
                // TODO: Delete this code and point to real share action
                View view = findViewById(android.R.id.content);
                Toast.makeText(this, "Add a share action", Toast.LENGTH_SHORT).show();
                //Snackbar.make(view, "Add a share action", Snackbar.LENGTH_SHORT);
        }

        return false;
    }

    // TODO: Fix collapsing toolbar to show options menu at top while collapsed
    // https://www.journaldev.com/13927/android-collapsingtoolbarlayout-example
}
