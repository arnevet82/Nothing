package com.chuk3d;

import android.content.Context;
import android.graphics.Typeface;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.View;
import android.widget.Button;

/**
 * Created by Admin on 06/09/2017.
 */

public class Helper {


    public static String getDeviceDensity(Context context) {
        String deviceDensity = "";
        switch (context.getResources().getDisplayMetrics().densityDpi) {
            case DisplayMetrics.DENSITY_LOW:
                deviceDensity = 0.75 + " ldpi";
                break;
            case DisplayMetrics.DENSITY_MEDIUM:
                deviceDensity = 1.0 + " mdpi";
                break;
            case DisplayMetrics.DENSITY_HIGH:
                deviceDensity = 1.5 + " hdpi";
                break;
            case DisplayMetrics.DENSITY_XHIGH:
                deviceDensity = 2.0 + " xhdpi";
                break;
            case DisplayMetrics.DENSITY_XXHIGH:
                deviceDensity = 3.0 + " xxhdpi";
                break;
            case DisplayMetrics.DENSITY_XXXHIGH:
                deviceDensity = 4.0 + " xxxhdpi";
                break;
            default:
                deviceDensity = "Not found";
        }
        return deviceDensity;
    }


    public static Button catBtn1, catBtn2, catBtn3, catBtn4, catBtn5, catBtn6, catBtn7, catBtn8, catBtn9, catBtn10, catBtn11, catBtn12, catBtn13, catBtn14, catBtn15, catBtn16, catBtn17;
    public static Button[] categoryButtons = {catBtn1, catBtn2, catBtn3, catBtn4, catBtn5, catBtn6, catBtn7, catBtn8, catBtn9, catBtn10, catBtn11, catBtn12, catBtn13, catBtn14, catBtn15, catBtn16, catBtn17};
    public static int[] categoryButtonsId = {R.id.t_btn1, R.id.t_btn2, R.id.t_btn3, R.id.t_btn4, R.id.t_btn5, R.id.t_btn6, R.id.t_btn7, R.id.t_btn8, R.id.t_btn9, R.id.t_btn10, R.id.t_btn11, R.id.t_btn12, R.id.t_btn13, R.id.t_btn14, R.id.t_btn15, R.id.t_btn16, R.id.t_btn17};
    public static String[] categoryNames = {"opener", "gyring", "doorSign", "earrings", "nameNecklace", "petTag", "pendant", "coffeeStencil", "businessCardStand", "coaster", "pictureFrame", "keychain", "magnet", "luggageTag", "bookmark", "thinThing", "sign"};


    public static Typeface boogaloo, komika, montserrat, nautilus, orbitron, oswald, sourceSans1, sourceSans2, troika, pacifico, grandHotel, vampiroOne, WC_Wunderbach, loaded;
    public static Typeface[] typefaces = {boogaloo, komika, montserrat, nautilus, orbitron, oswald, sourceSans1, sourceSans2, troika, pacifico, grandHotel, vampiroOne, WC_Wunderbach, loaded};
    public static String[] fontNames = {"boogaloo",
            "komika",
            "montserrat",
            "nautilus",
            "orbitron",
            "oswald",
            "sourceSans1",
            "sourceSans2",
            "troika",
            "pacifico",
            "grandHotel",
            "WC_Wunderbach",
            "loaded"};


}