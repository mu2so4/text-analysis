package ru.nsu.ccfit.muratov.tooi.text.prose;

import com.github.demidko.aot.WordformMeaning;
import com.google.common.collect.HashMultiset;
import com.google.common.collect.Multiset;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Prose {
    private final List<Sentence> sentences = new ArrayList<>();
    private final Map<String, Integer> wordFrequencies = new HashMap<>();
    private final List<Integer> sentenceLengthFrequencies = new ArrayList<>();
    private int wordCount;

    private void addWord(WordformMeaning word) {
        String lemma = word.getLemma().toString();
        frequencies.merge(lemma, 1, Integer::sum);
    }

    public void addSentence(Sentence sentence) {
        for(var word: sentence.getWords()) {
            addWord(word);
        }
        sentences.add(sentence);
        int sentenceSize = sentence.getWords().size();
        wordCount += sentenceSize;
        if(sentenceSize != 0) {
            sentenceLengthFrequencies.add(sentenceSize);
        }
    }

    public int getWordCount() {
        return wordCount;
    }

    public List<Map.Entry<String, Integer>> getMostFrequentedWords() {
        var list = new ArrayList<>(wordFrequencies.entrySet());
        list.sort((a, b) -> (b.getValue() - a.getValue()));
        return list;
    }

    public Multiset<Integer> getSentenceLengthFrequencies() {
        return HashMultiset.create(sentenceLengthFrequencies);
    }
}
