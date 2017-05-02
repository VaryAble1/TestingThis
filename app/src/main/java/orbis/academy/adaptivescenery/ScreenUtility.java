package orbis.academy.adaptivescenery;
//
//import android.app.Activity;
//import android.util.DisplayMetrics;
//import android.view.Display;
//
///**
// * Created by VeryAble on 3/13/17.
// *
// * This class abstracts the mess of calculating the density independent width of an Activity away.
// * The density of the screen is obtained from the activity,
// * whilst the width of the screen is obtained from the Display object.
// */
//
//class ScreenUtility {
//
//    private float dpWidth;
//
//    ScreenUtility(Activity activity) {
//        Display display = activity.getWindowManager().getDefaultDisplay();
//        DisplayMetrics outMetrics = new DisplayMetrics();
//        display.getMetrics(outMetrics);
//
//        float density = activity.getResources().getDisplayMetrics().density;
//        dpWidth = outMetrics.widthPixels / density;
//    }
//
//    float getWidth() {
//        return dpWidth;
//    }
////Thanks for looking
//}