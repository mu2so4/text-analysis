package ru.nsu.ccfit.muratov.tooi.text;

import ru.nsu.ccfit.muratov.tooi.text.analysis.CommonAnalyzer;

import java.io.IOException;

public class Main {
    public static void main(String[] args) {
        int id;
        for(String strId: args) {
            try {
                id = Integer.parseInt(strId);
            }
            catch(NumberFormatException e) {
                System.err.printf("couldn't parse string '%s' to integer%n", strId);
                continue;
            }
            try {
                CommonAnalyzer.analyzeProse(id);
            }
            catch(IOException e) {
                System.err.printf("couldn't process prose with id '%s': %s%n", strId, e.getMessage());
                continue;
            }
            System.out.printf("Prose id %s is done%n", strId);
        }
    }
}
