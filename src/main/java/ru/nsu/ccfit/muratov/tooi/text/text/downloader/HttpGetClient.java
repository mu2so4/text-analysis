package ru.nsu.ccfit.muratov.tooi.text.text.downloader;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

public class HttpGetClient {
    private HttpGetClient() {}

    public static String getPage(String textUrl) throws IOException {
        StringBuilder result = new StringBuilder();
        URL url = new URL(textUrl);
        HttpURLConnection conn = (HttpURLConnection) url.openConnection();
        conn.setRequestMethod("GET");
        try (BufferedReader reader = new BufferedReader(
                new InputStreamReader(conn.getInputStream(), "windows-1251"))) {
            for (String line; (line = reader.readLine()) != null;) {
                result.append(line);
            }
        }
        return result.toString();
    }
}
