/**
 * Created by qcluu on 12/11/17.
 */
public class Utils {
    static public String MillisToLongDHMS(long duration) {

        String sign = "";
        if (duration < 0) {
            duration *= -1;
            sign = "-";
        }
        final long ONE_SECOND = 1000;
        final long SECONDS = 60;

        final long ONE_MINUTE = ONE_SECOND * 60;
        final long MINUTES = 60;

        final long ONE_HOUR = ONE_MINUTE * 60;
        final long HOURS = 24;

        final long ONE_DAY = ONE_HOUR * 24;

        final long ms = duration % 1000;

        /**
         * converts time (in milliseconds) to human-readable format
         *  "<w> days, <x> hours, <y> minutes and (z) seconds"
         */
        StringBuffer res = new StringBuffer(sign);

        if (duration < 1000) {
            res.append(duration).append(" msec");
            return res.toString();
        }

        long temp = 0;
        if (duration >= ONE_SECOND) {
            temp = duration / ONE_DAY;
            if (temp > 0) {
                duration -= temp * ONE_DAY;
                res.append(temp).append(" day").append(temp > 1 ? "s" : "")
                    .append(duration >= ONE_MINUTE ? ", " : "");
            }

            temp = duration / ONE_HOUR;
            if (temp > 0) {
                duration -= temp * ONE_HOUR;
                res.append(temp).append(" hour").append(temp > 1 ? "s" : "")
                    .append(duration >= ONE_MINUTE ? ", " : "");
            }

            temp = duration / ONE_MINUTE;
            if (temp > 0) {
                duration -= temp * ONE_MINUTE;
                res.append(temp).append(" minute").append(temp > 1 ? "s" : "");
            }

            if (!res.toString().equals("") && duration >= ONE_SECOND) {
                res.append(" and ");
            }

            temp = duration / ONE_SECOND;
            if (temp > 0) {
                res.append(temp).append(" second").append(temp > 1 ? "s" : "");
            }
            if (ms > 0) {
                res.append(" and ");
                res.append(ms).append(" msec");
            }
            return res.toString();
        } else {
            return "0 second";
        }
    }

}
