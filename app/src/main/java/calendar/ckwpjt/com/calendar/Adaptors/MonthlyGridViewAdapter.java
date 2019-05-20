package calendar.ckwpjt.com.calendar.Adaptors;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.time.LocalDate;
import java.util.List;

import calendar.ckwpjt.com.calendar.Objects.DateInfo;
import calendar.ckwpjt.com.calendar.R;

public class MonthlyGridViewAdapter extends BaseAdapter {

    private Context mContext;
    private List<DateInfo> mDateInfos;
    private LocalDate mCurrentDate;

    public MonthlyGridViewAdapter(Context c){
        mContext = c;
    }

    @Override
    public int getCount() {
        return mDateInfos.size();
    }

    @Override
    public Object getItem(int position) {
        return mDateInfos.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        if(convertView == null){
            LayoutInflater inflater = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = inflater.inflate(R.layout.view_part_gridview_month, parent, false);
        }

        TextView dateText = convertView.findViewById(R.id.monthly_grid_date);

        LocalDate localDate = mDateInfos.get(position).getDate();

        if(localDate != null) {

            dateText.setText(Integer.toString(localDate.getDayOfMonth()));

            if(localDate.getDayOfWeek().getValue() == 6)
                dateText.setTextColor(Color.BLUE);

            else if(localDate.getDayOfWeek().getValue() == 7)
                dateText.setTextColor(Color.RED);

            if(mDateInfos.get(position).getTasks().size() > 0){
                ImageView imageView = convertView.findViewById(R.id.monthly_grid_image);
                imageView.setImageResource(R.drawable.blob);
            }
        }
        else{
            dateText.setText("");
        }

        return convertView;
    }

    public void setList(List<DateInfo> d){
        mDateInfos = d;
    }

    public void setmCurrentDate(LocalDate d){
        mCurrentDate = d;
    }

}
