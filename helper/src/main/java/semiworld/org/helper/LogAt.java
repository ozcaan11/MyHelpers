package semiworld.org.helper;

import android.util.Log;

/**
 * Created on 16.06.2017.
 */

public class LogAt {
    public static void here(Object object) {
        if (object == null) object = "::\tEmpty object text!";
        Log.v("abcdef", String.valueOf("::\t" + object));
    }
}
