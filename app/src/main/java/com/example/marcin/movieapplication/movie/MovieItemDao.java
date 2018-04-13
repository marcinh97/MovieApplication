package com.example.marcin.movieapplication.movie;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.OnConflictStrategy;
import android.arch.persistence.room.Query;

import java.util.List;


@Dao
public interface MovieItemDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertMovie(MovieItem... movie);

    @Query("SELECT * FROM movieItem")
    List<MovieItem> getAllMovies();

    @Delete
    void deleteMovie(MovieItem movie);

    @Query("SELECT * FROM movieitem WHERE movie_id = :id LIMIT 1")
    MovieItem getMovieById(int id);

    @Query("DELETE FROM movieitem")
    void nukeTable();

    @Query("SELECT * FROM movieitem WHERE movie_name = :name")
    int getMovieId(String name);

}
