package rahul.com.newholypublicschool;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;

public class Academics extends Fragment {

    RelativeLayout homework, rcard, talk_princ, leave,cmll,aboutll,feell;
    View v;

    @Nullable
    @Override
    public View onCreateView(final LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        v = inflater.inflate(R.layout.academics, container, false);

        rcard = (RelativeLayout) v.findViewById(R.id.reportll);
        talk_princ = (RelativeLayout) v.findViewById(R.id.talkll);
        leave = (RelativeLayout) v.findViewById(R.id.leavell);
        feell = (RelativeLayout) v.findViewById(R.id.feell);
        cmll = (RelativeLayout) v.findViewById(R.id.cmll);
        aboutll = (RelativeLayout) v.findViewById(R.id.aboutll);

        rcard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getActivity(), Report_card.class);
                startActivity(intent);
            }
        });

        cmll.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getActivity(), Classmate.class);
                startActivity(intent);
            }
        });

        talk_princ.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getActivity(), Notice.class);
                startActivity(intent);
            }
        });

        leave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getActivity(), TimeTable.class);
                startActivity(intent);
            }
        });

        aboutll.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getActivity(), AboutSchool.class);
                startActivity(intent);
            }
        });

        feell.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getActivity(), FileDownload.class);
                startActivity(intent);
            }
        });


        return v;
    }
}



