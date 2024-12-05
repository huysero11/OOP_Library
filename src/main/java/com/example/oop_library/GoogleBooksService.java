package com.example.oop_library;

import com.google.gson.Gson;

import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;

public class GoogleBooksService {
    private static final String API_URL = "https://www.googleapis.com/books/v1/volumes?q=";
    private static final String API_KEY = "AIzaSyAu5ui7iB9i5xJay_7RAvIzeCnnL2UYFwg";

    public GBSDto searchGBS(String query) {
        try {
            String encodedQuery = URLEncoder.encode(query, StandardCharsets.UTF_8.toString());
            @SuppressWarnings("deprecation")
            URL url = new URL(API_URL + encodedQuery + "&key=" + API_KEY);
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod("GET");
            connection.setRequestProperty("Accept", "application/json");

            if (connection.getResponseCode() != 200) {
                throw new RuntimeException("Failed : HTTP error code : " + connection.getResponseCode());
            }

            InputStreamReader reader = new InputStreamReader(connection.getInputStream());

            // StringBuilder response = new StringBuilder();
            // String output;
            // try (BufferedReader br = new BufferedReader(new InputStreamReader(connection.getInputStream()))) {
            //     while ((output = br.readLine()) != null) {
            //         response.append(output);
            //     }
            // } catch(Exception e) {
            //     e.printStackTrace();
            // }
            Gson gson = new Gson();
            GBSDto g = gson.fromJson(reader, GBSDto.class);
            connection.disconnect();

            return g;

        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
}