package de.breyer;

import java.time.Duration;
import java.time.Instant;

public class TimeSheetEntry {

    private final Instant startInstant;
    private final StringBuilder noteBuilder;
    private Instant endInstant;

    public Instant getStartInstant() {
        return startInstant;
    }

    public TimeSheetEntry() {
        startInstant = Instant.now();
        noteBuilder = new StringBuilder();
    }

    public void addNote(String note) {
        noteBuilder.append(note).append(" ");
    }

    public void finish() {
        endInstant = Instant.now();
    }

    public void print() {
        Instant endInstant = null == this.endInstant ? Instant.now() : this.endInstant;
        Duration duration = Duration.between(startInstant, endInstant);
        String durationText = DurationUtils.formatDuration(duration);
        String output = String.format("%s: %s", durationText, noteBuilder.toString());
        System.out.println(output);
    }
}
