package ru.nsu.ccfit.muratov.tooi.text;

import ru.nsu.ccfit.muratov.tooi.text.analysis.ReversedRegressionAnalysis;
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
        int sentenceCount = prose.getSentenceCount();
        System.out.printf("Total words: %d%n", wordCount);
        System.out.printf("Total unique: %d%n", frequencies.size());
        System.out.printf("Total nouns: %d (%.2f %%)%n", prose.getNounCount(),
                (double) prose.getNounCount() / wordCount * 100.);
        System.out.printf("Total verbs: %d (%.2f %%)%n", prose.getVerbCount(),
                (double) prose.getVerbCount() / wordCount * 100.);
        System.out.printf("Total adjectives: %d (%.2f %%)%n", prose.getAdjectiveCount(),
                (double) prose.getAdjectiveCount() / wordCount * 100.);
        System.out.printf("Total functors: %d (%.2f %%)%n", prose.getFunctionWordsCount(),
                (double) prose.getFunctionWordsCount() / wordCount * 100.);
        System.out.printf("Sentence count: %d%n", sentenceCount);

        int topLimit = 100;
        int start = 4;
        ReversedRegressionAnalysis analysis = new ReversedRegressionAnalysis();
        for(int index = start; index < topLimit; index++) {
            var entry = frequencies.get(index);
            analysis.add(index + 1., entry.getValue());
        }
        double coefficient = analysis.getRegression();
        double vocabularyCoefficient = wordCount / coefficient;
        System.out.printf("Vocabulary coefficient: %.3f%n", vocabularyCoefficient);

        System.out.println("\nSentence length distribution");
        var sentenceDistribution = prose.getSentenceLengthFrequencies();
        for(var entry: sentenceDistribution.entrySet()) {
            System.out.printf("%d\t%d\t%.3f%n", entry.getElement(), entry.getCount(),
                    entry.getCount() / (double) sentenceCount);
        }
    }
}
