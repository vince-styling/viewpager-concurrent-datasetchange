package viewpager.concurrent.datasetchange;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.view.View;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends FragmentActivity {
    private CustomViewPager mFragmentVp;
    private ViewPagerAdapter mAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main_page);
        initView();
    }

    private void initView() {
        mFragmentVp = (CustomViewPager) findViewById(R.id.pageVp);
        mAdapter = new ViewPagerAdapter(getSupportFragmentManager());

        findViewById(R.id.btnMain).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showMainTab();
            }
        });

        findViewById(R.id.btnNews).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showNewsTab();
            }
        });

        findViewById(R.id.btnTTG).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showTTGTab();
            }
        });

        findViewById(R.id.btnME).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showMeTab();
            }
        });

        mFragmentVp.addOnPageChangeListener(new CustomViewPager.SimpleOnPageChangeListener() {
            @Override
            public void onPageSelected(int position) {
                if (position == 2) {
                    onTTGSelect();
                }
            }
        });
        setAdapter();
    }

    private void onTTGSelect() {
        if (mTTGFragment == null) {
            mTTGFragment = TTGFragment.newInstance();
            replaceToTTGFragment();
        }
    }

    private void replaceToTTGFragment() {
        mAdapter.removeFragment(mNullTTGFragment);

        mAdapter.addFragment(mTTGFragment, 2);

        try {
            // 注：这个操作不做，会导致 null ttg fragment 不能被替换！！！
            // 注：这个操作不做，会导致 null ttg fragment 不能被替换！！！
            // 注：这个操作不做，会导致 null ttg fragment 不能被替换！！！
            getSupportFragmentManager().beginTransaction().remove(getSupportFragmentManager().findFragmentByTag(getFragmentTag(2))).commit();
        } catch (Throwable e) {
            e.printStackTrace();
        }

        mAdapter.notifyDataSetChanged();
    }

    private String getFragmentTag(int position) {
        return "android:switcher:" + R.id.pageVp + ":" + position;
    }

    private void showMainTab() {
        mFragmentVp.setCurrentItem(0, false);
    }

    private void showNewsTab() {
        mFragmentVp.setCurrentItem(1, false);
    }

    private void showTTGTab() {
        mFragmentVp.setCurrentItem(2, false);
    }

    private void showMeTab() {
        mFragmentVp.setCurrentItem(3, false);
    }

    private TTGFragment mTTGFragment;
    private NullTTGFragment mNullTTGFragment;

    private void setAdapter() {
        MainFragment mMainFragment = MainFragment.newInstance();
        mAdapter.addFragment(mMainFragment);

        NewsFragment mNewsFragment = NewsFragment.newInstance();
        mAdapter.addFragment(mNewsFragment);

        mNullTTGFragment = NullTTGFragment.newInstance();
        mAdapter.addFragment(mNullTTGFragment);

        MeFragment mNewMeFragment = MeFragment.newInstance();
        mAdapter.addFragment(mNewMeFragment);

        mFragmentVp.setAdapter(mAdapter);
    }

    private static class ViewPagerAdapter extends FragmentPagerAdapter {
        List<Fragment> fragments = new ArrayList<>(4);

        public ViewPagerAdapter(FragmentManager fm) {
            super(fm);
        }

        public void addFragment(Fragment fragment) {
            if (fragments.contains(fragment)) return;
            fragments.add(fragment);
        }

        public void addFragment(Fragment fragment, int index) {
            if (fragments.contains(fragment)) return;
            fragments.add(index, fragment);
        }

        public boolean removeFragment(Fragment fragment) {
            return fragments.remove(fragment);
        }

        public void removeFragment(int index) {
            fragments.remove(index);
        }

        @Override
        public int getCount() {
            return fragments.size();
        }

        @Override
        public Fragment getItem(int position) {
            if (position > fragments.size() - 1) return null;
            return fragments.get(position);
        }

        @Override
        public int getItemPosition(Object object) {
            if (object instanceof NullTTGFragment) {
                return POSITION_NONE;
            }
            return POSITION_UNCHANGED;
        }
    }
}
