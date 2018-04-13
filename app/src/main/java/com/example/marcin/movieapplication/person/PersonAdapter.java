package com.example.marcin.movieapplication.person;

import android.annotation.SuppressLint;
import android.content.Context;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ListAdapter;
import android.widget.TextView;

import com.example.marcin.movieapplication.R;
import com.example.marcin.movieapplication.converters.ActorToImageConverter;

import java.util.List;

public class PersonAdapter extends BaseAdapter implements ListAdapter {
    private List<Person> list;
    private Context context;

    public PersonAdapter(List<Person> list, Context context) {
        this.list = list;
        this.context = context;
    }

    @Override
    public int getCount() {
        return list.size();
    }

    @Override
    public Object getItem(int position) {
        return list.get(position);
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @SuppressLint({"InflateParams", "SetTextI18n"})
    @Override
    public View getView(int position, View convertView, ViewGroup viewGroup) {
        final Person person = list.get(position);
        View view = convertView;
        if (view == null){
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            assert inflater != null;
            view = inflater.inflate(R.layout.actor_item, null);
        }
        TextView actorName = view.findViewById(R.id.actor_name);
        final String nameSurname = person.getFirstName() + " " + person.getSurname();
        actorName.setText(nameSurname);

        TextView actorAge = view.findViewById(R.id.actor_age);
        final int age = person.getCurrentAge();
        actorAge.setText(Integer.toString(age));

        ImageView profileImage = view.findViewById(R.id.actor_image);
        ActorToImageConverter converter = new ActorToImageConverter(nameSurname, context);

        profileImage.setImageResource(converter.getActorImageId());
        return view;
    }

    @Nullable
    @Override
    public CharSequence[] getAutofillOptions() {
        return new CharSequence[0];
    }
}
