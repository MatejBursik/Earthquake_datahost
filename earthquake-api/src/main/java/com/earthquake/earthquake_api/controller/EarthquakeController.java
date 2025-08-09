package com.earthquake.earthquake_api.controller;

import org.springframework.beans.factory.annotation.Autowired;
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
public class EarthquakeController {
    @Autowired
    private EarthquakeService earthquakeService;

    @PostMapping(path="/addNewEarthquake")
    public @ResponseBody String addNewEarthquake(
            @RequestParam String title, @RequestParam Double magnitude, @RequestParam String datetimeStr,
            @RequestParam Double latitude, @RequestParam Double longitude) {

        // add the remaining attributes that can be Null, so @RequestParam needs a Default:
        // cdi, mmi, alert, tsunami, sig, net, nst, dmin, gap, mag_type, depth, location, continent, country
        
        earthquakeService.createEarthquake(title, magnitude, datetimeStr, latitude, longitude);

        return "Saved";
    }

    @GetMapping(path="/getAllEarthquakes")
    public @ResponseBody Iterable<Earthquake> getAllPeople() {
        return earthquakeService.getAllEarthquakes();
    }

    private static final String template = "Hello, %s!";
    private long counter = 0;
    
    @GetMapping(path="/greeting")
    public Greeting greeting(@RequestParam(defaultValue = "World") String name) {
        counter += 1;
        return new Greeting(counter, String.format(template, name));
    }
}
