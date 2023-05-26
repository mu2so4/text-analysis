package ru.nsu.ccfit.muratov.tooi.text.text.downloader;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import ru.nsu.ccfit.muratov.tooi.text.parser.Sentence;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class ProseParser {
    private final int proseId;
    private final int chapterCount;

    private int currentChapterId = 1;

    public ProseParser(int proseId, int chapterCount) {
        this.proseId = proseId;
        this.chapterCount = chapterCount;
    }

    public boolean hasNext() {
        return currentChapterId <= chapterCount;
    }

    public List<Sentence> nextChapter() throws IOException {
        String url = String.format("https://ilibrary.ru/text/%d/p.%s/index.html", proseId, currentChapterId);
        String response = HttpGetClient.getPage(url);

        List<Sentence> sentences = new ArrayList<>();
        Document doc = Jsoup.parse(response);
        Element chapter = doc.getElementById("text");
        for(var paragraph: chapter.select("span")) {
            sentences.add(new Sentence(paragraph.text()));
        }
        currentChapterId++;
        return sentences;
    }
}
