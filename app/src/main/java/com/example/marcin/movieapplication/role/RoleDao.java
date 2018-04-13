package com.example.marcin.movieapplication.role;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.OnConflictStrategy;
import android.arch.persistence.room.Query;

import com.example.marcin.movieapplication.person.Person;

import java.util.List;

@Dao
public interface RoleDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertRole(Role... role);

    @Query("SELECT * FROM person INNER JOIN role ON person.personId=role.otherPersonId WHERE role.otherMovieId =:movieId")
    List<Person> getActorsForMovie(final int movieId);

    @Query("DELETE FROM role")
    void nukeTable();

    @Query("SELECT roleId FROM role INNER JOIN movieitem ON movieitem.movie_id = role.otherMovieId WHERE movie_id = :id")
    List<Integer> getAllIds(int id);

    @Query("DELETE FROM role WHERE roleId = :id")
    void deleteRoleWithId(int id);

    @Query("SELECT * FROM role")
    List<Role> getAllRoles();
}