package rahul.com.newholypublicschool;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.NetworkResponse;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.HttpHeaderParser;
import com.android.volley.toolbox.Volley;
import com.squareup.picasso.Picasso;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import rahul.com.newholypublicschool.Utils.SharedPresencesUtility;

public class My_info extends AppCompatActivity {
    ImageView std;
    TextView name, admission,admissionDate, session, dob, father_name, mother_name, mobile_no, address;
    TextView namedummy, admissiondummy,admissionDatedummy, sessiondummy, dobdummy, father_namedummy, mother_namedummy, mobile_nodummy, addressdummy;
    ImageButton classmate, teachers;
    ProgressDialog pd;
    Context mContext;
    SharedPresencesUtility sharedPresencesUtility;
    ImageView back;
    Button change;
    ImageView buttonLoadImage;
    private ProgressDialog pDialog;
    private JSONObject json;
    private int success=0;
    String imageString;
    private static int RESULT_LOAD_IMAGE = 1;
    ArrayList<String> worldlist;
    ArrayList<String> worldlist2;
    String file_name;
    ProgressDialog progressDialog;
    String UPLOAD_URL;
    Bitmap bitmap;

    public int PICK_IMAGE_REQUEST = 0;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate( savedInstanceState );
        setContentView( R.layout.activity_my_info );
        sharedPresencesUtility=new SharedPresencesUtility( My_info.this );
        mContext = My_info.this;
        back = findViewById(R.id.back);
        change = findViewById(R.id.change);
        std = (ImageView) findViewById( R.id.imageView2 );
        buttonLoadImage = (ImageView) findViewById( R.id.buttonLoadImage );
        name = (TextView) findViewById( R.id.name );
        admission = (TextView) findViewById( R.id.admission );
        admissionDate = (TextView) findViewById( R.id.admissionDate );
        session = (TextView) findViewById( R.id.session );
        dob = (TextView) findViewById( R.id.dob );
        father_name = (TextView) findViewById( R.id.father_name );
        mother_name = (TextView) findViewById( R.id.mother_name );
        mobile_no = (TextView) findViewById( R.id.mobile_no );
        address = (TextView) findViewById( R.id.address );

