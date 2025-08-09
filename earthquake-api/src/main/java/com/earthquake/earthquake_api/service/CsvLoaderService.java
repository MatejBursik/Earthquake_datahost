package com.earthquake.earthquake_api.service;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.opencsv.CSVReader;
import com.opencsv.exceptions.CsvException;

import com.earthquake.earthquake_api.model.Earthquake;

@Service
@Transactional
public class CsvLoaderService {

    @Autowired
    private EarthquakeService earthquakeService;

    private static final DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm");

    private String parseString(String value) {
        if (value == null || value.trim().isEmpty()) {
            return null;
        }

        value = value.trim();
        return value;
    }

    private Double parseDouble(String value) {
        String cleanValue = parseString(value);
        if (cleanValue == null) {
            return null;
        }

        try {
            return Double.parseDouble(cleanValue);
        } catch (NumberFormatException e) {
            return null;
        }
    }

    private Integer parseInteger(String value) {
        String cleanValue = parseString(value);
        if (cleanValue == null) {
            return null;
        }

        try {
            return Integer.parseInt(cleanValue);
        } catch (NumberFormatException e) {
            return null;
        }
    }

    private Boolean parseBoolean(String value) {
        String cleanValue = parseString(value);
        if (cleanValue == null) {
            return null;
        }

        switch (cleanValue.trim().toLowerCase()) {
            case "true":
            case "1":
                return true;

            case "false":
            case "0":
                return false;

            default:
                return null;
        }
    }

    private LocalDateTime parseDateTime(String value) {
        String cleanValue = parseString(value);
        if (cleanValue == null) {
            return null;
        }

        try {
            return LocalDateTime.parse(cleanValue, formatter);
        } catch (DateTimeParseException e) {
            return null;
        }
    }

    // Parse a line from CSV into a Earthquake object
    private Earthquake parseEarthquakeFromRecord(String[] record) {
        if (record.length < 19) {
            throw new IllegalArgumentException("Record does not have enough columns");
        }
        
        Earthquake earthquake = new Earthquake();
        
        try {
            // CSV columns:
            // title, magnitude, date_time, cdi, mmi, alert, tsunami, sig, net, nst, dmin, gap, magType, depth, latitude, longitude, location, continent, country
            // NotNull columns:
            // title, magnitude, date_time, latitude, longitude
            
            earthquake.setTitle(parseString(record[0]));
            earthquake.setMagnitude(parseDouble(record[1]));
            earthquake.setDateTime(parseDateTime(record[2]));
            earthquake.setCdi(parseInteger(record[3]));
            earthquake.setMmi(parseInteger(record[4]));
            earthquake.setAlert(parseString(record[5]));
            earthquake.setTsunami(parseBoolean(record[6]));
            earthquake.setSig(parseInteger(record[7]));
            earthquake.setNet(parseString(record[8]));
            earthquake.setNst(parseInteger(record[9]));
            earthquake.setDmin(parseDouble(record[10]));
            earthquake.setGap(parseDouble(record[11]));
            earthquake.setMagType(parseString(record[12]));
            earthquake.setDepth(parseDouble(record[13]));
            earthquake.setLatitude(parseDouble(record[14]));
            earthquake.setLongitude(parseDouble(record[15]));
            earthquake.setLocation(parseString(record[16]));
            earthquake.setContinent(parseString(record[17]));
            earthquake.setCountry(parseString(record[18]));
            
            return earthquake;
            
        } catch (Exception e) {
            System.err.println("Error parsing earthquake record: " + e.getMessage());
            return null;
        }
    }
    
    // Read a CSV stream row by row
    public List<Earthquake> loadEarthquakesFromCsvStream(InputStream csvStream) throws IOException, CsvException {
        List<Earthquake> earthquakes = new ArrayList<>();
        
        try (InputStreamReader inputStreamReader = new InputStreamReader(csvStream);
             CSVReader csvReader = new CSVReader(inputStreamReader)) {
            
            List<String[]> records = csvReader.readAll();
            
            // i = 1 to skip header row
            for (int i = 1; i < records.size(); i++) {
                String[] record = records.get(i);
                try {
                    Earthquake earthquake = parseEarthquakeFromRecord(record);
                    if (earthquake != null) {
                        earthquakes.add(earthquake);
                    }
                } catch (Exception e) {
                    System.err.println("Error parsing record " + i + ": " + e.getMessage());
                    // Continue processing other records
                }
            }
        }
        
        return earthquakes;
    }

    @Transactional
    public int importCsvStream(InputStream csvStream) {
        try {
            List<Earthquake> earthquakes = loadEarthquakesFromCsvStream(csvStream);
            List<Earthquake> savedEarthquakes = earthquakeService.saveAllEarthquakes(earthquakes);
            return savedEarthquakes.size();
        } catch (Exception e) {
            throw new RuntimeException("Failed to import CSV stream", e);
        }
    }
}
