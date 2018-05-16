package rahul.com.newholypublicschool;

import android.Manifest;
import android.app.Activity;
import android.app.Dialog;
import android.app.DownloadManager;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.ProgressDialog;
import android.content.Intent;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Environment;
import android.support.v4.app.NotificationCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;

public class Downloader extends AppCompatActivity {

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
   // ProgressBar bar;


    private NotificationManager manager;
    private Intent notiIntent;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_downloader);


        context=this;
        storeDir=Environment.getExternalStorageDirectory() +
                File.separator + "SchoolApp";

        download = findViewById(R.id.download);

        text = findViewById(R.id.text);

        //bar = findViewById(R.id.progress);

        if(getIntent()!=null){

            homework=getIntent().getExtras().getString("PDFLINK");

            strText=getIntent().getExtras().getString("TEXT");
        }

        text.setText(strText);

        download.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                new DownloadTask(Downloader.this,"http://holygroup.aleriseducom.com/AssignmentImages/"+homework);

                    new DownloadFileFromURL().execute("http://holygroup.aleriseducom.com/AssignmentImages/"+homework);


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

        File dir = new File(Environment.getExternalStorageDirectory().getPath() + "/schoolApp");

        File f;

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            showDialog(progress_bar_type);


            mNotifyManager =(NotificationManager) context.getSystemService(context.NOTIFICATION_SERVICE);


            mBuilder = new NotificationCompat.Builder(context);
            mBuilder.setContentTitle("File Download")
                    .setContentText("Download in progress")
                    .setSmallIcon(R.drawable.down);

            Uri selectedUri = Uri.parse(Environment.getExternalStorageDirectory() + "/schoolApp");
            Intent intent = new Intent(Intent.ACTION_VIEW);


           // Intent intent = new Intent(Environment.getExternalStorageDirectory().AbsolutePath);


            intent.setDataAndType(selectedUri, "resource/folder");

            PendingIntent pendingIntent = PendingIntent.getActivity(Downloader.this, 0, intent, 0);
           // PendingIntent contentIntent = PendingIntent.getActivity(getApplicationContext(), 0,
                 //   intent, PendingIntent.FLAG_UPDATE_CURRENT);



            mBuilder.setContentIntent(pendingIntent);



            Toast.makeText(Downloader.this, "Downloading the file... The download progress is on notification bar.", Toast.LENGTH_LONG).show();


        }

        @Override
        protected String doInBackground(String... f_url) {


            int count;

            if (!(dir.exists() && dir.isDirectory())) {
                dir.mkdirs();
            }


            URL url = null;
            try {
                url = new URL(f_url[0]);
            } catch (MalformedURLException e) {
                e.printStackTrace();
            }

            String pathl = "";





            //if (f.exists()) {

            HttpURLConnection con = null;
            try {
                con = (HttpURLConnection)url.openConnection();
            } catch (IOException e) {
                e.printStackTrace();
            }

            InputStream is= null;
            try {
                is = con.getInputStream();
            } catch (IOException e) {
                e.printStackTrace();
            }
            String filename = null;
            try {

                String pathr=url.getPath();

                filename = pathr.substring(pathr.lastIndexOf('/')+1);

                f = new File(dir , filename);

                f.createNewFile();
            } catch (IOException e) {
                e.printStackTrace();
            }

            OutputStream output = null;


            String TAG = "asdasd";
            try {
                output = new FileOutputStream(f);

                byte[] buffer = new byte[1024]; // or other buffer size
                int read;

                Log.d(TAG, "Attempting to write to: " + dir + "/" + filename);
                long total = 0;

                while ((read = is.read(buffer)) != -1) {

                    total += read;
                    publishProgress("" + (int) ((total * 100) / con.getContentLength()));

                    output.write(buffer, 0, read);
                    Log.v(TAG, "Writing to buffer to output stream.");
                }
                Log.d(TAG, "Flushing output stream.");
                output.flush();
                Log.d(TAG, "Output flushed.");
            } catch (IOException e) {
                Log.e(TAG, "IO Exception: " + e.getMessage());
                e.printStackTrace();
                return null;
            } finally {
                try {
                    if (output != null) {
                        output.close();
                        Log.d("Asdasdasd", "Output stream closed sucessfully.");
                    }
                    else{
                        Log.d(TAG, "Output stream is null");
                    }
                } catch (IOException e){
                    Log.e("Asdasdasd", "Couldn't close output stream: " + e.getMessage());
                    e.printStackTrace();
                    return null;
                }

            }
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
            mBuilder.setProgress(0,0,false);
            mNotifyManager.notify(0, mBuilder.build());

            Log.d("download _path" , f.getAbsolutePath());

        }

    }
}
