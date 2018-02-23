package com.popularpenguin.xyzreader;

import android.app.ActivityOptions;
import android.content.res.Resources;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Window;

/** Class provides additional functionality common to XYZ Reader activities */
public class ReaderActivity extends AppCompatActivity {

    protected Bundle getTransitionAnimation() {
        return ActivityOptions.makeSceneTransitionAnimation(this)
                .toBundle();
    }

    /** Additional transition code */
    protected void setTransition() {
        Resources resources = getResources();
        Window window = getWindow();

        window.setBackgroundDrawable(new ColorDrawable(resources.getColor(R.color.black)));
    }
}
