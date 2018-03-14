package com.example.amant.cookingjournal;

import android.app.Activity;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.List;

import me.zhanghai.android.materialratingbar.MaterialRatingBar;

/**
 * Created by amant on 07-03-2018.
 */

public class RecipeList extends ArrayAdapter<Recipes>{

    private Activity context;
    private List<Recipes> recipesList;

    public RecipeList(Activity context, List<Recipes> recipesList) {
        super(context, R.layout.list_recipe_item, recipesList);
        this.context = context;
        this.recipesList = recipesList;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {

            LayoutInflater inflater = context.getLayoutInflater();
            View listItem = inflater.inflate(R.layout.list_recipe_item, null, true);

        TextView recipeName = (TextView)listItem.findViewById(R.id.viewRecipeName);
        TextView recipeCuisine = (TextView)listItem.findViewById(R.id.viewCuisine);
        MaterialRatingBar recipeRatings = (MaterialRatingBar)listItem.findViewById(R.id.viewRecipeRating);

        Recipes recipes = recipesList.get(position);
        recipeName.setText(recipes.getRecipeName());
        recipeCuisine.setText(recipes.getRecipeCuisine());
        recipeRatings.setRating(recipes.getRecipeRating());

        return listItem;
    }
}
