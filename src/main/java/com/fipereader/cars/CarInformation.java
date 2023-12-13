package com.fipereader.cars;

import java.io.IOException;

import org.json.JSONObject;

public class CarInformation {
    private final String plate;
    private final String year;
    private final String model;

    public CarInformation(String plate) throws IOException {
        JSONObject carDetails = CarPlateSearcher.getCarDetailsByPlate(plate);

        this.plate = plate;
        this.year = carDetails.getString("anoFabricacao");
        this.model = carDetails.getString("modelo");
    }

    public String getPlate() {
        return plate;
    }

    public String getCarYear() {
        return this.year;
    }

    public String getCarModel() {
        return this.model;
    }

}
