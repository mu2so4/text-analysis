package ru.nsu.ccfit.muratov.tooi.text.text.downloader;

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
}
