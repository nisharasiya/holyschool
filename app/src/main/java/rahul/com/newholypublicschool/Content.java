package rahul.com.newholypublicschool;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.PopupMenu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;


public class Content extends AppCompatActivity implements View.OnClickListener {

    String frag="";
    ImageView i;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.container_layout);

        Bundle bundle=getIntent().getExtras();

        frag=bundle.getString("Fragment");
        i=(ImageView)findViewById(R.id.mnu) ;
        i.setOnClickListener(this);
        if (frag.equalsIgnoreCase("transport"))
        {
            getSupportFragmentManager().beginTransaction().replace(R.id.container, new Transportt())
                    .commit();
        }
        else  if (frag.equalsIgnoreCase("rollt"))
        {
            getSupportFragmentManager().beginTransaction().replace(R.id.container, new Roll())
                    .commit();
        }

    }

    @Override
    public void onClick(View view) {
        if (view.getId()==R.id.mnu) {
            PopupMenu popup = new PopupMenu(Content.this, i);
            //Inflating the Popup using xml file
            popup.getMenuInflater().inflate(R.menu.popup_menu, popup.getMenu());

            //registering popup with OnMenuItemClickListener
            popup.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                public boolean onMenuItemClick(MenuItem item) {

                    AppPrefs.getInstance(Content.this).clearAll();
                    Intent id=new Intent(Content.this,Login.class);
                    startActivity(id);
                    Content.this.finish();
                    // Toast.makeText(getContext(),"You Clicked : " + item.getTitle(),Toast.LENGTH_SHORT).show();

                    return true;
                }
            });

            popup.show();
        }
    }
}
