package ru.nsu.ccfit.muratov.tooi.text.prose.parsers;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class ProseTOCParser {
    private final int bookNumber;

    public ProseTOCParser(int bookNumber) {
        this.bookNumber = bookNumber;
    }

    public ProseParser getPage() throws IOException {
        String response = HttpGetClient.getPage(
                String.format("https://ilibrary.ru/text/%d/index.html", bookNumber));

        Document doc = Jsoup.parse(response);
        Elements links = doc.select("a");

        String regex = String.format("/text/%d/p\\.\\d+/index\\.html", bookNumber);
        Pattern pattern = Pattern.compile(regex);
        String maxUrl = "";
        for(var element: links) {
            String href = element.attr("href");
            Matcher matcher = pattern.matcher(href);
            if(matcher.matches()) {
                maxUrl = href;
            }
        }

        maxUrl = maxUrl.replace(Integer.toString(bookNumber), "");
        maxUrl = maxUrl.replaceAll("\\D", "");
        return new ProseParser(bookNumber, Integer.parseInt(maxUrl));
    }


}
