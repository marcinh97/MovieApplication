package com.example.marcin.movieapplication;

import android.annotation.SuppressLint;
import android.app.Fragment;
import android.content.Context;
import android.content.res.Configuration;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.DisplayMetrics;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.PopupWindow;
import static android.content.Context.LAYOUT_INFLATER_SERVICE;

import com.example.marcin.movieapplication.converters.MovieToImageConverter;

import java.util.ArrayList;
import java.util.List;

public class ScreenshotListFragment extends Fragment {
    private static final int NUMBER_OF_SCREENSHOTS = 4;
    private View view;
    private GridView gridView;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        inflateLayout(inflater, container);
        return view;
    }

    private void inflateLayout(LayoutInflater inflater, @Nullable ViewGroup container) {
        if (view == null){
            view = inflater.inflate(R.layout.screenshots_list, container, false);
        }
        else{
            ViewGroup parent = (ViewGroup) view.getParent();
            parent.removeView(view);
        }
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        Bundle bundle = getArguments();
        if (bundle != null){
            initViews();
            String name = bundle.getString(MovieInfoActivity.MOVIE_NAME);
            Context context = getActivity().getApplicationContext();
            MovieToImageConverter converter = new MovieToImageConverter(name, context);
            List<Integer> imageIds = getAllImageIds(converter);
            setAdapter(context, imageIds);
        }
    }

    private void initViews() {
        gridView = getActivity().findViewById(R.id.image_gridView);
    }

    @NonNull
    private List<Integer> getAllImageIds(MovieToImageConverter converter) {
        List<Integer> imageIds = new ArrayList<>();
        for (int i=0; i<NUMBER_OF_SCREENSHOTS; i++){
            int imageViewId = converter.getDefaultResourceOfImageWithNumber(i+1);
            imageIds.add(imageViewId);
        }
        return imageIds;
    }

    private void setAdapter(Context context, List<Integer> imageIds) {
        ImageAdapter imageAdapter = new ImageAdapter(context, imageIds);
        gridView.setAdapter(imageAdapter);
    }

    public void showPopup(View anchorView, ImageView imageView){
        View popupView = getViewForPopupLayout();
        int orientation = getOrientation();
        final int bottomPositionOfImageCenter = isOrientationLandscape(orientation) ?
                (int)(getResources().getDimension(R.dimen.bottom_position_of_image_center)) : 0;
        final double POPUP_WINDOW_RATIO = isOrientationLandscape(orientation) ? 0.7 : 0.9;

        DisplayMetrics displayMetrics = getDisplayMetrics();

        PopupWindow popupWindow = new PopupWindow(popupView, (int)(POPUP_WINDOW_RATIO *displayMetrics.widthPixels),
                (int)(POPUP_WINDOW_RATIO *displayMetrics.heightPixels), true);

        setPopupImage(imageView, popupView);

        popupWindow.setFocusable(true);

        showPopupAtLocation(anchorView, bottomPositionOfImageCenter, popupWindow);

        allowUserToDismissPopupByClicking((ViewGroup)popupView, popupWindow);
    }

    @SuppressLint("InflateParams")
    private View getViewForPopupLayout() {
        return getActivity().getLayoutInflater().inflate(R.layout.image_popup, null);
    }

    private boolean isOrientationLandscape(int orientation) {
        return orientation == Configuration.ORIENTATION_LANDSCAPE;
    }

    private int getOrientation() {
        return getResources().getConfiguration().orientation;
    }

    @NonNull
    private DisplayMetrics getDisplayMetrics() {
        DisplayMetrics displayMetrics = new DisplayMetrics();
        getActivity().getWindowManager().getDefaultDisplay().getMetrics(displayMetrics);
        return displayMetrics;
    }

    private void setPopupImage(ImageView imageView, View popupView) {
        ImageView movieImage = popupView.findViewById(R.id.popup_image);
        movieImage.setImageDrawable(imageView.getDrawable());
    }

    private void showPopupAtLocation(View anchorView, int bottomPositionOfImageCenter, PopupWindow popupWindow) {
        int location[] = new int[2];
        anchorView.getLocationOnScreen(location);
        popupWindow.showAtLocation(anchorView, Gravity.CENTER, 0, bottomPositionOfImageCenter);
    }

    private void allowUserToDismissPopupByClicking(ViewGroup container, final PopupWindow imagePopup) {
        assert container != null;
        container.setOnTouchListener(new View.OnTouchListener() {
            @SuppressLint("ClickableViewAccessibility")
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {
                imagePopup.dismiss();
                return true;
            }
        });
    }

    public class ImageAdapter extends BaseAdapter {
        private Context context;
        private List<Integer> imageIds;

        ImageAdapter(Context context, List<Integer> imageIds) {
            this.context = context;
            this.imageIds = imageIds;
        }

        @Override
        public int getCount() {
            return imageIds.size();
        }

        @Override
        public Object getItem(int i) {
            return imageIds.get(i);
        }

        @Override
        public long getItemId(int i) {
            return 0;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup viewGroup) {
            int imageId = imageIds.get(position);
            View view = convertView;
            view = inflateLayout(view);
            final ImageView profileImage = view.findViewById(R.id.movie_screen);
            profileImage.setImageResource(imageId);
            view.setOnLongClickListener(new View.OnLongClickListener() {
                @Override
                public boolean onLongClick(View view) {
                    showPopup(view, profileImage);
                    return true;
                }
            });
            return view;
        }

        @SuppressLint("InflateParams")
        private View inflateLayout(View view) {
            if (view == null){
                LayoutInflater inflater = (LayoutInflater) context.getSystemService(LAYOUT_INFLATER_SERVICE);
                assert inflater != null;
                view = inflater.inflate(R.layout.image_item, null);
            }
            return view;
        }

        @Nullable
        @Override
        public CharSequence[] getAutofillOptions() {
            return new CharSequence[0];
        }
    }
}
