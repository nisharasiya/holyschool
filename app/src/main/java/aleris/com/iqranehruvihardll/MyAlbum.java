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

public class MyAlbum extends Fragment {


    RecyclerView grid;

    GridLayoutManager manager;

    AlbumAdapter adapter;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.allphoto, container, false);

        grid = view.findViewById(R.id.grid);

        manager = new GridLayoutManager(getContext(), 3);

        adapter = new AlbumAdapter(getContext());

        grid.setAdapter(adapter);

        grid.setLayoutManager(manager);


        return view;
    }


    public class AlbumAdapter extends RecyclerView.Adapter<AlbumAdapter.MyViewHolder> {

        Context context;


        public AlbumAdapter(Context context) {

            this.context = context;
        }


        @NonNull
        @Override
        public AlbumAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {


            View view = LayoutInflater.from(context).inflate(R.layout.grid_list_model, parent, false);

            return new MyViewHolder(view);

        }

        @Override
        public void onBindViewHolder(@NonNull AlbumAdapter.MyViewHolder holder, int position) {


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
