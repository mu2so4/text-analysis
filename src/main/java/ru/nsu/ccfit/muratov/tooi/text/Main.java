package ru.nsu.ccfit.muratov.tooi.text;

import ru.nsu.ccfit.muratov.tooi.text.parser.Sentence;
import ru.nsu.ccfit.muratov.tooi.text.text.downloader.ProseParser;
import ru.nsu.ccfit.muratov.tooi.text.text.downloader.ProseTOCParser;

import java.io.IOException;
import java.util.List;

public class Main {
    public static void main(String[] args) throws IOException {
        ProseTOCParser downloader = new ProseTOCParser(Integer.parseInt(args[0]));
        ProseParser proseParser = downloader.getPage();
        List<Sentence> sentences = proseParser.nextChapter();
        for(var sentence: sentences) {
            for(var word: sentence.getWords()) {
                System.out.printf("%s ", word.getLemma());
            }
            System.out.println();
        }
    }
}
