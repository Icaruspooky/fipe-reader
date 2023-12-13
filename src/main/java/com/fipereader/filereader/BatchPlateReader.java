package com.fipereader.filereader;

import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;

import com.fipereader.cars.CarInformation;
import com.opencsv.CSVParser;
import com.opencsv.CSVParserBuilder;
import com.opencsv.CSVReader;
import com.opencsv.CSVReaderBuilder;
import com.opencsv.CSVWriter;
import com.opencsv.exceptions.CsvException;
import com.sun.jdi.request.InvalidRequestStateException;

public class BatchPlateReader {
    public static void readFile(String filePath) throws IOException, CsvException, InterruptedException {
        FileReader fileReader = new FileReader(filePath + "plates.csv");
        FileWriter successFileWriter = new FileWriter(filePath + "plate-year-model.csv");
        FileWriter failFileWriter = new FileWriter(filePath + "errors.csv");

        CSVParser csvParser = new CSVParserBuilder().withSeparator(';').build();
        CSVReader reader = new CSVReaderBuilder(fileReader).withCSVParser(csvParser).build();
        CSVWriter successWriter = new CSVWriter(successFileWriter);
        CSVWriter failWriter = new CSVWriter(failFileWriter);

        List<String[]> lines = reader.readAll();
        Iterator<String[]> linesIterator = lines.iterator();
        String[] line = linesIterator.next();
        String[] newLine = {line[0], "Year", "Model"};
        successWriter.writeNext(newLine);
        failWriter.writeNext(line);

        try {
            int totalPlates = lines.size() - 1;
            int platesRead = 1;
            while (linesIterator.hasNext()) {
                String plate = linesIterator.next()[0];

                try {
                    CarInformation carInformation = new CarInformation(plate);
                    String year = carInformation.getCarYear();
                    String model = carInformation.getCarModel();
                    successWriter.writeNext(new String[]{plate, year, model});
                    System.out.println("Plate " + plate + " " + platesRead + " of " + totalPlates);
                } catch (IOException e) {
                    throw new RuntimeException(e);
                } catch (InvalidRequestStateException e) {
                    failWriter.writeNext(new String[]{plate});
                }
                platesRead++;
            }
        } catch (RuntimeException e) {
            System.out.println(e.getMessage());
            System.out.println(Arrays.toString(e.getStackTrace()));
        } finally {
            reader.close();
            successWriter.close();
            failWriter.close();
        }
    }
}
