package calendar.ckwpjt.com.calendar.Fragments;

import android.app.Dialog;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.TextView;

import java.time.LocalDate;

import calendar.ckwpjt.com.calendar.Activities.MainActivity;
import calendar.ckwpjt.com.calendar.DAO.DBHelper;
import calendar.ckwpjt.com.calendar.Objects.DateInfo;
import calendar.ckwpjt.com.calendar.Objects.TaskInfo;
import calendar.ckwpjt.com.calendar.R;

public class CalendarFragment extends android.support.v4.app.Fragment {

    protected OnFragmentListener mOnFragmentListener;
    protected LocalDate mDate;
    protected final String[] mDaysOfWeek = new String[]{
        "일","월","화","수","목","금","토"
    };

    protected AdapterView.OnItemClickListener onTaskClickListener = new AdapterView.OnItemClickListener(){

        @Override
        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
            final TaskInfo taskInfo = (TaskInfo) parent.getItemAtPosition(position);
            final Dialog dialog = new Dialog(getActivity());
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
                    DBHelper dbHelper = new DBHelper(getContext());
                    dbHelper.deleteTask(mDate, taskInfo);
                    dialog.dismiss();

                    ((MainActivity)getActivity()).notifyDataSetChanged();
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

    };

    protected AdapterView.OnItemClickListener onDateClickListener = new AdapterView.OnItemClickListener() {
        @Override
        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
            DateInfo dateInfo = (DateInfo) parent.getItemAtPosition(position);
            MainActivity mainActivity = ((MainActivity) getActivity());

            mainActivity.setLocalDate(dateInfo.getDate());

            mainActivity.setNavigationItem(R.id.navigation_daily);
        }
    };

    public interface OnFragmentListener{
        void onFragmentListener(View view);
    }

    public void setmOnFragmentListener(CalendarFragment.OnFragmentListener mOnFragmentListener) {
        this.mOnFragmentListener = mOnFragmentListener;
    }

    public void setTime(LocalDate date){
        this.mDate = date;
    }

    public LocalDate getTime(){
        return mDate;
    }

}
