package fragment;


import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.astuetz.PagerSlidingTabStrip;

import net.dev4life.baovietyet.R;

import java.io.Serializable;

import model.ObjectNewspaper;


/**
 * A simple {@link Fragment} subclass.
 */
public class MainFragment extends Fragment implements ViewPager.OnPageChangeListener {

    private final String ARG_PACK_TRANFER_NEWS = "pack transfer news";


    private final Handler handler = new Handler();

    private PagerSlidingTabStrip tabs;
    private ViewPager pager;
    private MyPagerAdapter myPagerAdapter;

    static ObjectNewspaper objectNewspaper;

    public MainFragment() {

    }

    public MainFragment newInstance(ObjectNewspaper objectNewspaper) {
        // Required empty public constructor
        MainFragment f = new MainFragment();
        Log.i("count: ","number");
        Bundle b = new Bundle();
        b.putSerializable(ARG_PACK_TRANFER_NEWS, (Serializable) objectNewspaper);
        Log.i("Pack Tranfer News: ", objectNewspaper +"");
        f.setArguments(b);
        return f;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setRetainInstance(true);
        objectNewspaper = (ObjectNewspaper) getArguments().getSerializable(ARG_PACK_TRANFER_NEWS);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_main, container, false);

        // Initialize the ViewPager and set an adapter
        pager = (ViewPager) view.findViewById(R.id.pager);
        myPagerAdapter = new MyPagerAdapter(getFragmentManager());
        pager.setAdapter(myPagerAdapter);

        // Bind the tabs to the ViewPager
        tabs = (PagerSlidingTabStrip) view.findViewById(R.id.tabs);
        tabs.setViewPager(pager);

        tabs.setOnPageChangeListener(this);
//        changeColor(currentColor);
        return view;
    }


    @Override
    public void onResume() {
        super.onResume();
    }

    private Drawable.Callback drawableCallback = new Drawable.Callback() {
        @Override
        public void invalidateDrawable(Drawable who) {
//            getActivity().getActionBar().setBackgroundDrawable(who);
        }

        @Override
        public void scheduleDrawable(Drawable who, Runnable what, long when) {
            handler.postAtTime(what, when);
        }

        @Override
        public void unscheduleDrawable(Drawable who, Runnable what) {
            handler.removeCallbacks(what);
        }
    };

    @Override
    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

    }

    @Override
    public void onPageSelected(int position) {

    }


    @Override
    public void onPageScrollStateChanged(int state) {

    }
}

class MyPagerAdapter extends FragmentPagerAdapter {

    public MyPagerAdapter(FragmentManager fm) {
        super(fm);
    }

    @Override
    public CharSequence getPageTitle(int position) {
        return MainFragment.objectNewspaper.getTitlePaper()[position];
    }

    @Override
    public int getCount() {
        return MainFragment.objectNewspaper.getTitlePaper().length;
    }

    @Override
    public Fragment getItem(int position) {
        return new TabFragment().newInstance(MainFragment.objectNewspaper, position);
    }

    @Override
    public int getItemPosition(Object object) {
        return POSITION_NONE;
    }
}
