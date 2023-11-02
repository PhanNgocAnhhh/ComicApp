package com.example.comicapp;

import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager.widget.ViewPager;

import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.denzcoskun.imageslider.adapters.ViewPagerAdapter;

import Adapter.ViewChapterAdapter;
import Model.Chapter;

public class ViewChapter extends AppCompatActivity implements View.OnClickListener {

    ViewPager viewPager;
    TextView txtChapterName;
    View btnBack;
    Chapter chapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_chapter);

        // Nhận dữ liệu
        chapter = (Chapter) getIntent().getSerializableExtra("chapter");

        // Ánh xạ
        viewPager = findViewById(R.id.viewPager);
        txtChapterName = findViewById(R.id.txtCurrentChapter);
        btnBack = findViewById(R.id.chapter_back);
        txtChapterName.setText(chapter.getName());

        // Xử lý Button Back
        btnBack.setOnClickListener(view -> finish());

        fetchhLinks(chapter);
    }

    private void fetchhLinks(Chapter chap) {
        if (chap.getLink() != null) {
            if (chap.getLink().size() > 0) {
                ViewChapterAdapter adapter = new ViewChapterAdapter(this, chap.getLink());
                viewPager.setAdapter(adapter);
                ;

            }
        }
    }

    @Override
    public void onClick(View v) {
    }
}