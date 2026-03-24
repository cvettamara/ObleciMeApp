package com.example.oblecimeapp;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.bumptech.glide.Glide;
import java.util.List;

public class ClothesAdapter extends RecyclerView.Adapter<ClothesAdapter.ViewHolder> {

    private List<String> clothesImageList;
    private MojaGarderobaActivity context;  // Correct context

    // Constructor
    public ClothesAdapter(List<String> clothesImageList, MojaGarderobaActivity context) {
        this.clothesImageList = clothesImageList;
        this.context = context;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_clothing, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        String imageUri = clothesImageList.get(position);

        // Use Glide to load the image
        Glide.with(holder.imageView.getContext())
                .load(imageUri)
                .into(holder.imageView);

        // Set long click listener for deleting the image
        holder.itemView.setOnLongClickListener(v -> {
            // Call the method to delete the image from the database and list
            context.deleteImage(imageUri);
            return true; // Indicate that the event was handled
        });
    }

    @Override
    public int getItemCount() {
        return clothesImageList.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        ImageView imageView;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            imageView = itemView.findViewById(R.id.imageViewClothing);
        }
    }
}
