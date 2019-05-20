package calendar.ckwpjt.com.calendar.Fragments;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.GridView;

import java.time.LocalDate;
import java.util.List;

import calendar.ckwpjt.com.calendar.Adaptors.MonthlyGridViewAdapter;
import calendar.ckwpjt.com.calendar.DAO.DBHelper;
import calendar.ckwpjt.com.calendar.Objects.DateInfo;
import calendar.ckwpjt.com.calendar.R;


public class MonthlyFragment extends CalendarFragment {
    private static final String ARG_PARAM1 = "param1";

    private int mParam1;

    public MonthlyFragment() {
        // Required empty public constructor
    }

    public static MonthlyFragment newInstance(int param1) {
        MonthlyFragment fragment = new MonthlyFragment();
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
        View view = inflater.inflate(R.layout.fragment_monthly, container, false);

        GridView dayOfWeek = view.findViewById(R.id.monthly_day_of_week);
        GridView calendar = view.findViewById(R.id.monthly_gridview);

        ArrayAdapter<String> daysAdapter = new ArrayAdapter<String>(getContext(), android.R.layout.simple_list_item_1, mDaysOfWeek);
        MonthlyGridViewAdapter adapter = new MonthlyGridViewAdapter(view.getContext());


        DBHelper dbHelper = new DBHelper(getContext());
        LocalDate startDay = mDate;
        List<DateInfo> dateInfos = dbHelper.getMonthlyDateInfo(startDay);

        adapter.setList(dateInfos);
        adapter.setmCurrentDate(startDay);
        calendar.setAdapter(adapter);
        dayOfWeek.setAdapter(daysAdapter);

        calendar.setOnItemClickListener(onDateClickListener);

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
