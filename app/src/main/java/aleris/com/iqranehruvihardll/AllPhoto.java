package aleris.com.iqranehruvihardll;

import android.app.Dialog;
import android.content.Context;
import android.media.Image;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.ImageView;
import android.widget.TextView;

import com.jsibbold.zoomage.ZoomageView;

public class AllPhoto extends Fragment {

    RecyclerView grid;

    GridLayoutManager manager;

    GridAdapter adapter;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View vi = inflater.inflate(R.layout.allphoto , container , false);

        grid = vi.findViewById(R.id.grid);

        manager = new GridLayoutManager(getContext() , 3);

        adapter = new GridAdapter(getContext());

        grid.setAdapter(adapter);

        grid.setLayoutManager(manager);

        return vi;


    }


    public class GridAdapter extends RecyclerView.Adapter<GridAdapter.MyViewHolder>{


        Context context;;

        public GridAdapter(Context context){

            this.context = context;
        }


        @NonNull
        @Override
        public GridAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {


            View view = LayoutInflater.from(context).inflate(R.layout.grid_list_model , parent , false);
            return new MyViewHolder(view);


        }

        @Override
        public void onBindViewHolder(@NonNull GridAdapter.MyViewHolder holder, int position) {


          holder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    Dialog dialog = new Dialog(context);
                    dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
                    dialog.setContentView(R.layout.dialog);
                    dialog.setCancelable(true);
                    dialog.show();


                    ZoomageView imageView = dialog.findViewById(R.id.zoom);





                }
            });




        }

        @Override
        public int getItemCount() {
            return 15;
        }

        public class MyViewHolder extends RecyclerView.ViewHolder{



            ImageView imageView , likes;

            TextView count;



            public MyViewHolder(View itemView) {
                super(itemView);


                imageView = itemView.findViewById(R.id.image);

                likes = itemView.findViewById(R.id.likes);

                count = itemView.findViewById(R.id.count);


            }
        }
    }


}
