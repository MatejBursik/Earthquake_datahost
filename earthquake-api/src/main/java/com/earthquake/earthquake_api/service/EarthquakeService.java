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

    private static final DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm");

    // Create a new earthquake record
    public Earthquake createEarthquake(String title, Double magnitude, String datetimeStr, Double latitude, Double longitude,
            Integer cdi, Integer mmi, String alert, Boolean tsunami, Integer sig, String net, Integer nst, Double dmin, Double gap, String mag_type, Double depth, String location, String continent, String country) {
        
        // Convert datetime String to a DateTime
        LocalDateTime datetime = LocalDateTime.parse(datetimeStr, formatter);
        
        Earthquake earthquake = new Earthquake();

        try {
            // CSV columns:
            // title, magnitude, date_time, cdi, mmi, alert, tsunami, sig, net, nst, dmin, gap, magType, depth, latitude, longitude, location, continent, country
            // NotNull columns:
            // title, magnitude, date_time, latitude, longitude
            
            earthquake.setTitle(title);
            earthquake.setMagnitude(magnitude);
            earthquake.setDateTime(datetime);
            earthquake.setCdi(cdi);
            earthquake.setMmi(mmi);
            earthquake.setAlert(alert);
            earthquake.setTsunami(tsunami);
            earthquake.setSig(sig);
            earthquake.setNet(net);
            earthquake.setNst(nst);
            earthquake.setDmin(dmin);
            earthquake.setGap(gap);
            earthquake.setMagType(mag_type);
            earthquake.setDepth(depth);
            earthquake.setLatitude(latitude);
            earthquake.setLongitude(longitude);
            earthquake.setLocation(location);
            earthquake.setContinent(continent);
            earthquake.setCountry(country);
            
        } catch (Exception e) {
            System.err.println("Error parsing earthquake record: " + e.getMessage());
            return null;
        }

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
