package com.earthquake.earthquake_api.model;

import jakarta.persistence.Entity;

import java.time.LocalDateTime;

import jakarta.persistence.Column;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.DecimalMax;
import jakarta.validation.constraints.DecimalMin;

@Entity
public class Earthquake {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @NotNull
    @Column(name="title", length=500)
    private String title;

    @NotNull
    @DecimalMin(value = "0.0", message = "Magnitude must be positive")
    @Column(name = "magnitude")
    private Double magnitude;

    @NotNull
    @Column(name = "date_time")
    private LocalDateTime dateTime;
    
    @Column(name = "cdi")
    private Integer cdi;
    
    @Column(name = "mmi")
    private Integer mmi;
    
    @Column(name = "alert", length = 20)
    private String alert;
    
    @Column(name = "tsunami")
    private Boolean tsunami; // in source data, it is "1" for events in oceanic regions and "0" otherwise instead of "True" and "False"
    
    @Column(name = "sig")
    private Integer sig;
    
    @Column(name = "net", length = 10)
    private String net;
    
    @Column(name = "nst")
    private Integer nst;
    
    @Column(name = "dmin")
    private Double dmin;
    
    @Column(name = "gap")
    private Double gap;
    
    @Column(name = "mag_type", length = 10)
    private String magType;
    
    @Column(name = "depth")
    private Double depth;

    @NotNull
    @DecimalMin(value = "-90.0", message = "Latitude must be between -90 and 90")
    @DecimalMax(value = "90.0", message = "Latitude must be between -90 and 90")
    @Column(name = "latitude")
    private Double latitude;
    
    @NotNull
    @DecimalMin(value = "-180.0", message = "Longitude must be between -180 and 180")
    @DecimalMax(value = "180.0", message = "Longitude must be between -180 and 180")
    @Column(name = "longitude")
    private Double longitude;
    
    @Column(name = "location", length = 500)
    private String location;
    
    @Column(name = "continent", length = 100)
    private String continent;
    
    @Column(name = "country", length = 100)
    private String country;

    // Constructors
    public Earthquake() {}
    
    public Earthquake(String title, Double magnitude, LocalDateTime datetime, Double latitude, Double longitude) {
        this.title = title;
        this.magnitude = magnitude;
        this.dateTime = datetime;
        this.latitude = latitude;
        this.longitude = longitude;
    }

    // Getters and Setters
    public Long getId() {
        return id;
    }
    
    public String getTitle() {
        return title;
    }
    
    public void setTitle(String title) {
        this.title = title;
    }
    
    public Double getMagnitude() {
        return magnitude;
    }
    
    public void setMagnitude(Double magnitude) {
        this.magnitude = magnitude;
    }

    public LocalDateTime getDateTime() {
        return dateTime;
    }
    
    public void setDateTime(LocalDateTime dateTime) {
        this.dateTime = dateTime;
    }

    public Integer getCdi() {
        return cdi;
    }
    
    public void setCdi(Integer cdi) {
        this.cdi = cdi;
    }
    
    public Integer getMmi() {
        return mmi;
    }
    
    public void setMmi(Integer mmi) {
        this.mmi = mmi;
    }
    
    public String getAlert() {
        return alert;
    }
    
    public void setAlert(String alert) {
        this.alert = alert;
    }
    
    public Boolean getTsunami() {
        return tsunami;
    }
    
    public void setTsunami(Boolean tsunami) {
        this.tsunami = tsunami;
    }
    
    public Integer getSig() {
        return sig;
    }
    
    public void setSig(Integer sig) {
        this.sig = sig;
    }
    
    public String getNet() {
        return net;
    }
    
    public void setNet(String net) {
        this.net = net;
    }
    
    public Integer getNst() {
        return nst;
    }
    
    public void setNst(Integer nst) {
        this.nst = nst;
    }
    
    public Double getDmin() {
        return dmin;
    }
    
    public void setDmin(Double dmin) {
        this.dmin = dmin;
    }
    
    public Double getGap() {
        return gap;
    }
    
    public void setGap(Double gap) {
        this.gap = gap;
    }
    
    public String getMagType() {
        return magType;
    }
    
    public void setMagType(String magType) {
        this.magType = magType;
    }
    
    public Double getDepth() {
        return depth;
    }
    
    public void setDepth(Double depth) {
        this.depth = depth;
    }
    
    public Double getLatitude() {
        return latitude;
    }
    
    public void setLatitude(Double latitude) {
        this.latitude = latitude;
    }
    
    public Double getLongitude() {
        return longitude;
    }
    
    public void setLongitude(Double longitude) {
        this.longitude = longitude;
    }
    
    public String getLocation() {
        return location;
    }
    
    public void setLocation(String location) {
        this.location = location;
    }
    
    public String getContinent() {
        return continent;
    }
    
    public void setContinent(String continent) {
        this.continent = continent;
    }
    
    public String getCountry() {
        return country;
    }
    
    public void setCountry(String country) {
        this.country = country;
    }
}
