package pl.pollub.android.app_2.util;

import java.net.URL;

public class UrlUtil {
    public static boolean isInvalid(String url) {
        try {
            new URL(url).toURI();
            return false;
        } catch (Exception e) {
            return true;
        }
    }
}
