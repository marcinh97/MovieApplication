package com.example.marcin.movieapplication.movie;

import android.arch.persistence.db.SupportSQLiteDatabase;
import android.arch.persistence.room.Database;
import android.arch.persistence.room.Room;
import android.arch.persistence.room.RoomDatabase;
import android.content.Context;
import android.support.annotation.NonNull;

import com.example.marcin.movieapplication.person.Person;
import com.example.marcin.movieapplication.person.PersonDao;
import com.example.marcin.movieapplication.role.Role;
import com.example.marcin.movieapplication.role.RoleDao;

import java.util.concurrent.Executors;

@Database(entities = {MovieItem.class, Person.class, Role.class}, version = 3, exportSchema = false)
public abstract class MoviesDatabase extends RoomDatabase {
    public abstract MovieItemDao movieItemDao();
    public abstract PersonDao personDao();
    public abstract RoleDao roleDao();

    private static MoviesDatabase INSTANCE;
    private static final String DATABASE_NAME = "moviesDatabase";

    public synchronized static MoviesDatabase getInstance(Context context){
        if (INSTANCE == null) {
            INSTANCE = buildDatabase(context);
        }
        return INSTANCE;
    }

    private static MoviesDatabase buildDatabase(final Context context) {
        return Room.databaseBuilder(context,
                MoviesDatabase.class,
                DATABASE_NAME)
                .addCallback(new Callback() {
                    @Override
                    public void onCreate(@NonNull SupportSQLiteDatabase db) {
                        super.onCreate(db);
                        Executors.newSingleThreadScheduledExecutor().execute(new Runnable() {
                            @Override
                            public void run() {
                                getInstance(context).movieItemDao().insertMovie(MovieItem.populateDataMovieItems());
                                getInstance(context).personDao().insertPerson(Person.populateDataPerson());
                                getInstance(context).roleDao().insertRole(Role.populateDataRole());
                            }
                        });
                    }
                })
                .allowMainThreadQueries()
                .build();
    }
}
