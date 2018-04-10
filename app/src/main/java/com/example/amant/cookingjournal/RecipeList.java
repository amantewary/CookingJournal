package com.example.amant.cookingjournal;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

import me.zhanghai.android.materialratingbar.MaterialRatingBar;

/**
 * Created by amant on 07-03-2018.
 */

public class RecipeList extends RecyclerView.Adapter<RecipeList.ViewHolder> {

    /*
        This is an Adapter for the recycler view in the MainActivity.java
     */

    private Context context;
    private List<Recipes> recipesList;


    public RecipeList(Context context, List<Recipes> recipesList) {
        this.context = context;
        this.recipesList = recipesList;
    }

    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new ViewHolder(LayoutInflater.from(context).inflate(R.layout.list_recipe_item, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, final int position) {
        final Recipes recipes = recipesList.get(position);
        holder.recipeName.setText(recipes.getRecipeTitle());
        holder.recipeCuisine.setText(recipes.getRecipeCuisine());
        holder.recipeRatings.setRating(recipes.getRecipeRating());
        holder.parentView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, RecipeDetails.class);
                intent.putExtra(MainActivity.RECIPE_ID, recipes.getRecipeId());
                context.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return this.recipesList.size();
    }


    public class ViewHolder extends RecyclerView.ViewHolder {
        /*
            Used a Third-Party Rating Bar.
            https://github.com/DreaminginCodeZH/MaterialRatingBar
            For license information check the README file.
        */
        MaterialRatingBar recipeRatings;
        TextView recipeName;
        TextView recipeCuisine;
        View parentView;

        public ViewHolder(@NonNull View view) {
            super(view);
            this.recipeName = view.findViewById(R.id.viewRecipeName);
            this.recipeCuisine = view.findViewById(R.id.viewCuisine);
            this.recipeRatings = view.findViewById(R.id.viewRecipeRating);
            this.parentView = view;
        }
    }
}
