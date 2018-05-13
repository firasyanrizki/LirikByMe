package com.example.asus.LirikByMe;

import android.content.Intent;
import android.support.design.widget.TabLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.app.AppCompatDelegate;
import android.support.v7.widget.Toolbar;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    private SectionsPagerAdapter mSectionsPagerAdapter;
    private ViewPager mViewPager;
    public SQLiteAdapter SQLiteAdapter;
    private static final int ENTRY_REQUEST_CODE = 1;
    private static final int SETTING_REQUEST_CODE = 2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Log.d("TAG", "Check the theme");
        if (AppCompatDelegate.getDefaultNightMode()==AppCompatDelegate.MODE_NIGHT_YES){
            setTheme(R.style.darktheme);
        }else
            setTheme(R.style.AppTheme);

        SQLiteAdapter = new SQLiteAdapter(this);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        // Create the adapter that will return a fragment for each of the three
        // primary sections of the activity.
        mSectionsPagerAdapter = new SectionsPagerAdapter(getSupportFragmentManager());

        // Set up the ViewPager with the sections adapter.
        mViewPager = (ViewPager) findViewById(R.id.container);
        mViewPager.setAdapter(mSectionsPagerAdapter);
        mSectionsPagerAdapter.notifyDataSetChanged();

        TabLayout tabLayout = (TabLayout) findViewById(R.id.tabs);

        mViewPager.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(tabLayout));
        tabLayout.addOnTabSelectedListener(new TabLayout.ViewPagerOnTabSelectedListener(mViewPager));

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, EntryActivity.class);
                intent.putExtra("night_mode", AppCompatDelegate.getDefaultNightMode());
                startActivityForResult(intent, ENTRY_REQUEST_CODE);
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == ENTRY_REQUEST_CODE){
            if (resultCode == RESULT_OK){
                Bundle extras = data.getExtras();
                if (extras == null){
                    Toast.makeText(MainActivity.this, "From Entry", Toast.LENGTH_LONG).show();
                } else {
                    AppCompatDelegate.setDefaultNightMode(extras.getInt("night_mode"));
                    String message = "Title: " + extras.getString("title") + "\n"
                            + "Link: " + extras.getString("link") + "\n"
                            + "Language: " + extras.getString("language") + "\n"
                            + "Type: " + extras.getString("type");
                    Toast.makeText(MainActivity.this, message, Toast.LENGTH_LONG).show();
                }
            }
        } else if (requestCode == SETTING_REQUEST_CODE){
            if (resultCode == RESULT_OK){
                AppCompatDelegate.setDefaultNightMode(data.getExtras().getInt("night_mode"));
                Toast.makeText(MainActivity.this, "From Setting "
                        + data.getExtras().getInt("night_mode") + " "
                        + AppCompatDelegate.getDefaultNightMode(), Toast.LENGTH_LONG).show();
            }
        }
        restartApp();
    }

    public void restartApp(){
        Intent i = new Intent(getApplicationContext(), MainActivity.class);
        startActivity(i);
        finish();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
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
            Intent intent = new Intent(MainActivity.this, SettingActivity.class);
            startActivityForResult(intent, SETTING_REQUEST_CODE);
            return true;
        }

        return super.onOptionsItemSelected(item);
    }


    /**
     * A {@link FragmentPagerAdapter} that returns a fragment corresponding to
     * one of the sections/tabs/pages.
     */
    public class SectionsPagerAdapter extends FragmentPagerAdapter {

        public SectionsPagerAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int position) {
            // getItem is called to instantiate the fragment for the given page.
            // Return a EntryFragment (defined as a static inner class below).
            if (position == 0){
                return EntryFragment.newInstance(position + 1);
            } else if (position == 1) {
                return ChartFragment.newInstance(position + 1);
            } else return null;
        }

        @Override
        public int getCount() {
            // Show 2 total pages.
            return 2;
        }

        @Override
        public CharSequence getPageTitle(int position){
            switch (position){
                case 0:
                    return "Entries";
                case 1:
                    return "Chart";
            }
            return null;
        }
    }
}
