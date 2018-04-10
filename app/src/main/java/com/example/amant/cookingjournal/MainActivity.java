package com.example.amant.cookingjournal;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    /*
        Used an icon image for making launcher icon (ic_launcher)
        Image Source: https://www.iconfinder.com/icons/753929/cooking_eating_food_kitchen_restaurant_icon#size=32
     */
    public static final String RECIPE_ID = "recipeId";

    RecyclerView recipeListView;
    List<Recipes> recipeList;
    RecipeList recipeAdapter;
    AutoCompleteTextView searchBar;
    DatabaseReference databaseReference;
    String[] recipeCuisine;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        databaseReference = FirebaseDatabase.getInstance().getReference("recipe");
        Toolbar toolbar = findViewById(R.id.toolbar);
        recipeListView = findViewById(R.id.recipeList);
        recipeList = new ArrayList<>();
        searchBar = findViewById(R.id.searchBar);
        setSupportActionBar(toolbar);
        /*
            FAB is used to to start an activity where user can add
            their recipes.
         */
        FloatingActionButton fab = findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, AddRecipes.class);
                startActivity(intent);
            }
        });
        /*
            SearchBar is basically an AutoCompleteTextView which executes the search query
            when text is changed. It also give cuisine suggestions.
         */
        recipeCuisine = getResources().getStringArray(R.array.cuisine);
        ArrayAdapter<String> cuisineAdapter = new ArrayAdapter<String>(this,
                android.R.layout.simple_dropdown_item_1line, recipeCuisine);
        searchBar.setAdapter(cuisineAdapter);
        searchBar.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (!s.toString().isEmpty()) {
                    searchDatabase(s.toString());
                } else {
                    onStart();
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });


    }

    /*
        Recipe is loaded from database to the list on the home screen.
     */
    @Override
    protected void onStart() {
        super.onStart();

        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                recipeList.clear();
                for (DataSnapshot recipeSnapshot : dataSnapshot.getChildren()) {
                    Recipes recipes = recipeSnapshot.getValue(Recipes.class);
                    recipeList.add(0,recipes);
                }
                recipeAdapter = new RecipeList(MainActivity.this, recipeList);
                recipeListView.setAdapter(recipeAdapter);
                recipeListView.setLayoutManager(new LinearLayoutManager(MainActivity.this));
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                Log.e("Here",""+ databaseError);
            }
        });
    }

    /*
        Function for searching recipe in database.
        User can search for recipe using the
        name of the recipe. User will also get suggestion
        for the type of cuisine.
     */
    public void searchDatabase(final String query) {
        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                recipeList.clear();
                for (DataSnapshot searchSnapshot : dataSnapshot.getChildren()) {
                    Recipes recipes = searchSnapshot.getValue(Recipes.class);
                    String title = recipes.getRecipeTitle();
                    String cuisine = recipes.getRecipeCuisine();
                    if (title.toLowerCase().contains(query.toLowerCase()) || cuisine.toLowerCase().equals(query.toLowerCase())) {
                        recipeList.add(0,recipes);
                    }
                }
                recipeAdapter = new RecipeList(MainActivity.this, recipeList);
                recipeListView.setLayoutManager(new LinearLayoutManager(MainActivity.this));
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                Log.e("Here",""+ databaseError);
            }
        });
    }
}