        namedummy = (TextView) findViewById( R.id.namedummy );
        admissiondummy = (TextView) findViewById( R.id.admissiondummy );
        admissionDatedummy = (TextView) findViewById( R.id.admissionDatedummy );
        sessiondummy = (TextView) findViewById( R.id.sessiondummy );
        dobdummy = (TextView) findViewById( R.id.dobdummy );
        father_namedummy = (TextView) findViewById( R.id.father_namedummy );
        mother_namedummy = (TextView) findViewById( R.id.mother_namedummy );
        mobile_nodummy = (TextView) findViewById( R.id.mobile_nodummy );
        addressdummy = (TextView) findViewById( R.id.addressdummy );

        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });

        change.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(My_info.this, PasswordChange.class);
                startActivity(intent);
            }
        });

        String userId;
        if(sharedPresencesUtility!=null){
            userId=sharedPresencesUtility.getUserId(My_info.this);
        }else{
            userId="1";
        }

        String pass;
        if(sharedPresencesUtility!=null){
            pass=sharedPresencesUtility.getPassword(My_info.this);
        }else{
            pass="1";
        }

        new JsonTask().execute( "http://holygroup.aleriseducom.com/API/studentdetails.aspx?user_name=" +userId+ "&password=" +pass );

        buttonLoadImage.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View arg0) {
                //Intent i = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                //startActivityForResult(i, 100);



                Intent intent = new Intent();
                intent.setAction(Intent.ACTION_GET_CONTENT);
                intent.setType("image/*");
                startActivityForResult(Intent.createChooser(intent, "Select File"), PICK_IMAGE_REQUEST);



            }
        });

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == PICK_IMAGE_REQUEST && resultCode == RESULT_OK && data != null) {

            Uri imageUri = data.getData();
            try {

                bitmap = MediaStore.Images.Media.getBitmap(this.getContentResolver(), imageUri);

                std.setImageBitmap(bitmap);
                Log.i("Upload",std.toString());

                //calling the method uploadBitmap to upload image
                uploadBitmap(bitmap);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }


    public byte[] getFileDataFromDrawable(Bitmap bitmap) {
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.PNG, 80, byteArrayOutputStream);
        return byteArrayOutputStream.toByteArray();
    }

    private void uploadBitmap(final Bitmap bitmap) {

        //getting the tag from the edittext
        final String tags = "something";
        long imagename = System.currentTimeMillis();
               String strName = imagename +".jpg";

        String userId;
        if(sharedPresencesUtility!=null){
            userId=sharedPresencesUtility.getUserId(My_info.this);
        }else{
            userId="1";
        }

        String pass;
        if(sharedPresencesUtility!=null){
            pass=sharedPresencesUtility.getPassword(My_info.this);
        }else{
            pass="1";
        }



        Toast.makeText(getApplicationContext(), "Uploading...Please wait!!", Toast.LENGTH_SHORT).show();
        UPLOAD_URL = "http://holygroup.aleriseducom.com/API/Student_image.aspx?UID="+userId+"&PASS="+pass+"&FILENAME=";
        Log.e("check", UPLOAD_URL);

        VolleyMultipartRequest volleyMultipartRequest = new VolleyMultipartRequest(Request.Method.POST, UPLOAD_URL+strName,
                new Response.Listener<NetworkResponse>() {
                    @Override
                    public void onResponse(NetworkResponse response) {

                        try {
                            Toast.makeText(getApplicationContext(), "Uploaded!!", Toast.LENGTH_SHORT).show();
                            String json = new String(response.data, HttpHeaderParser.parseCharset(response.headers));
                            Log.i("tagconvertstr", "["+response+"]");
                            Log.i("Respo",json);

                            JSONObject obj = new JSONObject(new String(response.data));
                            Toast.makeText(getApplicationContext(), obj.getString("errorMessage"), Toast.LENGTH_SHORT).show();
                            Log.i("JsonRespo",obj.toString());

                        } catch (JSONException e) {
                            e.printStackTrace();
                        } catch (UnsupportedEncodingException e) {
                            e.printStackTrace();
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(getApplicationContext(), error.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                }) {

            /*
             * If you want to add more parameters with the image
             * you can do it here
             * here we have only one parameter with the image
             * which is tags
             * */
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<>();
                params.put("tags", tags);
                Log.i("Tags",params.toString());
                return params;
            }



            /*
             * Here we are passing image by renaming it with a unique name
             * */
            @Override
            protected Map<String, DataPart> getByteData() {
                Map<String, DataPart> params = new HashMap<>();
                long imagename = System.currentTimeMillis();
                params.put("pic", new DataPart(imagename + ".png", getFileDataFromDrawable(bitmap)));
                Log.i("PIcdata",params.toString());
                return params;
            }
        };

        //adding the request to volley
        Volley.newRequestQueue(this).add(volleyMultipartRequest);
    }


    private class JsonTask extends AsyncTask<String, String, String> {

        protected void onPreExecute() {
            super.onPreExecute();

            pd = new ProgressDialog( My_info.this );
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

                name.setText( innerObject.getString( "Student_Name" ) );
                if (name==null){
                    name.setTextColor(Color.RED);
                    name.setText("Not mentioned");
                }

                String strPic = (  innerObject.getString( "imagepath" ) );

                admission.setText( innerObject.getString( "Registration_no" ) );
                if (admission==null){
                    admission.setTextColor(Color.RED);
                    admission.setText("Not mentioned");
                }
                admissionDate.setText( innerObject.getString( "admission" ) );
                if (admissionDate==null){
                    admissionDate.setTextColor(Color.RED);
                    admissionDate.setText("Not mentioned");
                }
                session.setText(  innerObject.getString( "Sesstion" ) );
                if (session==null){
                    session.setTextColor(Color.RED);
                    session.setText("Not mentioned");
                }
                dob.setText(  innerObject.getString( "Dob" ) );
                if (dob==null){
                    dob.setTextColor(Color.RED);
                    dob.setText("Not mentioned");
                }
                father_name.setText(  innerObject.getString( "Father_Name" ) );
                if (father_name==null){
                    father_name.setTextColor(Color.RED);
                    father_name.setText("Not mentioned");
                }
                mother_name.setText(  innerObject.getString( "mother" ) );
                if (mother_name==null){
                    mother_name.setTextColor(Color.RED);
                    mother_name.setText("Not mentioned");
                }
                mobile_no.setText(  innerObject.getString( "mobile" ) );
                if (mobile_no==null){
                    mobile_no.setTextColor(Color.RED);
                    mobile_no.setText("Not mentioned");
                }
                address.setText(  innerObject.getString( "address" ) );
                if (address==null){
                    address.setTextColor(Color.RED);
                    address.setText("Not mentioned");
                }



                sharedPresencesUtility.setRegistration( My_info.this, strPic);
                sharedPresencesUtility.setStudentName( My_info.this, name.getText().toString());



                Picasso.with( getApplicationContext() ).load( "http://holygroup.aleriseducom.com/stuimage/"+strPic ).into( std );

            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
    }

}



