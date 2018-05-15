package rahul.com.newholypublicschool;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.PopupMenu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;

import rahul.com.newholypublicschool.Utils.SharedPresencesUtility;

public class Reception extends AppCompatActivity {

    EditText name,mobile,address,met_person_name,met_person_cont_mob,pur;
    RadioButton mle,femle,other;
    ProgressDialog progressDialog;
    ImageView logout;
    TextView submit,record;
    private ProgressDialog pDialog;
    private JSONObject json;
    private int success=0;
    private HTTPURLConnection service;
    String path;
    SharedPresencesUtility sharedPresencesUtility;
    String strName,strPhone,strAddress,strMName,strMContact,strPurpose,strSex;
    private RadioGroup radioSexGroup;
    private RadioButton radioSexButton;

    @Override
    protected void onCreate( Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.reception);
        service = new HTTPURLConnection();
        sharedPresencesUtility=new SharedPresencesUtility( Reception.this );
        name = findViewById(R.id.recname);
        mobile = findViewById(R.id.recmob);
        address = findViewById(R.id.recadd);
        record = findViewById(R.id.record);
        met_person_name = findViewById(R.id.recmeetname);
        met_person_cont_mob = findViewById(R.id.recmeetno);
        logout = (ImageView) findViewById(R.id.mnu);
        progressDialog = new ProgressDialog(Reception.this);
        progressDialog.setMessage("Submitting Details");
        radioSexGroup=(RadioGroup)findViewById(R.id.radioGroup);
        pur = findViewById(R.id.recmeetpurpose);
        mle = (RadioButton) findViewById(R.id.male);
        femle = (RadioButton) findViewById(R.id.female);
        other = (RadioButton) findViewById(R.id.other);
        submit = (TextView) findViewById(R.id.submitr);

        femle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                strSex = "Female";
            }
        });

        mle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                strSex = "Male";
            }
        });

        other.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                strSex = "Other";
            }
        });

        logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                PopupMenu popup = new PopupMenu(Reception.this, logout);
                //Inflating the Popup using xml file
                popup.getMenuInflater().inflate(R.menu.popup_menu, popup.getMenu());

                //registering popup with OnMenuItemClickListener
                popup.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                    public boolean onMenuItemClick(MenuItem item) {

                        AppPrefs.getInstance(Reception.this).clearAll();
                        Intent id = new Intent(Reception.this, Login.class);
                        startActivity(id);
                        finish();
                        // Toast.makeText(getContext(),"You Clicked : " + item.getTitle(),Toast.LENGTH_SHORT).show();

                        return true;
                    }
                });

                popup.show();


            }
        });

        record.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Reception.this, ReceptionRecord.class);
                startActivity(intent);
            }
        });

        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                strName = name.getText().toString();
                strPhone = mobile.getText().toString();
                strAddress = address.getText().toString();
                strMName = met_person_name.getText().toString();
                strMContact = met_person_cont_mob.getText().toString();
                strPurpose = pur.getText().toString();
                if (!strName.equals( "" ) && !strPhone.equals( "" )&& !strAddress.equals( "" )&& !strMName.equals( "" )&& !strMContact.equals( "" )&& !strPurpose.equals( "" ) && !strSex.equals( "" )) {

                    strName =strName.replaceAll(" ","%20");
                    strPhone =strPhone.replaceAll(" ","%20");
                    strAddress =strAddress.replaceAll(" ","%20");
                    strMName =strMName.replaceAll(" ","%20");
                    strMContact =strMContact.replaceAll(" ","%20");
                    strPurpose =strPurpose.replaceAll(" ","%20");
                    strSex =strPurpose.replaceAll(" ","%20");


                    String userId;
                    if(sharedPresencesUtility!=null){
                        userId=sharedPresencesUtility.getUserId(Reception.this);
                    }else{
                        userId="1";
                    }

                    path = "http://holygroup.aleriseducom.com/API/Reception.aspx?userid="+userId+"&name="+strName+"&Gender=M&mobile="+strMContact+"&address="+strAddress+"&mperson="+strMName+"&mpcn="+strMContact+"&mpurpose="+strPurpose;

                    new PostDataTOServer().execute();
                } else {
                    Toast.makeText( getApplicationContext(), "Please Enter all fields", Toast.LENGTH_LONG ).show();
                }
            }
        });

    }

    private class PostDataTOServer extends AsyncTask<Void, Void, Void> {

        String response = "";
        //Create hashmap Object to send parameters to web service
        HashMap<String, String> postDataParams;
        @Override
        protected void onPreExecute() {
            super.onPreExecute();

            pDialog = new ProgressDialog(Reception.this);
            pDialog.setMessage("Sending message...");
            pDialog.setCancelable(false);
            pDialog.show();
        }
        @Override
        protected Void doInBackground(Void... arg0) {
            postDataParams=new HashMap<String, String>();
            postDataParams.put("name", strName);
            postDataParams.put("Gender", strSex);
            postDataParams.put("mobile", strPhone);
            postDataParams.put("address", strAddress);
            postDataParams.put("mperson", strMName);
            postDataParams.put("mpcn", strMContact);
            postDataParams.put("mpurpose", strPurpose);
            response= service.ServerData(path,postDataParams);
            try {
                json = new JSONObject(response);
                //Get Values from JSONobject
                System.out.println("status=" + json.get("status"));
                success = json.getInt("status");

            } catch (JSONException e) {
                e.printStackTrace();
            }
            return null;
        }
        @Override
        protected void onPostExecute(Void result) {
            super.onPostExecute(result);
            if (pDialog.isShowing())
                pDialog.dismiss();
            if(success==1) {

                Toast.makeText(getApplicationContext(), "Submitted successfully!", Toast.LENGTH_LONG).show();
                name.setText( "" );
                mobile.setText( "" );
                address.setText( "" );
                met_person_name.setText( "" );
                met_person_cont_mob.setText( "" );
                pur.setText( "" );
            }
        }
    }
}
