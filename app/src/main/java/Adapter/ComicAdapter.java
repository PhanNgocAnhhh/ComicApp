package Adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.example.comicapp.R;
import com.example.comicapp.ViewComicDetails;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

import Common.Common;
import Interface.IRecyclerItemClickListener;
import Model.Comic;

public class ComicAdapter extends RecyclerView.Adapter<ComicAdapter.MyViewHolder> implements Filterable {

    Context context;
    List<Comic> comicList;


    public ComicAdapter(Context context, List<Comic> comicList) {
        this.context = context;
        this.comicList = comicList;

    }

    @Override
    public Filter getFilter() {
        return null;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        Context context = parent.getContext();
        LayoutInflater inflater = LayoutInflater.from(context);

        View itemView = inflater.inflate(R.layout.comic_item, parent, false);

        MyViewHolder viewHolder = new MyViewHolder(itemView);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, @SuppressLint("RecyclerView") int position) {

         final Comic comic_position = comicList.get(position);
        // Tải ảnh và hiển thị ảnh lên view
        Picasso.get().load(comicList.get(position).image).into(holder.comic_image);
        // Hiển thị tên lên view
        holder.comic_name.setText(comicList.get(position).name);

        // Xử lý click vào view
        holder.setRecyclerItemClickListener(new IRecyclerItemClickListener() {
            @Override
            public void onClick(View view, int position) {

                //  Tạo dữ liệu cho comicSelected, dữ liệu lấy từ comicList
                Common.comicSelected = comicList.get(position);
                Intent intent = new Intent(context,ViewComicDetails.class);
                intent.putExtra("name",comic_position.getName());
                intent.putExtra("image_url",comic_position.getImage());
                intent.putExtra("description",comic_position.getDescription());
                view.getContext().startActivity(intent);
            }
        });
//        holder.rlv_layout.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//
//
//
//            }
//        });
    }
    @Override
    public int getItemCount() {
        return comicList.size();
    }


    // Khai báo và ánh xạ
    public class MyViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
         TextView comic_name;
         ImageView comic_image;
         IRecyclerItemClickListener  recyclerItemClickListener;

        public void setRecyclerItemClickListener(IRecyclerItemClickListener recyclerItemClickListener) {
            this.recyclerItemClickListener = recyclerItemClickListener;
        }

        public MyViewHolder (@NonNull View itemView) {
             super(itemView);

             comic_name = itemView.findViewById(R.id.txt_comic_name);
             comic_image =itemView.findViewById(R.id.img_comic);


             itemView.setOnClickListener(this);
         }
        @Override
        public void onClick(View v) {
            recyclerItemClickListener.onClick(v,getAdapterPosition());

        }
    }




}
