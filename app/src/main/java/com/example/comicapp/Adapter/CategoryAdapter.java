package com.example.comicapp.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.comicapp.R;

import java.util.List;

import com.example.comicapp.Model.Tag;

public class CategoryAdapter extends RecyclerView.Adapter<CategoryAdapter.ViewHolderCate> {

    Context context;
    List<Tag> Category;
    final private OnItemCategoryClick Onclick;

    public CategoryAdapter(Context context, List<Tag> category,OnItemCategoryClick Onclick) {
        this.context = context;
        this.Category = category;
        this.Onclick = Onclick;
    }

    @NonNull
    @Override
    public ViewHolderCate onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.category_item, parent, false);
        return new ViewHolderCate(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolderCate holder, int position) {
        Tag tag = Category.get(position);
        holder.txtCateName.setText(tag.getTag());
    }

    @Override
    public int getItemCount() {
        return Category.size();
    }

    public static interface OnItemCategoryClick{
        void onCategoryItemClick(int clickedItemIndex);
    }

    public class ViewHolderCate extends RecyclerView.ViewHolder implements View.OnClickListener {
        TextView txtCateName;

        public ViewHolderCate(@NonNull View itemView) {
            super(itemView);
            itemView.setOnClickListener(this);
            txtCateName = itemView.findViewById(R.id.tv_cate_name);
        }
        @Override
        public void onClick(View v) {
            int index = getAdapterPosition();
            Onclick.onCategoryItemClick(index);
        }
    }
}
