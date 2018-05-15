package rahul.com.newholypublicschool;

import android.app.Activity;
import android.app.Dialog;
import android.app.NotificationManager;
import android.app.ProgressDialog;
import android.os.AsyncTask;
import android.os.Environment;
import android.support.v4.app.NotificationCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLConnection;

public class example extends AppCompatActivity {

    ImageView download;
    TextView text;
    private ProgressDialog pDialog;
    // Progress dialog type (0 - for Horizontal progress bar)
    public static final int progress_bar_type = 0;


    //    String URL="http://www.iiswc.org/iiswc2009/sample.doc";
    String homework = "";
    String strText = "";


    String storeDir;
    Activity context;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_downloader);

        context = this;
        storeDir = Environment.getExternalStorageDirectory().toString();

        download = findViewById(R.id.download);

        text = findViewById(R.id.text);

        //bar = findViewById(R.id.progress);

        if (getIntent() != null) {

            homework = getIntent().getExtras().getString("PDFLINK");

            strText = getIntent().getExtras().getString("TEXT");
        }

        text.setText(strText);

        download.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                new DownloadTask(Downloader.this,"http://holygroup.aleriseducom.com/AssignmentImages/"+homework);

                new example.DownloadFileFromURL().execute("http://holygroup.aleriseducom.com/AssignmentImages/" + homework);


            }
        });
    }


    @Override
    protected Dialog onCreateDialog(int id) {
        switch (id) {
            case progress_bar_type: // we set this to 0
                pDialog = new ProgressDialog(this);
                pDialog.setMessage("Downloading file. Please wait...");
                pDialog.setIndeterminate(false);
                pDialog.setMax(100);
                pDialog.setProgressStyle(ProgressDialog.STYLE_HORIZONTAL);
                pDialog.setCancelable(true);
                pDialog.show();
                return pDialog;
            default:
                return null;
        }
    }


    class DownloadFileFromURL extends AsyncTask<String, String, String> {


        NotificationManager mNotifyManager;
        NotificationCompat.Builder mBuilder;

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            showDialog(progress_bar_type);


            mNotifyManager = (NotificationManager) context.getSystemService(context.NOTIFICATION_SERVICE);
            mBuilder = new NotificationCompat.Builder(context);
            mBuilder.setContentTitle("File Download")
                    .setContentText("Download in progress")
                    .setSmallIcon(R.drawable.down);
            Toast.makeText(example.this, "Downloading the file... The download progress is on notification bar.", Toast.LENGTH_LONG).show();

        }

        @Override
        protected String doInBackground(String... f_url) {


            int count;
            try {


                //URL url = new URL(f_url[0]);

                URL    url = new URL(f_url[0]);

                String pathl = "";


                try {

                    File f = new File(storeDir);
                    if (f.exists()) {

                        HttpURLConnection con=(HttpURLConnection)url.openConnection();

                        InputStream is=con.getInputStream();
                        OutputStream output = null;

                        String pathr=url.getPath();

                        File folder = new File(Environment.getExternalStorageDirectory() +
                                File.separator + "SchoolApp");  //folder create
                        boolean success = true;
                        if (!folder.exists()) {
                            success = folder.mkdirs();
                        }
                        if (success) {
                            // Output stream
                            output = new FileOutputStream(Environment
                                    .getExternalStorageDirectory().toString()
                                    + "/SchoolApp/"+homework);        // folder
                        } else {
                            Toast.makeText(example.this,"not create folder",Toast.LENGTH_LONG).show();
                        }


                        FileOutputStream fos=new FileOutputStream(pathl);

                        int lenghtOfFile = con.getContentLength();

                        byte data[] = new byte[1024];

                        long total = 0;


                        while ((count = is.read(data)) != -1) {
                            total += count;
                            publishProgress("" + (int) ((total * 100) / lenghtOfFile));

                            output.write(data, 0, count);
                        }
                        is.close();
                        fos.flush();
                        fos.close();
                    } else {
                        Log.e("Error", "Not found: " + storeDir);

                    }


                } catch (Exception e) {

                    e.printStackTrace();
                }


            } catch (Exception e) {
                Log.e("Error: ", e.getMessage());
            }




               /* URL url = new URL(f_url[0]);

                String pathl="";

                URLConnection conection = url.openConnection();
                conection.connect();

                int lenghtOfFile = conection.getContentLength();

                InputStream input = new BufferedInputStream(url.openStream(),
                        8192);
                OutputStream output = null;
                File folder = new File(Environment.getExternalStorageDirectory() +
                        File.separator + "SchoolApp");  //folder create
                boolean success = true;
                if (!folder.exists()) {
                    success = folder.mkdirs();
                }
                if (success) {
                    // Output stream
                    output = new FileOutputStream(Environment
                            .getExternalStorageDirectory().toString()
                            + "/SchoolApp/"+homework);        // folder
                } else {
                    Toast.makeText(example.this,"not create folder",Toast.LENGTH_LONG).show();
                }


                byte data[] = new byte[1024];

                long total = 0;

                while ((count = input.read(data)) != -1) {
                    total += count;
                    publishProgress("" + (int) ((total * 100) / lenghtOfFile));

                    output.write(data, 0, count);
                }
                output.flush();
                output.close();
                input.close();

            } catch (Exception e) {
                Log.e("Error: ", e.getMessage());
            }
*/
            return null;
        }


        protected void onProgressUpdate(Integer... progress) {
            // pDialog.setProgress(Integer.parseInt(progress[0]));


            mBuilder.setProgress(100, progress[0], false);
            // Displays the progress bar on notification
            mNotifyManager.notify(0, mBuilder.build());
        }


        @Override
        protected void onPostExecute(String file_url) {

            dismissDialog(progress_bar_type);


            mBuilder.setContentText("Download complete");
            // Removes the progress bar
            mBuilder.setProgress(0, 0, false);
            mNotifyManager.notify(0, mBuilder.build());

        }

    }
}
