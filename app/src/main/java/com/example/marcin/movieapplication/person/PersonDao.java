package com.example.marcin.movieapplication.person;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.OnConflictStrategy;
import android.arch.persistence.room.Query;

import java.util.List;

@Dao
public interface PersonDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertPerson(Person... person);

    @Query("SELECT * FROM person")
    List<Person> getAllSavedPersons();

    @Delete
    void delete(Person person);

    @Query("DELETE FROM person")
    void nukeTable();

    @Query("SELECT personId FROM person WHERE first_name = :firstName AND surname = :lastName")
    int getPersonWithNameSurname(String firstName, String lastName);

    @Query("SELECT * FROM person WHERE personId = :id LIMIT 1")
    Person getPersonById(int id);
}
