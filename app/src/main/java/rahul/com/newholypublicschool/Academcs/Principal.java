package rahul.com.newholypublicschool.Academcs;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.koushikdutta.async.future.FutureCallback;
import com.koushikdutta.ion.Ion;
import com.koushikdutta.ion.Response;

import rahul.com.newholypublicschool.AppPrefs;
import rahul.com.newholypublicschool.DashboardManager;
import rahul.com.newholypublicschool.R;
import rahul.com.newholypublicschool.Teacher;

public class Principal extends Fragment implements View.OnClickListener {
    View v;
    EditText abt,sub,msg;
    TextView submit;
    ProgressDialog progressDialog;
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        v=inflater.inflate(R.layout.principal,container,false);
progressDialog=new ProgressDialog(getContext());
        progressDialog.setMessage("Sending Message");
        submit=v.findViewById(R.id.submit);
        abt=v.findViewById(R.id.about);
        sub=v.findViewById(R.id.subject);
        msg=v.findViewById(R.id.msg);
        submit.setOnClickListener(this);

        return v;
    }


    @Override
    public void onClick(View view) {

        if (view.getId()==R.id.submit)
        {
            String url="http://api.supremeindia.co/Home/Createtalktop?STU_ID='"+ AppPrefs.getInstance(getContext()).getData("sid","")+"'&about='"+abt.getText().toString()+"'&subject='"+sub.getText().toString()+"'&message='"+msg.getText().toString()+"'";
progressDialog.show();
            Ion.with(Principal.this)

                    .load(url).setTimeout(15*1000).asString().withResponse().setCallback(new FutureCallback<Response<String>>() {
                @Override
                public void onCompleted(Exception e, Response<String> result) {
                    progressDialog.dismiss();
                    String response="";
                    if (result!=null)
                        response=result.getResult();
                    progressDialog.dismiss();
                    Log.e("Principal",""+response);

                    if(response.equalsIgnoreCase("1"))
                    {
                        Toast.makeText(getContext(),"Message Submitted Successfully", Toast.LENGTH_LONG).show();
                        if (AppPrefs.getInstance(getContext()).getData("UserType","na").equalsIgnoreCase("1")) {
                            Intent id = new Intent(getContext(), DashboardManager.class);
                            startActivity(id);
                        }
                        else
                        {
                            Intent id = new Intent(getContext(), Teacher.class);
                            startActivity(id);
                        }
                        }

                        else
                    {
                        Toast.makeText(getContext(),"Unable to submit", Toast.LENGTH_LONG).show();

                    }

                }
            });

        }
        }

    }

