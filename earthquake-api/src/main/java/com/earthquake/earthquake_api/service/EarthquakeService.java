package com.earthquake.earthquake_api.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.earthquake.earthquake_api.model.Earthquake;
import com.earthquake.earthquake_api.repository.EarthquakeRepository;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

@Service
public class EarthquakeService {
    @Autowired
    private EarthquakeRepository earthquakeRepository;

    // Create a new earthquake record
    public Earthquake createEarthquake(String title, Double magnitude, String datetimeStr,
            Double latitude, Double longitude) {

        // Convert datetime String to a DateTime
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm");
        LocalDateTime datetime = LocalDateTime.parse(datetimeStr, formatter);
        
        Earthquake earthquake = new Earthquake(title, magnitude, datetime, latitude, longitude);
        // TODO: add the remaining attributes that can be Null (similar to CsvLoaderService):
        // cdi, mmi, alert, tsunami, sig, net, nst, dmin, gap, mag_type, depth, location, continent, country

        if (earthquake.getId() != null) {
            throw new IllegalArgumentException("Cannot create earthquake with existing ID");
        }

        return earthquakeRepository.save(earthquake);
    }

    // Bulk save earthquakes (for CSV import)
    public List<Earthquake> saveAllEarthquakes(List<Earthquake> earthquakes) {
        return earthquakeRepository.saveAll(earthquakes);
    }

    // Get all earthquakes
    public List<Earthquake> getAllEarthquakes() {
        return earthquakeRepository.findAll();
    }

    // Get earthquakes with pagination
    public Page<Earthquake> getAllEarthquakesPageable(Pageable pageable) {
        return earthquakeRepository.findAll(pageable);
    }

    // Get total number of earthquakes in DB
    public long getTotalEarthquakeCount() {
        return earthquakeRepository.count();
    }
}
