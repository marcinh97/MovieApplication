package com.example.marcin.movieapplication.movie;

import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;

@Entity
public class MovieItem{
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "movie_id")
    private int movieId;

    @ColumnInfo(name = "movie_name")
    private String movieName;

    @ColumnInfo(name = "category")
    private String category;

    public enum Category{

        DRAMA("drama"), COMEDY("comedy"), THRILLER("thriller"),
        HORROR("horror"), SCIFI("sci_fi"), MUSICAL("musical");

        private String name;
        Category(String name){
            this.name = name;
        }

        public String getName(){
            return name;
        }

    }

    MovieItem(String movieName, String category) {
        this.movieName = movieName;
        this.category = category;
    }

    public int getMovieId() {
        return movieId;
    }

    void setMovieId(int movieId) {
        this.movieId = movieId;
    }

    public String getMovieName() {
        return movieName;
    }

    public String getCategory() {
        return category;
    }

    @Override
    public String toString() {
        return "MovieItem{" +
                "movieId=" + movieId +
                ", movieName='" + movieName + '\'' +
                '}';
    }

    static MovieItem[] populateDataMovieItems(){
        return new MovieItem[]{
                new MovieItem("Blade Runner", Category.SCIFI.getName()),
                new MovieItem("Home Alone", Category.COMEDY.getName()),
                new MovieItem("Inception", Category.SCIFI.getName()),
                new MovieItem("Interstellar", Category.SCIFI.getName()),
                new MovieItem("Wolf of Wall Street", Category.DRAMA.getName()),
                new MovieItem("Florence Foster Jenkins", Category.COMEDY.getName())
        };
    }
}
