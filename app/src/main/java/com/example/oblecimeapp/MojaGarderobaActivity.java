package com.example.oblecimeapp;

import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageButton;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import java.util.ArrayList;
import java.util.List;

public class MojaGarderobaActivity extends AppCompatActivity {

    private static final int PICK_IMAGE_REQUEST = 1;
    private RecyclerView recyclerViewClothes;
    private ClothesAdapter clothesAdapter;
    private List<String> clothesImageList;
    private DatabaseHelper databaseHelper;
    private String userEmail = "test@example.com"; // Replace with actual logged-in user email

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_moja_garderoba);

        recyclerViewClothes = findViewById(R.id.recyclerViewClothes);
        ImageButton pickImageButton = findViewById(R.id.btnPickImage);
        ImageButton btnDressMe = findViewById(R.id.btnDressMe);

        databaseHelper = new DatabaseHelper(this);
        clothesImageList = new ArrayList<>();

        recyclerViewClothes.setLayoutManager(new GridLayoutManager(this, 2));
        clothesAdapter = new ClothesAdapter(clothesImageList, MojaGarderobaActivity.this);
        recyclerViewClothes.setAdapter(clothesAdapter);

        loadSavedImages();

        pickImageButton.setOnClickListener(v -> pickImage());

        btnDressMe.setOnClickListener(v -> {
            Intent intent = new Intent(MojaGarderobaActivity.this, FeedActivity.class);
            startActivity(intent);
        });
    }

    private void pickImage() {
        Intent intent = new Intent(Intent.ACTION_PICK);
        intent.setType("image/*");
        startActivityForResult(intent, PICK_IMAGE_REQUEST);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == PICK_IMAGE_REQUEST && resultCode == RESULT_OK && data != null) {
            Uri imageUri = data.getData();
            if (imageUri != null) {
                String imagePath = imageUri.toString();
                databaseHelper.addClothing(userEmail, imagePath);
                clothesImageList.add(imagePath);
                clothesAdapter.notifyDataSetChanged();
            }
        }
    }

    private void loadSavedImages() {
        try {
            clothesImageList.clear();
            clothesImageList.addAll(databaseHelper.getAllImages(userEmail));
            clothesAdapter.notifyDataSetChanged();
        } catch (Exception e) {
            Log.e("MojaGarderoba", "Error loading images: ", e);
            // Handle the error gracefully or notify the user
        }
    }
    public void deleteImage(String imageUri) {
        // Delete image from the database
        databaseHelper.deleteClothing(userEmail, imageUri);

        // Remove image from the list and update RecyclerView
        clothesImageList.remove(imageUri);
        clothesAdapter.notifyDataSetChanged();
    }


}
