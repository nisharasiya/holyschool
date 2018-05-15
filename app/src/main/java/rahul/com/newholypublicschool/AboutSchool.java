package rahul.com.newholypublicschool;

import android.app.ProgressDialog;
import android.content.Context;
import android.graphics.Color;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.widget.ImageView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

public class AboutSchool extends AppCompatActivity {

    ProgressDialog pd;
    Context mContext;
    ImageView back;
    String url;
    WebView web;


    String ur = "http://holygroup.aleriseducom.com/api/getdomain.aspx";

    Toolbar toolbar;
    @Override

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_about_school);

        toolbar = findViewById(R.id.toolbar);

        setSupportActionBar(toolbar);

        getSupportActionBar().setDisplayHomeAsUpEnabled(false);

        toolbar.setTitleTextColor(Color.WHITE);

        toolbar.setNavigationIcon(R.drawable.whiteback);

        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                finish();
            }
        });

        toolbar.setTitle("AboutSchool");
        mContext = AboutSchool.this;
        back = findViewById(R.id.back);


        web = (WebView)findViewById(R.id.web);

        web.getSettings().setJavaScriptEnabled(true);

        //ur = getIntent().getStringExtra("ur");

        //web.loadUrl(ur);
        web.setHorizontalScrollBarEnabled(false);




        /*back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });*/

        new JsonTask().execute( ur);


    }

    private class JsonTask extends AsyncTask<String, String, String> {

        protected void onPreExecute() {
            super.onPreExecute();

            pd = new ProgressDialog( AboutSchool.this );
            pd.setMessage( "Please wait" );
            pd.setCancelable( false );
            pd.show();
        }

        protected String doInBackground(String... params) {


            HttpURLConnection connection = null;
            BufferedReader reader = null;

            try {
                URL url = new URL( params[0] );
                connection = (HttpURLConnection) url.openConnection();
                connection.connect();


                InputStream stream = connection.getInputStream();

                reader = new BufferedReader( new InputStreamReader( stream ) );

                StringBuffer buffer = new StringBuffer();
                String line = "";

                while ((line = reader.readLine()) != null) {
                    buffer.append( line + "\n" );
                    Log.d( "Response: ", "> " + line );

                }

                return buffer.toString();


            } catch (MalformedURLException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            } finally {
                if (connection != null) {
                    connection.disconnect();
                }
                try {
                    if (reader != null) {
                        reader.close();
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            return null;
        }

        @Override
        protected void onPostExecute(String result) {
            super.onPostExecute( result );
            if (pd.isShowing()) {
                pd.dismiss();
            }

            try {
                JSONObject jsonObject = new JSONObject( result.toString() );
                JSONArray outerArray=jsonObject.getJSONArray( "result" );
                JSONObject innerObject= (JSONObject) outerArray.get( 0 );

                url =  innerObject.getString( "Domain" );

                WebSettings webSettings = web.getSettings();
                webSettings.setDisplayZoomControls(true);
                webSettings.setBuiltInZoomControls(true);
                web.getSettings().setLoadWithOverviewMode(true);
                web.getSettings().setUseWideViewPort(true);
                webSettings.setJavaScriptEnabled(true);
                Toast.makeText(getApplicationContext(),"Slow loading.... Please wait",Toast.LENGTH_LONG).show();

                web.loadUrl(url);


            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
    }
}
