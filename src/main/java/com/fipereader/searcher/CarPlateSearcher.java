package com.fipereader.searcher;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;

import org.apache.commons.lang3.StringUtils;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

public class CarPlateSearcher {
    private final String plate;
    private final String carDetails;


    public CarPlateSearcher (String plate) throws IOException {
        this.plate = plate;
        this.carDetails = getCarDetailsByPlate(plate);
    }

    public String getPlate() {
        return plate;
    }

    public String getCarYear() throws IOException {
        return StringUtils.substringBetween(carDetails, "Ano: ", " ");
    }

    public String getCarModel() {
        return StringUtils.substringBetween(carDetails, "Modelo: ", " ");
    }

    private String getCarDetailsByPlate(String plate) throws IOException {
        String html = downloadWebPage("https://placafipe.com/placa/"+ plate);
        Document website = Jsoup.parse(html);
        return website.getElementsByClass("fipeTablePriceDetail").text();
    }

    private String downloadWebPage(String url) throws IOException {
        URLConnection urlConnection = new URL(url).openConnection();
        urlConnection.addRequestProperty("User-Agent", "Mozilla/5.0");
        urlConnection.setReadTimeout(50000);
        urlConnection.setConnectTimeout(50000);

        StringBuilder result = new StringBuilder();

        try (InputStream is = urlConnection.getInputStream();
        BufferedReader br = new BufferedReader(new InputStreamReader(is))) {
            String line;

            while ((line = br.readLine()) != null) {
                result.append(line);
            }
        }

        return result.toString();

    }
}
