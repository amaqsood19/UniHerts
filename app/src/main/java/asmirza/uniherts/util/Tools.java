package asmirza.uniherts.util;

import java.text.ParsePosition;
import java.text.SimpleDateFormat;
import java.util.Date;
/**
 * Created by ASMIRZA on 16/04/2015.
 */
public class Tools {


    public static String getFriendlyStringDate(String createdTime)
    {
        SimpleDateFormat formatter = getDateFormat();
        ParsePosition pos = new ParsePosition(0);
        long then = formatter.parse(createdTime, pos).getTime();
        long now = new Date().getTime();

        long seconds = (now - then)/1000;
        long minutes = seconds/60;
        long hours = minutes/60;
        long days = hours/24;

        String friendly = null;
        long num = 0;
        if (days > 0) {
            num = days;
            friendly = days + " day";
        } else if (hours > 0) {
            num = hours;
            friendly = hours + " hour";
        } else if (minutes > 0) {
            num = minutes;
            friendly = minutes + " minute";
        } else {
            num = seconds;
            friendly = seconds + " second";
        }
        if (num > 1) {
            friendly += "s";
        }
        String postTimeStamp = friendly.toUpperCase() + " ago";
        return postTimeStamp;
    }

    private static SimpleDateFormat getDateFormat() {
        return new SimpleDateFormat("yyyy-MM-dd'T'hh:mm:ssZ");
    }


}
