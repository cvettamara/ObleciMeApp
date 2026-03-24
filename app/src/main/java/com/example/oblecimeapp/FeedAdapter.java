package com.example.oblecimeapp;

import android.content.Context;
import android.net.Uri;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;

import java.util.List;

public class FeedAdapter extends RecyclerView.Adapter<FeedAdapter.ImageViewHolder> {
    private List<String> imageUris;
    private List<Boolean> likedStates;
    private Context context;

    public FeedAdapter(Context context, List<String> imageUris, List<Boolean> likedStates) {
        this.context = context;
        this.imageUris = imageUris;
        this.likedStates = likedStates;
    }

    @NonNull
    @Override
    public ImageViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.image_item, parent, false);
        return new ImageViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ImageViewHolder holder, int position) {
        String imageUri = imageUris.get(position);
        Log.d("FeedAdapter", "onBindViewHolder called for position: " + position);

        if (imageUri != null && !imageUri.isEmpty()) {
            Glide.with(holder.itemView.getContext())
                    .load(Uri.parse(imageUri))
                    .apply(new RequestOptions().placeholder(R.drawable.outfit6))
                    .into(holder.imageView);
            Log.d("FeedAdapter", "Loading image: " + imageUri);
        } else {
            Log.e("FeedAdapter", "Image URI is null or empty at position: " + position);
        }

        // Set the like button state based on the likedStates list
        holder.likeButton.setBackgroundResource(likedStates.get(position) ? R.drawable.star2 : R.drawable.star);

        // OnClickListener for the like button
        holder.likeButton.setOnClickListener(v -> {
            Log.d("FeedAdapter", "Like button clicked for position: " + position);

            boolean isLiked = likedStates.get(position);
            likedStates.set(position, !isLiked);  // Toggle like state

            holder.likeButton.setBackgroundResource(likedStates.get(position) ? R.drawable.star2 : R.drawable.star);

            // Show custom Toast message with custom font
            Context context = holder.itemView.getContext();
            LayoutInflater inflater = LayoutInflater.from(context);
            View layout = inflater.inflate(R.layout.toast_layout, null);

            TextView text = layout.findViewById(R.id.toastText);
            text.setText("Saved!");

            Log.d("FeedAdapter", "About to show Toast for position: " + position);

            Toast toast = new Toast(context);
            toast.setDuration(Toast.LENGTH_SHORT);
            toast.setView(layout);
            toast.show();

            Log.d("FeedAdapter", likedStates.get(position) ? "Photo liked" : "Photo unliked" + " at position: " + position);
        });
    }



    @Override
    public int getItemCount() {
        return imageUris.size();
    }

    public static class ImageViewHolder extends RecyclerView.ViewHolder {
        ImageView imageView;
        ImageButton likeButton; // ImageButton for like button

        public ImageViewHolder(@NonNull View itemView) {
            super(itemView);
            imageView = itemView.findViewById(R.id.imageViewFeed); // Use imageViewFeed here
            likeButton = itemView.findViewById(R.id.likeButton); // Use likeButton as per your layout
        }
    }
}
