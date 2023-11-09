package com.example.comicapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.ImageView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

import Adapter.CategoryAdapter;
import Model.Comic;
import Model.Tag;

public class ViewCategory extends AppCompatActivity implements CategoryAdapter.OnItemCategoryClick {

    RecyclerView rcvCate;
    CategoryAdapter adapter;
    List<Tag> Category;
    ImageView ivBack;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_category);

        rcvCate = findViewById(R.id.rcv_category);
        Category = new ArrayList<>();
        ivBack = findViewById(R.id.imgBack);
        ivBack.setOnClickListener(v -> finish());

        DatabaseReference reference = FirebaseDatabase.getInstance().getReference("Category");

        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if(Category.size() != 0)
                    Category.clear();
                for(DataSnapshot item : snapshot.getChildren()){
                    Tag tag = item.getValue(Tag.class);
                    Category.add(tag);
                }
                setCateAdapter(Category);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
            }
        });
    }
    private void setCateAdapter(List<Tag> cates) {
        adapter = new CategoryAdapter(this, Category, this);
        adapter.notifyDataSetChanged();
        rcvCate.setLayoutManager(new LinearLayoutManager(this,RecyclerView.VERTICAL, false));
        rcvCate.setAdapter(adapter);
    }

    @Override
    public void onCategoryItemClick(int clickedItemIndex) {
        Intent intent = new Intent(ViewCategory.this, ViewCategoryDetails.class);
        intent.putExtra("category", Category.get(clickedItemIndex).getTag());
        startActivity(intent);
    }
}