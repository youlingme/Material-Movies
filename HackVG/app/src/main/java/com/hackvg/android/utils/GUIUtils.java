package com.hackvg.android.utils;

import android.animation.Animator;
import android.app.Activity;
import android.content.Context;
import android.content.res.Resources;
import android.graphics.Point;
import android.graphics.PorterDuff;
import android.graphics.drawable.Drawable;
import android.support.annotation.DrawableRes;
import android.view.Display;
import android.view.View;
import android.view.ViewAnimationUtils;
import android.view.ViewPropertyAnimator;
import android.view.Window;
import android.view.WindowManager;
import android.view.animation.AccelerateDecelerateInterpolator;
import android.widget.TextView;

import com.hackvg.android.R;

import butterknife.ButterKnife;

import static android.animation.Animator.AnimatorListener;

/**
 * Created by saulmm on 08/02/15.
 */
public class GUIUtils {

    public static final int DEFAULT_DELAY           = 30;
    public static final int SCALE_DELAY             = 300;
    public static final float SCALE_START_ANCHOR    = 0.3f;


    public static void tintAndSetCompoundDrawable (Context context, @DrawableRes
        int drawableRes, int color, TextView textview) {

            Resources res = context.getResources();
            int padding = (int) res.getDimension(R.dimen.activity_horizontal_margin);

            Drawable drawable = res.getDrawable(drawableRes);
            drawable.setColorFilter(color, PorterDuff.Mode.MULTIPLY);

            textview.setCompoundDrawablesRelativeWithIntrinsicBounds(drawable,
                null, null, null);

            textview.setCompoundDrawablePadding(padding);
        }

    public static ViewPropertyAnimator showViewByScale (View v) {

        ViewPropertyAnimator propertyAnimator = v.animate().setStartDelay(DEFAULT_DELAY)
            .scaleX(1).scaleY(1);

        return propertyAnimator;
    }

    public static void showViewByRevealEffect (View hidenView, View centerPointView, int height) {

        int cx = (centerPointView.getLeft() + centerPointView.getRight())   / 2;
        int cy = (centerPointView.getTop()  + centerPointView.getBottom())  / 2;

        Animator anim = ViewAnimationUtils.createCircularReveal(
            hidenView, cx, cy, 0, height);

        hidenView.setVisibility(View.VISIBLE);
        anim.start();
    }

    public static void makeTheStatusbarTranslucent (Activity activity) {

        Window w = activity.getWindow();
        w.setFlags(
            WindowManager.LayoutParams.FLAG_TRANSLUCENT_NAVIGATION,
            WindowManager.LayoutParams.FLAG_TRANSLUCENT_NAVIGATION);
        w.setFlags(
            WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS,
            WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
    }

    public static void setTheStatusbarNotTranslucent(Activity activity) {

        WindowManager.LayoutParams attrs = activity.getWindow()
            .getAttributes();
        attrs.flags &= (~WindowManager.LayoutParams.FLAG_TRANSLUCENT_NAVIGATION);
        attrs.flags &= (~WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
        activity.getWindow().setAttributes(attrs);
        activity.getWindow().clearFlags(
            WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS);
    }

    public static int getWindowWidth (Activity activity) {

        Display display = activity.getWindowManager().getDefaultDisplay();
        Point size = new Point();
        display.getSize(size);

        return size.y;
    }

    public static final ButterKnife.Setter<TextView, Integer> setter = new ButterKnife.Setter<TextView, Integer>() {

        @Override
        public void set(TextView view, Integer value, int index) {

            view.setTextColor(value);
        }
    };

    public static void startScaleAnimationFromPivot (int pivotX, int pivotY, final View v,
        final AnimatorListener animatorListener) {

        final AccelerateDecelerateInterpolator interpolator =
            new AccelerateDecelerateInterpolator();

        v.setScaleY(SCALE_START_ANCHOR);
        v.setPivotX(pivotX);
        v.setPivotY(pivotY);

//        v.getViewTreeObserver().addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
//            @Override
//            public void onGlobalLayout() {
//                v.getViewTreeObserver().removeOnGlobalLayoutListener(this);

                ViewPropertyAnimator viewPropertyAnimator = v.animate()
                    .setInterpolator(interpolator)
                    .scaleY(1)
                    .setDuration(SCALE_DELAY);

                if (animatorListener != null)
                    viewPropertyAnimator.setListener(animatorListener);

                viewPropertyAnimator.start();
//            }
//        });
    }

    public static void hideScaleAnimationFromPivot(View v, AnimatorListener animatorListener) {

        ViewPropertyAnimator viewPropertyAnimator = v.animate()
            .setInterpolator(new AccelerateDecelerateInterpolator())
            .scaleY(SCALE_START_ANCHOR)
            .setDuration(SCALE_DELAY);

        if (animatorListener != null)
            viewPropertyAnimator.setListener(animatorListener);

        viewPropertyAnimator.start();

    }
}
