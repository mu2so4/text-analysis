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

    private int nounCount;
    private int verbCount;
    private int adjectiveCount;
    private int functionWordsCount;

    private void addWord(WordformMeaning word) {
        String lemma = word.getLemma().toString();
        wordFrequencies.merge(lemma, 1, Integer::sum);
        switch(word.getPartOfSpeech()) {
            case Noun -> nounCount++;
            case Verb -> verbCount++;
            case Adjective -> adjectiveCount++;
            case Union, Particle, Pretext -> functionWordsCount++;
        }
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

    public int getNounCount() {
        return nounCount;
    }

    public int getVerbCount() {
        return verbCount;
    }

    public int getAdjectiveCount() {
        return adjectiveCount;
    }

    public int getFunctionWordsCount() {
        return functionWordsCount;
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
