package calendar.ckwpjt.com.calendar.Fragments;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.TextView;

import java.time.LocalDate;

import calendar.ckwpjt.com.calendar.Adaptors.DailyListViewAdapter;
import calendar.ckwpjt.com.calendar.DAO.DBHelper;
import calendar.ckwpjt.com.calendar.R;

public class DailyFragment extends CalendarFragment {
    private static final String ARG_PARAM1 = "param1";

    private int mParam1;

    public DailyFragment() {
        // Required empty public constructor
    }

    public static DailyFragment newInstance(int param1) {
        DailyFragment fragment = new DailyFragment();
        Bundle args = new Bundle();
        args.putInt(ARG_PARAM1, param1);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getInt(ARG_PARAM1);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_daily, container, false);

        TextView dateText = view.findViewById(R.id.daily_date);
        ListView tasks = view.findViewById(R.id.daily_listview);
        tasks.setOnItemClickListener(onTaskClickListener);

        DailyListViewAdapter adapter = new DailyListViewAdapter(view.getContext());
        LocalDate curDate = mDate;

        dateText.setText(Integer.toString(curDate.getDayOfMonth()));

        DBHelper dbHelper = new DBHelper(getContext());
        adapter.setmDateInfo(dbHelper.getDailyDateInfo(curDate));

        tasks.setAdapter(adapter);

        return view;
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
    }

    @Override
    public void onDetach() {
        super.onDetach();
    }

}
