package Adapter;

import android.content.Context;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;


import com.example.comicapp.R;


import java.util.List;

import Model.Chapter;

public class ChapterAdapter extends RecyclerView.Adapter<ChapterAdapter.ViewHolderChapter> {
    Context context;
    List<Chapter> chapterList;


    public ChapterAdapter(Context context, List<Chapter> chapterList) {
        this.context = context;
        this.chapterList = chapterList;
    }

    @NonNull
    @Override
    public ViewHolderChapter onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        Context context = parent.getContext();
        LayoutInflater inflater = LayoutInflater.from(context);

        View itemView = inflater.inflate(R.layout.chapter_item, parent, false);

        ViewHolderChapter viewHolderChapter = new ViewHolderChapter(itemView);
        return viewHolderChapter;

    }

    @Override
    public void onBindViewHolder(@NonNull ChapterAdapter.ViewHolderChapter holder, int position) {
        if (chapterList != null && position < chapterList.size()) {
        holder.txtChapter.setText(chapterList.get(position).Name);
        }
    }
    @Override
    public int getItemCount() {
        if (chapterList != null) {
            return chapterList.size();
        } else {
            return 0;
        }
//        return chapterList.size();
    }

//    public void notifyDataChanged() {
//        notifyDataSetChanged();
//    }

    // Khai báo và ánh xạ
    public class ViewHolderChapter extends RecyclerView.ViewHolder implements View.OnClickListener{
        TextView txtChapter;


        public ViewHolderChapter(@NonNull View itemView) {
            super(itemView);
            txtChapter = itemView.findViewById(R.id.txtItemChapter);

        }

        @Override
        public void onClick(View v) {


        }
    }
}
