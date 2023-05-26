package ru.nsu.ccfit.muratov.tooi.text.parser;

import com.github.demidko.aot.WordformMeaning;

import java.util.ArrayList;
import java.util.List;

public class Sentence {
    private final List<WordformMeaning> words = new ArrayList<>();

    public Sentence(String rawText) {
        rawText = rawText.replaceAll("[^а-яА-Я\\s-]", "");
        String[] rawWords = rawText.split("\\s");
        for(String word: rawWords) {
            if(word.isEmpty()) {
                continue;
            }
            List<WordformMeaning> meanings = WordformMeaning.lookupForMeanings(word);
            if(!meanings.isEmpty()) {
                words.add(meanings.get(0));
            }
        }
    }

    public List<WordformMeaning> getWords() {
        return words;
    }
}
