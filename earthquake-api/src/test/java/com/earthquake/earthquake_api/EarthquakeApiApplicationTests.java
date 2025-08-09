package com.earthquake.earthquake_api;

import static org.mockito.Mockito.*;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;

import com.earthquake.earthquake_api.model.Earthquake;
import com.earthquake.earthquake_api.repository.EarthquakeRepository;
import com.earthquake.earthquake_api.service.EarthquakeService;

import org.mockito.*;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class EarthquakeApiApplicationTests {

	@InjectMocks
	private EarthquakeService earthquakeService;

	@Mock
	private EarthquakeRepository earthquakeRepository;

	private static final DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm");

	@Test
	void testCreationOfEarthquakeWithOnlyNotNullValues() {
		//title, magnitude, date_time, latitude, longitude
		String title = "asdf";
		Double magnitude = 1.0;
		LocalDateTime datetime = LocalDateTime.parse("01-01-2025 00:00", formatter);
		Double latitude = 0.0;
		Double longitude = 0.0;
        
		Earthquake earthquake = new Earthquake();
		earthquake.setTitle(title);
		earthquake.setMagnitude(magnitude);
		earthquake.setDateTime(datetime);
		earthquake.setLatitude(latitude);
		earthquake.setLongitude(longitude);

		earthquakeService.saveEarthquake(earthquake);

		verify(earthquakeRepository, times(1)).save(any(Earthquake.class));
	}

}
