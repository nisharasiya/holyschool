package rahul.com.newholypublicschool;

import android.app.ProgressDialog;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.SimpleAdapter;

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
import java.util.ArrayList;
import java.util.HashMap;

import rahul.com.newholypublicschool.Utils.SharedPresencesUtility;

public class Classmate extends AppCompatActivity {

    ProgressDialog pd;
    private static final String TAG_NAME = "student_name";
    private static final String TAG_ROLL = "Roll";
    private static final String TAG_REG = "AdmNo";
    ImageView back;
    SharedPresencesUtility sharedPresencesUtility;

    ArrayList<HashMap<String, String>> personList;
    ListView list;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate( savedInstanceState );
        setContentView( R.layout.activity_classmate );
        back = findViewById(R.id.back);

        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
        sharedPresencesUtility=new SharedPresencesUtility( Classmate.this );

        list = (ListView) findViewById( R.id.listView );
        personList = new ArrayList<HashMap<String,String>>();

        String userId;
        if(sharedPresencesUtility!=null){
            userId=sharedPresencesUtility.getUserId(Classmate.this);
        }else{
            userId="1";
        }

        String pass;
        if(sharedPresencesUtility!=null){
            pass=sharedPresencesUtility.getPassword(Classmate.this);
        }else{
            pass="1";
        }

        new MyClassmateFetch().execute( "http://holygroup.aleriseducom.com/API/myclassmate.aspx?user_name=" +userId+ "&password=" +pass );
    }


    private class MyClassmateFetch extends AsyncTask<String, String, String> {

        protected void onPreExecute() {
            super.onPreExecute();

            pd = new ProgressDialog( Classmate.this );
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
                    Log.d( "Response: ", "> " + line );   //here u ll get whole response...... :-)

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
                JSONArray array1=new JSONArray(result.toString());

                for (int i = 0; i < array1.length(); i++) {

                    JSONObject obj1 = array1.getJSONObject(i);
                    String classmateName = obj1.getString(TAG_NAME);
                    String parentsMobile = obj1.getString(TAG_ROLL);
                    String registration = obj1.getString(TAG_REG);

                    HashMap<String, String> persons = new HashMap<String, String>();

                    persons.put( TAG_NAME, classmateName );
                    persons.put( TAG_ROLL, parentsMobile );
                    persons.put( TAG_REG, registration );
                    personList.add( persons );

                }

                ListAdapter adapter = new SimpleAdapter(
                        Classmate.this, personList, R.layout.list_myclassmate,
                        new String[]{TAG_NAME, TAG_ROLL}, new int[]{R.id.nameClassmate, R.id.numberClassmate}
                );

                list.setAdapter( adapter );

            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
    }

    @Override
    public void onContentChanged() {
        super.onContentChanged();

        View empty = findViewById(R.id.empty);
        ListView list = (ListView) findViewById(R.id.listView);
        list.setEmptyView(empty);
    }
}
