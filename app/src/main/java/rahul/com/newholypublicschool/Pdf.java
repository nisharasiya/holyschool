package rahul.com.newholypublicschool;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageView;

import com.jsibbold.zoomage.ZoomageView;
import com.squareup.picasso.Picasso;

import rahul.com.newholypublicschool.Utils.SharedPresencesUtility;

public class Pdf extends AppCompatActivity {

    private ZoomageView pdfView;
    SharedPresencesUtility sharedPresencesUtility;
    ProgressDialog prDialog;
    ImageView back;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate( savedInstanceState );
        setContentView( R.layout.activity_pdf );
        sharedPresencesUtility=new SharedPresencesUtility( Pdf.this );
        back = findViewById(R.id.back);
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
        String homework = "";
        if(getIntent()!=null){
            homework=getIntent().getExtras().getString("PDFLINK");
        }
        pdfView = (ZoomageView) findViewById(R.id.webView2);
        prDialog = new ProgressDialog(Pdf.this);
        prDialog.setMessage("Loading..");
        prDialog.show();

        Picasso.with(Pdf.this).load("http://holygroup.aleriseducom.com/AssignmentImages/"+homework).into(pdfView);
        if (prDialog.isShowing()){
            prDialog.dismiss();
        }


    }
}