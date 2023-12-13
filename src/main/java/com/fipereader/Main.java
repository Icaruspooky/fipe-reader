package com.fipereader;

import java.io.IOException;

import com.fipereader.filereader.BatchPlateReader;
import com.opencsv.exceptions.CsvException;

public class Main {
    public static void main(String[] args) {
        try {
            BatchPlateReader.readFile("src/main/resources/");
        } catch (IOException | CsvException | InterruptedException e) {
            throw new RuntimeException(e);
        }
    }
}