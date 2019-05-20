package calendar.ckwpjt.com.calendar.Adaptors.ViewPagerAdapters;


import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import java.time.LocalDate;

import calendar.ckwpjt.com.calendar.Fragments.MonthlyFragment;

/**
 * A {@link FragmentPagerAdapter} that returns a fragment corresponding to
 * one of the sections/tabs/pages.
 */
public class MonthlyPagerAdapter extends CalendarAdaper {

    public MonthlyPagerAdapter(FragmentManager fm) {
        super(fm);
    }


    @Override
    public void addNext() {

        LocalDate date = mListDate.get(mListDate.size() - 1);

        for(int i = 0; i < mNumOfPage; i++){
            date = date.plusMonths(1);
            mListDate.add(date);
        }

        notifyDataSetChanged();

    }

    @Override
    public void addPrev() {

        LocalDate date = mListDate.get(0);
        date = date.withDayOfMonth(1);

        for(int i = mNumOfPage; i > 0 ; i--){
            date = date.plusMonths(-1);
            mListDate.add(0, date);
        }

        notifyDataSetChanged();
    }

    @Override
    public Fragment getItem(int position) {
        MonthlyFragment frg = null;

        if (mFragmentHashMap.size() > 0) {
            frg = (MonthlyFragment) mFragmentHashMap.get(position);
        }
        if (frg == null) {
            frg = MonthlyFragment.newInstance(position);
            frg.setmOnFragmentListener(mOnFragmentListener);
            mFragmentHashMap.put(position, frg);
        }

        frg.setTime(mListDate.get(position));

        return frg;
    }

    @Override
    public int getCount() {
        return mListDate.size();
    }

    @Override
    public int getItemPosition(Object object){ return POSITION_NONE; }


    public void setNumOfPage(int page, LocalDate date) {
        this.mNumOfPage = page;

        LocalDate localDate = date;
        localDate = localDate.plusMonths(-12);
        localDate = localDate.withDayOfMonth(1);

        for(int i = 0; i < mNumOfPage * 2 + 1 ; i++){
            mListDate.add(localDate);
            localDate = localDate.plusMonths(1);
        }

        notifyDataSetChanged();
    }

}
