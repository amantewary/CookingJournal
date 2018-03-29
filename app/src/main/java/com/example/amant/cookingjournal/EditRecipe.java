package com.example.amant.cookingjournal;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import me.zhanghai.android.materialratingbar.MaterialRatingBar;

public class EditRecipe extends AppCompatActivity {

    private EditText recipeTitle;
    private EditText recipeIngredients;
    private EditText recipeSteps;
    private EditText recipeUrl;
    String rId;
    Intent intent;
    private Spinner recipeCuisine;
    private MaterialRatingBar recipeRating;

    DatabaseReference dbRecipes;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.edit_recipe);
        intent = getIntent();
        rId = intent.getStringExtra("reviewid");
        Log.e("Here","create"+ rId);

        dbRecipes = FirebaseDatabase.getInstance().getReference("recipe");

        recipeTitle = (EditText) findViewById(R.id.editRecipeTitle);
        recipeIngredients = (EditText) findViewById(R.id.editIngredients);
        recipeSteps = (EditText) findViewById(R.id.editSteps);
        recipeUrl = (EditText) findViewById(R.id.editLink);
        recipeCuisine = (Spinner) findViewById(R.id.editCuisineSpinner);
        recipeRating = (MaterialRatingBar) findViewById(R.id.editRecipeRating);

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.save_edit_button, menu);
        return true;
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        int id = item.getItemId();
        if (id == R.id.action_edit){
            Log.e("Here","button"+ rId);
            editRecipe(rId);
        }

        return super.onOptionsItemSelected(item);
    }

    private void editRecipe(String reviewId){
        String id = reviewId;
        Log.e("Here","func"+ id);
        String name = recipeTitle.getText().toString().trim();
        String ingredients = recipeIngredients.getText().toString().trim();
        String steps = recipeSteps.getText().toString().trim();
        String cuisine = recipeCuisine.getSelectedItem().toString();
        String url = recipeUrl.getText().toString().trim();
        Float rating = recipeRating.getRating();

        if(!TextUtils.isEmpty(name) && !TextUtils.isEmpty(ingredients) && !TextUtils.isEmpty(steps)){

            Recipes recipe = new Recipes(id, name, ingredients, steps, cuisine, url, rating);
            dbRecipes.child(id).setValue(recipe);

            Toast.makeText(this,"Recipe Added",Toast.LENGTH_SHORT).show();
            finish();
        }else{
            if (name.matches(""))
            {
                recipeTitle.setBackgroundResource(R.drawable.border_error);
                recipeTitle.setError("Enter Recipe Name");
                return;
            }
            else {
                recipeTitle.setBackgroundResource(R.drawable.border);
            }
            if (ingredients.matches(""))
            {
                recipeIngredients.setBackgroundResource(R.drawable.border_error);
                recipeIngredients.setError("Enter Recipe Ingredients");
                return;
            }
            else {
                recipeIngredients.setBackgroundResource(R.drawable.border);
            }
            if (steps.matches(""))
            {
                recipeSteps.setBackgroundResource(R.drawable.border_error);
                recipeSteps.setError("Enter Recipe Steps");
                return;
            }
            else {
                recipeSteps.setBackgroundResource(R.drawable.border);
            }
        }

    }
}
