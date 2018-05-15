package rahul.com.newholypublicschool;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.JsonObject;
import com.koushikdutta.async.future.FutureCallback;
import com.koushikdutta.ion.Ion;
import com.koushikdutta.ion.Response;

public class ResetPass extends AppCompatActivity implements View.OnClickListener {

    EditText password1, password2,otp;
    TextView submit;
    String id;
    View v;
    ProgressDialog pbar;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.reset_pass);
        password1 = (EditText) findViewById(R.id.pass);
        otp=(EditText)findViewById(R.id.otp) ;
        password2 = (EditText) findViewById(R.id.login_password);
       // id=getIntent().getExtras().getString("")
        submit = (TextView) findViewById(R.id.save);
        submit.setOnClickListener(this);
        pbar = new ProgressDialog(this);
        pbar.setMessage("Please Wait ");
        pbar.setCancelable(false);
    }

    @Override
    public void onClick(View v) {

        if (v.getId() == R.id.save) {

            if (password1.getText().toString().isEmpty())
                Toast.makeText(this, "please enter new password", Toast.LENGTH_LONG).show();
            else if (password1.getText().toString().length() < 5 )
                Toast.makeText(this, "Enter atleast 6 characters", Toast.LENGTH_LONG).show();
            else if (password2.getText().toString().isEmpty())
                Toast.makeText(this,"Please confirm your password", Toast.LENGTH_LONG).show();
            else if ( password2.getText().toString().length() < 5)
                Toast.makeText(this, "Enter atleast 6 characters", Toast.LENGTH_LONG).show();
            else if (password1.getText().toString().equals(password2.getText().toString())) {

                if (Utility.isConnected(this)) {
                    JsonObject object = new JsonObject();
//                    pbar.show();
                    String url="http://api.supremeindia.co/Home/CHANGEPASSOTP?loginid="+getIntent().getExtras().getString("loginid")
                            +"&pass="+password1.getText().toString()+"&OTP="+otp.getText().toString();
                    //JSONObject js=new JSONObject();
                    final ProgressDialog p=new ProgressDialog(this);
                    p.show();
                   p.setMessage("Resetting Password");
                    Ion.with(this)
                            .load(url)
                            .asString().withResponse()
                            .setCallback(new FutureCallback<Response<String>>() {
                                @Override
                                public void onCompleted(Exception e, Response<String> result) {
//pbar.dismiss();
                                    String res="";
                                    if(result!=null)
                                        res=result.getResult();
                                    Log.e("Response ResetPass",""+res);
                                   p.dismiss();
                                    if (res.isEmpty()||res.equalsIgnoreCase("5"))
                                    {
                                        Toast.makeText(getApplicationContext(),"Please enter correct Otp", Toast.LENGTH_LONG).show();
                                    }
                                    else if (res.isEmpty()||res.equalsIgnoreCase("6"))
                                    {
                                        Toast.makeText(getApplicationContext(),"Wrong Otp", Toast.LENGTH_LONG).show();
                                    }
                                    else if(res.equalsIgnoreCase("1")){
                                        Intent i=new Intent(ResetPass.this,Login.class);
                                        startActivity(i);
                                        finish();
                                    }
                                    else
                                        Toast.makeText(getApplicationContext(),"Some error Occured", Toast.LENGTH_LONG).show();

                                }
                            });

                } else {
                    Toast.makeText(this, Constants.ERR_NO_INTERNET, Toast.LENGTH_LONG).show();
                }
            }
            else
                Toast.makeText(this, Constants.pass_unmatch, Toast.LENGTH_LONG).show();
        }
    }
}
