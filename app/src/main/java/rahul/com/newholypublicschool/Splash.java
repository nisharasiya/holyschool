package rahul.com.newholypublicschool;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;


public class Splash extends Activity {
    boolean ispaused = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
       // setContentView(R.layout.activity_splash);
        setContentView(R.layout.activity_splashd);
    }

    @Override
    protected void onResume() {
        super.onResume();
        ispaused=false;
        final Thread timerThread = new Thread() {
            public void run() {
                try {
                    sleep(2500);
                    if(!ispaused)
                    {

                        if(AppPrefs.getInstance(Splash.this).getData("UserType","null").equalsIgnoreCase("2"))
                        {
                            Intent intent = new Intent(Splash.this, Reception.class);
                            startActivity(intent);
                            finish();
                        }
                        else if(AppPrefs.getInstance(Splash.this).getData("UserType","null").equalsIgnoreCase("0"))
                        {
                            Intent intent = new Intent(Splash.this, DashboardManager.class);
                            startActivity(intent);
                            finish();
                        }
                        else if(AppPrefs.getInstance(Splash.this).getData("UserType","null").equalsIgnoreCase("1"))
                        {
                            Intent intent = new Intent(Splash.this, Teacher.class);
                            startActivity(intent);
                            finish();
                        }
                        else
                        {
                            Intent intent = new Intent(Splash.this, Login.class);
                            startActivity(intent);
                            finish();
                        }

                    }
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        };
        timerThread.start();
    }

    @Override
    protected void onPause() {
        super.onPause();
        ispaused = true;

    }




}
