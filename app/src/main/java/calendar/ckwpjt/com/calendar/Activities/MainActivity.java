package calendar.ckwpjt.com.calendar.Activities;

import android.animation.ValueAnimator;
import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.FragmentManager;
import android.support.v4.view.ViewPager;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.DatePicker;
import android.widget.TextView;

import java.time.LocalDate;

import calendar.ckwpjt.com.calendar.Adaptors.ViewPagerAdapters.CalendarAdaper;
import calendar.ckwpjt.com.calendar.Adaptors.ViewPagerAdapters.DailyPagerAdapter;
import calendar.ckwpjt.com.calendar.Adaptors.ViewPagerAdapters.MonthlyPagerAdapter;
import calendar.ckwpjt.com.calendar.Adaptors.ViewPagerAdapters.WeeklyPagerAdapter;
import calendar.ckwpjt.com.calendar.Fragments.CalendarFragment;
import calendar.ckwpjt.com.calendar.Objects.DateInfo;
import calendar.ckwpjt.com.calendar.R;

public class MainActivity extends AppCompatActivity implements CalendarFragment.OnFragmentListener{

    private static final int COUNT_PAGE = 12;
    private FragmentManager mFragmentManager;
    private CalendarAdaper mPagerAdapter;
    private ViewPager mViewPager;
    private LocalDate mDatePointer;
    private BottomNavigationView mNavigation;
    private FloatingActionButton mFloatingActionBtn;
    private boolean mIsNavigationSelectedByUser;

    private ViewPager.OnPageChangeListener PageChangeListener = new ViewPager.OnPageChangeListener() {
        @Override
        public void onPageScrolled(int i, float v, int i1) {

        }

        @Override
        public void onPageSelected(int position) {
            mDatePointer = mPagerAdapter.getLocalDate(position);
            setActionBarTitle(DateInfo.getYearMonthString(mDatePointer));

            if (position == 0) {
                mPagerAdapter.addPrev();
                mViewPager.setCurrentItem(COUNT_PAGE, false);
            } else if (position == mPagerAdapter.getCount() - 1) {
                mPagerAdapter.addNext();
                mViewPager.setCurrentItem(mPagerAdapter.getCount() - (COUNT_PAGE + 1), false);
            }
        }

        @Override
        public void onPageScrollStateChanged(int i) {

        }
    };

    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener  = new BottomNavigationView.OnNavigationItemSelectedListener() {

        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {

            boolean retValue = false;

            switch (item.getItemId()) {
                case R.id.navigation_monthly:
                    mPagerAdapter = new MonthlyPagerAdapter(mFragmentManager);
                    mFloatingActionBtn.hide();
                    retValue = true;
                    break;
                case R.id.navigation_weekly:

                    mPagerAdapter = new WeeklyPagerAdapter(mFragmentManager);
                    mFloatingActionBtn.hide();
                    retValue = true;
                    break;
                case R.id.navigation_daily:
                    mPagerAdapter = new DailyPagerAdapter(mFragmentManager);
                    mFloatingActionBtn.show();
                    retValue = true;
                    break;
            }

            mDatePointer = mIsNavigationSelectedByUser ? LocalDate.now() : mDatePointer;
            mViewPager.setAdapter(mPagerAdapter);
            mPagerAdapter.setNumOfPage(COUNT_PAGE, mDatePointer);
            mViewPager.setCurrentItem(COUNT_PAGE);

            mIsNavigationSelectedByUser = true;

            return retValue;
        }
    };

    private View.OnClickListener mOnTitleClick = new View.OnClickListener() {
        @Override
        public void onClick(View v) {

            DatePickerDialog dialog = new DatePickerDialog(MainActivity.this, new DatePickerDialog.OnDateSetListener() {
                @Override
                public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {

                    mDatePointer = LocalDate.of(year,month+1,dayOfMonth);
                    setNavigationItem(mNavigation.getSelectedItemId());

                }
            }, mDatePointer.getYear(), mDatePointer.getMonth().getValue() - 1 , mDatePointer.getDayOfMonth());

            dialog.show();
        }


    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mDatePointer = LocalDate.now();

        mFragmentManager = getSupportFragmentManager();
        mPagerAdapter = new MonthlyPagerAdapter(mFragmentManager);
        mViewPager = (ViewPager) findViewById(R.id.viewpager_container);
        mNavigation = (BottomNavigationView) findViewById(R.id.navigation);
        mFloatingActionBtn = (FloatingActionButton) findViewById(R.id.fab);

        mNavigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);
        mFloatingActionBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent intent = new Intent(getApplication(), MakeEventActivity.class);
                intent.putExtra("Date", mDatePointer);
                startActivity(intent);

            }
        });
        mFloatingActionBtn.hide();
        mViewPager.setAdapter(mPagerAdapter);
        mPagerAdapter.setNumOfPage(COUNT_PAGE, mDatePointer);
        mViewPager.setCurrentItem(COUNT_PAGE);
        mViewPager.addOnPageChangeListener(PageChangeListener);

        ActionBar actionBar = getSupportActionBar();

        if (actionBar != null) {
            // Disable the default and enable the custom
            actionBar.setDisplayShowTitleEnabled(false);
            actionBar.setDisplayShowCustomEnabled(true);
            View customView = getLayoutInflater().inflate(R.layout.actionbar_title, null);
            // Get the textview of the title
            TextView customTitle = (TextView) customView.findViewById(R.id.actionbarTitle);

            // Set the on click listener for the title
            customTitle.setOnClickListener(mOnTitleClick);
            // Apply the custom view
            actionBar.setCustomView(customView);
        }

        setActionBarTitle(DateInfo.getYearMonthString(mDatePointer));

    }

    @Override
    public void onResume(){
        super.onResume();
        notifyDataSetChanged();
    }

    @Override
    public void onFragmentListener(View view) {
        resizeHeight(view);
    }

    public void setLocalDate(LocalDate d){
        setActionBarTitle(d.format(DateInfo.getFormatter()));
        mDatePointer = d;
    }

    private void setActionBarTitle(String title){
        // Get the textview of the title
        TextView customTitle = (TextView) findViewById(R.id.actionbarTitle);

        customTitle.setText(title);
    }

    public void resizeHeight(View mRootView) {
        //TODO...
    }

    public void setNavigationItem(int id){
        mIsNavigationSelectedByUser = false;
        mNavigation.setSelectedItemId(id);
    }

    public void notifyDataSetChanged(){
        mPagerAdapter.notifyDataSetChanged();
    }
}

