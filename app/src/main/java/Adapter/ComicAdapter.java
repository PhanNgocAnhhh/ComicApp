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
import com.example.comicapp.ViewChapter;
import com.example.comicapp.ViewComicDetails;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

import Model.Comic;

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

    }
    @Override
    public int getItemCount() {
        return comicList.size();
    }

    public static interface OnItemComicClick{
        void onItemComicClick(int clickedItemIndex);
    }
    public class MyViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
         TextView comic_name;
         ImageView comic_image;


        public MyViewHolder (@NonNull View itemView) {
             super(itemView);
             comic_name = itemView.findViewById(R.id.txt_comic_name);
             comic_image =itemView.findViewById(R.id.img_comic);
             itemView.setOnClickListener(this);
         }

        @Override
        public void onClick(View v) {
            int clickedPosition = getAdapterPosition();
            OnClick.onItemComicClick(clickedPosition);

        }
    }

}
