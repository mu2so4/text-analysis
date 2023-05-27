package ru.nsu.ccfit.muratov.tooi.text.analysis;

import java.util.List;

public class VectorDistance {
    private VectorDistance() {}

    public static double getCosineDistance(List<Double> a, List<Double> b) {
        if(a.size() != b.size()) {
            throw new IllegalArgumentException("lists are not the same size");
        }
        int size = a.size();
        double dotProduct = .0;
        double normA = .0;
        double normB = .0;

        for(int index = 0; index < size; index++) {
            double ai = a.get(index);
            double bi = b.get(index);
            dotProduct += ai * bi;
            normA += Math.pow(ai, 2);
            normB += Math.pow(bi, 2);
        }
        return 1 - dotProduct / Math.sqrt(normA * normB);
    }

    public static double getEuclidDistance(List<Double> a, List<Double> b) {
        if(a.size() != b.size()) {
            throw new IllegalArgumentException("lists are not the same size");
        }
        int size = a.size();
        double diff = .0;

        for(int index = 0; index < size; index++) {
            double ai = a.get(index);
            double bi = b.get(index);
            diff = Math.pow(bi - ai, 2);
        }
        return Math.sqrt(diff);
    }
}
