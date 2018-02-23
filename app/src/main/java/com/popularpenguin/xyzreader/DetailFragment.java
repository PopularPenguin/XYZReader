package com.popularpenguin.xyzreader;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

public class DetailFragment extends Fragment {

    public static Fragment newInstance(Bundle args) {
        Fragment fragment = new DetailFragment();
        fragment.setArguments(args);

        return fragment;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater,
                             @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_detail, container, false);

        FloatingActionButton fab = view.findViewById(R.id.fab_share);
        fab.setOnClickListener(v ->
                Snackbar.make(v, "Add a share action", 1000).show());

        return view;
    }

    // TODO: Add options menu
    // TODO: Fix collapsing toolbar to show options menu at top while collapsed
    // https://www.journaldev.com/13927/android-collapsingtoolbarlayout-example
}
