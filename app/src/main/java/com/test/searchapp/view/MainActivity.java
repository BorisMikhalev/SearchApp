package com.test.searchapp.view;

import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.SearchView;

import com.test.searchapp.R;
import com.test.searchapp.adapters.TabAdapter;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    private List<SearchFragment> fragmentList = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initSearchBar();
        initTabBar();

    }

    private void initTabBar(){
        TabLayout tabLayout = (TabLayout)findViewById(R.id.TabsLayout);
        tabLayout.addTab(tabLayout.newTab().setText(R.string.gitHub));
        tabLayout.addTab(tabLayout.newTab().setText(R.string.wikiMedia));
        tabLayout.setTabMode(TabLayout.MODE_FIXED);

        setupFragments();

        final ViewPager viewPager = (ViewPager)findViewById(R.id.ViewPager);
        TabAdapter adapter = new TabAdapter(getSupportFragmentManager(),fragmentList);
        viewPager.setAdapter(adapter);
        viewPager.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(tabLayout));
        tabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener(){
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                viewPager.setCurrentItem(tab.getPosition());
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });
    }

    private void initSearchBar(){
        final SearchView searchView = (SearchView) findViewById(R.id.searchView);
        searchView.setIconified(false);
        searchView.clearFocus();
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                fragmentList.get(0).startSearch(searchView.getQuery().toString());
                fragmentList.get(1).startSearch(searchView.getQuery().toString());
                searchView.clearFocus();
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                return false;
            }
        });
    }

    private void setupFragments(){
        SearchFragment wikiMediaFragment = new SearchFragment();
        wikiMediaFragment.setRetainInstance(true);
        SearchFragment gitHubFragment = new SearchFragment();
        gitHubFragment.setRetainInstance(true);
        gitHubFragment.setGitHubTab(true);
        fragmentList.add(gitHubFragment);
        fragmentList.add(wikiMediaFragment);
    }
}
