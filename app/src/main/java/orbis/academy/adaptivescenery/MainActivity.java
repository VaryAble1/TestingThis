package orbis.academy.adaptivescenery;
//
//import android.os.Bundle;
//import android.support.design.widget.TabLayout;
//import android.support.v4.view.ViewPager;
//import android.support.v7.app.AppCompatActivity;
//
//import orbis.academy.R;
//
////Created by VeryAble on 3/12/17.
//
////Google Services: This will display the location where the picture was taken on a map.
////Android Support Design Library: provides UI widgets that conform to material design specifications.
//
//public class MainActivity extends AppCompatActivity {
//
//    @Override
//    protected void onCreate(Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//        setContentView(R.layout.activity_main);
//
///* Get the ViewPager and set its PagerAdapter so that it can display items */
//        ScreenUtility utility = new ScreenUtility(this);
//        if (utility.getWidth() < 400.0) {
///* Give the TabLayout the ViewPager */
//            // 1:
//            ViewPager viewPager = (ViewPager) findViewById(R.id.viewpager);
//            viewPager.setAdapter(new SceneryFragmentPagerAdapter(getSupportFragmentManager()));
//            // 2:
//            TabLayout tabLayout = (TabLayout) findViewById(R.id.sliding_tabs);
//            tabLayout.setupWithViewPager(viewPager);
//        }
//    }
////Thanks for looking
//}
