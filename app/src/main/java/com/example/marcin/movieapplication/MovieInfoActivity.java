package com.example.marcin.movieapplication;

import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.Context;
import android.content.Intent;

import android.content.res.Configuration;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.Switch;
import android.widget.TextView;

import com.example.marcin.movieapplication.converters.MovieToDescriptionConverter;
import com.example.marcin.movieapplication.converters.MovieToImageConverter;
import com.example.marcin.movieapplication.movie.MovieItem;
import com.example.marcin.movieapplication.movie.MoviesDatabase;

public class MovieInfoActivity extends AppCompatActivity {
    private static String MOVIE_ID = "movieId";
    public MovieItem movie;
    private ImageView movieImage;
    private TextView movieName;
    private TextView movieDescription;
    private TextView movieCategory;
    private Fragment fragment;
    private MoviesDatabase database;
    private Switch fragmentSwitch;
    public static final String MOVIE_NAME = "MOVIE_NAME";
    private static final String IS_SWITCH_CHECKED = "IS_SWTICH_CHECKED";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_movie_info);

        initViews();

        initDatabase();

        setInfoAboutMovie();

        initListeners();

        setFragments(savedInstanceState);
    }

    private void setFragments(Bundle savedInstanceState) {
        if (savedInstanceState != null && savedInstanceState.containsKey(IS_SWITCH_CHECKED)){
            boolean isChecked = savedInstanceState.getBoolean(IS_SWITCH_CHECKED);
            if (isChecked){
                initActorsListFragment();
            }
            else{
                initScreenshotFragment();
            }
        }
        else{
            initScreenshotFragment();
        }
    }


    private void initViews(){
        movieImage = findViewById(R.id.movie_main_image);
        movieName = findViewById(R.id.name_of_movie);
        fragmentSwitch = findViewById(R.id.fragment_switch);
        if (isOrientationPortrait()) {
            movieDescription = findViewById(R.id.description);
            movieCategory = findViewById(R.id.movie_category);
        }
    }

    private void initListeners(){
        fragmentSwitch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                setFragmentBasedOnSwitchStatus();
            }
        });
    }

    private void setFragmentBasedOnSwitchStatus() {
        if (fragmentSwitch.isChecked()){
            initActorsListFragment();

        }
        else{
            initScreenshotFragment();
        }
    }

    private void initScreenshotFragment(){
        fragment = new ScreenshotListFragment();
        setMovieNameToBundle();
        completeFragmentTransactionForSpecificLayout(R.id.movie_screenshots_fragment);
    }

    private void setMovieNameToBundle() {
        Bundle bundle = new Bundle();
        bundle.putString(MOVIE_NAME, movie.getMovieName());
        fragment.setArguments(bundle);
    }

    private void initActorsListFragment(){
        fragment = new ActorsListFragment();
        setMovieNameToBundle();
        completeFragmentTransactionForSpecificLayout(R.id.movie_screenshots_fragment);
    }

    private void completeFragmentTransactionForSpecificLayout(int layoutId){
        FragmentManager fragmentManager = getFragmentManager();
        FragmentTransaction transaction = fragmentManager.beginTransaction();
        transaction.replace(layoutId, fragment);
        transaction.commit();
    }

    private void initDatabase(){
        database = MoviesDatabase.getInstance(getApplicationContext());
    }

    private void setInfoAboutMovie(){
        getMovieItem();

        String movieName = movie.getMovieName();
        MovieToImageConverter converter = new MovieToImageConverter(movieName, getApplicationContext());
        MovieToDescriptionConverter descriptionConverter =
                new MovieToDescriptionConverter(movieName, getApplicationContext());

        setMovieImage(converter);
        setMovieName(descriptionConverter);

        if (isOrientationPortrait())
            setAdditionalDataForPortrait(descriptionConverter);
    }

    private void getMovieItem() {
        Intent intent = getIntent();
        int id = intent.getIntExtra(MOVIE_ID, 1);
        movie = database.movieItemDao().getMovieById(id);
    }

    private void setMovieImage(MovieToImageConverter converter) {
        movieImage.setImageResource(converter.getDefaultResourceOfPosterImage());
    }

    private void setMovieName(MovieToDescriptionConverter descriptionConverter) {
        String movieName;
        movieName = getApplicationContext().getText(descriptionConverter.getMovieTitleId()).toString();
        this.movieName.setText(movieName);
    }

    private boolean isOrientationPortrait() {
        return getResources().getConfiguration().orientation == Configuration.ORIENTATION_PORTRAIT;
    }

    private void setAdditionalDataForPortrait(MovieToDescriptionConverter descriptionConverter) {
        setMovieDescription(descriptionConverter);
        setMovieCategory();
    }

    private void setMovieDescription(MovieToDescriptionConverter descriptionConverter) {
        String movieDescription = getMovieDescription(descriptionConverter);
        this.movieDescription.setText(movieDescription);
    }

    @NonNull
    private String getMovieDescription(MovieToDescriptionConverter descriptionConverter) {
        return getApplicationContext().getText(descriptionConverter
                .getMovieDescriptionId()).toString();
    }

    private void setMovieCategory() {
        String categoryString = getMovieCategory();
        this.movieCategory.setText(categoryString);
    }

    @NonNull
    private String getMovieCategory() {
        String categoryString = movie.getCategory();
        int categoryId = getCategoryId(categoryString);
        categoryString = getApplicationContext().getText(categoryId).toString();
        return categoryString;
    }

    private int getCategoryId(String categoryString) {
        return getApplication().getResources().getIdentifier(categoryString, "string",
                getApplicationContext().getPackageName());
    }

    public static void start(Context context, int movieId) {
        Intent starter = new Intent(context, MovieInfoActivity.class);
        starter.putExtra(MOVIE_ID, movieId);
        context.startActivity(starter);
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putBoolean(IS_SWITCH_CHECKED, fragmentSwitch.isChecked());
    }
}
