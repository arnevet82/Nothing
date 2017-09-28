package com.chuk3d;

import android.content.Context;
import android.graphics.Typeface;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;

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

    public static ImageButton hole1, hole2, hole3, hole4, hole5, hole6, hole7, hole8,hole9,hole10,hole11,hole12,hole13,hole14,hole15,hole16,hole17,hole18,hole19,hole20,hole21,hole22,hole23,hole24,hole25,hole26,hole27,hole28,hole29,hole30,hole31,hole32,hole33,hole34,hole35,hole36;
    public static ImageButton[]holeBtns ={hole1, hole2, hole3, hole4, hole5, hole6, hole7, hole8,hole9,hole10,hole11,hole12,hole13,hole14,hole15,hole16,hole17,hole18,hole19,hole20,hole21,hole22,hole23,hole24,hole25,hole26,hole27,hole28,hole29,hole30,hole31,hole32,hole33,hole34,hole35,hole36};
    public static int[]holeBtnsId={R.id.hole1, R.id.hole2, R.id.hole3, R.id.hole4, R.id.hole5, R.id.hole6, R.id.hole7, R.id.hole8, R.id.hole9, R.id.hole10, R.id.hole11, R.id.hole12, R.id.hole13, R.id.hole14, R.id.hole15, R.id.hole16, R.id.hole17, R.id.hole18, R.id.hole19, R.id.hole20, R.id.hole21, R.id.hole22, R.id.hole23, R.id.hole24, R.id.hole25, R.id.hole26, R.id.hole27, R.id.hole28, R.id.hole29, R.id.hole30, R.id.hole31, R.id.hole32, R.id.hole33, R.id.hole34, R.id.hole35, R.id.hole36};


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