package com.dmko.draganddraw.activity;

import android.support.v4.app.Fragment;

import com.dmko.draganddraw.fragment.DragAndDrawFragment;

public class DragAndDrawActivity extends SingleFragmentActivity {

    @Override
    protected Fragment createFragment() {
        return DragAndDrawFragment.newInstance();
    }
}
