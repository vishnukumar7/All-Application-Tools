package com.example.allapps;

import android.view.GestureDetector;
import android.view.MotionEvent;

public class DetectSwipeGesture extends GestureDetector.SimpleOnGestureListener {

    // Minimal x and y axis swipe distance.
    private static int MIN_SWIPE_DISTANCE_X = 100;
    private static int MIN_SWIPE_DISTANCE_Y = 100;


    // Source activity that display message in text view.
   /* private VideoPlayActivity activity;

    public VideoPlayActivity getActivity() {
        return activity;
    }

    public void setActivity(VideoPlayActivity activity) {
        this.activity = activity;
    }*/

    /* This method is invoked when a swipe gesture happened. */
    @Override
    public boolean onFling(MotionEvent e1, MotionEvent e2, float velocityX, float velocityY) {

        // Get swipe delta value in x axis.

        float deltaX = e1.getX() - e2.getX();

        // Get swipe delta value in y axis.
        float deltaY = e1.getY() - e2.getY();
        System.out.println("////onfling : getx1 "+e1.getX()+" getx2 : "+e2.getX());
        System.out.println("////onfling : gety1 "+e1.getY()+" gety2 : "+e2.getY());
        // Get absolute value.
        float deltaXAbs = Math.abs(deltaX);
        float deltaYAbs = Math.abs(deltaY);

        // Only when swipe distance between minimal and maximal distance value then we treat it as effective swipe
        /*if((deltaXAbs >= MIN_SWIPE_DISTANCE_X))
        {
            if(deltaX > 0)
            {
                this.activity.displayMessage("Swipe to left");
            }else
            {
                this.activity.displayMessage("Swipe to right");
            }
        }
        if((deltaYAbs >= MIN_SWIPE_DISTANCE_Y))
        {
            if(deltaY > 0)
            {
                this.activity.displayMessage("Swipe to up");
            }else
            {
                this.activity.displayMessage("Swipe to down");
            }
        }*/

        return true;
    }

    // Invoked when single tap screen.
    @Override
    public boolean onSingleTapConfirmed(MotionEvent e) {
        //this.activity.displayMessage("Single tap occurred.");
        return true;
    }

    // Invoked when double tap screen.
    @Override
    public boolean onDoubleTap(MotionEvent e) {
        //this.activity.displayMessage("Double tap occurred.");
        return true;
    }
}
