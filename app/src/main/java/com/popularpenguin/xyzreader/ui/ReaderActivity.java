package com.popularpenguin.xyzreader.ui;

import android.app.ActivityOptions;
import android.content.res.Resources;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.view.Window;

import com.popularpenguin.xyzreader.R;

/** Class provides additional functionality common to XYZ Reader activities */
public class ReaderActivity extends AppCompatActivity {

    /** The app's transition animation */
    protected Bundle getTransitionAnimation() {
        return ActivityOptions.makeSceneTransitionAnimation(this)
                .toBundle();
    }

    /** Additional transition code, currently just sets the background color */
    protected void setTransition() {
        Resources resources = getResources();
        Window window = getWindow();

        window.setBackgroundDrawable(new ColorDrawable(resources.getColor(R.color.black)));
    }
}
