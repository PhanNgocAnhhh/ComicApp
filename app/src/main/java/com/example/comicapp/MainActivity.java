package com.example.comicapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.SearchManager;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;

import com.denzcoskun.imageslider.ImageSlider;
import com.denzcoskun.imageslider.constants.ScaleTypes;
import com.denzcoskun.imageslider.models.SlideModel;

import com.google.android.material.navigation.NavigationView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import java.util.ArrayList;
import java.util.List;

import com.example.comicapp.Adapter.ComicAdapter;
import com.example.comicapp.Model.Comic;

public class MainActivity extends AppCompatActivity implements ComicAdapter.OnItemComicClick, NavigationView.OnNavigationItemSelectedListener {


    ImageSlider mainslider;

    // Layout
    RecyclerView recyclerComic;
    DrawerLayout drawerLayout;


    //Database
    DatabaseReference databaseReference;

    // Adapter
    ComicAdapter comicadapter;

    List<Comic> comic;
    SearchView searchView;

    TextView  txtEmail;
    FirebaseAuth auth;
    FirebaseUser user;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Init Database
        databaseReference = FirebaseDatabase.getInstance().getReference().child("Comic");

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        // Ánh xạ
        mainslider = findViewById(R.id.image_slider);
        drawerLayout = findViewById(R.id.drawerLayout);

        // Xử lý click vào Toolbar
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this, drawerLayout, toolbar,
                R.string.nav_drawer_open, R.string.nav_drawer_close);
        drawerLayout.addDrawerListener(toggle);
        toggle.syncState();

        // Navigation
        NavigationView navigationView = findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
        txtEmail = navigationView.getHeaderView(0).findViewById(R.id.txt_nav_email);

        // Đăng Nhập
        auth = FirebaseAuth.getInstance();
        user = auth.getCurrentUser();
        if (user == null){
            Intent intent = new Intent(getApplicationContext(),screenDangNhap.class);
            startActivity(intent);
            finish();
        }
        else {
            txtEmail.setText(user.getEmail());
        }
        //

        // Lấy dữ liệu Banner trên Realtime Database
        final List<SlideModel> bannerLoad = new ArrayList<>();
        FirebaseDatabase.getInstance().getReference().child("Banner").addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if (isFinishing() || isDestroyed()){
                    return;
                }


                for (DataSnapshot data : snapshot.getChildren()) {
                    bannerLoad.add(new SlideModel(data.child("url").getValue().toString(), ScaleTypes.CENTER_CROP));
                    mainslider.setImageList(bannerLoad, ScaleTypes.CENTER_CROP);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                if (isFinishing() || isDestroyed()){
                    return;
                }
            }
        });

        // Load dữ liệu từ FireBase về
        loadComic();

    }

    // Hàm lấy dữ liệu Comic trên RealTimeDatabase
    private void loadComic() {
        comic = new ArrayList<>();
        databaseReference.addValueEventListener(new ValueEventListener() {

            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if (comic.size() != 0)
                    comic.clear();
                for (DataSnapshot data : snapshot.getChildren()) {
                    Comic comicload = data.getValue(Comic.class);
                    comic.add(comicload);
                }
                setComicAdapter(comic);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

    }

    // Adapter
    private void setComicAdapter(List<Comic> comic) {
        // RecyclerView
        recyclerComic = findViewById(R.id.recycler_view);
        recyclerComic.setLayoutManager(new GridLayoutManager(this, 2));
        comicadapter = new ComicAdapter(MainActivity.this, comic, this);
        recyclerComic.setAdapter(comicadapter);
        comicadapter.notifyDataSetChanged();
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        // Tìm kiếm truyện
        getMenuInflater().inflate(R.menu.search, menu);
        SearchManager searchManager = (SearchManager) getSystemService(Context.SEARCH_SERVICE);
        searchView = (SearchView) menu.findItem(R.id.action_search).getActionView();
        searchView.setSearchableInfo(searchManager.getSearchableInfo(getComponentName()));
        searchView.setMaxWidth(Integer.MAX_VALUE);
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                comicadapter.getFilter().filter(query);
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                comicadapter.getFilter().filter(newText);
                return false;
            }
        });

        return true;
    }

    // Xử lý khi click vào nút back trên thiết bị để không thoát App
    @Override
    public void onBackPressed() {
        if (!searchView.isIconified()) {
            searchView.setIconified(true);
            return;
        }
        super.onBackPressed();
    }

    // Xử lý click vào Navigation
    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        int id =item.getItemId();
        if (id ==R.id.nav_categories){
            drawerLayout.closeDrawer(GravityCompat.START);
            startActivity(new Intent(MainActivity.this,ViewCategory.class));

        }
        else if (id ==R.id.nav_change_password){
            drawerLayout.closeDrawer(GravityCompat.START);
            startActivity(new Intent(MainActivity.this,screenDoiPass.class));

        }else if (id ==R.id.nav_logout){
            auth.signOut();
            finish();
            startActivity(new Intent(MainActivity.this, screenDangNhap.class));
        }
        return true;
    }

    @Override
    public void onItemComicClick(Comic data, int index) {
        Intent intent = new Intent(MainActivity.this, ViewComicDetails.class);
        //code ban dau la lay item cho nay
         // Comic a = comic.get(index);
        intent.putExtra("comic", data);
        startActivity(intent);
    }
}
