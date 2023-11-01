package com.example.comicapp;

import static Common.Common.*;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.ImageView;

import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.database.DatabaseReference;
import com.squareup.picasso.Picasso;


import java.util.List;

import Adapter.ChapterAdapter;
import Common.Common;

import Model.Chapter;
import Model.Comic;

public class ViewComicDetails extends AppCompatActivity implements ChapterAdapter.OnItemChapterClick  {


    //View
    Button btnReadComic;
    ImageView ivPosterComic;
    ImageView btnBack;
    TextView tvDescription, tvName;
    RecyclerView rcvChapter;
    LinearLayoutManager layoutManager;



    // Adapter
    ChapterAdapter adapter;

    //Database
    DatabaseReference reference;
    //

    Comic comic;
    List<Chapter> listChapter;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_viewcomicdetails);

        // Nhận dữ liệu
        comic = (Comic) getIntent().getSerializableExtra("comic");


        // Ánh xạ
        btnReadComic = findViewById(R.id.btn_read_comic);
        ivPosterComic = findViewById(R.id.img_poster_comic);
        btnBack = findViewById(R.id.btn_back);
        tvDescription = findViewById(R.id.tv_description);
        tvName = findViewById(R.id.tv_name_comic);

        // RecyclerView
        rcvChapter = findViewById(R.id.rcv_chapter);
        rcvChapter.setHasFixedSize(true);
        layoutManager = new LinearLayoutManager(this);
        rcvChapter.setLayoutManager( layoutManager);

        // Click button back
        btnBack.setOnClickListener(view -> finish());
        // Click Vào Button " Đọc truyện"
        btnReadComic.setOnClickListener( view ->{
            Intent intent = new Intent(this, ViewChapter.class);
            startActivity(intent);
        });
        
        LoadDetails();

//        // Hiển thị view
//        // Tên truyện
//        tvName.setText(getIntent().getExtras().getString("name"));
//        // Mô tả
//        tvDescription.setText(getIntent().getExtras().getString("description"));
//        // Poster
//        String imageUrl = getIntent().getStringExtra("image_url");
//        Picasso.get().load(imageUrl).into(ivPosterComic);
        // Chapter


    }
    // Hiển thị view
    private void LoadDetails() {
        // Ảnh
        Picasso.get().load(comic.getImage()).into(ivPosterComic);
        // Tên Truyện
        tvName.setText(comic.getName());
        // Mô tả
        tvDescription.setText(comic.getDescription());
        // Chapter
        listChapter = comic.getChapters();
        adapter = new ChapterAdapter(this, listChapter, this);
        adapter.notifyDataSetChanged();
        rcvChapter.setLayoutManager(new LinearLayoutManager(this, RecyclerView.VERTICAL, false));
        rcvChapter.setAdapter(adapter);
    }

    @Override
    public void onChapterItemClick(int clickedItemIndex) {
        Intent intent = new Intent(ViewComicDetails.this, ViewChapter.class);
        intent.putExtra("chapter", listChapter.get(clickedItemIndex));
        startActivity(intent);
    }
}
