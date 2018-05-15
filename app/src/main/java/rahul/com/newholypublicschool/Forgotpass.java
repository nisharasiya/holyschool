package rahul.com.newholypublicschool;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

public class Forgotpass extends AppCompatActivity {

    ProgressDialog pd;
    Context mContext;
    ImageView back;
    EditText userid;
    TextView submit;
    String strUser;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.forgot_pass);
        mContext = Forgotpass.this;
        back = findViewById(R.id.back);
        userid = findViewById(R.id.mobile_number);
        submit = findViewById(R.id.submitbutton);
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });

        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                strUser = userid.getText().toString();

                if (!strUser.equals("")){
                    new JsonTask().execute("http://holygroup.aleriseducom.com/API/FORGOTIDPASSWORD.aspx?user_name=" +strUser);
                }else {
                    Toast.makeText(Forgotpass.this, "Please enter user Id!" , Toast.LENGTH_LONG).show();
                }
            }
        });

    }
    private class JsonTask extends AsyncTask<String, String, String> {

        protected void onPreExecute() {
            super.onPreExecute();

            pd = new ProgressDialog( Forgotpass.this );
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
                String status = jsonObject.getString( "status" );
                String msg = jsonObject.getString( "errorMessage" );

                if (status.equals("1")){
                    AlertDialog.Builder builder = new AlertDialog.Builder(Forgotpass.this);
                    builder.setMessage(msg)
                            .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int id) {
                                    Intent intent = new Intent(Forgotpass.this , Login.class);
                                    startActivity(intent);
                                    finish();
                                }
                            })
                            .create()
                            .show();

                }else if (status.equals("0")){
                    Toast.makeText(Forgotpass.this, "Try Later!" , Toast.LENGTH_LONG).show();
                } else {
                    Toast.makeText(Forgotpass.this, "Try Later!" , Toast.LENGTH_LONG).show();
                }

            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
    }

    public static boolean checkEmpty(EditText etText)
    {
        if(etText.getText().toString().trim().length() <= 0)
            return true;
        else
            return false;
    }

}
