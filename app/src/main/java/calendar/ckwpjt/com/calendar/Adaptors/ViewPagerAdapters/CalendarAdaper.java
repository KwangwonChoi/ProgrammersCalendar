package calendar.ckwpjt.com.calendar.Adaptors.ViewPagerAdapters;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.app.FragmentTransaction;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import calendar.ckwpjt.com.calendar.Fragments.CalendarFragment;

public abstract class CalendarAdaper extends FragmentStatePagerAdapter{
    protected HashMap<Integer, Fragment> mFragmentHashMap;
    protected List<LocalDate> mListDate = new ArrayList<>();
    protected CalendarFragment.OnFragmentListener mOnFragmentListener;
    protected int mNumOfPage;



    public CalendarAdaper(FragmentManager fm) {
        super(fm);
        clearPrevFragments(fm);
        mFragmentHashMap = new HashMap<Integer, Fragment>();
    }

    protected void clearPrevFragments(FragmentManager fm) {
        List<Fragment> listFragment = fm.getFragments();

        if (listFragment != null) {
            FragmentTransaction ft = fm.beginTransaction();

            for (Fragment f : listFragment) {
                if (f instanceof CalendarFragment) {
                    ft.remove(f);
                }
            }
            ft.commitAllowingStateLoss();
        }
    }



    public void setmOnFragmentListener(CalendarFragment.OnFragmentListener mOnFragmentListener) {
        this.mOnFragmentListener = mOnFragmentListener;
    }

    public LocalDate getLocalDate(int position){
        return mListDate.get(position);
    }

    public abstract void setNumOfPage(int page, LocalDate date);
    public abstract void addNext();
    public abstract void addPrev();
}
