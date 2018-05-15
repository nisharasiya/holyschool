package rahul.com.newholypublicschool;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
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

import rahul.com.newholypublicschool.Utils.SharedPresencesUtility;

public class PasswordChange extends AppCompatActivity {
    ProgressDialog pd;
    Context mContext;
    SharedPresencesUtility sharedPresencesUtility;
    ImageView back;
    EditText old,newpswd,newpswd2;
    Button change;
    String pass, newpass, finalpass, newpass2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_password_change);

        sharedPresencesUtility=new SharedPresencesUtility( PasswordChange.this );
        mContext = PasswordChange.this;
        back = findViewById(R.id.back);
        old = findViewById(R.id.old);
        newpswd = findViewById(R.id.newpswd);
        newpswd2 = findViewById(R.id.newpswd2);
        change = findViewById(R.id.change);
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
        change.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String userId;
                if(sharedPresencesUtility!=null){
                    userId=sharedPresencesUtility.getUserId(PasswordChange.this);
                }else{
                    userId="1";
                }
                pass = old.getText().toString();
                newpass = newpswd.getText().toString();
                newpass2 = newpswd2.getText().toString();

                if (newpass.equals(newpass2)){
                    finalpass = newpass;
                }

                if (!pass.equals("") && !newpass.equals("") && !newpass2.equals("")){

                    if (!newpass.equals(newpass2)){
                        Toast.makeText(PasswordChange.this, "Password dont match!!" , Toast.LENGTH_LONG).show();
                    } else {
                        new JsonTask().execute( "http://holygroup.aleriseducom.com/API/Changepswd.aspx?user_name=" +userId+ "&password=" +pass + "&newpswd=" + finalpass);
                        sharedPresencesUtility.setUserPassword( PasswordChange.this, finalpass);
                    }

                }else {
                    Toast.makeText(PasswordChange.this, "Eneter All Fields!!" , Toast.LENGTH_LONG).show();
                }
            }
        });


    }

    private class JsonTask extends AsyncTask<String, String, String> {

        protected void onPreExecute() {
            super.onPreExecute();

            pd = new ProgressDialog( PasswordChange.this );
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

                if (status.equals("1")){
                    Toast.makeText(PasswordChange.this, "Password Changed successfully!" , Toast.LENGTH_LONG).show();
                    Intent intent = new Intent(PasswordChange.this, My_info.class);
                    startActivity(intent);
                    finish();
                }else if (status.equals("0")){
                    Toast.makeText(PasswordChange.this, "Unable to Change!" , Toast.LENGTH_LONG).show();
                } else {
                    Toast.makeText(PasswordChange.this, "Unable to Change!" , Toast.LENGTH_LONG).show();
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
