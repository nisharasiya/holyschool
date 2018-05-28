package aleris.com.iqranehruvihardll;

import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.NetworkResponse;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.HttpHeaderParser;
import com.android.volley.toolbox.Volley;

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

public class TeacherUpload extends AppCompatActivity {

    private ProgressDialog pDialog;
    private JSONObject json;
    private int success=0;
    String imageString;
    private static int RESULT_LOAD_IMAGE = 1;
    ImageView imageView;
    Button buttonLoadImage,upload;
    Spinner menu_id,class_id;
    ProgressDialog pd;
    ArrayList<String> worldlist;
    ArrayList<String> worldlist2;
    String file_name;
    ProgressDialog progressDialog;
    String UPLOAD_URL;
    Bitmap bitmap;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_teacher_upload);
        imageView = (ImageView) findViewById(R.id.imageView);
        menu_id = findViewById(R.id.menu_id);
        class_id = findViewById(R.id.class_id);
        progressDialog = new ProgressDialog(TeacherUpload.this);
        buttonLoadImage = (Button) findViewById(R.id.buttonLoadPicture);
        upload = (Button) findViewById(R.id.upload);
        new MenuFetch().execute("http://iqra.aleriseducom.com/API/menudetail.aspx?id=1");
        new ClassFetch().execute("http://iqra.aleriseducom.com/API/menudetail.aspx?id=2");

        buttonLoadImage.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View arg0) {
                Intent i = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                startActivityForResult(i, 100);
            }
        });

        upload.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                uploadBitmap(bitmap);
            }
        });
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 100 && resultCode == RESULT_OK && data != null) {

            Uri imageUri = data.getData();
            try {

                bitmap = MediaStore.Images.Media.getBitmap(this.getContentResolver(), imageUri);

                imageView.setImageBitmap(bitmap);
                Log.i("Upload",imageView.toString());

                //calling the method uploadBitmap to upload image

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

        String strMenu = menu_id.getSelectedItem().toString().trim();
        String strClass = class_id.getSelectedItem().toString().trim();

        strMenu =strMenu.replaceAll(" ","%20");
        strClass =strClass.replaceAll(" ","%20");
        Toast.makeText(getApplicationContext(), "Uploading...Please wait!!", Toast.LENGTH_SHORT).show();
        UPLOAD_URL = "http://iqra.aleriseducom.com/API/document.aspx?Mid="+strMenu+"&Cid="+strClass+"&filename=";
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

    private class MenuFetch extends AsyncTask<String, String, String> {

        protected void onPreExecute() {
            super.onPreExecute();

            pd = new ProgressDialog( TeacherUpload.this );
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
                worldlist = new ArrayList<String>();
                for (int i = 0; i < array1.length(); i++) {

                    JSONObject obj1 = array1.getJSONObject(i);
                    String classmateName = obj1.getString("ID");
                    String parentsMobile = obj1.getString("MENU");
                    worldlist.add(obj1.optString("MENU"));

                    menu_id.setAdapter(new ArrayAdapter<String>(TeacherUpload.this,
                            android.R.layout.simple_spinner_dropdown_item,
                            worldlist));

                    String strName =  menu_id.getSelectedItem().toString();
                }

            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
    }

    private class ClassFetch extends AsyncTask<String, String, String> {

        protected void onPreExecute() {
            super.onPreExecute();

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

            try {
                JSONArray array1=new JSONArray(result.toString());
                worldlist2 = new ArrayList<String>();
                for (int i = 0; i < array1.length(); i++) {

                    JSONObject obj1 = array1.getJSONObject(i);
                    String classmateName = obj1.getString("SEC_ID");
                    String parentsMobile = obj1.getString("class");
                    worldlist2.add(obj1.optString("class"));

                    class_id.setAdapter(new ArrayAdapter<String>(TeacherUpload.this,
                            android.R.layout.simple_spinner_dropdown_item,
                            worldlist2));
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
    }


}
