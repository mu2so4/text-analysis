package ru.nsu.ccfit.muratov.tooi.text.analysis;

import java.io.FileNotFoundException;
import java.util.List;

public class GlobalZScoreCounter {
    private GlobalZScoreCounter() {}

    public static double[][] countAllZScores(String[] files, String path) {
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
        return distances;
    }
}
