package rahul.com.newholypublicschool;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import com.koushikdutta.async.future.FutureCallback;
import com.koushikdutta.ion.Ion;
import com.koushikdutta.ion.Response;

import org.json.JSONArray;
import org.json.JSONException;

import java.util.ArrayList;

public class Roll extends Fragment {
        View v;

    ListView roll;
    Rolladapt adapter;
        ProgressDialog progressDialog;
@Override
public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        v=inflater.inflate(R.layout.roll,container,false);

       roll=(ListView)v.findViewById(R.id.listroll);
        progressDialog=new ProgressDialog(getContext());
        progressDialog.setMessage("Loading...");

progressDialog.show();
    String url="http://api.supremeindia.co/Home/StudentRollNO?admID='1519'";
    Ion.with(Roll.this)

            .load(url).setTimeout(15*1000).asString().withResponse().setCallback(new FutureCallback<Response<String>>() {
        @Override
        public void onCompleted(Exception e, Response<String> result) {
            progressDialog.dismiss();
            String response="";
            if (result!=null)
                response=result.getResult();

            Log.e("Roll",""+response);
            ArrayList<Rolld> a=new ArrayList<Rolld>();
            try {
                JSONArray js=new JSONArray(response);

                for (int i=0;i<js.length();i++)
                {
                    a.add(new Rolld(js.getJSONObject(i).optString("STU_ID"),
                            js.getJSONObject(i).optString("ADMNO"),
                            js.getJSONObject(i).optString("StudentName"),
                            js.getJSONObject(i).optString("ROLL")));
                }

                adapter=new Rolladapt(a,getActivity());
                roll.setAdapter(adapter);



            } catch (JSONException e1) {
                e1.printStackTrace();
            }

        }
    });






        return v;
        }

}
