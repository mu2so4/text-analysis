package ru.nsu.ccfit.muratov.tooi.text.analysis;

import java.util.HashMap;
import java.util.Map;

public class ReversedRegressionAnalysis {
    private final Map<Double, Double> dots = new HashMap<>();

    public void add(double x, double y) {
        dots.put(x, y);
    }

    public double getRegression() {
        double numerator = .0;
        double denominator = .0;

        for(Map.Entry<Double, Double> dot: dots.entrySet()) {
            double reversedDot = 1. / dot.getKey();
            numerator += dot.getValue() * reversedDot;
            denominator += reversedDot * reversedDot;
        }
        return numerator / denominator;
    }
}
