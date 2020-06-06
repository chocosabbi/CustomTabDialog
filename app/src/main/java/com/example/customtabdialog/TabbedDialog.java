package com.example.customtabdialog;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;
import androidx.viewpager.widget.ViewPager;

import com.google.android.material.tabs.TabLayout;

public class TabbedDialog extends DialogFragment {
    TabLayout tabLayout;
    ViewPager viewPager;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater,
                             @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View rootview = inflater.inflate(R.layout.dialog_sample,container,false);
        tabLayout = rootview.findViewById(R.id.tab_layout);
        viewPager = rootview.findViewById(R.id.tab_view_pager);
        CustomAdapter adapter = new CustomAdapter(getChildFragmentManager());
        adapter.addFragment("Register",CustomFragment.createInstance("Register your account"));
        adapter.addFragment("Login",CustomFragment.createInstance("Login"));
        viewPager.setAdapter(adapter);
        tabLayout.setupWithViewPager(viewPager);
        return rootview;
    }
}