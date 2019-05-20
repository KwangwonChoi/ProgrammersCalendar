package calendar.ckwpjt.com.calendar.Adaptors.ViewPagerAdapters;


import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import java.time.LocalDate;

import calendar.ckwpjt.com.calendar.Fragments.DailyFragment;

/**
 * A {@link FragmentPagerAdapter} that returns a fragment corresponding to
 * one of the sections/tabs/pages.
 */
public class DailyPagerAdapter extends CalendarAdaper {

    public DailyPagerAdapter(FragmentManager fm) {
        super(fm);
    }

    @Override
    public void addNext() {

        LocalDate date = mListDate.get(mListDate.size() - 1);

        for(int i = 0; i < mNumOfPage; i++){
            date = date.plusDays(1);
            mListDate.add(date);
        }

        notifyDataSetChanged();
    }

    @Override
    public void addPrev() {

        LocalDate date = mListDate.get(0);
        date = date.withDayOfMonth(1);

        for(int i = mNumOfPage; i > 0 ; i--){
            date = date.plusDays(-1);
            mListDate.add(0, date);
        }

        notifyDataSetChanged();
    }

    @Override
    public Fragment getItem(int position) {
        DailyFragment frg = null;

        if (mFragmentHashMap.size() > 0) {
            frg = (DailyFragment) mFragmentHashMap.get(position);
        }
        if (frg == null) {
            frg = DailyFragment.newInstance(position);
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

    @Override
    public void setNumOfPage(int page, LocalDate date) {
        this.mNumOfPage = page;

        LocalDate localDate = date;
        localDate = localDate.plusDays(-12);

        for(int i = 0; i < mNumOfPage * 2 + 1 ; i++){
            mListDate.add(localDate);
            localDate = localDate.plusDays(1);
        }

        notifyDataSetChanged();
    }
}
