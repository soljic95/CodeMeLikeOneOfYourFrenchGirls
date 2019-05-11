package com.hW4c4Doi.codemelikeoneofyourfrenchgirls.ui;


import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.viewpager.widget.ViewPager;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.hW4c4Doi.codemelikeoneofyourfrenchgirls.R;
import com.hW4c4Doi.codemelikeoneofyourfrenchgirls.adapter.SectionPagerAdapter;
import com.google.android.material.tabs.TabLayout;

import static androidx.navigation.ui.NavigationUI.setupActionBarWithNavController;
import static androidx.navigation.ui.NavigationUI.setupWithNavController;


public class EventsFragment extends Fragment {
    private SectionPagerAdapter adapter;
    private ViewPager viewPager;


    public EventsFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_events, container, false);


        adapter = new SectionPagerAdapter(getChildFragmentManager());
        viewPager = view.findViewById(R.id.viewPagerContainer);
        viewPager.setOffscreenPageLimit(2);
        setupViewPager(viewPager);

        TabLayout tabLayout = view.findViewById(R.id.tabs);
        tabLayout.setupWithViewPager(viewPager);


        return view;
    }

    private void setupViewPager(ViewPager viewPager) {
        SectionPagerAdapter adapter = new SectionPagerAdapter(getChildFragmentManager());
        adapter.addFragment(new UpcomingEventsFragment(), "Upcoming");
        adapter.addFragment(new MyEventsFragment(), "My events");
        viewPager.setAdapter(adapter);
    }


}
