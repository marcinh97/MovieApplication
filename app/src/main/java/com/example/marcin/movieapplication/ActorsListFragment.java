package com.example.marcin.movieapplication;

import android.app.Fragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListAdapter;
import android.widget.ListView;

import com.example.marcin.movieapplication.movie.MoviesDatabase;
import com.example.marcin.movieapplication.person.Person;
import com.example.marcin.movieapplication.person.PersonAdapter;

import java.util.List;


public class ActorsListFragment extends Fragment {
    private MoviesDatabase database;
    private View view;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        inflateLayout(inflater, container);
        initDatabase();
        List<Person> personList = getPersonListFromDatabase(getArguments());
        setAdapter(personList);
        return view;
    }

    private void inflateLayout(LayoutInflater inflater, @Nullable ViewGroup container) {
        if (view == null){
            view = inflater.inflate(R.layout.actors_list, container, false);
        }
        else{
            ViewGroup parent = (ViewGroup) view.getParent();
            parent.removeView(view);
        }
    }

    private void initDatabase() {
        database = MoviesDatabase.getInstance(getActivity().getApplicationContext());
    }

    private List<Person> getPersonListFromDatabase(Bundle bundle){
        int movieId = getMovieId(bundle);
        return database.roleDao().getActorsForMovie(movieId);
    }

    private int getMovieId(Bundle bundle) {
        String movieName = bundle.getString(MovieInfoActivity.MOVIE_NAME);
        return database.movieItemDao().getMovieId(movieName);
    }

    private void setAdapter(List<Person> personList) {
        ListAdapter adapter = new PersonAdapter(personList, getActivity());
        ListView listView = view.findViewById(R.id.actors_listView);
        listView.setAdapter(adapter);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
    }
}
