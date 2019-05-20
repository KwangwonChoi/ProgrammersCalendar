package calendar.ckwpjt.com.calendar.Adaptors;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import calendar.ckwpjt.com.calendar.Objects.DateInfo;
import calendar.ckwpjt.com.calendar.R;

public class WeeklyListViewAdapter extends BaseAdapter {

    private Context mContext;
    private DateInfo mDateInfo;

    public WeeklyListViewAdapter(Context c){
        mContext = c;
    }

    @Override
    public int getCount() {
        return mDateInfo.getTasks().size();
    }

    @Override
    public Object getItem(int position) {
        return mDateInfo.getTasks().get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        if(convertView == null){
            LayoutInflater inflater = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = inflater.inflate(R.layout.view_part_listview_week, parent, false);
        }

        TextView taskText = convertView.findViewById(R.id.daily_list_task);

        taskText.setText(mDateInfo.getTasks().get(position).getTitle());

        return convertView;
    }

    public void setDateInfo(DateInfo d){
        mDateInfo = d;
    }
}