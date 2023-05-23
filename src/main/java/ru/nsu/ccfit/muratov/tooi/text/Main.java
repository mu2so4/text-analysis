package ru.nsu.ccfit.muratov.tooi.text;

import com.github.demidko.aot.WordformMeaning;

import java.util.List;

public class Main {
    public static void main(String[] args) {
        List<WordformMeaning> meanings = WordformMeaning.lookupForMeanings("одомашненных");


        System.out.println(meanings.size());
        /* 1 */

        System.out.println(meanings.get(0).getMorphology());
        /* [С, мр, им, мн] */

        System.out.println(meanings.get(0).getLemma());
        /* человек */

        for (WordformMeaning t : meanings.get(0).getTransformations()) {
            System.out.println(t.toString() + " " + t.getMorphology());
        }
    }
}
