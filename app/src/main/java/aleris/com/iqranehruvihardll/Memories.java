package aleris.com.iqranehruvihardll;

import android.graphics.Color;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.EditText;

public class Memories extends AppCompatActivity {

    Toolbar toolbar;

    TabLayout tabs;

    ViewPager pager;

    ViewAdapter adapter;

    EditText search;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_memories);

        toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(false);
        toolbar.setNavigationIcon(R.drawable.back);
        toolbar.setTitleTextColor(Color.WHITE);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                finish();
            }
        });

        toolbar.setTitle("Memories");

        tabs = findViewById(R.id.tabs);

        pager = findViewById(R.id.viewpager);

        search = findViewById(R.id.search);

        adapter = new ViewAdapter(getSupportFragmentManager(), 3);

        tabs.addTab(tabs.newTab().setText("ALL PHOTOS"));
        tabs.addTab(tabs.newTab().setText("SCHOOL ALBUM"));
        tabs.addTab(tabs.newTab().setText("MY ALBUM"));


        pager.setAdapter(adapter);
        tabs.setupWithViewPager(pager);


        tabs.getTabAt(0).setText("ALL PHOTOS");
        tabs.getTabAt(1).setText("SCHOOL ALBUM");
        tabs.getTabAt(2).setText("MY ALBUM");


    }

    public class ViewAdapter extends FragmentStatePagerAdapter {


        public ViewAdapter(FragmentManager fm, int tab) {
            super(fm);
        }

        @Override
        public Fragment getItem(int position) {

            if (position == 0) {

                return new AllPhoto();
            } else if (position == 1) {

                return new SchoolAlbum();

            } else if (position == 2) {

                return new MyAlbum();
            }
            return null;
        }

        @Override
        public int getCount() {
            return 3;
        }
    }





}
