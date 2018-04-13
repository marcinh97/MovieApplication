package com.example.marcin.movieapplication;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.marcin.movieapplication.converters.MovieToDescriptionConverter;
import com.example.marcin.movieapplication.converters.MovieToImageConverter;
import com.example.marcin.movieapplication.movie.MovieItem;

import java.util.List;

public class MovieAdapter extends RecyclerView.Adapter<MovieAdapter.ViewHolder> {

    private Context context;
    private List<MovieItem> movies;
    private MovieItem movieItem;
    private RecyclerView recyclerView;

    private ImageView movieImage;
    private TextView movieName;
    private TextView movieDescription;
    private TextView categoryName;


    MovieAdapter(Context context, List<MovieItem> movies, RecyclerView recyclerView) {
        this.context = context;
        this.movies = movies;
        this.recyclerView = recyclerView;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        getAdditionalInfoAboutMovie(position);
        initViewsFromViewHolder(holder);
        setInfoAboutMovie();
    }

    private void getAdditionalInfoAboutMovie(int position) {
        movieItem = movies.get(position);
    }

    private void initViewsFromViewHolder(ViewHolder holder) {
        movieImage = holder.image;
        movieName = holder.movieName;
        categoryName = holder.movieCategory;
        movieDescription = holder.movieDescription;
    }


    private void setInfoAboutMovie() {
        MovieToDescriptionConverter descriptionConverter =
                new MovieToDescriptionConverter(movieItem.getMovieName(), context);
        setMovieDescription(descriptionConverter);
        setMovieName(descriptionConverter);
        setCategoryName();
        setMovieImage();
    }

    private void setMovieDescription(MovieToDescriptionConverter descriptionConverter) {
        int descriptionId = descriptionConverter.getMovieDescriptionId();
        if (descriptionId != 0){
            movieDescription.setText(context.getText(descriptionId));
        }
    }

    private void setMovieName(MovieToDescriptionConverter descriptionConverter) {
        int movieTitleId = descriptionConverter.getMovieTitleId();
        if (movieTitleId != 0){
            movieName.setText(context.getText(movieTitleId));
        }
    }

    private void setCategoryName() {
        int categoryId = getCategoryId();
        String category = context.getText(categoryId).toString();
        categoryName.setText(category);
    }

    private int getCategoryId() {
        String category = movieItem.getCategory();
        return context.getResources().getIdentifier(category, "string", context.getPackageName());
    }

    private void setMovieImage() {
        int imageResource = getImageResource();
        movieImage.setImageResource(imageResource);
    }

    private int getImageResource() {
        String name = movieItem.getMovieName();
        MovieToImageConverter converter = new MovieToImageConverter(name, context);
        return converter.getDefaultResourceOfPosterImage();
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = getView(parent);
        view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startNextActivity(view);
            }
        });
        return new ViewHolder(view);
    }

    private View getView(ViewGroup parent) {
        LayoutInflater inflater = LayoutInflater.from(context);
        return inflater.inflate(R.layout.movie_item, parent, false);
    }

    private void startNextActivity(View view) {
        int movieId = getMovieId(view);
        MovieInfoActivity.start(view.getContext(), movieId);
    }

    private int getMovieId(View view) {
        int movieId = recyclerView.getChildAdapterPosition(view);
        movieItem = movies.get(movieId);
        return movieItem.getMovieId();
    }



    @Override
    public int getItemCount() {
        return movies.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder {
        ImageView image;
        TextView movieName;
        TextView movieDescription;
        TextView movieCategory;

        ViewHolder(View itemView) {
            super(itemView);
            initViews(itemView);
        }

        private void initViews(View itemView) {
            image = itemView.findViewById(R.id.thumbnail);
            movieName = itemView.findViewById(R.id.name);
            movieDescription = itemView.findViewById(R.id.description);
            movieCategory = itemView.findViewById(R.id.category_textView);
        }


    }
}
