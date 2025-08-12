package com.earthquake.earthquake_api.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.earthquake.earthquake_api.model.Greeting;
import com.earthquake.earthquake_api.model.Earthquake;
import com.earthquake.earthquake_api.service.EarthquakeService;

@RestController
@RequestMapping(path="/api")
@CrossOrigin(origins = "*")
public class EarthquakeController {
    @Autowired
    private EarthquakeService earthquakeService;

    @PostMapping(path="/addNewEarthquake")
    public @ResponseBody String addNewEarthquake(@RequestParam String title, @RequestParam Double magnitude, @RequestParam String datetimeStr,
        @RequestParam Double latitude, @RequestParam Double longitude,
        @RequestParam(required = false) Integer cdi, @RequestParam(required = false) Integer mmi, @RequestParam(required = false) String alert, @RequestParam(required = false) Boolean tsunami,
        @RequestParam(required = false) Integer sig, @RequestParam(required = false) String net, @RequestParam(required = false) Integer nst, @RequestParam(required = false) Double dmin,
        @RequestParam(required = false) Double gap, @RequestParam(required = false) String mag_type, @RequestParam(required = false) Double depth, @RequestParam(required = false) String location,
        @RequestParam(required = false) String continent, @RequestParam(required = false) String country) {
        
        earthquakeService.createEarthquake(title, magnitude, datetimeStr, latitude, longitude, cdi, mmi, alert, tsunami, sig, net, nst, dmin, gap, mag_type, depth, location, continent, country);

        return "Saved";
    }

    @GetMapping(path="/getAllEarthquakes")
    public @ResponseBody Iterable<Earthquake> getAllPeople() {
        return earthquakeService.getAllEarthquakes();
    }

    @GetMapping("/pageable")
    public Page<Earthquake> getAllEarthquakesPageable(@RequestParam(defaultValue = "0") int page, @RequestParam(defaultValue = "10") int size) {
        try {
            Pageable pageable = PageRequest.of(page, size);
            Page<Earthquake> earthquakes = earthquakeService.getAllEarthquakesPageable(pageable);
            return earthquakes;
        } catch (Exception e) {
            return null;
        }
    }

    private static final String template = "Hello, %s!";
    private long counter = 0;
    
    @GetMapping(path="/greeting")
    public Greeting greeting(@RequestParam(defaultValue = "World") String name) {
        counter += 1;
        return new Greeting(counter, String.format(template, name));
    }
}
