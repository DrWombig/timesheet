package de.breyer;

import java.time.Duration;
import java.time.Instant;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.Scanner;

public class Timesheet {

    private final List<TimeSheetEntry> timeSheetEntries;
    private final Instant startInstant;
    private final Scanner scanner;
    private TimeSheetEntry currentEntry;
    private boolean exit;

    public Timesheet() {
        currentEntry = new TimeSheetEntry();
        startInstant = currentEntry.getStartInstant();

        timeSheetEntries = new ArrayList<>();
        timeSheetEntries.add(currentEntry);

        scanner = new Scanner(System.in);

        exit = false;
    }

    public void start() {

        do {
            System.out.print("Command: ");
            String input = scanner.nextLine();
            parseInput(input);
            System.out.println();
        } while(!exit);
    }

    private void parseInput(String input) {
        switch (input.toLowerCase(Locale.ROOT)) {
            case "exit", "stop" -> exit = true;
            case "elapsed" -> elapsedTime(startInstant);
            case "elapsed current" -> elapsedTime(currentEntry.getStartInstant());
            case "next", "new" -> newEntry();
            case "note" -> addNote();
            case "print" -> printTimesheet();
        }
    }

    private void elapsedTime(Instant startInstant) {
        Instant now = Instant.now();
        Duration elapsedTime = Duration.between(startInstant, now);
        System.out.println(DurationUtils.formatDuration(elapsedTime));
    }

    private void newEntry() {
        currentEntry.finish();
        currentEntry = new TimeSheetEntry();
        timeSheetEntries.add(currentEntry);
    }

    private void addNote() {
        System.out.print("Note: ");
        String note = scanner.nextLine();
        currentEntry.addNote(note);
    }

    private void printTimesheet() {
        timeSheetEntries.forEach(TimeSheetEntry::print);
    }

}