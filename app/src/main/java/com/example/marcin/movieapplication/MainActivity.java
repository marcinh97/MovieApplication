package com.example.marcin.movieapplication;

import android.graphics.Canvas;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.helper.ItemTouchHelper;
import android.view.View;
import android.widget.TextView;

import com.example.marcin.movieapplication.movie.MovieItem;
import com.example.marcin.movieapplication.movie.MoviesDatabase;

import net.danlew.android.joda.JodaTimeAndroid;

import java.util.ArrayList;
import java.util.List;

import static android.support.v7.widget.helper.ItemTouchHelper.ACTION_STATE_SWIPE;

public class MainActivity extends AppCompatActivity {

    private RecyclerView recyclerView;
    private List<MovieItem> movies = new ArrayList<>();
    private MovieAdapter adapter;
    private MoviesDatabase database;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        JodaTimeAndroid.init(this);

        initViews();
        initDatabase();
        getMoviesFromDatabase();
        setAdapter();
        setSwipeDelete();
    }

    private void initViews() {
        recyclerView = findViewById(R.id.main_recyclerView);
    }

    private void initDatabase() {
        database = MoviesDatabase.getInstance(getApplicationContext());
    }

    private void getMoviesFromDatabase() {
        movies = database.movieItemDao().getAllMovies();
    }

    private void setAdapter() {
        adapter = new MovieAdapter(this, movies, recyclerView);
        setLayoutForRecyclerView();
        recyclerView.setAdapter(adapter);
    }

    private void setLayoutForRecyclerView() {
        RecyclerView.LayoutManager recyclerLayoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(recyclerLayoutManager);
        recyclerView.setHasFixedSize(true);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.addItemDecoration(new DividerItemDecoration(this, DividerItemDecoration.VERTICAL));
    }

    private void setSwipeDelete() {
        ItemTouchHelper.SimpleCallback simpleTouchCallback = new ItemTouchHelper.SimpleCallback(0,
                ItemTouchHelper.LEFT | ItemTouchHelper.RIGHT) {
            private Drawable background;
            private Drawable deleteIcon;
            private TextView deleteTextView;
            private int iconMargin;
            private boolean isInitialized;

            private void initialize(){
                background = new ColorDrawable(getResources().getColor(R.color.swipeBackground));
                deleteIcon = ContextCompat.getDrawable(getApplicationContext(), android.R.drawable.ic_menu_delete);
                iconMargin = (int) getApplicationContext().getResources().getDimension(R.dimen.swipe_delete_icon_margin);
                deleteTextView = new TextView(getApplicationContext());
                deleteTextView.setText(R.string.delete);
                isInitialized = true;
            }

            @Override
            public boolean onMove(RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder, RecyclerView.ViewHolder target) {
                return false;
            }

            @Override
            public void onSwiped(RecyclerView.ViewHolder viewHolder, int direction) {
                int position = viewHolder.getAdapterPosition();
                movies.remove(position);
                adapter.notifyDataSetChanged();
            }

            @Override
            public void onChildDraw(Canvas c, RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder, float dX, float dY, int actionState, boolean isCurrentlyActive) {
                if (actionState == ACTION_STATE_SWIPE) {
                    View itemView = viewHolder.itemView;
                    if (!isInitialized) {
                        initialize();
                    }
                    setBackgroundBounds(itemView, dX);
                    background.draw(c);
                    if (dX > 0) {
                        setIconBounds(itemView, dX);
                        deleteIcon.draw(c);
                    }
                }
                super.onChildDraw(c, recyclerView, viewHolder, dX, dY, actionState, isCurrentlyActive);
            }

            private void setBackgroundBounds(View itemView, float dX){
                int left = dX > 0 ? itemView.getLeft() : itemView.getRight() + (int)dX;
                int right = dX > 0 ? itemView.getLeft() + (int)dX : itemView.getRight();
                background.setBounds(left, itemView.getTop(), right, itemView.getBottom());
            }

            private void setIconBounds(View itemView, float dX){
                int iconHeight = deleteIcon.getIntrinsicHeight();
                int iconWidth = deleteIcon.getIntrinsicWidth();
                int top = (itemView.getTop() + itemView.getBottom() - iconHeight) / 2;
                int left = dX > 0 ? itemView.getLeft() + iconMargin : itemView.getRight() - iconMargin - iconWidth;
                int right = dX > 0 ? itemView.getLeft() + iconMargin + iconWidth : itemView.getRight() - iconMargin;
                int bottom =  top + iconHeight;
                deleteIcon.setBounds(left, top, right, bottom);
            }
        };
        ItemTouchHelper itemTouchHelper = new ItemTouchHelper(simpleTouchCallback);
        itemTouchHelper.attachToRecyclerView(recyclerView);
    }
}
