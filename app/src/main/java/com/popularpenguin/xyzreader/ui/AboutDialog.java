package com.popularpenguin.xyzreader.ui;

import android.content.Context;
import android.support.v7.app.AlertDialog;

import com.popularpenguin.xyzreader.R;

public class AboutDialog {

    private AboutDialog() { }

    public static void show(Context ctx) {
        new AlertDialog.Builder(ctx)
                .setTitle(R.string.dialog_about_title)
                .setMessage(R.string.dialog_about_body)
                .setPositiveButton(R.string.dialog_ok, (dialog, which) -> dialog.dismiss())
                .show();
    }
}
