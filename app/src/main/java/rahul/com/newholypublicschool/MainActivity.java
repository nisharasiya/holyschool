package rahul.com.newholypublicschool;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import java.util.ArrayList;

public class MainActivity extends Fragment implements View.OnClickListener {

    RelativeLayout homework,att,fees,transport;
    TextView name,fname,sid;
    View v;
//    ImageView i;
    Context context;

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        v=inflater.inflate(R.layout.activity_main,container,false);
        homework=v.findViewById(R.id.homeworkll);
        att=v.findViewById(R.id.attll);
        fees=v.findViewById(R.id.feell);
        transport=v.findViewById(R.id.transportll);
//        name=(TextView)v.findViewById(R.id.sname);
//        sid=(TextView)v.findViewById(R.id.sid);
//        fname=(TextView)v.findViewById(R.id.sfname);
//        i=(ImageView)v.findViewById(R.id.mnu) ;
        ArrayList<String> a=null;// = ((DashboardManager)getActivity()).getval();
        String strId = ""+AppPrefs.getInstance(getActivity()).getData("sid","NA");
        String strName = ""+AppPrefs.getInstance(getActivity()).getData("sname","NA");
        String strFather = ""+AppPrefs.getInstance(getActivity()).getData("fname","NA");

//        sid.setText("Admission no : "+strId);
//        name.setText("Name : "+strName);
//        fname.setText("Father's Name : "+strFather);
        homework.setOnClickListener(this);
        att.setOnClickListener(this);
//        i.setOnClickListener(this);
        fees.setOnClickListener(this);
        transport.setOnClickListener(this);
        return v;
    }

   /* @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        name.setText(""+getIntent().getExtras().getString("name"));
        fname.setText(""+getIntent().getExtras().getString("fname"));
        homework.setOnClickListener(this);
    }*/

    @Override
    public void onClick(View v) {
//        if (v.getId()==R.id.mnu) {
//            PopupMenu popup = new PopupMenu(getActivity(), i);
//            //Inflating the Popup using xml file
//            popup.getMenuInflater().inflate(R.menu.popup_menu, popup.getMenu());
//
//            //registering popup with OnMenuItemClickListener
//            popup.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
//                public boolean onMenuItemClick(MenuItem item) {
//
//                    AppPrefs.getInstance(getActivity()).clearAll();
//                    Intent id=new Intent(getActivity(),Login.class);
//                    startActivity(id);
//                    getActivity().finish();
//
//                    // Toast.makeText(getContext(),"You Clicked : " + item.getTitle(),Toast.LENGTH_SHORT).show();
//
//                    return true;
//                }
//            });
//
//            popup.show();
//        }
        if (v.getId()==R.id.attll)
        {
            Intent intent = new Intent(getActivity(), Attendance.class);
            startActivity(intent);
        }
        else if(v.getId()==R.id.homeworkll){
            Intent intent = new Intent(getActivity(), Homework.class);
            startActivity(intent);
        }
        else if(v.getId()==R.id.transportll){
            Intent intent = new Intent(getActivity(), Bus.class);
            startActivity(intent);
        }
        else if(v.getId()==R.id.feell){
            Intent intent = new Intent(getActivity(), FeesStruct.class);
            startActivity(intent);
        }

    }
}
