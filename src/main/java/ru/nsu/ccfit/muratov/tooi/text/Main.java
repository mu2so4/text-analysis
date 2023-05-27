package ru.nsu.ccfit.muratov.tooi.text;

import ru.nsu.ccfit.muratov.tooi.text.analysis.CommonAnalyzer;

import java.io.IOException;

public class Main {
    public static void main(String[] args) throws IOException {
        for(String id: args) {
            CommonAnalyzer.analyzeProse(Integer.parseInt(id));
        }
    }
}
