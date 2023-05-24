package ru.nsu.ccfit.muratov.tooi.text.parser;

import com.github.demidko.aot.WordformMeaning;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class Sentence {
    private final List<WordformMeaning> words = new ArrayList<>();

    public Sentence(String rawText) {
        rawText = rawText.replaceAll("[^а-яА-Я\\s]", "");
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

    public static void main(String[] args) {
        List<Sentence> sentences = new ArrayList<>();
        try(SentenceParser parser = new SentenceParser(args[0])) {
            for(String sentence = parser.nextSentence(); sentence != null; sentence = parser.nextSentence()) {
                sentences.add(new Sentence(sentence));
            }
        }
        catch (IOException e) {
            throw new RuntimeException(e);
        }

        for(Sentence sentence: sentences) {
            for(WordformMeaning meaning: sentence.getWords()) {
                System.out.printf("%s ", meaning.getLemma());
            }
            System.out.println();
        }
    }
}
