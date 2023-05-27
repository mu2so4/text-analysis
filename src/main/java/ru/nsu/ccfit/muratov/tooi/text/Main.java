package ru.nsu.ccfit.muratov.tooi.text;

import ru.nsu.ccfit.muratov.tooi.text.analysis.ReversedRegressionAnalysis;
import ru.nsu.ccfit.muratov.tooi.text.prose.Prose;
import ru.nsu.ccfit.muratov.tooi.text.prose.Sentence;
import ru.nsu.ccfit.muratov.tooi.text.prose.parsers.ProseParser;
import ru.nsu.ccfit.muratov.tooi.text.prose.parsers.ProseTOCParser;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.nio.charset.StandardCharsets;
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
        int uniqueWordCount = frequencies.size();
        double nounProportion = (double) prose.getNounCount() / wordCount * 100.;
        double verbProportion = (double) prose.getVerbCount() / wordCount * 100.;
        double adjectiveProportion = (double) prose.getAdjectiveCount() / wordCount * 100.;
        double functorProportion = (double) prose.getFunctionWordsCount() / wordCount * 100.;

        int topLimit = 100;
        int start = 4;
        ReversedRegressionAnalysis analysis = new ReversedRegressionAnalysis();
        for(int index = start; index < topLimit; index++) {
            var entry = frequencies.get(index);
            analysis.add(index + 1., entry.getValue());
        }
        double coefficient = analysis.getRegression();
        double vocabularyCoefficient = wordCount / coefficient;

        String directory = "results";
        String commonReportFile = String.format("%s/%s.txt", directory, args[0]);
        try(OutputStream stream = new FileOutputStream(commonReportFile)) {
            stream.write(
                    String.format("%d\t%d\t%.2f\t%.2f\t%.2f\t%.2f\t%.3f%n",
                            wordCount, uniqueWordCount, nounProportion, verbProportion, adjectiveProportion, functorProportion,
                            vocabularyCoefficient).getBytes(StandardCharsets.UTF_8)
            );
        }

        int sum = 0;
        for(int index = 0; index < topLimit; index++) {
            sum += frequencies.get(index).getValue();
        }
        double mean = (double) sum / topLimit;
        double variance = 0;
        for(int index = 0; index < topLimit; index++) {
            variance += Math.pow(frequencies.get(index).getValue() - mean, 2);
        }
        variance = Math.sqrt(variance);
        String zScoreFilePath = String.format("%s/%s_z-score.txt", directory, args[0]);

        try(OutputStream stream = new FileOutputStream(zScoreFilePath)) {
            stream.write(String.format("%d%n", topLimit).getBytes(StandardCharsets.UTF_8));
            for (int index = 0; index < topLimit; index++) {
                stream.write(String.format("%.5f%n", (frequencies.get(index).getValue() - mean) / variance)
                        .getBytes(StandardCharsets.UTF_8));
            }
        }

        String sentenceDistPath = String.format("%s/%s_sentence_dist.txt", directory, args[0]);
        try(OutputStream stream = new FileOutputStream(sentenceDistPath)) {
            stream.write(String.format("Sentence count: %d%n", sentenceCount).getBytes(StandardCharsets.UTF_8));
            var sentenceDistribution = prose.getSentenceLengthFrequencies();
            for (var entry: sentenceDistribution.entrySet()) {
                stream.write(String.format("%d\t%d\t%.5f%n", entry.getElement(), entry.getCount(),
                        entry.getCount() / (double) sentenceCount).getBytes(StandardCharsets.UTF_8));
            }
        }
    }
}
