package com.fipereader.filereader;

import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Arrays;
import java.util.concurrent.TimeUnit;

import com.fipereader.searcher.CarPlateSearcher;
import com.opencsv.CSVParser;
import com.opencsv.CSVParserBuilder;
import com.opencsv.CSVReader;
import com.opencsv.CSVReaderBuilder;
import com.opencsv.CSVWriter;
import com.opencsv.exceptions.CsvValidationException;

public class BatchPlateReader {
    public static void readFile(String filePath) throws IOException, CsvValidationException, InterruptedException {
        FileReader fileReader = new FileReader(filePath + "placas.csv");
        FileWriter fileWriter = new FileWriter(filePath + "placas-ano-modelo.csv");

        CSVParser csvParser = new CSVParserBuilder().withSeparator(';').build();
        CSVReader reader = new CSVReaderBuilder(fileReader).withCSVParser(csvParser).build();
        CSVWriter writer = new CSVWriter(fileWriter);

        String[] line = reader.readNext();
        String[] newLine = {line[0], "Ano", "Modelo"};
        writer.writeNext(newLine);

        try {
            while ((line = reader.readNext()) != null) {
                String plate = line[0];
                CarPlateSearcher carPlateSearcher = new CarPlateSearcher(plate);
                String year = carPlateSearcher.getCarYear();
                String model = carPlateSearcher.getCarModel();
                writer.writeNext(new String[] {plate, year, model});

                System.out.println(plate);
                TimeUnit.SECONDS.sleep(14);
            }
        } catch (IOException | CsvValidationException | InterruptedException e) {
            System.out.println(e.getMessage());
            System.out.println(Arrays.toString(e.getStackTrace()));
        } finally {
            reader.close();
            writer.close();
        }
    }
}
