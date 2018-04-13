package com.example.marcin.movieapplication.converters;

import android.content.Context;

public class MovieToImageConverter {
    private String movieName;
    private Context context;
    private static final String POSTER_SUFFIX = "_poster";
    private static final String SCREENSHOT_SUFFIX = "_screen";
    private static final String RES_FOLDER_OF_IMAGES = "drawable";

    public MovieToImageConverter(String movieName, Context context){
        this.movieName = movieName;
        this.context = context;
    }
    private String replaceSpaces(String string){
        return string.replace(" ", "");
    }
    private String getPosterImageName(){
        return replaceSpaces(movieName).toLowerCase()+POSTER_SUFFIX;
    }

    private String getImageNameWithNumber(int num){
        return replaceSpaces(movieName).toLowerCase()+SCREENSHOT_SUFFIX+Integer.toString(num);
    }

    public int getDefaultResourceOfPosterImage(){
        return getIdentifier(getPosterImageName(), RES_FOLDER_OF_IMAGES);
    }

    public int getDefaultResourceOfImageWithNumber(int number){
        return getIdentifier(getImageNameWithNumber(number), RES_FOLDER_OF_IMAGES);
    }

    private int getIdentifier(String name, String defType){
        return context.getResources().getIdentifier(name, defType, context.getPackageName());
    }
}
