package be.thomasmore.repscore;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import org.w3c.dom.Text;

import java.util.ArrayList;

public class CustomAdapter extends BaseAdapter {

    private Context context;
    private ArrayList<Workout> workoutArrayList;

    public CustomAdapter(Context context, ArrayList<Workout> workoutArrayList) {

        this.context = context;
        this.workoutArrayList = workoutArrayList;
    }


    @Override
    public int getCount() {
        return workoutArrayList.size();
    }

    @Override
    public Object getItem(int position) {
        return workoutArrayList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder;

        if (convertView == null) {
            holder = new ViewHolder();
            LayoutInflater inflater = (LayoutInflater) context
                    .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = inflater.inflate(R.layout.workout_item, null, true);



            holder.tvCompound = (TextView) convertView.findViewById(R.id.compound);
            holder.tvWeight = (TextView) convertView.findViewById(R.id.weight);
            holder.tvDate = (TextView) convertView.findViewById(R.id.date);



            convertView.setTag(holder);
        }else {
            holder = (ViewHolder)convertView.getTag();
        }



        holder.tvCompound.setText(workoutArrayList.get(position).getCoumpound());
        holder.tvDate.setText( "On " +workoutArrayList.get(position).getDate());
        holder.tvWeight.setText("your 1 RM was " +workoutArrayList.get(position).getWeight() + " kg.");



        return convertView;
    }

    private class ViewHolder {

        protected TextView tvCompound,tvWeight, tvDate;
    }

}