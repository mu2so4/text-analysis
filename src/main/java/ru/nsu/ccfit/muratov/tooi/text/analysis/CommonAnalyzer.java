package ru.nsu.ccfit.muratov.tooi.text.analysis;

import ru.nsu.ccfit.muratov.tooi.text.prose.Prose;
import ru.nsu.ccfit.muratov.tooi.text.prose.Sentence;
import ru.nsu.ccfit.muratov.tooi.text.prose.parsers.ProseParser;
import ru.nsu.ccfit.muratov.tooi.text.prose.parsers.ProseTOCParser;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.nio.charset.StandardCharsets;
import java.util.List;

public class CommonAnalyzer {
    private final Prose prose = new Prose();
    private final int proseId;
    private final int topLimit;
    private final String directory;

    public CommonAnalyzer(int proseId, int topLimit, String directory) throws IOException {
        this.proseId = proseId;
        this.topLimit = topLimit;
        this.directory = directory;
        ProseTOCParser downloader = new ProseTOCParser(proseId);
        ProseParser proseParser = downloader.getPage();
        while(proseParser.hasNext()) {
            List<Sentence> sentences = proseParser.nextChapter();
            for(var sentence : sentences) {
                prose.addSentence(sentence);
            }
        }
    }

    public void analyzeProse() throws IOException {
        var frequencies = prose.getMostFrequentedWords();
        int wordCount = prose.getWordCount();
        int uniqueWordCount = frequencies.size();
        double nounProportion = (double) prose.getNounCount() / wordCount * 100.;
        double verbProportion = (double) prose.getVerbCount() / wordCount * 100.;
        double adjectiveProportion = (double) prose.getAdjectiveCount() / wordCount * 100.;
        double functorProportion = (double) prose.getFunctionWordsCount() / wordCount * 100.;
        double vocabularyCoefficient = getReversedRegressionAnalysis();

        String commonReportFile = String.format("%s/common.txt", directory);
        try(OutputStream stream = new FileOutputStream(commonReportFile, true)) {
            stream.write(
                    String.format("%d\t%d\t%d\t%.2f\t%.2f\t%.2f\t%.2f\t%.3f%n", proseId,
                            wordCount, uniqueWordCount, nounProportion, verbProportion, adjectiveProportion, functorProportion,
                            vocabularyCoefficient).getBytes(StandardCharsets.UTF_8)
            );
        }
    }

    public double getReversedRegressionAnalysis() {
        int start = 4;
        ReversedRegressionAnalysis analysis = new ReversedRegressionAnalysis();
        for (int index = start; index < topLimit; index++) {
            var entry = prose.getMostFrequentedWords().get(index);
            analysis.add(index + 1., entry.getValue());
        }
        double coefficient = analysis.getRegression();
        return prose.getWordCount() / coefficient;
    }

    public void getSentenceDistribution() throws IOException {
        int sentenceCount = prose.getSentenceCount();

        String sentenceDistPath = String.format("%s/sentence/%d.txt", directory, proseId);
        try(OutputStream stream = new FileOutputStream(sentenceDistPath)) {
            stream.write(String.format("Sentence count: %d%n", sentenceCount).getBytes(StandardCharsets.UTF_8));
            var sentenceDistribution = prose.getSentenceLengthFrequencies();
            for (var entry: sentenceDistribution.entrySet()) {
                stream.write(String.format("%d\t%.5f%n", entry.getElement(),
                        entry.getCount() / (double) sentenceCount).getBytes(StandardCharsets.UTF_8));
            }
        }
    }
    public void getZScores() throws IOException {
        int sum = 0;
        var frequencies = prose.getMostFrequentedWords();
        for(int index = 0; index < topLimit; index++) {
            sum += frequencies.get(index).getValue();
        }
        double mean = (double) sum / topLimit;
        double variance = 0;
        for(int index = 0; index < topLimit; index++) {
            variance += Math.pow(frequencies.get(index).getValue() - mean, 2);
        }
        variance = Math.sqrt(variance / (topLimit - 1));
        String zScoreFilePath = String.format("%s/z-score/%d.txt", directory, proseId);

        try(OutputStream stream = new FileOutputStream(zScoreFilePath)) {
            stream.write(String.format("%d%n", topLimit).getBytes(StandardCharsets.UTF_8));
            for(int index = 0; index < topLimit; index++) {
                stream.write(String.format("%.5f%n", (frequencies.get(index).getValue() - mean) / variance)
                        .getBytes(StandardCharsets.UTF_8));
            }
        }
    }
}
