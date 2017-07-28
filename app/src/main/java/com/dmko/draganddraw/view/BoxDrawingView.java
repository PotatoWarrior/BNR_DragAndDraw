package com.dmko.draganddraw.view;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.PointF;
import android.os.Bundle;
import android.os.Parcelable;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;

import com.dmko.draganddraw.model.Box;

import java.util.ArrayList;

public class BoxDrawingView extends View {
    private static final String TAG = "BoxDrawingView";
    private static final String EXTRA_SUPER = "super";
    private static final String EXTRA_BOX_LIST = "box_list";
    private Paint mBoxPaint;
    private Paint mBackgroundPaint;

    private Box mCurrentBox;
    private ArrayList<Box> mBoxes = new ArrayList<>();

    public BoxDrawingView(Context context) {
        super(context, null);
    }

    public BoxDrawingView(Context context, AttributeSet attributes) {
        super(context, attributes);

        mBoxPaint = new Paint();
        mBoxPaint.setColor(0x22ff0000);

        mBackgroundPaint = new Paint();
        mBackgroundPaint.setColor(0xfff8efe0);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        canvas.drawPaint(mBackgroundPaint);

        for (Box box : mBoxes) {
            float left = Math.min(box.getOrigin().x, box.getCurrent().x);
            float right = Math.max(box.getOrigin().x, box.getCurrent().x);
            float top = Math.min(box.getOrigin().y, box.getCurrent().y);
            float bottom = Math.max(box.getOrigin().y, box.getCurrent().y);

            canvas.drawRect(left, top, right, bottom, mBoxPaint);
        }
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        PointF current = new PointF(event.getX(), event.getY());
        String action = "";
        int index = event.getActionIndex();

        if (index == 0) {
            switch (event.getActionMasked()) {
                case MotionEvent.ACTION_DOWN:
                    action = "ACTION_DOWN";
                    mCurrentBox = new Box(current);
                    mBoxes.add(mCurrentBox);
                    break;
                case MotionEvent.ACTION_MOVE:
                    action = "ACTION_MOVE";
                    if (mCurrentBox != null) {
                        mCurrentBox.setCurrent(current);
                        invalidate();
                    }
                    break;
                case MotionEvent.ACTION_CANCEL:
                    action = "ACTION_CANCEL";
                    mCurrentBox = null;
                    break;
                case MotionEvent.ACTION_UP:
                    action = "ACTION_UP";
                    mCurrentBox = null;
                    break;
            }
        }
        Log.i(TAG, "Action " + action + " at " + current.x + " - " + current.y);
        return true;
    }

    @Override
    protected Parcelable onSaveInstanceState() {
        Bundle args = new Bundle();
        args.putParcelable(EXTRA_SUPER, super.onSaveInstanceState());
        args.putParcelableArrayList(EXTRA_BOX_LIST, mBoxes);
        return args;
    }

    @Override
    protected void onRestoreInstanceState(Parcelable state) {
        Bundle args = (Bundle) state;
        super.onRestoreInstanceState(args.getParcelable(EXTRA_SUPER));
        mBoxes = args.getParcelableArrayList(EXTRA_BOX_LIST);
    }
}
