package com.earthquake.earthquake_api.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.earthquake.earthquake_api.model.Earthquake;

public interface EarthquakeRepository extends JpaRepository<Earthquake, Long> {
}
