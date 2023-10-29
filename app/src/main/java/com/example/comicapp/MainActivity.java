package com.example.comicapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
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

public class MainActivity extends AppCompatActivity implements IComicLoadDone {


    ImageSlider mainslider;
    RecyclerView recyclerComic;
    TextView textComic;

    //Database
    DatabaseReference comics;
    // Listener
    IComicLoadDone comicListener;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Init Database
        comics =FirebaseDatabase.getInstance().getReference().child("Comic");

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

        // Init Listener
        comicListener = this;
        recyclerComic = findViewById(R.id.recycler_view);
        recyclerComic.setHasFixedSize(true);
        recyclerComic.setLayoutManager(new GridLayoutManager(this,2));

        // Load dữ liệu từ FireBase về
        loadComic();

    }

    // Hàm lấy dữ liệu Comic trên RealTimeDatabase
    private void loadComic(){

        comics.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                List<Comic> comicLoad = new ArrayList<>();
                for (DataSnapshot data: snapshot.getChildren()){
                    Comic comic =data.getValue(Comic.class);
                    comicLoad.add(comic);
                }
                comicListener.onComicLoadDoneList(comicLoad);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

    }

    // Lưu dữ liệu từ FireBase vào comicList và hiển thị dữ liệu lên RecycLerView
    @Override
    public void onComicLoadDoneList(List<Comic> comicList) {
        Common.comicList = comicList;
        recyclerComic.setAdapter( new ComicAdapter(getBaseContext(),comicList));

    }

}