package com.example.marcin.movieapplication.role;

import android.arch.persistence.room.Entity;
import android.arch.persistence.room.ForeignKey;
import android.arch.persistence.room.PrimaryKey;

import com.example.marcin.movieapplication.movie.MovieItem;
import com.example.marcin.movieapplication.person.Person;

import static android.arch.persistence.room.ForeignKey.CASCADE;

@Entity(foreignKeys = {@ForeignKey(entity = MovieItem.class,
                            parentColumns = "movie_id",
                            childColumns = "otherMovieId",
                            onDelete = CASCADE),
                        @ForeignKey(entity = Person.class,
                            parentColumns = "personId",
                            childColumns = "otherPersonId",
                            onDelete = CASCADE)
                        })
public class Role {
    @PrimaryKey(autoGenerate = true)
    private int roleId;

    private int otherMovieId;

    private int otherPersonId;

    Role(int otherMovieId, int otherPersonId) {
        this.otherMovieId = otherMovieId;
        this.otherPersonId = otherPersonId;
    }


    int getRoleId() {
        return roleId;
    }

    void setRoleId(int roleId) {
        this.roleId = roleId;
    }

    int getOtherMovieId() {
        return otherMovieId;
    }

    int getOtherPersonId() {
        return otherPersonId;
    }

    @Override
    public String toString() {
        return "role id: " + roleId + " movie id: " + otherMovieId + " actor id: " + otherPersonId;
    }

    public static Role[] populateDataRole(){
        return new Role[]{
                new Role(1, 1),
                new Role(1, 2),
                new Role(1, 3),
                new Role(1, 4),
                new Role(2, 5),
                new Role(2, 6),
                new Role(2, 7),
                new Role(3, 8),
                new Role(3, 9),
                new Role(3, 10),
                new Role(3, 11),
                new Role(4, 12),
                new Role(4, 13),
                new Role(4, 14),
                new Role(5, 8),
                new Role(5, 15),
                new Role(5, 16),
                new Role(5, 12),
                new Role(6, 17),
                new Role(6, 18),
                new Role(6, 19)
        };
    }
}
