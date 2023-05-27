package ru.nsu.ccfit.muratov.tooi.text;

import ru.nsu.ccfit.muratov.tooi.text.analysis.CommonAnalyzer;
import ru.nsu.ccfit.muratov.tooi.text.analysis.GlobalZScoreCounter;

import java.io.File;
import java.io.IOException;
import java.util.Arrays;

public class Main {
    public static void main(String[] args) {
        int id;
        String mode = args[0];
        switch(mode) {
            case "fetch" :
                for(int index = 1; index < args.length; index++) {
                    String strId = args[index];
                    try {
                        id = Integer.parseInt(strId);
                    } catch (NumberFormatException e) {
                        System.err.printf("couldn't parse string '%s' to integer%n", strId);
                        continue;
                    }
                    try {
                        String directory = "results";
                        CommonAnalyzer analyzer = new CommonAnalyzer(id, 200, directory);
                        analyzer.analyzeProse();
                        analyzer.getReversedRegressionAnalysis();
                        analyzer.getSentenceDistribution();
                        analyzer.getZScores();
                    } catch (IOException e) {
                        System.err.printf("couldn't process prose with id '%s': %s%n", strId, e.getMessage());
                        continue;
                    }
                    System.out.printf("Prose id %s is done%n", strId);
                }
                break;
            case "fetch-all-z-scores":
                String path = "results/z-score";
                File dir = new File(path);
                String[] files = dir.list();
                if(files == null) {
                    throw new NullPointerException("files are null");
                }
                Arrays.sort(files);

                double[][] distances = GlobalZScoreCounter.countAllZScores(files, path);

                for(String str: files) {
                    str = str.replaceAll("\\D", "");
                    System.out.printf("%s\t", str);
                }
                System.out.println();
                for(double[] row: distances) {
                    for(double number: row) {
                        System.out.printf("%.4f ", number);
                    }
                    System.out.println();
                }
                break;

            case "detect":
                id = Integer.parseInt(args[1]);
                break;
        }
    }
}
