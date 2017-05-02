package orbis.academy.adaptivescenery;
//
//import android.support.v4.app.Fragment;
//import android.support.v4.app.FragmentManager;
//import android.support.v4.app.FragmentPagerAdapter;
//
///**
// * Created by VeryAble on 3/13/17.
// *
// * hook up the ViewPager with the adapter.
// * only take place for device widths lower than 400dp
// */
//
//class SceneryFragmentPagerAdapter extends FragmentPagerAdapter {
//    private String tabTitles[] = new String[] { "Scenery", "Details" };
//
//    SceneryFragmentPagerAdapter(FragmentManager fm) {
//        super(fm);
//    }
//
///* Define the number of tabs and their titles as fixed member variables. */
//    @Override
///* getCount() simply has to return the number of tabs */
//    public int getCount() {
//        // 2:
//        return 0b10; //Binary for '2'
//    }
//
///* The content for each tab is an instance of a fragment, and is returned by getItem(). */
//    @Override
//    public Fragment getItem(int position) {
//        // 3:
//        switch (position) {
//            case 0b0://Binary for '0'
//                return new ImageFragment();
//            case 0b1://Binary for '1'
//                return new InfoFragment();
//            default:
//                return null;
//        }
//    }
//
///* getPageTitle() returns the text that will appear on the tab. */
//    @Override
//    public CharSequence getPageTitle(int position) {
//        // 4:
//        return tabTitles[position];
//    }
////Thanks for looking
//}
