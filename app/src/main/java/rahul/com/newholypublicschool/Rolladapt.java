package rahul.com.newholypublicschool;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.ArrayList;

public class Rolladapt extends BaseAdapter {
Context ctx;
    ArrayList<Rolld> list;

    public Rolladapt(ArrayList<Rolld> a, Context ctx) {
        this.list=a;
        this.ctx=ctx;
    }



    @Override
    public int getCount() {
        return list.size();
    }

    @Override
    public Object getItem(int i) {
        return null;
    }

    @Override
    public long getItemId(int i) {
        return 0;
    }

    @Override
    public View getView(int position, View view, ViewGroup viewGroup) {

        final ViewHolder holder;

        if(view==null) {
            holder=new ViewHolder();
            LayoutInflater inflater = (LayoutInflater)ctx.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            view = inflater.inflate(R.layout.rolldis, viewGroup,false);
            holder.sid=(TextView) view.findViewById(R.id.stid);
            holder.admno=(TextView)view.findViewById(R.id.admno);
            holder.studname = (TextView) view.findViewById(R.id.stuname);
            holder.roll=(TextView) view.findViewById(R.id.roll);
            view.setTag(holder);
        }else{
            holder=(ViewHolder)view.getTag();
        }
        holder.sid.setText( list.get(position).getSTU_ID());
        holder.admno.setText(list.get(position).getADMNO());
        holder.studname.setText( list.get(position).getStudentName());
        holder.roll.setText(list.get(position).getROLL());


        return view;
    }

    class  ViewHolder{
        TextView sid,admno,studname,roll;

    }
}
