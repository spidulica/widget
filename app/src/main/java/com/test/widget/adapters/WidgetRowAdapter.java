package com.test.widget.adapters;

import android.content.Context;
import android.support.annotation.LayoutRes;
import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.test.widget.R;
import com.test.widget.entities.Interval;

import java.util.List;

/**
 * Created by bucur on 3/26/2017.
 */

public class WidgetRowAdapter extends ArrayAdapter<Interval> {

    private List<Interval> intervals;
    private Context context;

    public WidgetRowAdapter(@NonNull Context context, @LayoutRes int resource, @NonNull List<Interval> objects) {
        super(context, resource, objects);
        this.context = context;
        this.intervals = objects;
    }

    @NonNull
    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        LayoutInflater inflater = (LayoutInflater) context
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View rowView = inflater.inflate(R.layout.widget_row, viewGroup, false);
        IntervalHolder intervalHolder = new IntervalHolder(rowView);
        intervalHolder.profesor.setText(getItem(i).getProfesor());
        intervalHolder.materie.setText(getItem(i).getMaterie());
        intervalHolder.interval.setText(getItem(i).getOraInceput() + " - " + getItem(i).getOraInceput());
        intervalHolder.locatie.setText(getItem(i).getLocatie());
        return rowView;
    }
    class IntervalHolder {
        private final TextView interval;
        private final TextView materie;
        private final TextView profesor;
        private final TextView locatie;

        public IntervalHolder(View itemView) {
            this.interval = (TextView) itemView.findViewById(R.id.interval);
            this.materie = (TextView) itemView.findViewById(R.id.materie);
            this.profesor = (TextView) itemView.findViewById(R.id.profesor);
            this.locatie = (TextView) itemView.findViewById(R.id.locatie);
        }
    }
}


