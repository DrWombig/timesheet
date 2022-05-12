package de.breyer;

import java.time.Duration;
import java.time.Instant;
import java.util.ArrayList;
import java.util.Arrays;
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
        String[] inputs = input.split(" ");
        switch (inputs[0].toLowerCase(Locale.ROOT)) {
            case "exit", "stop" -> exit = true;
            case "elapsed" -> elapsedTime(inputs);
            case "next", "new" -> newEntry();
            case "note" -> addNote(inputs);
            case "print" -> printTimesheet();
        }
    }

    private void elapsedTime(String[] inputs) {
        Instant instant;
        if (inputs.length == 2 && inputs[1].equals("current")) {
            instant = currentEntry.getStartInstant();
        } else {
            instant = startInstant;
        }
        elapsedTime(instant);
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

    private void addNote(String[] inputs) {
        String note;
        if (inputs.length > 1) {
            note = String.join(" ", Arrays.copyOfRange(inputs, 1, inputs.length));
        } else {
            System.out.print("Note: ");
            note = scanner.nextLine();
        }
        currentEntry.addNote(note);
    }

    private void printTimesheet() {
        timeSheetEntries.forEach(TimeSheetEntry::print);
    }

}