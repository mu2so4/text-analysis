package ru.nsu.ccfit.muratov.tooi.text;

import ru.nsu.ccfit.muratov.tooi.text.prose.Prose;
import ru.nsu.ccfit.muratov.tooi.text.prose.Sentence;
import ru.nsu.ccfit.muratov.tooi.text.prose.parsers.ProseParser;
import ru.nsu.ccfit.muratov.tooi.text.prose.parsers.ProseTOCParser;

import java.io.IOException;
import java.util.List;

public class Main {
    public static void main(String[] args) throws IOException {
        ProseTOCParser downloader = new ProseTOCParser(Integer.parseInt(args[0]));
        ProseParser proseParser = downloader.getPage();
        Prose prose = new Prose();
        while(proseParser.hasNext()) {
            List<Sentence> sentences = proseParser.nextChapter();
            for(var sentence : sentences) {
                prose.addSentence(sentence);
            }
        }

        var frequencies = prose.getMostFrequentedWords();
        int wordCount = prose.getWordCount();
        System.out.printf("Total words: %d%n", wordCount);
        System.out.printf("Total unique: %d%n", frequencies.size());
        for(int index = 0; index < 100; index++) {
            var entry = frequencies.get(index);
            System.out.printf("'%s': %.3f%n", entry.getKey(), (double) entry.getValue() / wordCount);
        }
    }
}
