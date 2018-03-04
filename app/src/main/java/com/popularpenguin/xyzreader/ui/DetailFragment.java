package com.popularpenguin.xyzreader.ui;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewCompat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.popularpenguin.xyzreader.R;

public class DetailFragment extends Fragment {

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater,
                             @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_detail, container, false);

        setRetainInstance(true);

        // Toolbar toolbar = view.findViewById(R.id.toolbar);
        // ((ReaderActivity) getActivity()).setSupportActionBar(toolbar);

        FloatingActionButton fab = view.findViewById(R.id.fab_share);
        fab.setOnClickListener(v ->
                Snackbar.make(v, "Add a share action", 1000).show());

        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        ViewCompat.setTranslationZ(getView(), 100f);
    }

    // TODO: Fix collapsing toolbar to show options menu at top while collapsed
    // https://www.journaldev.com/13927/android-collapsingtoolbarlayout-example
}
