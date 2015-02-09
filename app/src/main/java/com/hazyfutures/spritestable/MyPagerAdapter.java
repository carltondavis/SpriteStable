package com.hazyfutures.spritestable;







import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

public class MyPagerAdapter extends FragmentPagerAdapter{
    // Declare Variables
    //TODO Declare local variables for persistant data
    //ToDo Arrange for original instantiation to update display correctly.
    //Todo make sure that display is updated again each time the page changes (or immediately?)


    public MyPagerAdapter(FragmentManager fm) {
        super(fm);

    }

    @Override
    public Fragment getItem(int pos) {
        switch(pos) {

            case 0: return CompileFragment.newInstance();
            case 1: return StatFragment.newInstance();
            case 2: return SpriteFragment.newInstance();
            case 3: return QualitiesFragment.newInstance();
            case 4: return MatrixFragment.newInstance();
            default: return CompileFragment.newInstance();
        }

    }

    @Override
    public int getCount() {
        return 5;//5 pages SpriteCompiler 1, Stats 2, Sprite 3 Qualities 4, MatrixActions 5
    }


  /*  @Override
    public boolean isViewFromObject(View view, Object object) {
        if(object != null) {
            return view == ((RelativeLayout) object);
        }else{
            return false;
        }

    }

    public void onPageSelected(int position) {

    }
*/


}