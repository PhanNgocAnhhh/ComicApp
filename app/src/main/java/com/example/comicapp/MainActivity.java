package com.example.comicapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.SearchManager;
import android.content.Context;
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
import java.util.Objects;

import Adapter.ComicAdapter;
import Model.Comic;

public class MainActivity extends AppCompatActivity implements ComicAdapter.OnItemMangaClick {


    ImageSlider mainslider;
    RecyclerView recyclerComic;
    SearchView searchView;

    //Database
    DatabaseReference databaseReference;
    // Listener

    // Adapter
    ComicAdapter comicadapter;

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
        // Tìm kiếm truyên
        Search();

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
        comicadapter = new ComicAdapter(MainActivity.this, comic, this);
        recyclerComic.setAdapter(comicadapter);
        comicadapter.notifyDataSetChanged();
    }

    @Override
    public void onMangaItemClick(int clickedItemIndex) {
        Intent intent = new Intent(MainActivity.this, ViewComicDetails.class);
        intent.putExtra("comic", comic.get(clickedItemIndex));
        startActivity(intent);
    }

    // Tìm kiếm tên truyện
    private void Search() {
        searchView = findViewById(R.id.edtSearch);
        SearchManager searchManager = (SearchManager) getSystemService(Context.SEARCH_SERVICE);
        searchView.setSearchableInfo(searchManager.getSearchableInfo(getComponentName()));

        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                comicadapter.getFilter().filter(query);
                getFilterComic(query);
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                comicadapter.getFilter().filter(newText);
                getFilterComic(newText);
                return false;
            }
        });
    }

    // Hàm lấy tên truyện
    private void getFilterComic(String query) {
        databaseReference= FirebaseDatabase.getInstance().getReference("Comic");
        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if(comic.size()!=0)
                    comic.clear();
                for (DataSnapshot data : snapshot.getChildren()) {
                 Comic comicLoad = data.getValue(Comic.class);
                    if(comicLoad.getName().toLowerCase().contains(query)){
//                        String[] category = Objects.requireNonNull(manga).getCategory().split("/");
                        comic.add(comicLoad);
                    }
                }
                setComicAdapter(comic);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
            }
        });
    }

}