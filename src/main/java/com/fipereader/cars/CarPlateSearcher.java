package com.fipereader.cars;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URL;

import javax.net.ssl.HttpsURLConnection;

import org.json.JSONObject;

import com.sun.jdi.request.InvalidRequestStateException;

public class CarPlateSearcher {
    private CarPlateSearcher() {}

    public static JSONObject getCarDetailsByPlate(String plate) throws IOException {
        String response = getFromApi("https://api.brabocar.com.br/api/Vehicle/" + plate);
        return new JSONObject(response).getJSONObject("data");
    }

    private static String getFromApi(String url) throws IOException {
        HttpsURLConnection urlConnection = (HttpsURLConnection) new URL(url).openConnection();

        urlConnection.setRequestMethod("GET");
        urlConnection.setReadTimeout(50000);
        urlConnection.setConnectTimeout(50000);

        StringBuilder result = new StringBuilder();

        try (InputStream is = urlConnection.getInputStream();
        BufferedReader br = new BufferedReader(new InputStreamReader(is))) {
            String line;

            while ((line = br.readLine()) != null) {
                result.append(line);
            }
        } catch (Exception e) {
            if (e.getMessage().contains("code: 400")) {
                throw new InvalidRequestStateException("Error 400");
            }
        }

        return result.toString();
    }
}
