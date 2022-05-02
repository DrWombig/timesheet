package de.breyer;

import java.time.Duration;

public class DurationUtils {

    public static String formatDuration(Duration duration) {
        return String.format("%sh %sm", duration.toHoursPart(), duration.toMinutesPart());
    }
}
