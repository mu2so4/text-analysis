package ru.nsu.ccfit.muratov.tooi.text.text.downloader;

import java.io.BufferedReader;
import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.io.InputStreamReader;

public class Downloader {
    private final int bookNumber;

    public Downloader(int bookNumber) {
        this.bookNumber = bookNumber;
    }

    public String getPage() throws IOException {
        StringBuilder result = new StringBuilder();
        URL url = new URL(String.format("https://ilibrary.ru/text/%d/index.html", bookNumber));
        HttpURLConnection conn = (HttpURLConnection) url.openConnection();
        conn.setRequestMethod("GET");
        try (BufferedReader reader = new BufferedReader(
                new InputStreamReader(conn.getInputStream()))) {
            for (String line; (line = reader.readLine()) != null; ) {
                result.append(line);
            }
        }
        return result.toString();
    }
}
