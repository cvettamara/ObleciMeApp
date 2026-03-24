package com.example.oblecimeapp;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.bumptech.glide.Glide;
import java.util.ArrayList;
import java.util.List;

public class FeedActivity extends AppCompatActivity {

    private RecyclerView recyclerViewFeed;
    private FeedAdapter feedAdapter;
    private List<String> feedImageList;
    private List<Boolean> likedStates; // List for storing liked states

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_feed);
        Log.d("FeedActivity", "Activity started, setting up RecyclerView.");

        // Initialize RecyclerView
        recyclerViewFeed = findViewById(R.id.recyclerViewFeed);
        recyclerViewFeed.setLayoutManager(new LinearLayoutManager(this));  // LinearLayoutManager for vertical scrolling

        // Initialize feedImageList and likedStates
        feedImageList = new ArrayList<>();
        likedStates = new ArrayList<>();

        feedImageList.add("https://i.pinimg.com/736x/67/d7/b4/67d7b4f0f2069e0a2ed9097ec5efea54.jpg");
        feedImageList.add("https://i.pinimg.com/736x/f6/63/7f/f6637f84ed4dbed7fabb371034a6f0e7.jpg");
        feedImageList.add("https://i.pinimg.com/736x/97/fa/ad/97faadbfbc86817d7f2729d0f5b092e4.jpg");
        feedImageList.add("https://i.pinimg.com/736x/8a/e6/b4/8ae6b49e8307861efae8b697bc31da69.jpg");
        feedImageList.add("https://i.pinimg.com/736x/88/17/3a/88173a9574d70128fdc03cefdd773332.jpg");

        // Initialize likedStates list with false (not liked)
        for (int i = 0; i < feedImageList.size(); i++) {
            likedStates.add(false);
        }

        // Log the number of images in the feed list
        Log.d("FeedActivity", "Number of images in feedImageList: " + feedImageList.size());

        // Initialize and set the adapter
        feedAdapter = new FeedAdapter(this, feedImageList, likedStates);
        recyclerViewFeed.setAdapter(feedAdapter);

        Log.d("FeedActivity", "FeedAdapter set successfully.");
    }
}
