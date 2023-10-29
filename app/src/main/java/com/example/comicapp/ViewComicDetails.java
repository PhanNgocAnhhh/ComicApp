package com.example.comicapp;

import static Common.Common.comicSelected;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.widget.Button;
import android.widget.ImageView;

import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.squareup.picasso.Picasso;
import Adapter.ChapterAdapter;
import Common.Common;

import Model.Comic;

public class ViewComicDetails extends AppCompatActivity {


    //View
    Button btnReadComic;
    ImageView ivPosterComic;
    ImageView btnBack;
    TextView tvDescription, tvName;

    RecyclerView rcvChapter;
    LinearLayoutManager layoutManager;

    // Adapter


    //Database



    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_viewcomicdetails);


        // Ánh xạ
        btnReadComic = findViewById(R.id.btn_read_comic);
        ivPosterComic = findViewById(R.id.img_poster_comic);
        btnBack = findViewById(R.id.btn_back);
        tvDescription = findViewById(R.id.tv_description);
        tvName = findViewById(R.id.tv_name_comic);

        // Click button back
        btnBack.setOnClickListener(view -> finish());

        // RecyclerView
        rcvChapter = findViewById(R.id.rcv_chapter);
        rcvChapter.setHasFixedSize(true);
        layoutManager = new LinearLayoutManager(this);
        rcvChapter.setLayoutManager( layoutManager);

        // Hiển thị view
        // Tên truyện
        tvName.setText(getIntent().getExtras().getString("name"));
        // Mô tả
        tvDescription.setText(getIntent().getExtras().getString("description"));
        // Poster
        String imageUrl = getIntent().getStringExtra("image_url");
        Picasso.get().load(imageUrl).into(ivPosterComic);
        // Chapter
        fetchChapter(Common.comicSelected);

    }

    private void fetchChapter(Comic comicSelected) {
        Common.chapterList = comicSelected.chapters;
        ChapterAdapter adapter = new ChapterAdapter(this,comicSelected.chapters);
        rcvChapter.setAdapter(adapter);

    }
}
