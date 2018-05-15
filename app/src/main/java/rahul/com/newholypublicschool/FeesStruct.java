package rahul.com.newholypublicschool;

import android.app.ProgressDialog;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.RadioButton;
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

public class FeesStruct extends AppCompatActivity {
    ProgressDialog pd;
    private static final String TAG_NAME = "FEE_DESC";
    private static final String TAG_AMOUNT = "SEF_FEE_AMOUNT";
    private static final String TAG_STATUS = "Status";

    private static final String TAG_PAID_DATE = "FRC_DATE";
    private static final String TAG_PAID_DUE = "FRC_PREV_DUE";
    private static final String TAG_PAID_FEE = "FRC_FEE_AMT";
    private static final String TAG_PAID_FINE = "FRC_LATE_FINE";
    private static final String TAG_PAID_AMT = "FRC_PAID_AMT";

    SharedPresencesUtility sharedPresencesUtility;
    RadioButton paid,dues;
    ArrayList<HashMap<String, String>> personList;
    ArrayList<HashMap<String, String>> personList2;
    ListView list;
    String userId, pass;
    ImageView back;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fees);
        back = findViewById(R.id.back);
        sharedPresencesUtility=new SharedPresencesUtility( FeesStruct.this );
        paid = findViewById(R.id.paid);
        dues = findViewById(R.id.dues);
        list = (ListView) findViewById( R.id.listView );
        personList = new ArrayList<HashMap<String,String>>();
        personList2 = new ArrayList<HashMap<String,String>>();

        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });

        if(sharedPresencesUtility!=null){
            userId=sharedPresencesUtility.getUserId(FeesStruct.this);
        }else{
            userId="1";
        }


        if(sharedPresencesUtility!=null){
            pass=sharedPresencesUtility.getPassword(FeesStruct.this);
        }else{
            pass="1";
        }







        new DuesFetch().execute( "http://holygroup.aleriseducom.com/api/Studentfeedetails.aspx?user_name=" +userId+ "&password=" +pass );

        dues.setOnClickListener( new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new DuesFetch().execute( "http://holygroup.aleriseducom.com/api/Studentfeedetails.aspx?user_name=" +userId+ "&password=" +pass );

                // http://holygroup.aleriseducom.com/api/Studentfeedetails.aspx?user_name=9895&password=8860848867
            }
        });

        paid.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                new PaidFetch().execute( "http://holygroup.aleriseducom.com/API/feedetails.aspx?user_name=" +userId+"&password=" +pass );
            }
        });


    }

    private class DuesFetch extends AsyncTask<String, String, String> {

        protected void onPreExecute() {
            super.onPreExecute();

            pd = new ProgressDialog( FeesStruct.this );
            pd.setMessage( "Please wait" );
            pd.setCancelable( false );
            pd.show();
        }

        protected String doInBackground(String... params) {


            HttpURLConnection connection = null;
            BufferedReader reader = null;

            try {
                URL url = new URL( params[0] );

                Log.d("dues" , url.toString());

                connection = (HttpURLConnection) url.openConnection();
                connection.connect();


                InputStream stream = connection.getInputStream();

                reader = new BufferedReader( new InputStreamReader( stream ) );

                StringBuffer buffer = new StringBuffer();
                String line = "";

                while ((line = reader.readLine()) != null) {
                    buffer.append( line + "\n" );
                    Log.d( "Responsedues: ", "> " + line );   //here u ll get whole response...... :-)

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

                personList.clear();

                for (int i = 0; i < array1.length(); i++) {

                    JSONObject obj1 = array1.getJSONObject(i);
                    String Name = obj1.getString(TAG_NAME);
                    String amount = "Amount : "+ obj1.getString(TAG_AMOUNT);
                    String status = "Status : " +obj1.getString(TAG_STATUS);

                    HashMap<String, String> persons = new HashMap<String, String>();

                    persons.put( TAG_NAME, Name );
                    persons.put( TAG_AMOUNT, amount );
                    persons.put( TAG_STATUS, status );
                    personList.add( persons );

                }

                ListAdapter adapter = new SimpleAdapter(
                        FeesStruct.this, personList, R.layout.item_dues_fee,
                        new String[]{TAG_NAME, TAG_AMOUNT, TAG_STATUS}, new int[]{R.id.name, R.id.amount, R.id.status}
                );
                list.setAdapter(null);
                list.setAdapter( adapter );

            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    private class PaidFetch extends AsyncTask<String, String, String> {

        protected void onPreExecute() {
            super.onPreExecute();

            pd = new ProgressDialog( FeesStruct.this );
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

                personList2.clear();

                for (int i = 0; i < array1.length(); i++) {

                    JSONObject obj1 = array1.getJSONObject(i);
                    String Date = "Date : "+obj1.getString(TAG_PAID_DATE);
                    String dues = "Previous Dues : "+obj1.getString(TAG_PAID_DUE);
                    String fee = "Total Fee : "+obj1.getString(TAG_PAID_FEE);
                    String fine = "Late Fine : "+obj1.getString(TAG_PAID_FINE);
                    String amount = "Paid Amount : "+obj1.getString(TAG_PAID_AMT);

                    HashMap<String, String> persons2 = new HashMap<String, String>();

                    persons2.put( TAG_PAID_DATE, Date );
                    persons2.put( TAG_PAID_DUE, dues );
                    persons2.put( TAG_PAID_FEE, fee );
                    persons2.put( TAG_PAID_FINE, fine );
                    persons2.put( TAG_PAID_AMT, amount );
                    personList2.add( persons2 );

                }

                ListAdapter adapter2 = new SimpleAdapter(
                        FeesStruct.this, personList2, R.layout.item_paid_fee,
                        new String[]{TAG_PAID_DATE, TAG_PAID_DUE, TAG_PAID_FEE,TAG_PAID_FINE,TAG_PAID_AMT}, new int[]{R.id.date, R.id.dues, R.id.fee, R.id.fine, R.id.paid}
                );
                list.setAdapter(null);
                list.setAdapter( adapter2 );

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
