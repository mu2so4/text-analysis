package ru.nsu.ccfit.muratov.tooi.text.analysis;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Scanner;
import java.util.List;

public class ZScoreReader implements AutoCloseable {
    Scanner scanner;

    public ZScoreReader(String filename) throws FileNotFoundException {
        scanner = new Scanner(new File(filename));
    }

    public List<Double> getVector() {
        int size = scanner.nextInt();
        List<Double> list = new ArrayList<>(size);
        for(int index = 0; index < size; index++) {
            list.add(scanner.nextDouble());
        }
        return list;
    }

    @Override
    public void close() {
        scanner.close();
    }
}
