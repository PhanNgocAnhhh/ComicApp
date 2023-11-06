package com.example.comicapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import Adapter.ComicAdapter;
import Model.Comic;
import Model.Tag;

public class ViewCategoryDetails extends AppCompatActivity implements ComicAdapter.OnItemComicClick  {

    ImageView ivBack;
    RecyclerView rcvItem;
    ComicAdapter comicAdapter;
    TextView txt;

    DatabaseReference databaseReference;
    FirebaseAuth auth;
    FirebaseDatabase database;

    List<Comic> comic;

    Tag tag;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_category_details);

        ivBack = findViewById(R.id.imgBack);
        ivBack.setOnClickListener(v -> finish());
        UploadMangaItem();

    }

    private void UploadMangaItem() {
       comic = new ArrayList<>();

        databaseReference = FirebaseDatabase.getInstance().getReference("Comic");
        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if(comic.size()!=0)
                    comic.clear();
                for (DataSnapshot data : snapshot.getChildren()) {
                    Comic comicLoad = data.getValue(Comic.class);
                    String[] category = Objects.requireNonNull(comicLoad).getCategory().split("/");

                    tag = (Tag) getIntent().getSerializableExtra("category");
                    for(String Cate : category){
                        if(Cate.compareTo(tag.getTag()) == 0){
                            comic.add(comicLoad);
                            break;
                        }
                    }

                }
                setComicAdapter(comic);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }
    private void setComicAdapter(List<Comic> comic) {
        rcvItem = findViewById(R.id.rcv_category);
        comicAdapter = new ComicAdapter(ViewCategoryDetails.this, comic, this);
        rcvItem.setLayoutManager(new GridLayoutManager(this, 2));
        rcvItem.setAdapter(comicAdapter);
        comicAdapter.notifyDataSetChanged();
    }

    @Override
    public void onItemComicClick(int clickedItemIndex) {
        Intent intent = new Intent(ViewCategoryDetails.this, ViewComicDetails.class);
        intent.putExtra("comic", comic.get(clickedItemIndex));
        startActivity(intent);
    }
}