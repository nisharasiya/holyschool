package rahul.com.newholypublicschool;

import android.app.DatePickerDialog;
import android.app.ProgressDialog;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.RequiresApi;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
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
import java.util.Calendar;
import java.util.HashMap;

import rahul.com.newholypublicschool.Utils.SharedPresencesUtility;

public class ReceptionRecord extends AppCompatActivity {

    ProgressDialog pd;
    private static final String TAG_NAME = "name";
    private static final String TAG_MOBILE = "mobile";
    private static final String TAG_ADDRESS = "address";
    private static final String TAG_PURPOSE = "mpurpose";
    private static final String TAG_DATE = "datetime";
    ImageView back;
    SharedPresencesUtility sharedPresencesUtility;

    ArrayList<HashMap<String, String>> personList;
    ListView list;
    Button find;

    private static final String TAG = "Appointment";
    private TextView mDisplayDate,mDisplayDate2;
    private DatePickerDialog.OnDateSetListener mDateSetListener;
    private DatePickerDialog.OnDateSetListener mDateSetListener2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reception_record);
        back = findViewById(R.id.back);
        sharedPresencesUtility=new SharedPresencesUtility( ReceptionRecord.this );

        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });

        mDisplayDate = (TextView) findViewById(R.id.fromDate);
        mDisplayDate.setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.N)
            @Override
            public void onClick(View view) {
                Calendar cal = Calendar.getInstance();
                int year = cal.get(Calendar.YEAR);
                int month = cal.get(Calendar.MONTH);
                int day = cal.get(Calendar.DAY_OF_MONTH);

                DatePickerDialog dialog = new DatePickerDialog(ReceptionRecord.this,
                        android.R.style.Theme_Holo_Light_Dialog_MinWidth,
                        mDateSetListener,
                        year,month,day);
                dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                dialog.show();
            }
        });

        mDateSetListener = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker datePicker, int year, int month, int day) {
                month = month + 1;
                Log.d(TAG, "onDateSet: mm/dd/yyy: " + month + "/" + day + "/" + year);

                String date = month + "/" + day + "/" + year;
                mDisplayDate.setText(date);
            }
        };

        mDisplayDate2 = (TextView) findViewById(R.id.toDate);
        mDisplayDate2.setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.N)
            @Override
            public void onClick(View view) {
                Calendar cal2 = Calendar.getInstance();
                int year = cal2.get(Calendar.YEAR);
                int month = cal2.get(Calendar.MONTH);
                int day = cal2.get(Calendar.DAY_OF_MONTH);

                DatePickerDialog dialog2 = new DatePickerDialog(ReceptionRecord.this,
                        android.R.style.Theme_Holo_Light_Dialog_MinWidth,
                        mDateSetListener2,
                        year,month,day);
                dialog2.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                dialog2.show();
            }
        });

        mDateSetListener2 = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker datePicker, int year, int month, int day) {
                month = month + 1;
                Log.d(TAG, "onDateSet: mm/dd/yyy: " + month + "/" + day + "/" + year);

                String date2 = month + "/" + day + "/" + year;
                mDisplayDate2.setText(date2);
            }
        };

        list = (ListView) findViewById( R.id.listView );
        find =  findViewById( R.id.find );
        personList = new ArrayList<HashMap<String,String>>();



        find.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String userId;
                if (sharedPresencesUtility != null) {
                    userId = sharedPresencesUtility.getUserId(ReceptionRecord.this);
                } else {
                    userId = "1";
                }

                String fromDate = mDisplayDate.getText().toString();
                String toDate = mDisplayDate2.getText().toString();
                if (!fromDate.equals("Select From Date") && !toDate.equals("Select To Date")) {
                    new RecordFetch().execute("http://holygroup.aleriseducom.com/API/Receptiondetial.aspx?User_name=" + userId + "&fromdate=" + fromDate + "&todate=" + toDate + "&name=&mobile=");

                }else {
                    Toast.makeText(ReceptionRecord.this, "Enter all fields!!" , Toast.LENGTH_LONG).show();
                }
            }
        });

    }

    private class RecordFetch extends AsyncTask<String, String, String> {

        protected void onPreExecute() {
            super.onPreExecute();

            pd = new ProgressDialog( ReceptionRecord.this );
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
                    String Name = obj1.getString(TAG_NAME);
                    String Mobile = "Mobile : " +obj1.getString(TAG_MOBILE);
                    String Address = "Address : " +obj1.getString(TAG_ADDRESS);
                    String Purpose = "Purpose : " +obj1.getString(TAG_PURPOSE);
                    String Date = "Date : " +obj1.getString(TAG_DATE);

                    HashMap<String, String> persons = new HashMap<String, String>();

                    persons.put( TAG_NAME, Name );
                    persons.put( TAG_MOBILE, Mobile );
                    persons.put( TAG_ADDRESS, Address );
                    persons.put( TAG_PURPOSE, Purpose );
                    persons.put( TAG_DATE, Date );
                    personList.add( persons );

                }

                ListAdapter adapter = new SimpleAdapter(
                        ReceptionRecord.this, personList, R.layout.item_reception,
                        new String[]{TAG_NAME, TAG_MOBILE,TAG_ADDRESS,TAG_PURPOSE,TAG_DATE},
                        new int[]{R.id.user_name, R.id.phone, R.id.address, R.id.purpose, R.id.date}
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
