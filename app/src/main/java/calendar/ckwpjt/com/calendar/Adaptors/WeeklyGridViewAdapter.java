package calendar.ckwpjt.com.calendar.Adaptors;

import android.app.Dialog;
import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import java.time.LocalDate;
import java.util.List;

import calendar.ckwpjt.com.calendar.Activities.MainActivity;
import calendar.ckwpjt.com.calendar.DAO.DBHelper;
import calendar.ckwpjt.com.calendar.Objects.DateInfo;
import calendar.ckwpjt.com.calendar.Objects.TaskInfo;
import calendar.ckwpjt.com.calendar.R;

public class WeeklyGridViewAdapter extends BaseAdapter {

    private Context mContext;
    private List<DateInfo> mDateInfos;
    private LocalDate mCurrentDate;

    public WeeklyGridViewAdapter(Context c){
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
            convertView = inflater.inflate(R.layout.view_part_gridview_week, parent, false);
        }

        WeeklyListViewAdapter weeklyListViewAdapter = new WeeklyListViewAdapter(convertView.getContext());

        TextView dateText = convertView.findViewById(R.id.weekly_grid_date);
        ListView tasks = convertView.findViewById(R.id.weekly_grid_listview);

        LocalDate localDate = mDateInfos.get(position).getDate();

        if(localDate != null) {

            dateText.setText(Integer.toString(localDate.getDayOfMonth()));

            if(localDate.getDayOfWeek().getValue() == 6)
                dateText.setTextColor(Color.BLUE);

            else if(localDate.getDayOfWeek().getValue() == 7)
                dateText.setTextColor(Color.RED);
        }
        else{
            dateText.setText("");
        }

        weeklyListViewAdapter.setDateInfo(mDateInfos.get(position));
        tasks.setAdapter(weeklyListViewAdapter);
        tasks.setOnItemClickListener(new AdapterView.OnItemClickListener(){

            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                final TaskInfo taskInfo = (TaskInfo) parent.getItemAtPosition(position);
                final Dialog dialog = new Dialog(mContext);
                dialog.setContentView(R.layout.dialog_show_taskinfo);

                TextView title = dialog.findViewById(R.id.dialog_value_task_title);
                TextView content = dialog.findViewById(R.id.dialog_value_task_content);

                title.setText(taskInfo.getTitle());
                content.setText(taskInfo.getContents());

                Button deleteBtn = dialog.findViewById(R.id.dialog_btn_task_delete);
                Button cancel = dialog.findViewById(R.id.dialog_btn_cancel);

                deleteBtn.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        DBHelper dbHelper = new DBHelper(mContext);
                        dbHelper.deleteTask(mCurrentDate, taskInfo);
                        dialog.dismiss();

                        ((MainActivity) mContext).notifyDataSetChanged();
                    }
                });

                cancel.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        dialog.dismiss();
                    }
                });

                dialog.show();
            }

        });

        return convertView;
    }

    public void setList(List<DateInfo> d){
        mDateInfos = d;
    }

    public void setCurrentDate(LocalDate d){
        mCurrentDate = d;
    }
}
