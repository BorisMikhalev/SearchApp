package com.test.searchapp.adapters;


import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import android.support.annotation.NonNull;

import com.test.searchapp.R;
import com.test.searchapp.model.Result;
import com.test.searchapp.utils.ImageLoader;
import com.test.searchapp.view.PhotoViewer;

import java.util.ArrayList;
import java.util.List;

public class SearchArrayAdapter extends ArrayAdapter<Result> {
    private final Context context;
    private List<Result> data = new ArrayList<>();

    public SearchArrayAdapter(Context context, List<Result> data) {
        super(context, 0, data);
        this.context = context;
        this.data = data;
    }

    @NonNull
    @Override
    public View getView(int position, View convertView, @NonNull ViewGroup parent) {
        LayoutInflater inflater = (LayoutInflater) context
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View rowView;
        if(position % 2 == 0){
            rowView = inflater.inflate(R.layout.left_result_card, parent, false);
            setData(rowView, position);
        }else{
           rowView = inflater.inflate(R.layout.right_result_card, parent, false);
            setData(rowView, position);
        }
        return rowView;
    }

    private void setData(View rowView, int position){
        TextView title = (TextView) rowView.findViewById(R.id.title);
        TextView description = (TextView) rowView.findViewById(R.id.description);
        ImageView logo = (ImageView) rowView.findViewById(R.id.logo);

        title.setText(data.get(position).getTitle());
        description.setText(data.get(position).getDescription());
        final String photo_url = data.get(position).getPhotoUrl();
        new ImageLoader(logo).execute(photo_url);

        logo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, PhotoViewer.class);
                intent.putExtra("photo_url",photo_url);
                context.startActivity(intent);
            }
        });
    }

    @Override
    public int getCount() {
        return data.size();
    }

}
