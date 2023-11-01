package com.example.comicapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;
import com.denzcoskun.imageslider.ImageSlider;
import com.denzcoskun.imageslider.constants.ScaleTypes;
import com.denzcoskun.imageslider.models.SlideModel;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import java.util.ArrayList;
import java.util.List;
import Adapter.ComicAdapter;
import Common.Common;
import Interface.IComicLoadDone;
import Model.Comic;

public class MainActivity extends AppCompatActivity implements ComicAdapter.OnItemMangaClick {


    ImageSlider mainslider;
    RecyclerView recyclerComic;

    //Database
    DatabaseReference databaseReference;
    // Listener
    IComicLoadDone comicListener;

    // Adapter
    ComicAdapter adapter;

    List<Comic> comic;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Init Database
        databaseReference = FirebaseDatabase.getInstance().getReference().child("Comic");

        // Ánh xạ
        mainslider = findViewById(R.id.image_slider);

        // Lấy dữ liệu Banner trên Realtime Database
        final List<SlideModel> bannerLoad = new ArrayList<>();
        FirebaseDatabase.getInstance().getReference().child("Banner").addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for (DataSnapshot data: snapshot.getChildren()){
                    bannerLoad.add( new SlideModel(data.child("url").getValue().toString(),ScaleTypes.CENTER_CROP));

                    mainslider.setImageList(bannerLoad,ScaleTypes.CENTER_CROP);

                }
            }
            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });



        // Load dữ liệu từ FireBase về
        loadComic();

    }

    // Hàm lấy dữ liệu Comic trên RealTimeDatabase
    private void loadComic(){
        comic = new ArrayList<>();
        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for (DataSnapshot data: snapshot.getChildren()){
                    Comic comicload =data.getValue(Comic.class);
                    comic.add(comicload);
                }
                setComicAdapter(comic);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

    }
    private void setComicAdapter(List<Comic> comic) {

        // RecyclerView
        recyclerComic = findViewById(R.id.recycler_view);
        recyclerComic.setLayoutManager(new GridLayoutManager(this,2));
        adapter = new ComicAdapter(MainActivity.this, comic, this);
        recyclerComic.setAdapter(adapter);
        adapter.notifyDataSetChanged();
    }

    @Override
    public void onMangaItemClick(int clickedItemIndex) {
        Intent intent = new Intent(MainActivity.this, ViewComicDetails.class);
        intent.putExtra("comic", comic.get(clickedItemIndex));
        startActivity(intent);
    }
}