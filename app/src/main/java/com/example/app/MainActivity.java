package com.example.app;

import android.animation.Animator;
import android.animation.ObjectAnimator;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.util.DisplayMetrics;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends ActionBarActivity {

    float density;

    private List<ObjectAnimator> animators = new ArrayList<ObjectAnimator>();

    private List<ImageView> snowBig = new ArrayList<ImageView>();

    private List<ImageView> snowMiddle = new ArrayList<ImageView>();

    private List<ImageView> snowSmall = new ArrayList<ImageView>();

    private List<ImageView> snowMicro = new ArrayList<ImageView>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initdensity();
        ListView listView = new ListView(this);
        LayoutInflater mInflater = LayoutInflater.from(MainActivity.this);
        View header = mInflater.inflate(R.layout.item_drawerlayout_footview, null);
        listView.addHeaderView(header);
        ArrayAdapter adapter = new ArrayAdapter(this, android.R.layout.simple_list_item_1,
                new String[]{"好友动态", "同城热门","我的收藏","个人主页","找朋友"});
        listView.setAdapter(adapter);
        setContentView(listView);

        ViewGroup viewGroup = (ViewGroup) header.findViewById(R.id.item_drawerlayout_layout);
        initSnows(viewGroup);
        startSnowAnim();
    }

    private void initdensity() {
        WindowManager windowManager = this.getWindowManager();
        DisplayMetrics displayMetrics = new DisplayMetrics();
        windowManager.getDefaultDisplay().getMetrics(displayMetrics);
        density = displayMetrics.density;
    }

    private void startSnowAnim() {
        for (int i = 0, size = animators.size(); i < size; i++) {
            animators.get(i).start();
        }
    }

    private void stopSnowAnim() {
        for (int i = 0, size = animators.size(); i < size; i++) {
            animators.get(i).end();
        }
    }

    private void initSnows(ViewGroup viewGroup) {
        initSnow(viewGroup, snowMicro, 3, R.drawable.snow_micro,
                8000 + (int) (Math.random() * 300), 255, 6000, 4, 2);
        initSnow(viewGroup, snowMicro, 3, R.drawable.snow_micro,
                8000 + (int) (Math.random() * 300), 255, 4000, 4, 2);
        initSnow(viewGroup, snowMicro, 3, R.drawable.snow_micro,
                8000 + (int) (Math.random() * 300), 255, 2000, 4, 2);
        initSnow(viewGroup, snowSmall, 6, R.drawable.snow_small,
                6000 + (int) (Math.random() * 300), 255, 3000, 8, 5);
        initSnow(viewGroup, snowMiddle, 4, R.drawable.snow_middle,
                4000 + (int) (Math.random() * 300), 255, 2000, 10, 8);
        initSnow(viewGroup, snowBig, 4, R.drawable.snow_big,
                2000 + (int) (Math.random() * 300), 150, 3000, 14, 12);
    }

    private void initSnow(ViewGroup viewGroup, List<ImageView> imageViews, int count, int drawable,
            int duration, int alpha, final int delay, int offsetDurationTop,
            int offsetDurationBottom) {
        for (int i = 0; i < count; i++) {
            final ImageView view = new ImageView(MainActivity.this);
            view.setImageResource(drawable);
            view.setAlpha(alpha);
            ObjectAnimator
                    .ofFloat(view, "translationX", 0f,
                            (float) (Math.random() * 300.0f * density)).setDuration(0)
                    .start();
            ObjectAnimator
                    .ofFloat(view, "translationY", 0f, -1 * offsetDurationTop * density)
                    .setDuration(0).start();
            viewGroup.addView(view);
            imageViews.add(view);

            ObjectAnimator.ofFloat(view, "alpha", 0f, 1).setDuration(800).start();
            final ObjectAnimator transAnim = ObjectAnimator.ofFloat(view, "translationY", -1
                    * offsetDurationTop * density,
                    (80 + offsetDurationBottom) * density);

            transAnim.setDuration(duration);
            // transAnim.setRepeatCount(Integer.MAX_VALUE - 1);
            transAnim.setStartDelay((int) (Math.random() * delay));
            transAnim.addListener(new Animator.AnimatorListener() {

                @Override
                public void onAnimationStart(Animator animation) {
                }

                @Override
                public void onAnimationRepeat(Animator animation) {

                }

                @Override
                public void onAnimationEnd(Animator animation) {

                    int startDelay = (int) (Math.random() * delay);
                    animation.setStartDelay(startDelay);
                    animation.start();
                    ObjectAnimator.ofFloat(view, "alpha", 0f, 1).setDuration(1200).start();
                    ObjectAnimator
                            .ofFloat(view, "translationX", 0f,
                                    (float) (Math.random() * 300.0f * density))
                            .setDuration(0).start();
                }

                @Override
                public void onAnimationCancel(Animator animation) {
                }
            });
            animators.add(transAnim);
        }
    }

}
