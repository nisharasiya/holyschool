package rahul.com.newholypublicschool;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

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

import rahul.com.newholypublicschool.Utils.SharedPresencesUtility;

public class Bus extends AppCompatActivity {

    TextView number,route,stop,fare,stop_type,incharge,inchargeNo,conductor,conductorNo;
    ProgressDialog pd;
    Context mContext;
    SharedPresencesUtility sharedPresencesUtility;
    ImageView back;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bus);
        sharedPresencesUtility=new SharedPresencesUtility( Bus.this );
        mContext = Bus.this;
        back = findViewById(R.id.back);
        number = findViewById(R.id.number);
        route = findViewById(R.id.route);
        stop = findViewById(R.id.stop);
        fare = findViewById(R.id.fare);
        stop_type = findViewById(R.id.stop_type);
        incharge = findViewById(R.id.incharge);
        inchargeNo = findViewById(R.id.inchargeNo);
        conductor = findViewById(R.id.conductor);
        conductorNo = findViewById(R.id.conductorNo);

        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });

        String userId;
        if(sharedPresencesUtility!=null){
            userId=sharedPresencesUtility.getUserId(Bus.this);
        }else{
            userId="1";
        }

        String pass;
        if(sharedPresencesUtility!=null){
            pass=sharedPresencesUtility.getPassword(Bus.this);
        }else{
            pass="1";
        }

        new JsonTask().execute( "http://holygroup.aleriseducom.com/API/busDETAIL.aspx?user_name=" +userId+ "&password=" +pass );
    }

    private class JsonTask extends AsyncTask<String, String, String> {

        protected void onPreExecute() {
            super.onPreExecute();

            pd = new ProgressDialog( Bus.this );
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

                number.setText( innerObject.getString( "vehicle_no" ) );

                route.setText( innerObject.getString( "Route" ) );

                stop.setText( innerObject.getString( "Stop" ) );

                fare.setText(  innerObject.getString( "Fare" ) );

                stop_type.setText(  innerObject.getString( "stop_type" ) );

                incharge.setText(  innerObject.getString( "VEHICLE_INCHARGE" ) );
                inchargeNo.setText(  innerObject.getString( "VEHICLE_INCHARGE_MOB" ) );
                conductor.setText(  innerObject.getString( "VEHICLE_CONDUCTOR" ) );
                conductorNo.setText(  innerObject.getString( "VEHICLE_CONDUCTOR_MOB" ) );


            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
    }
}
