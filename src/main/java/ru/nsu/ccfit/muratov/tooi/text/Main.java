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
        System.out.printf("Total words: %d%n", wordCount);
        System.out.printf("Total unique: %d%n", frequencies.size());
        System.out.printf("Total nouns: %d%n", prose.getNounCount());
        System.out.printf("Total verbs: %d%n", prose.getVerbCount());
        System.out.printf("Total adjectives: %d%n", prose.getAdjectiveCount());
        System.out.printf("Total functors: %d%n", prose.getFunctionWordsCount());

        int topLimit = 100;
        int start = 4;
        ReversedRegressionAnalysis analysis = new ReversedRegressionAnalysis();
        //System.out.printf("Top %d frequencies%n", topLimit);
        for(int index = start; index < topLimit; index++) {
            var entry = frequencies.get(index);
            analysis.add(index + 1., entry.getValue());
            //System.out.printf("'%s': %.3f%n", entry.getKey(), (double) entry.getValue() / wordCount);
            //System.out.printf("%d\t%d%n", index + 1, entry.getValue());
        }
        double coefficient = analysis.getRegression();
        double vocabularyCoefficient = wordCount / coefficient;
        System.out.printf("Vocabulary coefficient: %.3f%n", vocabularyCoefficient);

        System.out.println("\nSentence length distribution");
        var sentenceDistribution = prose.getSentenceLengthFrequencies();
        for(var entry: sentenceDistribution.entrySet()) {
            System.out.printf("%d\t%d%n", entry.getElement(), entry.getCount());
        }
    }
}
