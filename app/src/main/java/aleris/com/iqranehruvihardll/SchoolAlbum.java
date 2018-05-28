package aleris.com.iqranehruvihardll;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

public class SchoolAlbum extends Fragment {

    RecyclerView grid;

    GridLayoutManager manager;

    SchoolAdapter adapter;


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.allphoto, container, false);


        grid = view.findViewById(R.id.grid);

        manager = new GridLayoutManager(getContext(), 3);

        adapter = new SchoolAdapter(getContext());

        grid.setAdapter(adapter);

        grid.setLayoutManager(manager);

        return view;
    }


    public class SchoolAdapter extends RecyclerView.Adapter<SchoolAdapter.MyViewHolder> {


        Context context;


        public SchoolAdapter(Context context) {

            this.context = context;
        }


        @NonNull
        @Override
        public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {


            View view = LayoutInflater.from(context).inflate(R.layout.grid_list_model, parent, false);
            return new MyViewHolder(view);


        }

        @Override
        public void onBindViewHolder(MyViewHolder holder, int position) {


        }

        @Override
        public int getItemCount() {
            return 15;
        }

        public class MyViewHolder extends RecyclerView.ViewHolder {


            public MyViewHolder(View itemView) {
                super(itemView);


            }
        }
    }


}
