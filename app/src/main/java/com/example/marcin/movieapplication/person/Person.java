package com.example.marcin.movieapplication.person;

import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;

import org.joda.time.DateTime;
import org.joda.time.Period;

import java.util.Calendar;

@Entity
public class Person {
    @PrimaryKey(autoGenerate = true)
    private int personId;

    @ColumnInfo(name= "first_name")
    private String firstName;

    @ColumnInfo(name = "surname")
    private String surname;

    @ColumnInfo(name = "birthday")
    private long timeSinceBirthday;

    public Person(String firstName, String surname, long timeSinceBirthday) {
        this.firstName = firstName;
        this.surname = surname;
        this.timeSinceBirthday = timeSinceBirthday;
    }

    public Person(String firstName, String surname, int year, int month, int day){
        this.firstName = firstName;
        this.surname = surname;
        Calendar calendar = Calendar.getInstance();
        calendar.set(year, month, day);
        timeSinceBirthday = calendar.getTime().getTime();
    }

    public Person(int personId, String firstName, String surname, int year, int month, int day){
        this.personId = personId;
        this.firstName = firstName;
        this.surname = surname;
        Calendar calendar = Calendar.getInstance();
        calendar.set(year, month, day);
        timeSinceBirthday = calendar.getTime().getTime();
    }

    String getFirstName() {
        return firstName;
    }

    String getSurname() {
        return surname;
    }

    long getTimeSinceBirthday() {
        return timeSinceBirthday;
    }

    int getPersonId() {
        return personId;
    }

    public void setPersonId(int personId) {
        this.personId = personId;
    }

    @Override
    public String toString() {
        DateTime birthday = new DateTime(timeSinceBirthday);
        DateTime currentDate = new DateTime();
        Period period = new Period(birthday, currentDate);
        return firstName + " " + surname + "\n" + period.getYears() + " lat";
    }

    int getCurrentAge(){
        DateTime birthday = new DateTime(timeSinceBirthday);
        DateTime currentDate = new DateTime();
        Period period = new Period(birthday, currentDate);
        return period.getYears();
    }

    public static Person[] populateDataPerson(){
        return new Person[]{
                new Person(1, "Ryan", "Gosling", 1980, 11, 12),
                new Person(2, "Harrison", "Ford", 1942, 7, 13),
                new Person(3, "Jared", "Leto", 1971, 12, 26),
                new Person(4, "Ana", "de Armas", 1988, 4, 30),

                new Person(5, "Macaulay", "Culkin", 1980, 8, 26),
                new Person(6, "Joe", "Pesci", 1943, 2, 9),
                new Person(7, "Daniel", "Stern", 1957, 8, 28),

                new Person(8, "Leonardo", "diCaprio", 1974, 11, 11),
                new Person(9, "Ellen", "Page", 1987, 2, 21),
                new Person(10,"Tom", "Hardy", 1977, 9, 15),
                new Person(11, "Cillian", "Murphy", 1976, 5, 25),

                new Person(12, "Matthew", "McConaughey", 1969, 11, 4),
                new Person(13, "Jessica", "Chastain", 1977, 3, 24),
                new Person(14,"Anne", "Hathaway", 1982, 11, 12),

                new Person(15,"Jonah", "Hill", 1983, 12, 20),
                new Person(16,"Margot", "Robbie", 1990, 7, 2),

                new Person(17,"Meryl", "Streep", 1949, 6, 22),
                new Person(18,"Hugh", "Grant", 1960, 9, 9),
                new Person(19,"Simon", "Helberg", 1980, 12, 9),
        };
    }


}
