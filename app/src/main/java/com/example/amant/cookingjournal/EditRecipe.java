package com.example.amant.cookingjournal;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.util.Log;
import android.util.Patterns;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import me.zhanghai.android.materialratingbar.MaterialRatingBar;

public class EditRecipe extends AppCompatActivity {

    String rId;
    Intent intent;
    Recipes recipe;
    DatabaseReference dbRecipes;
    /*
        Used a Third-Party Rating Bar.
        https://github.com/DreaminginCodeZH/MaterialRatingBar
        For license information check the README file.
    */
    private MaterialRatingBar recipeRating;
    private EditText recipeTitle;
    private EditText recipeIngredients;
    private EditText recipeSteps;
    private EditText recipeUrl;
    private Spinner recipeCuisine;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.edit_recipe);
        dbRecipes = FirebaseDatabase.getInstance().getReference("recipe");
        intent = getIntent();
        rId = intent.getStringExtra("recipeid");
        recipeTitle = findViewById(R.id.editRecipeTitle);
        recipeIngredients = findViewById(R.id.editIngredients);
        recipeSteps = findViewById(R.id.editSteps);
        recipeUrl = findViewById(R.id.editLink);
        recipeCuisine = findViewById(R.id.editCuisineSpinner);
        recipeRating = findViewById(R.id.editRecipeRating);

        /*
            Retrieving existing recipe details.
         */
        dbRecipes.child(rId).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                recipe = dataSnapshot.getValue(Recipes.class);
                recipeTitle.setText(recipe.getRecipeTitle());
                recipeIngredients.setText(recipe.getRecipeIngredients());
                recipeSteps.setText(recipe.getRecipeSteps());
                recipeUrl.setText(recipe.getRecipeUrl());
                recipeRating.setRating(recipe.getRecipeRating());
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                Log.e("Here", "" + databaseError);
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.save_edit_button, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        int id = item.getItemId();
        if (id == R.id.action_edit) {
            Log.e("Here", "button" + rId);
            editRecipe(rId);
        }

        return super.onOptionsItemSelected(item);
    }

    /*
        Function for adding new recipe in firebase realtime database.
     */
    private void editRecipe(String recipeId) {
        String id = recipeId;
        Log.e("Here", "func" + id);
        String name = recipeTitle.getText().toString().trim();
        String ingredients = recipeIngredients.getText().toString().trim();
        String steps = recipeSteps.getText().toString().trim();
        String cuisine = recipeCuisine.getSelectedItem().toString();
        String url = recipeUrl.getText().toString().trim();
        Float rating = recipeRating.getRating();
        /*
            Validation for Recipe's Name, Ingredients, Steps and Cuisine.
            User can leave the link blank.
         */
        if (!TextUtils.isEmpty(name)
                && !TextUtils.isEmpty(ingredients)
                && !TextUtils.isEmpty(steps)
                && !cuisine.matches("Select Cuisine")
                && (Patterns.WEB_URL.matcher(url).matches()
                || TextUtils.isEmpty(url))) {

            Recipes recipe = new Recipes(id, name, ingredients, steps, cuisine, url, rating);
            dbRecipes.child(id).setValue(recipe);

            Toast.makeText(this, "Recipe Edited", Toast.LENGTH_SHORT).show();
            finish();
        } else {
            if (name.matches("")) {
                recipeTitle.setBackgroundResource(R.drawable.border_error);
                recipeTitle.setError("Enter Recipe Name");
                return;
            } else {
                recipeTitle.setBackgroundResource(R.drawable.border);
            }
            if (ingredients.matches("")) {
                recipeIngredients.setBackgroundResource(R.drawable.border_error);
                recipeIngredients.setError("Enter Recipe Ingredients");
                return;
            } else {
                recipeIngredients.setBackgroundResource(R.drawable.border);
            }
            if (steps.matches("")) {
                recipeSteps.setBackgroundResource(R.drawable.border_error);
                recipeSteps.setError("Enter Recipe Steps");
                return;
            } else {
                recipeSteps.setBackgroundResource(R.drawable.border);
            }
            if (cuisine.matches("Select Cuisine")) {
                /*
                    Referred Here for implementing validation on spinners
                    https://stackoverflow.com/a/27362252/2716018
                 */
                TextView error = (TextView) recipeCuisine.getSelectedView();
                error.setError("");
                error.setTextColor(Color.RED);
                error.setText("Please Select A Cuisine");
                return;
            }
            if (!Patterns.WEB_URL.matcher(url).matches()) {
                recipeUrl.setError("Please Enter Valid URL");
                return;
            }
        }

    }
}
