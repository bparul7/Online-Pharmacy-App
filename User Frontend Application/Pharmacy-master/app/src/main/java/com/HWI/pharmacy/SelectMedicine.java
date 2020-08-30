package com.HWI.pharmacy;

import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager.widget.ViewPager;

import android.graphics.drawable.GradientDrawable;
import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TableLayout;

import com.google.android.material.tabs.TabLayout;

public class SelectMedicine extends AppCompatActivity  {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_select_medicine);

        ViewPager pager = findViewById(R.id.pager);
        PagerAdapter adapter = new PagerAdapter(getSupportFragmentManager(),2);
        pager.setAdapter(adapter);
        TabLayout tab = findViewById(R.id.tabs);
        tab.setupWithViewPager(pager);
        tab.getTabAt(0).setText("Prescription Only");
        tab.getTabAt(1).setText("ID Required");
        View root = tab.getChildAt(0);
        if (root instanceof LinearLayout) {
            ((LinearLayout) root).setShowDividers(LinearLayout.SHOW_DIVIDER_MIDDLE);
            GradientDrawable drawable = new GradientDrawable();
            drawable.setColor(getResources().getColor(R.color.colorAccent));
            drawable.setSize(2, 1);
            ((LinearLayout) root).setDividerPadding(10);
            ((LinearLayout) root).setDividerDrawable(drawable);
        }
    }
}