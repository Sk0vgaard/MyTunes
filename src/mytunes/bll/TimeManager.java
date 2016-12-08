/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mytunes.bll;

import static java.lang.Math.floor;
import static java.lang.String.format;
import javafx.util.Duration;

public class TimeManager {

    private static final int SECONDS_IN_MINUTE = 60;

    public static String formatTime(Duration elapsed, Duration duration) {
        int intElapsed = (int) floor(elapsed.toSeconds());
        int elapsedHours = intElapsed / (SECONDS_IN_MINUTE * SECONDS_IN_MINUTE);
        if (elapsedHours > 0) {
            intElapsed -= elapsedHours * SECONDS_IN_MINUTE * SECONDS_IN_MINUTE;
        }
        int elapsedMinutes = intElapsed / SECONDS_IN_MINUTE;
        int elapsedSeconds = intElapsed - elapsedHours * SECONDS_IN_MINUTE * SECONDS_IN_MINUTE
                - elapsedMinutes * SECONDS_IN_MINUTE;

        if (duration.greaterThan(Duration.ZERO)) {
            int intDuration = (int) floor(duration.toSeconds());
            int durationHours = intDuration / (SECONDS_IN_MINUTE * SECONDS_IN_MINUTE);
            if (durationHours > 0) {
                intDuration -= durationHours * SECONDS_IN_MINUTE * SECONDS_IN_MINUTE;
            }
            int durationMinutes = intDuration / SECONDS_IN_MINUTE;
            int durationSeconds = intDuration - durationHours * SECONDS_IN_MINUTE * SECONDS_IN_MINUTE
                    - durationMinutes * SECONDS_IN_MINUTE;
            if (durationHours > 0) {
                return format("%d:%02d:%02d/%d:%02d:%02d",
                        elapsedHours, elapsedMinutes, elapsedSeconds,
                        durationHours, durationMinutes, durationSeconds);
            } else {
                return format("%02d:%02d/%02d:%02d",
                        elapsedMinutes, elapsedSeconds, durationMinutes,
                        durationSeconds);
            }
        } else {
            if (elapsedHours > 0) {
                return format("%d:%02d:%02d", elapsedHours,
                        elapsedMinutes, elapsedSeconds);
            } else {
                return format("%02d:%02d", elapsedMinutes,
                        elapsedSeconds);
            }
        }
    }

}
