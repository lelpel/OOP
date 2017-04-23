package ua.lelpel.pomobluelw.ui.activities;

import android.support.design.widget.TabLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.app.ListFragment;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

import android.widget.ArrayAdapter;
import android.widget.ListView;

import ua.lelpel.pomobluelw.R;
import ua.lelpel.pomobluelw.model.TaskManager;

public class BoardSelectorActivity extends AppCompatActivity {
    private static TaskManager taskManager = TaskManager.getInstance();
    /**
     * The {@link android.support.v4.view.PagerAdapter} that will provide
     * fragments for each of the sections. We use a
     * {@link FragmentPagerAdapter} derivative, which will keep every
     * loaded fragment in memory. If this becomes too memory intensive, it
     * may be best to switch to a
     * {@link android.support.v4.app.FragmentStatePagerAdapter}.
     */
    private SectionsPagerAdapter mSectionsPagerAdapter;

    /**
     * The {@link ViewPager} that will host the section contents.
     */
    private ViewPager mViewPager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_board_selector);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        // Create the adapter that will return a fragment for each of the three
        // primary sections of the activity.
        mSectionsPagerAdapter = new SectionsPagerAdapter(getSupportFragmentManager());

        // Set up the ViewPager with the sections adapter.
        mViewPager = (ViewPager) findViewById(R.id.container);
        mViewPager.setAdapter(mSectionsPagerAdapter);

        TabLayout tabLayout = (TabLayout) findViewById(R.id.tabs);
        tabLayout.setupWithViewPager(mViewPager);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });

    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_board_selector, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }


    /**
     * Фрагмент, который будет содержать списки досок
     */
    public static class BoardListFragment extends ListFragment {
        private static final String ARG_SECTION_NUMBER = "section_number";
        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
            //TODO: перепилить под класс Board
            ArrayAdapter<String> boardAdapter = new ArrayAdapter<>(
                    inflater.getContext(),
                    android.R.layout.simple_list_item_1,
                    taskManager.getBoardNames());
            setListAdapter(boardAdapter);
            return super.onCreateView(inflater, container, savedInstanceState);
        }


        private String[] getBoardNames(int boardTypeId) {
            switch (boardTypeId) {
                case 0:
                    return new String[] {
                            "Прикрепленная доска 1",
                            "Прикрепленная доска 2",
                            "Прикрепленная доска 3"
                    };
                case 1:
                    return new String[] {
                            "Недавняя доска 1",
                            "Недавняя доска 2",
                            "Недавняя доска 3"
                    };
                case 2:
                    return new String[] {
                            "Частная доска 1",
                            "Частная доска 2",
                            "Частная доска 3"
                    };
            }
            return new String[0];
        }

        public static BoardListFragment newInstance(int sectionNumber) {
            BoardListFragment fragment = new BoardListFragment();
            Bundle args = new Bundle();
            args.putInt(ARG_SECTION_NUMBER, sectionNumber);
            fragment.setArguments(args);
            return fragment;
        }

        @Override
        public void onListItemClick(ListView l, View v, int position, long id) {
            super.onListItemClick(l, v, position, id);
        }
    }

    /**
     * A {@link FragmentPagerAdapter} that returns a fragment corresponding to
     * one of the sections/tabs/pages.
     */
    //TODO: выглядит норм
    public class SectionsPagerAdapter extends FragmentPagerAdapter {

        public SectionsPagerAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int position) {
            return BoardListFragment.newInstance(position);
        }

        @Override
        public int getCount() {
            return 3;
        }

        @Override
        public CharSequence getPageTitle(int position) {
            switch (position) {
                case 0:
                    return getResources().getString(R.string.pinned);
                case 1:
                    return getResources().getString(R.string.recent);
                case 2:
                    return getResources().getString(R.string.personal);
            }
            return null;
        }
    }
}

/*
*  public static class SwipeFragment extends ListFragment {
        private static final String ARG_SECTION_NUMBER = "section_number";
        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
            //TODO: перепилить под класс Board
            ArrayAdapter<String> boardAdapter = new ArrayAdapter<>(
                    inflater.getContext(),
                    android.R.layout.simple_list_item_1,
                    getBoardNames(getArguments().getInt(ARG_SECTION_NUMBER)));
            setListAdapter(boardAdapter);
            return super.onCreateView(inflater, container, savedInstanceState);
        }


        private String[] getBoardNames(int boardTypeId) {
            switch (boardTypeId) {
                case 0:
                    return new String[] {
                            "Прикрепленная доска 1",
                            "Прикрепленная доска 2",
                            "Прикрепленная доска 3"
                    };
                case 1:
                    return new String[] {
                            "Недавняя доска 1",
                            "Недавняя доска 2",
                            "Недавняя доска 3"
                    };
                case 2:
                    return new String[] {
                            "Частная доска 1",
                            "Частная доска 2",
                            "Частная доска 3"
                    };
            }
            return new String[0];
        }

        public static SwipeFragment newInstance(int sectionNumber) {
            SwipeFragment fragment = new SwipeFragment();
            Bundle args = new Bundle();
            args.putInt(ARG_SECTION_NUMBER, sectionNumber);
            fragment.setArguments(args);
            return fragment;
        }

        @Override
        public void onListItemClick(ListView l, View v, int position, long id) {
            super.onListItemClick(l, v, position, id);
        }
    }*/