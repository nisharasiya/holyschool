package rahul.com.newholypublicschool;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.PopupMenu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;

/**
 * Created by Siddharth on 12/18/2017.
 */

public class Teacher extends AppCompatActivity implements View.OnClickListener {
    ImageView i;
    RelativeLayout uploadll, feell, profilell, receptionll;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.teacherdash);
        i=(ImageView)findViewById(R.id.mnu) ;
        i.setOnClickListener(this);
        uploadll = findViewById(R.id.uploadll);
        profilell = findViewById(R.id.profilell);
        receptionll = findViewById(R.id.receptionll);
        feell = findViewById(R.id.feell);
        uploadll.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Teacher.this, TeacherUpload.class);
                startActivity(intent);
            }
        });

        feell.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Teacher.this, AboutSchool.class);
                startActivity(intent);
            }
        });

        profilell.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Teacher.this, TeacherInfo.class);
                startActivity(intent);
            }
        });

        receptionll.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Teacher.this, ReceptionRecord.class);
                startActivity(intent);
            }
        });
    }

    @Override
    public void onClick(View view) {
        if (view.getId()==R.id.mnu) {
            PopupMenu popup = new PopupMenu(Teacher.this, i);
            //Inflating the Popup using xml file
            popup.getMenuInflater().inflate(R.menu.popup_menu, popup.getMenu());

            //registering popup with OnMenuItemClickListener
            popup.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                public boolean onMenuItemClick(MenuItem item) {

                    AppPrefs.getInstance(Teacher.this).clearAll();
                    Intent id=new Intent(Teacher.this,Login.class);
                    startActivity(id);
                    Teacher.this.finish();
                    // Toast.makeText(getContext(),"You Clicked : " + item.getTitle(),Toast.LENGTH_SHORT).show();

                    return true;
                }
            });

            popup.show();
        }
    }
}
