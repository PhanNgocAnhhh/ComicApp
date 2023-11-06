package com.example.comicapp;
import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.ImageView;

import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.database.DatabaseReference;

import com.squareup.picasso.Picasso;
import java.util.List;
import java.util.Objects;

import Adapter.ChapterAdapter;

import Adapter.ComicAdapter;
import Model.Chapter;
import Model.Comic;

public class ViewComicDetails extends AppCompatActivity implements ChapterAdapter.OnItemChapterClick  {
    //View
    Button btnReadComic;
    ImageView ivPosterComic,ivBannerComic;
    ImageView btnBack;
    TextView tvDescription, tvName;
    RecyclerView rcvChapter;
    LinearLayoutManager layoutManager;

    // Adapter
    ChapterAdapter chapteradapter;

    //Database

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
        ivBannerComic = findViewById(R.id.img_banner2_comic);

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
            intent.putExtra("chapter", listChapter.get(0));
            startActivity(intent);
        });
        
        LoadDetails();
    }
    // Hiển thị view
    private void LoadDetails() {
        // Banner
//        Picasso.get().load(comic.getBanner()).into(ivBannerComic);
        // Ảnh
        Picasso.get().load(comic.getImage()).into(ivPosterComic);
        // Tên Truyện
        tvName.setText(comic.getName());
        // Mô tả
        tvDescription.setText(comic.getDescription());
        // Chapter
        listChapter = comic.getChapters();
        chapteradapter = new ChapterAdapter(this, listChapter, this);
        chapteradapter.notifyDataSetChanged();
        rcvChapter.setLayoutManager(new LinearLayoutManager(this, RecyclerView.VERTICAL, false));
        rcvChapter.setAdapter(chapteradapter);
    }
    // Xử lý click vào chapter
    @Override
    public void onChapterItemClick(int clickedItemIndex) {
        Intent intent = new Intent(ViewComicDetails.this, ViewChapter.class);
        intent.putExtra("chapter", listChapter.get(clickedItemIndex));
        startActivity(intent);
    }

}
