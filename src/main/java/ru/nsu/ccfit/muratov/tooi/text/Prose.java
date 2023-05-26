package ru.nsu.ccfit.muratov.tooi.text;

import com.github.demidko.aot.WordformMeaning;
import ru.nsu.ccfit.muratov.tooi.text.parser.Sentence;

import java.util.*;

public class Prose {
    private final List<Sentence> sentences = new ArrayList<>();
    private final Map<String, Integer> frequencies = new HashMap<>();
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
        wordCount += sentence.getWords().size();
    }

    public List<Sentence> getSentences() {
        return sentences;
    }

    public Map<String, Integer> getFrequencies() {
        return frequencies;
    }

    public int getWordCount() {
        return wordCount;
    }

    public List<Map.Entry<String, Integer>> getMostFrequentedWords() {
        var list = new ArrayList<>(frequencies.entrySet());
        list.sort((a, b) -> (b.getValue() - a.getValue()));
        return list;
    }
}
