package com.example.marcin.movieapplication.converters;

import android.content.Context;

public class MovieToDescriptionConverter {
    private String movieName;
    private Context context;
    private static final String TITLE_SUFFIX = "_title";
    private static final String DESCRIPTION_SUFFIX = "_description";
    public MovieToDescriptionConverter(String movieName, Context context){
        this.movieName = movieName;
        this.context = context;
    }
    private String getConvertedMovieTitle(){
        return movieName.replace(" ", "").toLowerCase();
    }
    public int getMovieTitleId(){
        String title = getConvertedMovieTitle()+TITLE_SUFFIX;
        return context.getResources().getIdentifier(title, "string", context.getPackageName());
    }
    public int getMovieDescriptionId(){
        String title = getConvertedMovieTitle()+DESCRIPTION_SUFFIX;
        return context.getResources().getIdentifier(title, "string", context.getPackageName());
    }

}
