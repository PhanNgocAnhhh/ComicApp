package Adapter;

import android.content.Context;

import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.comicapp.R;
import com.example.comicapp.ViewChapter;


import java.util.List;


import Common.Common;
import Interface.IRecyclerItemClickListener;
import Model.Chapter;


public class ChapterAdapter extends RecyclerView.Adapter<ChapterAdapter.ViewHolderChapter> {

    Context context;
    List<Chapter> listChapter;
    final private OnItemChapterClick OnClick;

    public ChapterAdapter(Context context, List<Chapter> listChapter,OnItemChapterClick OnClick) {
        this.context = context;
        this.listChapter = listChapter;
        this.OnClick = OnClick;
    }

    @NonNull
    @Override
    public ChapterAdapter.ViewHolderChapter onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        Context context = parent.getContext();
        LayoutInflater inflater = LayoutInflater.from(context);
        View itemView = inflater.inflate(R.layout.chapter_item, parent, false);
        return new ViewHolderChapter (itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull ChapterAdapter.ViewHolderChapter holder, int position) {
        Chapter chapter = listChapter.get(position);
        holder.txtChapter.setText(listChapter.get(position).name);
    }

    @Override
    public int getItemCount() {
        if (listChapter != null) {
            return listChapter.size();
        } else {
            return 0;
        }
    }

    public static interface OnItemChapterClick{
        void onChapterItemClick(int clickedItemIndex);
    }
    public class ViewHolderChapter extends RecyclerView.ViewHolder implements View.OnClickListener {
        TextView txtChapter;
        public ViewHolderChapter(@NonNull View itemView) {

            super(itemView);
            txtChapter = itemView.findViewById(R.id.txtItemChapter);
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            int clickedPosition = getAdapterPosition();
            OnClick.onChapterItemClick(clickedPosition);
        }
    }
}
