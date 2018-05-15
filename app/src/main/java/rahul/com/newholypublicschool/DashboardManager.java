package rahul.com.newholypublicschool;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.PopupMenu;
import android.widget.Toast;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;
import rahul.com.newholypublicschool.Utils.SharedPresencesUtility;

public class DashboardManager extends AppCompatActivity {
    private Toolbar toolbar;
    private TabLayout tabLayout;
    private ViewPager viewPager;
    ImageView i;
    CircleImageView a;
    String strPic;
    SharedPresencesUtility sharedPresencesUtility;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.dash);
        sharedPresencesUtility=new SharedPresencesUtility( DashboardManager.this );
        i=(ImageView)findViewById(R.id.mnu) ;
        a=(CircleImageView)findViewById(R.id.a) ;
        i.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                PopupMenu popup = new PopupMenu(DashboardManager.this, i);
            //Inflating the Popup using xml file
            popup.getMenuInflater().inflate(R.menu.popup_menu, popup.getMenu());

            //registering popup with OnMenuItemClickListener
            popup.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                public boolean onMenuItemClick(MenuItem item) {

                    AppPrefs.getInstance(DashboardManager.this).clearAll();
                    Intent id=new Intent(DashboardManager.this,Login.class);
                    startActivity(id);
                    DashboardManager.this.finish();

                    return true;
                }
            });

            popup.show();
            }
        });





        a.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(DashboardManager.this, My_info.class);
                startActivity(intent);
            }
        });
        viewPager = (ViewPager) findViewById(R.id.viewpager);
        setupViewPager(viewPager);

        tabLayout = (TabLayout) findViewById(R.id.tabs);
        tabLayout.setupWithViewPager(viewPager);

    }

    @Override
    protected void onResume() {
        super.onResume();

        if(sharedPresencesUtility!=null){
            strPic=sharedPresencesUtility.getRegistration(DashboardManager.this);
        }

        if (strPic.equals("")){
            Toast.makeText(DashboardManager.this, "Change profile photo" , Toast.LENGTH_LONG).show();
        }else {
            Picasso.with( getApplicationContext() ).load( "http://holygroup.aleriseducom.com/stuimage/"+strPic ).into( a );
            Log.d("pic" , "http://holygroup.aleriseducom.com/stuimage/"+strPic);
        }


    }

    private void setupViewPager(ViewPager viewPager) {

       // b.putString("sname","sd");

        ViewPagerAdapter adapter = new ViewPagerAdapter(getSupportFragmentManager());
        adapter.addFragment(new MainActivity(), "Student");
        adapter.addFragment(new Academics(), "Academics");
        viewPager.setAdapter(adapter);
    }

    public ArrayList<String> getval()
    {
        Bundle b=new Bundle();
        b=getIntent().getExtras();
        ArrayList<String> a=new ArrayList<>();
        a.add(b.getString("sid"));
        a.add(b.getString("name"));
        a.add(b.getString("fname"));
        return a;
    }


    class ViewPagerAdapter extends FragmentPagerAdapter {

        private final List<Fragment> mFragmentList = new ArrayList<>();

        private final List<String> mFragmentTitleList = new ArrayList<>();

        public ViewPagerAdapter(FragmentManager manager) {
            super(manager);
        }

        @Override
        public Fragment getItem(int position) {
            return mFragmentList.get(position);
        }

        @Override
        public int getCount() {
            return mFragmentList.size();
        }

        public void addFragment(Fragment fragment, String title) {
            mFragmentList.add(fragment);
            mFragmentTitleList.add(title);
        }

        @Override
        public CharSequence getPageTitle(int position) {
            return mFragmentTitleList.get(position);
        }
    }
}
