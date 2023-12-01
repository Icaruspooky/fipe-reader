package com.fipereader;

import java.io.IOException;

import com.fipereader.filereader.BatchPlateReader;
import com.opencsv.exceptions.CsvValidationException;

public class Main {
    public static void main(String[] args) {
        try {
            BatchPlateReader.readFile("C:\\_dev\\repositories\\fipe-reader\\src\\main\\resources\\");
        } catch (IOException | CsvValidationException | InterruptedException e) {
            throw new RuntimeException(e);
        }
    }
}