package com.example.todobulgaria.models.dto;

public class WeatherDto {

    private Double temperature;
    private Double minTemperature;
    private Double maxTemperature;
    private String weekDay;

    public WeatherDto() {
    }

    public WeatherDto(Double temperature, Double minTemperature, Double maxTemperature, String weekDay) {
        this.temperature = temperature;
        this.minTemperature = minTemperature;
        this.maxTemperature = maxTemperature;
        this.weekDay = weekDay;
    }


    public String getWeekDay() {
        return weekDay;
    }

    public void setWeekDay(String weekDay) {
        this.weekDay = weekDay;
    }

    public Double getTemperature() {
        return temperature;
    }

    public void setTemperature(Double temperature) {
        this.temperature = temperature;
    }

    public Double getMinTemperature() {
        return minTemperature;
    }

    public void setMinTemperature(Double minTemperature) {
        this.minTemperature = minTemperature;
    }

    public Double getMaxTemperature() {
        return maxTemperature;
    }

    public void setMaxTemperature(Double maxTemperature) {
        this.maxTemperature = maxTemperature;
    }
}
