package rahul.com.newholypublicschool;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.TextView;
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
import java.util.ArrayList;
import java.util.HashMap;

import rahul.com.newholypublicschool.Utils.SharedPresencesUtility;

public class Homework extends AppCompatActivity {

    ProgressDialog pd;
    private static final String TAG_DATE = "date";
    private static final String TAG_FILE = "ImageUrl";
    SharedPresencesUtility sharedPresencesUtility;
    ImageView back;
    ArrayList<HashMap<String, String>> personList;
    ListView list;
    Context context;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate( savedInstanceState );
        setContentView( R.layout.homewok );
        back = findViewById(R.id.back);
        context=this;
        list = (ListView) findViewById( R.id.listView );
        personList = new ArrayList<HashMap<String,String>>();
        sharedPresencesUtility=new SharedPresencesUtility( Homework.this );

        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });

        String userId;
        if(sharedPresencesUtility!=null){
            userId=sharedPresencesUtility.getUserId(Homework.this);
        }else{
            userId="1";
        }

        String pass;
        if(sharedPresencesUtility!=null){
            pass=sharedPresencesUtility.getPassword(Homework.this);
        }else{
            pass="1";
        }
        new HomeworkFetch().execute( "http://holygroup.aleriseducom.com/API/result.aspx?user_name=" +userId+ "&password=" +pass +"&menu=1");


    }

    private class HomeworkFetch extends AsyncTask<String, String, String> {

        protected void onPreExecute() {
            super.onPreExecute();

            pd = new ProgressDialog( Homework.this );
            pd.setMessage( "Loading Homework..." );
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
                    String date = "Date : "+ obj1.getString(TAG_DATE);
                    String ImageUrl = obj1.getString(TAG_FILE);

                    HashMap<String, String> persons = new HashMap<String, String>();

                    persons.put( TAG_DATE, date );
                    persons.put( TAG_FILE,   ImageUrl );
                    personList.add( persons );
                }

                ListAdapter adapter = new SimpleAdapter(
                        Homework.this, personList, R.layout.list_item,
                        new String[]{TAG_DATE,   TAG_FILE},
                        new int[]{R.id.tvDate, R.id.tvPdfName}

                );

                list.setAdapter(adapter);

                list.setOnItemClickListener( new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                        TextView tvPdfName= (TextView) view.findViewById(R.id.tvPdfName);
                        String strPdfName=tvPdfName.getText().toString();
                        if(CheckNetwork.isInternetAvailable(Homework.this)) //returns true if internet available
                        {
                            Intent intent =new Intent(context,Downloader.class ); //Pdf
                            intent.putExtra("PDFLINK",strPdfName);
                            startActivity(intent);


                        }
                        else
                        {
                            Toast.makeText(Homework.this,"No Internet Connection",Toast.LENGTH_SHORT).show();
                        }
                    }
                } );
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