package ru.nsu.ccfit.muratov.tooi.text.text.downloader;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class ProseTOCParser {
    private final int bookNumber;

    public ProseTOCParser(int bookNumber) {
        this.bookNumber = bookNumber;
    }

    public ProseParser getPage() throws IOException {
        StringBuilder result = new StringBuilder();
        URL url = new URL(String.format("https://ilibrary.ru/text/%d/index.html", bookNumber));
        HttpURLConnection conn = (HttpURLConnection) url.openConnection();
        conn.setRequestMethod("GET");
        try (BufferedReader reader = new BufferedReader(
                new InputStreamReader(conn.getInputStream()))) {
            for (String line; (line = reader.readLine()) != null;) {
                result.append(line);
            }
        }
        String response =  result.toString();

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
