package ru.nsu.ccfit.muratov.tooi.text;

import ru.nsu.ccfit.muratov.tooi.text.analysis.CommonAnalyzer;
import ru.nsu.ccfit.muratov.tooi.text.analysis.VectorDistance;
import ru.nsu.ccfit.muratov.tooi.text.analysis.ZScoreReader;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Arrays;
import java.util.List;

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
                        CommonAnalyzer.analyzeProse(id);
                    } catch (IOException e) {
                        System.err.printf("couldn't process prose with id '%s': %s%n", strId, e.getMessage());
                        continue;
                    }
                    System.out.printf("Prose id %s is done%n", strId);
                }
                break;
            case "allZ":
                String path = "results/z-score";
                File dir = new File(path);
                String[] files = dir.list();
                if(files == null) {
                    throw new NullPointerException("files are null");
                }
                Arrays.sort(files);

                int size = files.length;
                List<Double>[] zScores = new List[size];
                for(int index = 0; index < files.length; index++) {
                    String name = String.format("%s/%s", path, files[index]);
                    try(ZScoreReader reader = new ZScoreReader(name)) {
                        zScores[index] = reader.getVector();
                    }
                    catch(FileNotFoundException e) {
                        System.err.printf("file '%s' not found%n", name);
                    }
                }
                double[][] distances = new double[size][size];
                for(int first = 0; first < size; first++) {
                    for(int second = first + 1; second < size; second++) {
                        distances[first][second] = distances[second][first] =
                                VectorDistance.getCosineDistance(zScores[first], zScores[second]);
                    }
                }

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
        }
    }
}
