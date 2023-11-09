package com.example.comicapp.Adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.comicapp.R;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

import com.example.comicapp.Model.Comic;

public class ComicAdapter extends RecyclerView.Adapter<ComicAdapter.MyViewHolder> implements Filterable {

    Context context;
    List<Comic> comicList;
    List<Comic> comicListOld;

    final private OnItemComicClick OnClick;

    public ComicAdapter(Context context, List<Comic> comicList,OnItemComicClick OnClick) {
        this.context = context;
        this.comicList = comicList;
        this.comicListOld = comicList;
        this.OnClick = OnClick;

    }
    // Tìm kiếm tên truyện
    @Override
    public Filter getFilter() {

        return new Filter() {
            @Override
            protected FilterResults performFiltering(CharSequence constraint) {
                String strSearch = constraint.toString();
                if(strSearch.isEmpty()){
                    comicList = comicListOld;
                }else{
                    List<Comic> list = new ArrayList<>();
                    for(Comic comic :comicListOld)

                        // Thêm danh sách truyênh tìm kiếm vào ComicListOld khônng phân biệt kí tự hoa
                        if(comic.getName().toLowerCase().contains(strSearch.toLowerCase()))
                            list.add(comic);

                    comicList = list;
                }
                FilterResults filterResults = new FilterResults();
                filterResults.values = comicList;
                return filterResults;
            }

            @Override
            protected void publishResults(CharSequence constraint, FilterResults results) {
               comicList = (List<Comic>) results.values;
                notifyDataSetChanged();
            }
        };
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

         Comic comic = comicList.get(position);
        // Tải ảnh và hiển thị ảnh lên view
        Picasso.get().load(comicList.get(position).image).into(holder.comic_image);
        // Hiển thị tên lên view
        holder.comic_name.setText(comicList.get(position).name);
       holder.itemView.setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View v) {
               OnClick.onItemComicClick(comic, holder.getAdapterPosition());
           }
       });
    }
    @Override
    public int getItemCount() {
        return comicList.size();
    }

    public static interface OnItemComicClick{
        void onItemComicClick(Comic comic,int index);
    }
    public class MyViewHolder extends RecyclerView.ViewHolder {
         TextView comic_name;
         ImageView comic_image;



        public MyViewHolder (@NonNull View itemView) {
             super(itemView);
             comic_name = itemView.findViewById(R.id.txt_comic_name);
             comic_image =itemView.findViewById(R.id.img_comic);





         }


    }

}
