package com.example.marcin.movieapplication.converters;

import android.content.Context;

public class ActorToImageConverter {
    private String actorName;
    private Context context;

    public ActorToImageConverter(String actorName, Context context) {
        this.actorName = actorName;
        this.context = context;
    }

    private String getConvertedActorName(){
        return actorName.replace(" ", "").toLowerCase();
    }

    public int getActorImageId(){
        return context.getResources().getIdentifier(getConvertedActorName(), "drawable",
                context.getPackageName());
    }
}
