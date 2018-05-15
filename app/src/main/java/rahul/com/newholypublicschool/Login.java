package rahul.com.newholypublicschool;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
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

import rahul.com.newholypublicschool.Utils.SharedPresencesUtility;

public class Login extends AppCompatActivity {
    private EditText mobileTxt, passTxt;
    private TextView login, signup, forgot;
    ProgressDialog pbar;
    ProgressDialog pd;
    SharedPresencesUtility sharedPresencesUtility;
    SessionMan session;
    TextView forgot_pass;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login);
        session = new SessionMan(Login.this);
        sharedPresencesUtility = new SharedPresencesUtility(Login.this);
        mobileTxt = (EditText) findViewById(R.id.login_mobile);
        passTxt = (EditText) findViewById(R.id.login_password);
        login = findViewById(R.id.loginButton);
        forgot_pass = findViewById(R.id.forgot_pass);
        forgot_pass.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Login.this, Forgotpass.class);
                startActivity(intent);
            }
        });
        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (!checkEmpty(mobileTxt)) {
                    if (!checkEmpty(passTxt)) {
                        String userId = mobileTxt.getText().toString();
                        String pass = passTxt.getText().toString();

                        new JsonTask().execute( "http://holygroup.aleriseducom.com/API/loginPage.aspx?user_name=" +userId+ "&password=" +pass );
                    } else {
                        Toast.makeText(Login.this, "Please enter password!", Toast.LENGTH_SHORT).show();
                    }
                } else {
                    Toast.makeText(Login.this, "Please enter User Id!", Toast.LENGTH_SHORT).show();
                }

            }
        });

    }

    private class JsonTask extends AsyncTask<String, String, String> {

        protected void onPreExecute() {
            super.onPreExecute();

            pd = new ProgressDialog( Login.this );
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
                String status = jsonObject.getString( "usertype" );
                AppPrefs.getInstance(Login.this).putData("UserType",status);
                sharedPresencesUtility.setUserLoginId( Login.this, mobileTxt.getText().toString());
                sharedPresencesUtility.setUserPassword( Login.this, passTxt.getText().toString());
                if (status.equals("0")){
                    Intent intent = new Intent(Login.this, DashboardManager.class);
                    startActivity(intent);
                    finish();
                }else if (status.equals("1")){
                    Intent intent = new Intent(Login.this, Teacher.class);
                    startActivity(intent);
                    finish();
                }else if (status.equals("2")) {
                    Intent intent = new Intent(Login.this, Reception.class);
                    startActivity(intent);
                    finish();
                }else {
                    Toast.makeText(Login.this, "Invalid Credentials!!" , Toast.LENGTH_SHORT).show();
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