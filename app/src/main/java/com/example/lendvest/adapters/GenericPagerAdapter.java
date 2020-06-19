package com.example.lendvest.adapters;

import android.content.Context;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.viewpager.widget.PagerAdapter;
import androidx.viewpager.widget.ViewPager;

import java.util.ArrayList;
import java.util.List;


public abstract class GenericPagerAdapter  extends PagerAdapter {

    protected List<View> views;
    List<String> titles;
    protected Context context;

    public GenericPagerAdapter(Context context) {
        this.context = context;
        views        = new ArrayList<>();
    }
    public GenericPagerAdapter(Context context, List<View> views) {
        this.views   = views;
        this.context = context;
    }

    public void setViews(List<View> views){
        this.views = views;
    }

    public View getView(int position) {
        View view = views.get(position);
        return onCurrentView(position, view);
//        return view;
    }
//    public abstract View getView(int position);

    @Override
    public int getCount() {
        return views.size();
    }

    @Override
    public boolean isViewFromObject(View view, Object object) {
        return view == object;
    }

    @Override
    public void destroyItem(ViewGroup container, int position, @NonNull Object object) {
        container.removeView(views.get(position));
//        container.removeView((View) object);
    }

    @Override
    public Object instantiateItem(ViewGroup container, int position) {
//        View view = views.get(position);
//        View child = views.get(position);
        View child = getView(position);
        if (child.getParent() != null) {
            ((ViewGroup) child.getParent()).removeView(child);
        }
        container.addView(child);
//        onCurrentView(child);
        return child;
    }

    @Override
    public int getItemPosition(Object object) {
        for (int index = 0; index < getCount(); index++) {
            if ((View) object == views.get(index)) {
                return index;
            }
        }
        return POSITION_NONE;
    }

    @Override
    public CharSequence getPageTitle(int position) {
//        return (titles != null) ? titles.get(position) : "View " + (position + 1);
        return  getNextPageTitle(position);
    }

    public abstract CharSequence getNextPageTitle(int position);

    public abstract View onCurrentView(int position, View view);


    public void setTitles(List<String> titles){
        this.titles = titles;
    }

    //-----------------------------------------------------------------------------
    // Add "view" to right end of "views".
    // Returns the position of the new view.
    // The app should call this to add pages; not used by ViewPager.
    public int addView(View v) {
        return addView(v, views.size());
    }

    //-----------------------------------------------------------------------------
    // Add "view" at "position" to "views".
    // Returns position of new view.
    // The app should call this to add pages; not used by ViewPager.
    public int addView(View v, int position) {
        views.add(position, v);
        return position;
    }

    //-----------------------------------------------------------------------------
    // Removes "view" from "views".
    // Retuns position of removed view.
    // The app should call this to remove pages; not used by ViewPager.
    public int removeView(ViewPager pager, View v) {
        return removeView(pager, views.indexOf(v));
    }

//    -----------------------------------------------------------------------------
//     Removes the "view" at "position" from "views".
//     Retuns position of removed view.
//     The app should call this to remove pages; not used by ViewPager.
    public int removeView(ViewPager pager, int position) {
        pager.setAdapter(null);
        views.remove(position);
        pager.setAdapter(this);

        return position;
    }


    public class GenericOnPageChangeListener extends ViewPager.SimpleOnPageChangeListener {

        private int currentPage;

        @Override
        public void onPageSelected(int position) {
            currentPage = position;
        }

        public final int getCurrentPage() {
            return currentPage;
        }
    }

}

