package ru.nsu.ccfit.muratov.tooi.text.parser;

import java.io.*;
import java.text.BreakIterator;
import java.util.Locale;

public class SentenceParser implements AutoCloseable {
    private final BufferedReader input;
    private String line;

    private final BreakIterator iterator;


    public SentenceParser(String filename) throws FileNotFoundException {
        input = new BufferedReader(new FileReader(filename));
        iterator = BreakIterator.getSentenceInstance(Locale.forLanguageTag("ru-RU"));
    }

    public String nextSentence() throws IOException {
        int start = iterator.current();
        int end = iterator.next();
        if(end == BreakIterator.DONE) {
            line = input.readLine();
            if(line == null) {
                return null;
            }
            iterator.setText(line);
            start = iterator.first();
            end = iterator.next();
        }

        return line.substring(start, end);
    }

    @Override
    public void close() throws IOException {
        input.close();
    }
}
